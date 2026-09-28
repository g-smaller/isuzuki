package com.isuzuki.core.http.logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DefaultHttpApiLogBuilder implements HttpApiLogBuilder {

    private DefaultHttpApiLog apiLog;
    private DefaultHttpApiLogBuilder(){
        apiLog = new DefaultHttpApiLog();
        // requestTime(System.currentTimeMillis());
    }

    public static DefaultHttpApiLogBuilder create(){
        return new DefaultHttpApiLogBuilder();
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
        String api = uri;
        if (uri.length() > 1) {
            api = uri.replace("/", ".");
            api = api.substring(1);
        }
        apiId(api);
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
    public HttpApiLogBuilder contentLength(String contentLength) {
        if (contentLength != null && !contentLength.isBlank()) {
            contentLength(Integer.parseInt(contentLength));
        }
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
    public HttpApiLogBuilder cookies(List<HttpApiCookie> cookies) {
        if (cookies != null && !cookies.isEmpty()) {
            if (apiLog.getCookies() == null) {
                apiLog.setCookies(new ArrayList<>());
            }
            apiLog.getCookies().addAll(cookies);
        }
        return this;
    }

    @Override
    public HttpApiLogBuilder cookies(HttpApiCookie cookie) {
        if (cookie != null) {
            if (apiLog.getCookies() == null) {
                apiLog.setCookies(new ArrayList<>());
            }
            apiLog.getCookies().add(cookie);
        }
        return this;
    }

    @Override
    public HttpApiLogBuilder headers(Map<String, Object> headers) {
        apiLog.setHeaders(headers);
        return this;
    }

    @Override
    public HttpApiLogBuilder headers(String key, String value) {
        if (apiLog.getHeaders() == null) {
            apiLog.setHeaders(new HashMap<>());
        }
        apiLog.getHeaders().put(key, value);
        return null;
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
    public HttpApiLogBuilder authentication(Map<String, Object> authentication) {
        if (authentication != null && !authentication.isEmpty()) {
            if (apiLog.getAuthentication() == null) {
                apiLog.setAuthentication(new HashMap<>());
            }
            apiLog.getAuthentication().putAll(authentication);
        }
        return this;
    }

    @Override
    public HttpApiLogBuilder authentication(String key, Object value) {
        if (apiLog.getAuthentication() == null) {
            apiLog.setAuthentication(new HashMap<>());
        }
        apiLog.getAuthentication().put(key, value);
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
    public HttpApiLog build(Consumer<HttpApiLog> consumer) {
        consumer.accept(apiLog);
        return apiLog;
    }
}
