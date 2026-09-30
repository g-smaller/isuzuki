package com.isuzuki.gateway.handler;

import com.isuzuki.gateway.Constants;
import com.isuzuki.gateway.http.HttpHeaderFilter;
import com.isuzuki.gateway.http.HttpRequestHeaderFilter;
import com.isuzuki.gateway.http.HttpResponseHeaderFilter;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.vertx.core.Handler;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class WebClientHandler implements Handler<RoutingContext> {

    private static final Logger logger = LoggerFactory.getLogger(WebClientHandler.class);

    private WebClient webClient;
    private List<HttpRequestHeaderFilter> requestHeaderFilters;
    private List<HttpResponseHeaderFilter> responseHeaderFilters;

    public WebClientHandler(WebClient webClient) {
        this(webClient, Collections.emptyList(), Collections.emptyList());
    }

    public WebClientHandler(WebClient webClient,
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
                .sendBuffer(event.body().buffer())
                .onComplete(clientResponse -> {

                    MultiMap responseHeaders = responseHeaderFilter(clientResponse.headers());
                    if (responseHeaders != null) {
                        responseHeaders.forEach(event.response()::putHeader);
                    }
                    if (event.response().ended()) {
                        event.response().setStatusCode(500);
                        logger.warn("响应事件已经结束无法写入数据!");
                    }else {
                        String body = clientResponse.bodyAsString(StandardCharsets.UTF_8.toString());
                        event.put(Constants.Http.RESPONSE_BODY, body);

                        event.response().setStatusCode(clientResponse.statusCode());
                        event.response().send(body).onSuccess(resp -> {
                            HttpApiLogHandler.afterHandler(event);
                        }).onFailure(throwable -> {
                            logger.error("ClientResponse Error!", throwable);
                            failure(event, throwable);
                        });
                    }
                }, throwable -> {
                    logger.error("WebClient Error!", throwable);
                    failure(event, throwable);
                });
    }

    private void failure(RoutingContext event, Throwable throwable) {
        event.fail(throwable);
        event.response().setStatusCode(500);
        event.next();
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
        String timeout = route.getMetadata(Constants.Route.REQUEST_TIMEOUT);
        if (timeout == null || timeout.isBlank()) {
            timeout = ctx.get(Constants.Http.REQUEST_TIMEOUT);
        }
        return timeout == null || timeout.isBlank() ? 3000 : Long.parseLong(timeout);
    }

    private long getTimeout(RoutingContext ctx) {
        Route route = ctx.currentRoute();
        String timeout = route.getMetadata(Constants.Route.RESPONSE_TIMEOUT);
        if (timeout == null || timeout.isBlank()) {
            timeout = ctx.get(Constants.Http.RESPONSE_TIMEOUT);
        }
        return timeout == null || timeout.isBlank() ? 3000 : Long.parseLong(timeout);
    }
}
