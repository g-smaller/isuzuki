package com.isuzuki.gateway.plugin;

import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.RoutingContext;

public class HttpClientPluginHandler implements PriorityPluginHandler<RoutingContext> {

    private HttpClient httpClient;

    @Override
    public void handle(RoutingContext event) {

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
