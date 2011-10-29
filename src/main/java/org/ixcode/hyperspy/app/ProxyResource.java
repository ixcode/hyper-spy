package org.ixcode.hyperspy.app;

import ixcode.platform.collection.Action;
import ixcode.platform.http.client.Http;
import ixcode.platform.http.protocol.request.Request;
import ixcode.platform.http.protocol.response.ResponseBuilder;
import ixcode.platform.http.representation.Representation;
import ixcode.platform.http.representation.TransformHypermediaToJson;
import ixcode.platform.http.server.resource.Resource;
import ixcode.platform.http.server.resource.ResourceMap;
import ixcode.platform.json.JsonObject;
import ixcode.platform.json.JsonPair;
import ixcode.platform.json.JsonParser;
import ixcode.platform.serialise.JsonSerialiser;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static ixcode.platform.collection.CollectionPrinter.printCollection;
import static ixcode.platform.http.protocol.UriFactory.uri;
import static ixcode.platform.http.representation.ExceptionHypermedia.exceptionHypermedia;

public class ProxyResource implements Resource {

    private static final Logger log = Logger.getLogger(ProxyResource.class);

    private final Http http = new Http();
    private JsonSerialiser serialiser = new JsonSerialiser(new TransformHypermediaToJson());
    private JsonParser jsonParser = new JsonParser();


    public void GET(Request request, ResponseBuilder respondWith, ResourceMap resourceMap) {
        URI uri = uri(request.parameters.getFirstValueOf("uri"));


        try {
            Representation representation = http.GET().from(uri);
            JsonObject jsonResult = jsonParser.parse(representation.<String>getEntity());

            String response = generateResponseFor(representation.httpHeaders, jsonResult);

            respondWith.status().ok()
                       .contentType().json()
                       .body(response);

        } catch (Exception e) {
            log.error("Could not execute proxy request", e);
            respondWith.status().serverError()
                       .contentType().json()
                       .body(serialiser.toJson(exceptionHypermedia(e).build()));
        }
    }

    private static String generateResponseFor(Map<String, List<String>> httpHeaders, JsonObject jsonObject) {
        StringBuilder sb = new StringBuilder()
                .append("<div class='http-headers'>");

        sb.append("<table>")
          .append("<thead><td>Name</td><td>Value</td></thead>")
          .append("<tbody>");

        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            sb.append("<tr><td>").append(entry.getKey()).append("</td>")
              .append("<td>").append(printCollection(entry.getValue())).append("</td></tr>");
        }

        sb.append("</tbody>")
          .append("</table>")
          .append("</div>");

        sb.append(printJsonObjectAsHtml(jsonObject));
        log.info(sb.toString());
        return sb.toString();
    }

    private static String printJsonObjectAsHtml(JsonObject jsonObject) {
        final StringBuilder sb = new StringBuilder();

        sb.append("<div class='json'>")
          .append("{")
          .append("<ul class='json-object'>");

        jsonObject.apply(new Action<JsonPair>() {
            @Override public void to(JsonPair item, Collection<JsonPair> tail) {
                sb.append("<li>")
                  .append("<span class='json-property'>")
                  .append(item.key).append("</span>").append(": ");

                appendJsonValue(sb, item.value);

                sb.append("</li>");
            }

        });

        sb.append("</ul>");
        sb.append("}");
        sb.append("</div>");

        return sb.toString();
    }

    private static void appendJsonValue(StringBuilder sb, Object value) {
        String type;
        String stringValue;

        if (value instanceof String) {
            type = "string";
            stringValue = (String)value;
            if (stringValue.startsWith("http://")) {
                 stringValue = parseLink(stringValue);
            } else {
                stringValue = (String)value;
            }
        } else if (value instanceof Number) {
            type = "number";
            stringValue = "" + value;
        } else {
            type = "string";
            stringValue = "Unkown type:" + value.getClass();
        }

        sb.append("<span class='").append(type).append("'>").append(stringValue).append("</span>");
    }

    private static String parseLink(String stringValue) {
        return new StringBuilder().append("<a href='").append(stringValue).append("'>").append(stringValue).append("</a>").toString();
    }

    private static void appendValue(String type, StringBuilder sb, Object value) {


    }

    private static boolean appendLink(StringBuilder sb, String value) {
        return false;
    }

}