package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;

public class MetadataHttpApiLogCustomizer implements HttpApiLogCustomizer {

    @Value("${spring.application.name:}")
    private String appName;
    @Value("${server.port:}")
    private String appPort;

    @Override
    public void customize(HttpServletRequest request, HttpApiLogBuilder builder) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put(HttpApiLogFields.Metadata.SERVER_HOST_NAME, HttpApiLogUtils.getLocalHostName());
        metadata.put(HttpApiLogFields.Metadata.SERVER_IP, HttpApiLogUtils.getLocalIP());
        metadata.put(HttpApiLogFields.Metadata.SERVER_USER, System.getenv("USER"));
        metadata.put(HttpApiLogFields.Metadata.SERVER_HOME, System.getenv("HOME"));

        metadata.put(HttpApiLogFields.Metadata.APP_NAME, appName);
        metadata.put(HttpApiLogFields.Metadata.APP_PORT, appPort);


        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryMXBean.getNonHeapMemoryUsage();

        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_MAX, heapUsage.getMax());
        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_COMMITTED, heapUsage.getCommitted());
        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_USAGE, heapUsage.getUsed());
        metadata.put(HttpApiLogFields.Metadata.JVM_HEAP_FREE, (heapUsage.getMax() - heapUsage.getUsed()));

        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_MAX, nonHeapUsage.getMax());
        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_COMMITTED, nonHeapUsage.getCommitted());
        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_USAGE, nonHeapUsage.getUsed());
        metadata.put(HttpApiLogFields.Metadata.JVM_NON_HEAP_FREE, (nonHeapUsage.getMax() -  nonHeapUsage.getUsed()));

        builder.metadata(metadata);
    }
}
