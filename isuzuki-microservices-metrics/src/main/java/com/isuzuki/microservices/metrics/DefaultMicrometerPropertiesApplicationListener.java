package com.isuzuki.microservices.metrics;


import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DefaultMicrometerPropertiesApplicationListener implements Ordered, ApplicationListener<ApplicationEnvironmentPreparedEvent>{

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("management.endpoints.web.exposure.include", "health,info,prometheus,metrics");
        properties.put("management.endpoint.prometheus.enabled", "true");
        properties.put("management.endpoint.health.show-details", "always");
        // properties.put("management.metrics.tags.application", "${spring.application.name:}");
        // properties.put("management.metrics.tags.env", "${ENV:dev}");
        // properties.put("management.metrics.tags.region", "${REGION:cn-north}");
        // 为 HTTP 请求生成直方图，用于计算 p99
        // properties.put("management.metrics.distribution.percentiles-histogram.http.server.requests", "true");
        // properties.put("management.metrics.distribution.slo.http.server.requests", "100ms,200ms,500ms,1s,2s,5s");

        MapPropertySource defaultMetricPropertySource = new  MapPropertySource("defaultMetricPropertySource", properties);
        event.getEnvironment().getPropertySources().addLast(defaultMetricPropertySource);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

