package com.isuzuki.http.apilog;

public interface HttpApiLogHandler {

    void handle(HttpApiLogBuilder builder, Throwable throwable);

}
