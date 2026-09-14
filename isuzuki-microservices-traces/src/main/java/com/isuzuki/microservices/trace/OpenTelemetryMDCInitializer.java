package com.isuzuki.microservices.trace;

import ch.qos.logback.classic.LoggerContext;
import io.opentelemetry.instrumentation.logback.mdc.v1_0.OpenTelemetryAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class OpenTelemetryMDCInitializer implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void loadAppender() {
        LoggerContext loggerContext = getLoggerContext();
        ch.qos.logback.classic.Logger root = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        // ch.qos.logback.classic.Logger console = loggerContext.getLogger("CONSOLE");
        // ch.qos.logback.classic.Logger file = loggerContext.getLogger("FILE");
        OpenTelemetryAppender otel = new  OpenTelemetryAppender();
        otel.setContext(loggerContext);
        otel.setName("OTEL");
        root.addAppender(otel);
        loggerContext.start();
    }

    private LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }
}
