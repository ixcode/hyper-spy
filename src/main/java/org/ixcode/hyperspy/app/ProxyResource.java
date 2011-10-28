package org.ixcode.hyperspy.app;

import ixcode.platform.http.client.Http;
import ixcode.platform.http.protocol.request.Request;
import ixcode.platform.http.protocol.response.ResponseBuilder;
import ixcode.platform.http.representation.Representation;
import ixcode.platform.http.representation.TransformHypermediaToJson;
import ixcode.platform.http.server.resource.Resource;
import ixcode.platform.http.server.resource.ResourceMap;
import ixcode.platform.serialise.JsonSerialiser;
import org.apache.log4j.Logger;

import java.net.URI;

import static ixcode.platform.http.protocol.UriFactory.uri;
import static ixcode.platform.http.representation.ExceptionHypermedia.exceptionHypermedia;

public class ProxyResource implements Resource {

    private static final Logger log = Logger.getLogger(ProxyResource.class);

    private final Http http = new Http();
    private JsonSerialiser serialiser = new JsonSerialiser(new TransformHypermediaToJson());


    public void GET(Request request, ResponseBuilder respondWith, ResourceMap resourceMap) {
        URI uri = uri(request.parameters.getFirstValueOf("uri"));


        try {
            Representation representation = http.GET().from(uri);

            respondWith.status().ok()
                       .contentType().json()
                       .body(representation.<String>getEntity());

        } catch (Exception e) {
            log.error("Could not execute proxy request", e);
            respondWith.status().serverError()
                       .contentType().json()
                       .body(serialiser.toJson(exceptionHypermedia(e).build()));
        }
    }

}