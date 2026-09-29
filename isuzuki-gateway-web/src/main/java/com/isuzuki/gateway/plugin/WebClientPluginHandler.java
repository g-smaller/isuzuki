package com.isuzuki.gateway.plugin;

import com.isuzuki.gateway.Constants;
import com.isuzuki.gateway.http.HttpHeaderFilter;
import com.isuzuki.gateway.http.HttpRequestHeaderFilter;
import com.isuzuki.gateway.http.HttpResponseHeaderFilter;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.http.impl.headers.HeadersAdaptor;
import io.vertx.core.net.Address;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

import java.util.Collections;
import java.util.List;

public class WebClientPluginHandler implements PriorityPluginHandler<RoutingContext> {

    private WebClient webClient;
    private List<HttpRequestHeaderFilter> requestHeaderFilters;
    private List<HttpResponseHeaderFilter> responseHeaderFilters;

    public WebClientPluginHandler(WebClient webClient) {
        this(webClient, Collections.emptyList(), Collections.emptyList());
    }

    public WebClientPluginHandler(WebClient webClient,
                                  List<HttpRequestHeaderFilter> requestHeaderFilters,
                                  List<HttpResponseHeaderFilter> responseHeaderFilters) {
        this.webClient = webClient;
        this.requestHeaderFilters = requestHeaderFilters;
        this.responseHeaderFilters = responseHeaderFilters;
    }

    @Override
    public void handle(RoutingContext event) {

        String uri = event.get(Constants.Http.URI);
        String method = event.get(Constants.Http.METHOD);
        Address address = event.get(Constants.Http.SERVER_ADDRESS);
        MultiMap params = event.request().params();

        HttpRequest<Buffer> request = webClient.request(new RequestOptions()
                .setMethod(HttpMethod.valueOf(method))
                .setURI(uri)
                .setServer(address)
        );
        if (params != null && !params.isEmpty()) {
            params.forEach(request::addQueryParam);
        }
        request.connectTimeout(getConnectionTimeout(event))
                .timeout(getTimeout(event))
                .putHeaders(requestHeaderFilter(event.request().headers()))
                .sendBuffer(event.request().body().result())
                .onComplete(clientResponse -> {
                    MultiMap responseHeaders = responseHeaderFilter(clientResponse.headers());
                    if (responseHeaders != clientResponse.headers()) {
                        responseHeaders.forEach(event.response()::putHeader);
                    }
                    event.response().send(clientResponse.body());
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    private MultiMap requestHeaderFilter(MultiMap requestHeaders) {
        if (requestHeaderFilters == null ||  requestHeaderFilters.isEmpty()) {
            return requestHeaders;
        }
        MultiMap httpHeaders = new HeadersAdaptor(new DefaultHttpHeaders());
        requestHeaders.forEach(httpHeaders::add);
        for (HttpHeaderFilter headerFilter : requestHeaderFilters) {
            httpHeaders = headerFilter.filter(httpHeaders);
        }
        return httpHeaders;
    }

    private MultiMap responseHeaderFilter(MultiMap responseHeaders) {
        if (responseHeaderFilters == null ||  responseHeaderFilters.isEmpty()) {
            return responseHeaders;
        }
        MultiMap httpHeaders = new HeadersAdaptor(new DefaultHttpHeaders());
        responseHeaders.forEach(httpHeaders::add);
        for (HttpHeaderFilter headerFilter : responseHeaderFilters) {
            httpHeaders = headerFilter.filter(httpHeaders);
        }
        return httpHeaders;
    }

    private long getConnectionTimeout(RoutingContext ctx) {
        Route route = ctx.currentRoute();
        Long timeout = route.getMetadata(Constants.Route.REQUEST_TIMEOUT);
        if (timeout == null) {
            timeout = ctx.get(Constants.Http.REQUEST_TIMEOUT);
        }
        return timeout == null ? 3000 : timeout;
    }

    private long getTimeout(RoutingContext ctx) {
        Route route = ctx.currentRoute();
        Long timeout = route.getMetadata(Constants.Route.RESPONSE_TIMEOUT);
        if (timeout == null) {
            timeout = ctx.get(Constants.Http.RESPONSE_TIMEOUT);
        }
        return timeout == null ? 3000 : timeout;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
