package com.isuzuki.http.apilog;

import com.isuzuki.core.http.logs.HttpApiLogBuilder;

public interface HttpApiLogHandler {

    void handle(HttpApiLogBuilder builder, Throwable throwable);

}
