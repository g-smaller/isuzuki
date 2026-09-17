package com.isuzuki.http.apilog;

import io.opentelemetry.api.trace.Span;

public class DefaultHttpApiLogTraceContext implements HttpApiLogTraceContext {
    @Override
    public String getTraceId() {
        return Span.current().getSpanContext().getTraceId();
    }
}
