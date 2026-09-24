package com.isuzuki.http.apilog;

import com.isuzuki.core.logs.http.HttpApiLogBuilder;

public interface HttpApiLogHandler {

    void handle(HttpApiLogBuilder builder, Throwable throwable);

}
