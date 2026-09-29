package com.isuzuki.gateway.handle;

import com.isuzuki.gateway.plugin.Ordered;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class HandlerDecorator implements Ordered {

    private Handler<RoutingContext> handler;
    private int order;
    public HandlerDecorator(Handler<RoutingContext> handler, int order) {
        this.handler = handler;
        this.order = order;
    }

    public void handle(RoutingContext event, HandlerChain chain) {
        handler.handle(event);
        chain.doChain(event);
    }

    @Override
    public int getOrder() {
        return order;
    }
}
