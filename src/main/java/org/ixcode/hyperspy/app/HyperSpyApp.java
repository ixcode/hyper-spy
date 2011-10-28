package org.ixcode.hyperspy.app;

import ixcode.platform.http.protocol.HttpMethod;
import ixcode.platform.http.server.HttpServer;
import ixcode.platform.http.server.RequestDispatcher;
import ixcode.platform.http.server.resource.ResourceMap;

import static ixcode.platform.http.protocol.HttpMethod.GET;
import static ixcode.platform.http.server.resource.ResourceMap.aResourceMap;
import static ixcode.platform.logging.ConsoleLog4jLogging.initialiseLog4j;

public class HyperSpyApp {
      public static void main(String[] args) {
        initialiseLog4j();
        RequestDispatcher requestDispatcher = new RequestDispatcher(withResourceMap());

        new HttpServer(HyperSpyApp.class.getSimpleName(), 9393, requestDispatcher)
                .servingStaticContentFrom("./")
                .start();
    }

    public static ResourceMap withResourceMap() {
        return aResourceMap()
                .mapping("/proxy").toA(new ProxyResource()).supporting(GET);
    }
}