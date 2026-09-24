package com.isuzuki.core.logs.http;

import java.util.Map;

public interface HttpAuthentication {

    String getName();

    Map<String, String> getMetadata();

    void metadata(String key, String value);

}
