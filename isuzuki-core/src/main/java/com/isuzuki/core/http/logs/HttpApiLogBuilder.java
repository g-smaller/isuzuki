package com.isuzuki.core.http.logs;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface HttpApiLogBuilder {

    static HttpApiLogBuilder builder() {
        return DefaultHttpApiLogBuilder.create().requestTime(System.nanoTime());
    }

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

    HttpApiLogBuilder contentLength(String contentLength);

    HttpApiLogBuilder contentType(String contentType);

    HttpApiLogBuilder referer(String referer);

    HttpApiLogBuilder ua(String ua);

    HttpApiLogBuilder cookies(List<HttpApiCookie> cookies);

    HttpApiLogBuilder cookies(HttpApiCookie cookie);

    HttpApiLogBuilder headers(Map<String, Object> headers);

    HttpApiLogBuilder headers(String key, String value);

    HttpApiLogBuilder annotations(Map<String, Object> annotations);

    HttpApiLogBuilder addAnnotation(String key, Object value);

    HttpApiLogBuilder metadata(Map<String, Object> metadata);

    HttpApiLogBuilder addMetadata(String key, Object value);

    HttpApiLogBuilder extra(Map<String, Object> extra);

    HttpApiLogBuilder addExtra(String key, Object value);

    HttpApiLogBuilder authentication(Map<String, Object> authentication);

    HttpApiLogBuilder authentication(String key, Object value);

    HttpApiLogBuilder statusCode(Integer statusCode);

    HttpApiLogBuilder response(Object response);

    HttpApiLogBuilder elapsedTime(Long elapsedTime);

    HttpApiLogBuilder elapsedTimeFormat(String elapsedTimeFormat);

    default HttpApiLog build() {
        return build((apiLog) -> {
            long elapsedTime = System.nanoTime() - apiLog.getRequestTime();
            elapsedTime(elapsedTime);
            elapsedTimeFormat(Duration.ofNanos(elapsedTime).toString());
        });
    }

    HttpApiLog build(Consumer<HttpApiLog> consumer);
}
