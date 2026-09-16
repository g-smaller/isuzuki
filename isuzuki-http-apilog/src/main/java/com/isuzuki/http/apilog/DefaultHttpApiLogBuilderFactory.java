package com.isuzuki.http.apilog;

import com.isuzuki.core.TraceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DefaultHttpApiLogBuilderFactory implements HttpApiLogBuilderFactory {

    private final TraceContext traceContext;

    public DefaultHttpApiLogBuilderFactory(TraceContext traceContext) {
        this.traceContext = traceContext;
    }

    @Override
    public HttpApiLogBuilder create(HttpServletRequest request) {
        Date now = new Date();
        return new DefaultHttpApiLogBuilder()
                .requestId(traceContext.getTraceId())
                .requestTime(now.getTime())
                .requestTimeFormat(DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss:SSS"));

    }
}
