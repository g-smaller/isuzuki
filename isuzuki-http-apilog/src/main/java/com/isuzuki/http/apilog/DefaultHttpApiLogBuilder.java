package com.isuzuki.http.apilog;

import java.util.Map;

public class DefaultHttpApiLogBuilder implements HttpApiLogBuilder {

    @Override
    public HttpApiLogBuilder requestTimeFormat(String requestTimeFormat) {
        
        return this;
    }

    @Override
    public HttpApiLogBuilder requestTime(Long requestTime) {
        return this;
    }

    @Override
    public HttpApiLogBuilder requestId(String requestId) {
        return this;
    }

    @Override
    public HttpApiLogBuilder apiId(String apiId) {
        return this;
    }

    @Override
    public HttpApiLogBuilder apiName(String apiName) {
        return this;
    }

    @Override
    public HttpApiLogBuilder annotations(Map<String, Object> annotations) {
        return this;
    }

    @Override
    public HttpApiLogBuilder addAnnotation(String key, Object value) {
        return this;
    }

    @Override
    public HttpApiLogBuilder metadata(Map<String, Object> metadata) {
        return this;
    }

    @Override
    public HttpApiLogBuilder addMetadata(String key, Object value) {
        return this;
    }

    @Override
    public HttpApiLogBuilder extra(Map<String, Object> extra) {
        return this;
    }

    @Override
    public HttpApiLogBuilder addExtra(String key, Object value) {
        return this;
    }

    @Override
    public HttpApiLogBuilder authenticator(HttpAuthenticator authenticator) {
        return this;
    }

    @Override
    public HttpApiLogBuilder clientIp(String clientIp) {
        return this;
    }

    @Override
    public HttpApiLogBuilder contentLength(int contentLength) {
        return this;
    }

    @Override
    public HttpApiLogBuilder host(String host) {
        return this;
    }

    @Override
    public HttpApiLogBuilder cookies(Map<String, Object> cookies) {
        return this;
    }

    @Override
    public HttpApiLogBuilder headers(Map<String, Object> headers) {
        return this;
    }

    @Override
    public HttpApiLogBuilder method(String method) {
        return this;
    }

    @Override
    public HttpApiLogBuilder referer(String referer) {
        return this;
    }

    @Override
    public HttpApiLogBuilder payload(Object payload) {
        return this;
    }

    @Override
    public HttpApiLogBuilder queryString(String queryString) {
        return this;
    }

    @Override
    public HttpApiLogBuilder statusCode(Integer statusCode) {
        return this;
    }

    @Override
    public HttpApiLogBuilder response(Object response) {
        return this;
    }

    @Override
    public HttpApiLogBuilder elapsedMilliSecond(Integer elapsedMilliSecond) {
        return this;
    }

    @Override
    public HttpApiLogBuilder ua(String ua) {
        return this;
    }

    @Override
    public HttpApiLogBuilder uri(String uri) {
        return this;
    }

    @Override
    public HttpApiLog build() {

        return null;
    }
}
