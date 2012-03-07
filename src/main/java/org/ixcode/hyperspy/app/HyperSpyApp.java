package org.ixcode.hyperspy.app;

import ixcode.platform.http.protocol.IanaContentType;
import ixcode.platform.http.server.HttpServer;
import ixcode.platform.http.server.RequestDispatcher;
import ixcode.platform.http.server.resource.RouteMap;

import static ixcode.platform.http.protocol.HttpMethod.GET;
import static ixcode.platform.http.server.RequestDispatcher.requestDispatcher;
import static ixcode.platform.http.server.resource.RouteMap.aResourceMapRootedAt;
import static ixcode.platform.logging.ConsoleLog4jLogging.initialiseLog4j;

public class HyperSpyApp {
    public static void main(String[] args) {
        initialiseLog4j();
        RequestDispatcher requestDispatcher = requestDispatcher("http://localhost:9393", withRouteMap(), IanaContentType.json);

        new HttpServer(HyperSpyApp.class.getSimpleName(), "localhost", 9393, requestDispatcher)
                .servingStaticContentFrom("./")
                .start();
    }

    public static RouteMap withRouteMap() {
        return aResourceMapRootedAt("http://localhost:9393")
                .thePath("/proxy").toA(new ProxyResource()).supporting(GET);
    }
}