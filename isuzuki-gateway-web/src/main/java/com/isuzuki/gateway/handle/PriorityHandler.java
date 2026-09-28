package com.isuzuki.gateway.handle;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface PriorityHandler extends Handler<RoutingContext>, Ordered {



}
