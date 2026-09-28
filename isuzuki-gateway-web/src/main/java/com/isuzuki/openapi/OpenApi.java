package com.isuzuki.openapi;

import java.util.List;
import java.util.Map;

public interface OpenApi {

    String getId();

    String getSummary();

    String getPath();

    String getMethod();

    Map<String, String> getHeaders();

    List<Plugin> getPlugins();

    Map<String, String> getMetadata();
}
