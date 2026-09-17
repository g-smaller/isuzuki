package com.isuzuki.http.apilog;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DefaultHttpApiLogBuilder implements HttpApiLogBuilder {

    private DefaultHttpApiLog apiLog;
    private DefaultHttpApiLogBuilder(){
        apiLog = new DefaultHttpApiLog();
        // requestTime(System.currentTimeMillis());
    }

    public static DefaultHttpApiLogBuilder builder(){
        return new  DefaultHttpApiLogBuilder();
    }

    @Override
    public String getTraceId() {
        return apiLog.getTraceId();
    }

    @Override
    public HttpApiLogBuilder requestTimeFormat(String requestTimeFormat) {
        apiLog.setRequestTimeFormat(requestTimeFormat);
        return this;
    }

    @Override
    public HttpApiLogBuilder requestTime(Long requestTime) {
        apiLog.setRequestTime(requestTime);
        requestTimeFormat(DateFormatUtils.format(requestTime, "yyyy-MM-dd HH:mm:ss:SSS"));
        return this;
    }

    @Override
    public HttpApiLogBuilder traceId(String traceId) {
        apiLog.setTraceId(traceId);
        return this;
    }

    @Override
    public HttpApiLogBuilder clientIp(String clientIp) {
        apiLog.setClientIp(clientIp);
        return this;
    }

    @Override
    public HttpApiLogBuilder apiId(String apiId) {
        apiLog.setApiId(apiId);
        return this;
    }

    @Override
    public HttpApiLogBuilder apiName(String apiName) {
        apiLog.setApiName(apiName);
        return this;
    }

    @Override
    public HttpApiLogBuilder uri(String uri) {
        apiLog.setUri(uri);
        return this;
    }

    @Override
    public HttpApiLogBuilder method(String method) {
        apiLog.setMethod(method);
        return this;
    }

    @Override
    public HttpApiLogBuilder host(String host) {
        apiLog.setHost(host);
        return this;
    }

    @Override
    public HttpApiLogBuilder queryString(String queryString) {
        apiLog.setQueryString(queryString);
        return this;
    }

    @Override
    public HttpApiLogBuilder payload(Object payload) {
        apiLog.setPayload(payload);
        return this;
    }

    @Override
    public HttpApiLogBuilder contentLength(int contentLength) {
        apiLog.setContentLength(contentLength);
        return this;
    }

    @Override
    public HttpApiLogBuilder contentType(String contentType) {
        apiLog.setContentType(contentType);
        return this;
    }

    @Override
    public HttpApiLogBuilder referer(String referer) {
        apiLog.setReferer(referer);
        return this;
    }

    @Override
    public HttpApiLogBuilder ua(String ua) {
        apiLog.setUa(ua);
        return this;
    }

    @Override
    public HttpApiLogBuilder cookies(Map<String, Object> cookies) {
        apiLog.setCookies(cookies);
        return this;
    }

    @Override
    public HttpApiLogBuilder headers(Map<String, Object> headers) {
        apiLog.setHeaders(headers);
        return this;
    }

    @Override
    public HttpApiLogBuilder annotations(Map<String, Object> annotations) {
        if (annotations != null && !annotations.isEmpty()) {
            if (apiLog.getAnnotations() == null) {
                apiLog.setAnnotations(new HashMap<>());
            }
            apiLog.getAnnotations().putAll(annotations);
        }
        return this;
    }

    @Override
    public HttpApiLogBuilder addAnnotation(String key, Object value) {
        if (apiLog.getAnnotations() == null) {
            apiLog.setAnnotations(new HashMap<>());
        }
        apiLog.getAnnotations().put(key, value);
        return this;
    }

    @Override
    public HttpApiLogBuilder metadata(Map<String, Object> metadata) {
        if (metadata != null && !metadata.isEmpty()) {
            if (apiLog.getMetadata() == null) {
                apiLog.setMetadata(new HashMap<>());
            }
            apiLog.getMetadata().putAll(metadata);
        }
        return this;
    }

    @Override
    public HttpApiLogBuilder addMetadata(String key, Object value) {
        if (apiLog.getMetadata() == null) {
            apiLog.setMetadata(new HashMap<>());
        }
        apiLog.getMetadata().put(key, value);
        return this;
    }

    @Override
    public HttpApiLogBuilder extra(Map<String, Object> extra) {
        if (extra != null && !extra.isEmpty()) {
            if (apiLog.getExtra() == null) {
                apiLog.setExtra(new HashMap<>());
            }
            apiLog.getExtra().putAll(extra);
        }
        return this;
    }

    @Override
    public HttpApiLogBuilder addExtra(String key, Object value) {
        if (apiLog.getExtra() == null) {
            apiLog.setExtra(new HashMap<>());
        }
        apiLog.getExtra().put(key, value);
        return this;
    }

    @Override
    public HttpApiLogBuilder authenticator(HttpAuthenticator authenticator) {
        apiLog.setAuthenticator(authenticator);
        return this;
    }

    @Override
    public HttpApiLogBuilder statusCode(Integer statusCode) {
        apiLog.setStatusCode(statusCode);
        return this;
    }

    @Override
    public HttpApiLogBuilder response(Object response) {
        apiLog.setResponse(response);
        return this;
    }

    @Override
    public HttpApiLogBuilder elapsedTime(Long elapsedTime) {
        apiLog.setElapsedTime(elapsedTime);
        return this;
    }

    @Override
    public HttpApiLogBuilder elapsedTimeFormat(String elapsedTimeFormat) {
        apiLog.setElapsedTimeFormat(elapsedTimeFormat);
        return this;
    }

    @Override
    public HttpApiLog build() {
        long elapsedTime = System.nanoTime() - apiLog.getRequestTime();
        elapsedTime(elapsedTime);
        elapsedTimeFormat(Duration.ofNanos(elapsedTime).toString());
        return apiLog;
    }
}
