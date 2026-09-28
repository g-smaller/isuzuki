package com.isuzuki.gateway.handle;

import io.vertx.ext.web.RoutingContext;

public class ServiceDiscoveryHandler implements PriorityHandler {
    @Override
    public void handle(RoutingContext ctx) {

    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
