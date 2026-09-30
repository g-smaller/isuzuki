package com.isuzuki.gateway.plugin;

import com.isuzuki.gateway.Constants;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;

public class ServiceDiscoveryPluginHandler implements PriorityPluginHandler<RoutingContext>{
    @Override
    public int getOrder() {
        return  PriorityPluginHandler.O_3;
    }

    @Override
    public void handle(RoutingContext event) {
        event.put(Constants.Http.SERVER_ADDRESS, SocketAddress.inetSocketAddress(9999, "192.168.18.185"));
    }
}
