package com.isuzuki.gateway.handle;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

public class HandlerChains implements Handler<RoutingContext> {

    private List<HandlerDecorator> handlers = new ArrayList<>();

    @Override
    public void handle(RoutingContext event) {
        new DefaultFilterChain(handlers).doNext(event);
    }

    private class DefaultFilterChain implements HandlerChain {

        private List<HandlerDecorator> handlers;
        private int index = 0;

        public DefaultFilterChain(List<HandlerDecorator> filters) {
            this.handlers = filters;
        }

        @Override
        public void doNext(RoutingContext event) {
            if (handlers == null || handlers.isEmpty()) {
                return;
            }
            if (index >= handlers.size()) {
                return;
            }
            HandlerDecorator filter = handlers.get(index++);
            filter.handle(event, this);
        }
    }
}
