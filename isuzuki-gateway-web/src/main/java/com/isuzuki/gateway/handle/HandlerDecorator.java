package com.isuzuki.gateway.handle;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class HandlerDecorator {

    private Handler<RoutingContext> handler;
    public HandlerDecorator(Handler<RoutingContext> handler) {
        this.handler = handler;
    }

    public void handle(RoutingContext event, HandlerChain chain) {
        handler.handle(event);
        chain.doNext(event);
    }
}
