package com.isuzuki.gateway.plugin;

import io.vertx.ext.web.RoutingContext;

public interface PluginHandlerChain {

    void doChain(RoutingContext event);

}
