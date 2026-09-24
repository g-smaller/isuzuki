package com.isuzuki.gateway.handle;

import io.vertx.ext.web.RoutingContext;

public interface HandlerChain {

    void doNext(RoutingContext event);

}
