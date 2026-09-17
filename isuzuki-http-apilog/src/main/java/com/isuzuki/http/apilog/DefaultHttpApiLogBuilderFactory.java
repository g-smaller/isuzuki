package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DefaultHttpApiLogBuilderFactory implements HttpApiLogBuilderFactory {

    public DefaultHttpApiLogBuilderFactory() {

    }

    @Override
    public HttpApiLogBuilder create(HttpServletRequest request) {
        return DefaultHttpApiLogBuilder.builder()
                .requestTime(System.nanoTime())
                .requestTimeFormat(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:SSS"));

    }
}
