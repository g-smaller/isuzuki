package com.isuzuki.http.apilog;

import com.isuzuki.core.TraceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DefaultHttpApiLogBuilderFactory implements HttpApiLogBuilderFactory {

    private final TraceContext traceContext;

    public DefaultHttpApiLogBuilderFactory(TraceContext traceContext) {
        this.traceContext = traceContext;
    }

    @Override
    public HttpApiLogBuilder create(HttpServletRequest request) {
        return DefaultHttpApiLogBuilder.builder()
                .requestId(traceContext.getTraceId())
                .requestTime(System.nanoTime())
                .requestTimeFormat(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:SSS"));

    }
}
