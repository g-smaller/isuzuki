package com.isuzuki.gateway.serviceresolver;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.net.Address;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.spi.endpoint.EndpointBuilder;
import io.vertx.core.spi.endpoint.EndpointResolver;
import io.vertx.serviceresolver.ServiceAddress;

public class NacosEndpointResolverImpl<B> implements EndpointResolver<ServiceAddress, SocketAddress, NacosServiceState<B>, B> {

    private Vertx vertx;

    public NacosEndpointResolverImpl(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public ServiceAddress tryCast(Address address) {
        return address instanceof ServiceAddress ? (ServiceAddress) address : null;
    }

    @Override
    public SocketAddress addressOf(SocketAddress server) {
        return server;
    }

    @Override
    public Future<NacosServiceState<B>> resolve(ServiceAddress address, EndpointBuilder<B, SocketAddress> builder) {

        return null;
    }

    @Override
    public B endpoint(NacosServiceState<B> state) {
        return state.endpoints.get();
    }

    @Override
    public boolean isValid(NacosServiceState<B> state) {
        return state.valid;
    }

    @Override
    public void dispose(NacosServiceState<B> data) {

    }

    @Override
    public void close() {

    }
}
