package com.isuzuki.gateway.plugin;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;

public class PluginHandlerChains implements Handler<RoutingContext> {

    public static PluginHandlerChains create() {
        return new PluginHandlerChains();
    }

    public PluginHandlerChains add(PriorityPluginHandler<RoutingContext> handler) {
        add(new PluginHandlerDecorator(handler, handler.getOrder()));
        return this;
    }

    public PluginHandlerChains add(PluginHandlerDecorator handler) {
        handlers.add(handler);
        return this;
    }

    private List<PluginHandlerDecorator> handlers = new ArrayList<>();

    @Override
    public void handle(RoutingContext event) {
        new DefaultFilterChain(handlers).doChain(event);
        event.next();
    }

    private class DefaultFilterChain implements PluginHandlerChain {

        private List<PluginHandlerDecorator> handlers;
        private int index = 0;

        public DefaultFilterChain(List<PluginHandlerDecorator> filters) {
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
            PluginHandlerDecorator plugin = handlers.get(index++);
            plugin.handle(event, this);
        }
    }
}
