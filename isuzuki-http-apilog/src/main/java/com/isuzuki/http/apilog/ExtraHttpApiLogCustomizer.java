package com.isuzuki.http.apilog;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class ExtraHttpApiLogCustomizer implements HttpApiLogCustomizer {

    @Override
    public void customize(HttpServletRequest request, HttpApiLogBuilder builder) {
        Map<String, Object> extra = new HashMap<>();
        SpanContext spanContext = Span.current().getSpanContext();
        extra.put("otel.trace_id", spanContext.getTraceId());
        extra.put("otel.span_id", spanContext.getSpanId());
        builder.extra(extra);
    }
}
