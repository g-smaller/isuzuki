package com.isuzuki.gateway.handle;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;

public class WebClientHandler implements PriorityHandler {

    @Override
    public void handle(RoutingContext event) {
        WebClient webClient = WebClient.create(event.vertx());
        webClient.request(HttpMethod.POST, "")
                .connectTimeout(0)
                .timeout(0)
                .send();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
