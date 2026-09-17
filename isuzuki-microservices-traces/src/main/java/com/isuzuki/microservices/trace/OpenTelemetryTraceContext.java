package com.isuzuki.microservices.trace;

import com.isuzuki.core.TraceContext;
import io.opentelemetry.api.trace.Span;

public class OpenTelemetryTraceContext implements TraceContext {

    @Override
    public String getTraceId() {
        return Span.current().getSpanContext().getTraceId();
    }
}
