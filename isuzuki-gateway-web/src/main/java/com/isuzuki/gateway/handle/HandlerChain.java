package com.isuzuki.gateway.handle;

import io.vertx.ext.web.RoutingContext;

public interface HandlerChain {

    void doChain(RoutingContext event);

}
