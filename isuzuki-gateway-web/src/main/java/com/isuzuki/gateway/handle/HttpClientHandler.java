package com.isuzuki.gateway.handle;

import io.vertx.ext.web.RoutingContext;

public class HttpClientHandler implements PriorityHandler {

    @Override
    public void handle(RoutingContext event) {

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
