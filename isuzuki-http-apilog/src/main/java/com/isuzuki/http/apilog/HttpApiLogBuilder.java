package com.isuzuki.http.apilog;

import java.util.Map;

public interface HttpApiLogBuilder {

    String getTraceId();

    HttpApiLogBuilder requestTimeFormat(String requestTimeFormat);

    HttpApiLogBuilder requestTime(Long requestTime);

    HttpApiLogBuilder traceId(String traceId);

    HttpApiLogBuilder clientIp(String clientIp);

    HttpApiLogBuilder apiId(String apiId);

    HttpApiLogBuilder apiName(String apiName);

    HttpApiLogBuilder uri(String uri);

    HttpApiLogBuilder method(String method);

    HttpApiLogBuilder host(String host);

    HttpApiLogBuilder queryString(String queryString);

    HttpApiLogBuilder payload(Object payload);

    HttpApiLogBuilder contentLength(int contentLength);

    HttpApiLogBuilder contentType(String contentType);

    HttpApiLogBuilder referer(String referer);

    HttpApiLogBuilder ua(String ua);

    HttpApiLogBuilder cookies(Map<String, Object> cookies);

    HttpApiLogBuilder headers(Map<String, Object> headers);

    HttpApiLogBuilder annotations(Map<String, Object> annotations);

    HttpApiLogBuilder addAnnotation(String key, Object value);

    HttpApiLogBuilder metadata(Map<String, Object> metadata);

    HttpApiLogBuilder addMetadata(String key, Object value);

    HttpApiLogBuilder extra(Map<String, Object> extra);

    HttpApiLogBuilder addExtra(String key, Object value);

    HttpApiLogBuilder authenticator(HttpAuthenticator authenticator);

    HttpApiLogBuilder statusCode(Integer statusCode);

    HttpApiLogBuilder response(Object response);

    HttpApiLogBuilder elapsedTime(Long elapsedTime);

    HttpApiLogBuilder elapsedTimeFormat(String elapsedTimeFormat);

    HttpApiLog build();
}
