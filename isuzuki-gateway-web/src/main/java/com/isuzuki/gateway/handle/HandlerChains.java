package com.isuzuki.gateway.handle;

import com.isuzuki.gateway.plugin.Ordered;
import com.isuzuki.gateway.plugin.PriorityPluginHandler;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

public class HandlerChains implements Handler<RoutingContext> {

    public static HandlerChains create() {
        return new HandlerChains();
    }

    public HandlerChains add(Handler<RoutingContext> handler) {
        add(new HandlerDecorator(handler, Ordered.LOWEST_PRECEDENCE));
        return this;
    }

    public HandlerChains add(PriorityPluginHandler<RoutingContext> handler) {
        add(new HandlerDecorator(handler, handler.getOrder()));
        return this;
    }

    public HandlerChains add(HandlerDecorator handler) {
        handlers.add(handler);
        return this;
    }

    private List<HandlerDecorator> handlers = new ArrayList<>();

    @Override
    public void handle(RoutingContext event) {
        new DefaultFilterChain(handlers).doChain(event);
    }

    private class DefaultFilterChain implements HandlerChain {

        private List<HandlerDecorator> handlers;
        private int index = 0;

        public DefaultFilterChain(List<HandlerDecorator> filters) {
            this.handlers = filters;
        }

        @Override
        public void doChain(RoutingContext event) {
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
