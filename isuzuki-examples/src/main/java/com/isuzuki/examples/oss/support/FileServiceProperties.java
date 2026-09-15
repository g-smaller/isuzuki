package com.isuzuki.examples.oss.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = FileServiceProperties.PREFIX)
public class FileServiceProperties {
    public static final String PREFIX = "isuzuki.fs";

    private Map<String, FileServiceAppProperties> app;

    public Map<String, FileServiceAppProperties> getApp() {
        return app;
    }

    public void setApp(Map<String, FileServiceAppProperties> app) {
        this.app = app;
    }
}
