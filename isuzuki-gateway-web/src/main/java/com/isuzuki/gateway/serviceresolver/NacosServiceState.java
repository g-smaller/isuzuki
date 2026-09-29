package com.isuzuki.gateway.serviceresolver;

import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.spi.endpoint.EndpointBuilder;
import io.vertx.serviceresolver.ServiceAddress;

import java.util.concurrent.atomic.AtomicReference;

public class NacosServiceState<B> {

    final ServiceAddress address;
    final String name;
    final EndpointBuilder<B, SocketAddress> endpointsBuilder;
    final AtomicReference<B> endpoints = new AtomicReference<>();
    volatile boolean valid;

    NacosServiceState(EndpointBuilder<B, SocketAddress> endpointsBuilder, ServiceAddress address, String name) {
        this.endpointsBuilder = endpointsBuilder;
        this.name = name;
        this.address = address;
        this.valid = true;
    }

    void handleUpdate(JsonObject update) {

    }

    void updateEndpoints(JsonObject item) {

    }

}
