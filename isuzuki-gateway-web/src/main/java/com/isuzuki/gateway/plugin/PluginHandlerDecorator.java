package com.isuzuki.gateway.plugin;

import io.vertx.ext.web.RoutingContext;

public class PluginHandlerDecorator implements Ordered {

    private PriorityPluginHandler<RoutingContext> handler;
    private int order;
    public PluginHandlerDecorator(PriorityPluginHandler<RoutingContext> handler, int order) {
        this.handler = handler;
        this.order = order;
    }

    public void handle(RoutingContext event, PluginHandlerChain chain) {
        handler.handle(event);
        chain.doChain(event);
        handler.afterHandler(event);
    }

    @Override
    public int getOrder() {
        return order;
    }
}
