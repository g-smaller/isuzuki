package com.isuzuki.gateway.handle;

import com.isuzuki.gateway.Constants;
import io.vertx.ext.web.RoutingContext;

public class LoadBalanceHandler implements PriorityHandler {

    @Override
    public void handle(RoutingContext ctx) {

        ctx.put(Constants.Http.DOMAIN, "");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
