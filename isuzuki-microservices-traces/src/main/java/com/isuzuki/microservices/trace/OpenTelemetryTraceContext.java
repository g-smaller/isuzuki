package com.isuzuki.microservices.trace;

import com.isuzuki.core.TraceContext;
import io.opentelemetry.api.trace.Span;
import org.springframework.stereotype.Component;

@Component
public class OpenTelemetryTraceContext implements TraceContext {

    @Override
    public String getTraceId() {
        return Span.current().getSpanContext().getTraceId();
    }
}
