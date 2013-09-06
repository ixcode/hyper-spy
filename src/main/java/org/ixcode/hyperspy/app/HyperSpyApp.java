package org.ixcode.hyperspy.app;

import ixcode.platform.http.server.HttpServer;
import ixcode.platform.http.server.RequestDispatcher;
import ixcode.platform.http.server.resource.RouteMap;

import static ixcode.platform.http.protocol.HttpMethod.GET;
import static ixcode.platform.http.protocol.IanaContentType.json;
import static ixcode.platform.http.server.RequestDispatcher.requestDispatcher;
import static ixcode.platform.http.server.resource.RouteMap.aResourceMapRootedAt;
import static ixcode.platform.logging.ConsoleLog4jLogging.initialiseLog4j;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class HyperSpyApp {
    public static void main(String[] args) {
        initialiseLog4j();

        boolean useDefult = args.length == 0;

        String hostname = (useDefult) ? "localhost" : args[0];
        int port = (useDefult) ? 9393 : parseInt(args[1]);
        String webRoot = (useDefult) ? "./" : args[2];

        RequestDispatcher requestDispatcher = requestDispatcher(
                format("http://%s:%d", hostname, port),
                withRouteMap(), json);

        new HttpServer(HyperSpyApp.class.getSimpleName(), hostname, port, requestDispatcher)
                .servingStaticContentFrom(webRoot)
                .start();
    }

    public static RouteMap withRouteMap() {
        return aResourceMapRootedAt("http://localhost:9393")
                .thePath("/proxy").toA(new ProxyResource()).supporting(GET);
    }
}