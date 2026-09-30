package com.isuzuki.gateway.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.RoutingContext;

public class HttpClientHandler implements Handler<RoutingContext> {

    private HttpClient httpClient;

    @Override
    public void handle(RoutingContext event) {

    }
}
