package com.isuzuki.gateway.serviceresolver;

import io.vertx.core.Vertx;
import io.vertx.core.net.AddressResolver;
import io.vertx.core.spi.endpoint.EndpointResolver;
import io.vertx.serviceresolver.ServiceAddress;

public class NacosResolver implements AddressResolver<ServiceAddress> {

    @Override
    public EndpointResolver<ServiceAddress, ?, ?, ?> endpointResolver(Vertx vertx) {
        return new NacosEndpointResolverImpl<>(vertx);
    }
}
