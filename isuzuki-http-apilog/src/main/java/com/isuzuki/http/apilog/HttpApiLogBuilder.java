package com.isuzuki.http.apilog;

import java.util.Map;

public interface HttpApiLogBuilder {

    HttpApiLogBuilder requestTimeFormat(String requestTimeFormat);

    HttpApiLogBuilder requestTime(Long requestTime);

    HttpApiLogBuilder requestId(String requestId);

    HttpApiLogBuilder apiId(String apiId);

    HttpApiLogBuilder apiName(String apiName);

    HttpApiLogBuilder annotations(Map<String, Object> annotations);

    HttpApiLogBuilder addAnnotation(String key, Object value);

    HttpApiLogBuilder metadata(Map<String, Object> metadata);

    HttpApiLogBuilder addMetadata(String key, Object value);

    HttpApiLogBuilder extra(Map<String, Object> extra);

    HttpApiLogBuilder addExtra(String key, Object value);

    HttpApiLogBuilder authenticator(HttpAuthenticator authenticator);

    HttpApiLogBuilder clientIp(String clientIp);

    HttpApiLogBuilder contentLength(int contentLength);

    HttpApiLogBuilder host(String host);

    HttpApiLogBuilder cookies(Map<String, Object> cookies);

    HttpApiLogBuilder headers(Map<String, Object> headers);

    HttpApiLogBuilder method(String method);

    HttpApiLogBuilder referer(String referer);

    HttpApiLogBuilder payload(Object payload);

    HttpApiLogBuilder queryString(String queryString);

    HttpApiLogBuilder statusCode(Integer statusCode);

    HttpApiLogBuilder response(Object response);

    HttpApiLogBuilder elapsedMilliSecond(Integer elapsedMilliSecond);

    HttpApiLogBuilder ua(String ua);

    HttpApiLogBuilder uri(String uri);

    HttpApiLog build();
}
