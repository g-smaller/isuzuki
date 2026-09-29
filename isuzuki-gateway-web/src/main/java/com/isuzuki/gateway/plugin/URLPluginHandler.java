package com.isuzuki.gateway.plugin;

import com.isuzuki.gateway.Constants;
import io.vertx.ext.web.RoutingContext;

public class URLPluginHandler implements PriorityPluginHandler<RoutingContext> {
    @Override
    public int getOrder() {
        return PriorityPluginHandler.O_2;
    }

    @Override
    public void handle(RoutingContext event) {
        event.put(Constants.Http.URI, event.request().uri());
        event.put(Constants.Http.METHOD, event.request().method().name());
        event.next();
    }
}
