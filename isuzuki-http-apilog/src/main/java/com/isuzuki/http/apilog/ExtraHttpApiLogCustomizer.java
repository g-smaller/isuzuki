package com.isuzuki.http.apilog;

import com.isuzuki.core.http.logs.HttpApiLogBuilder;
import com.isuzuki.core.http.logs.HttpApiLogFields;
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
        extra.put(HttpApiLogFields.Extra.OTEL_TRACE_ID, spanContext.getTraceId());
        extra.put(HttpApiLogFields.Extra.OTEL_SPAN_ID, spanContext.getSpanId());
        builder.extra(extra);
    }
}
