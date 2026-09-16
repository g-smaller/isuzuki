package com.isuzuki.http.apilog;

import java.util.Map;

public interface HttpAuthenticator {

    String getToken();

    String getIdentity();

    Map<String, String> getMetadata();

}
