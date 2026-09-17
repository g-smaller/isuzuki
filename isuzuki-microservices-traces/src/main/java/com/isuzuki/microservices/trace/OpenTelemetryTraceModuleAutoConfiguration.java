package com.isuzuki.microservices.trace;

import com.isuzuki.core.TraceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryTraceModuleAutoConfiguration {

    @Bean
    public TraceContext traceContext() {
        return new OpenTelemetryTraceContext();
    }

}
