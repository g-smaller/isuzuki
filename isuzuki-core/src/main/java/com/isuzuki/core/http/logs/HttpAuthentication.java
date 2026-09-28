package com.isuzuki.core.http.logs;

import java.util.Map;

public interface HttpAuthentication {

    String getName();

    Map<String, String> getMetadata();

    void metadata(String key, String value);

}
