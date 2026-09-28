package com.isuzuki.core.http.logs;

import java.util.HashMap;
import java.util.Map;

public class HttpApiCookie {

    private String name;
    private String value;
    private Map<String, String> attributes;


    public static HttpApiCookie cookie(String name, String value) {
        HttpApiCookie cookie = new HttpApiCookie();
        cookie.name = name;
        cookie.value = value;
        return cookie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public HttpApiCookie attributes(Map<String, String> attributes) {
        if (attributes != null && !attributes.isEmpty()) {
            if (this.attributes == null) {
                this.attributes = new HashMap<>();
            }
            this.attributes.putAll(attributes);
        }
        return this;
    }

    public HttpApiCookie attribute(String attribute, String value) {
        if (attribute != null && !attribute.isBlank()) {
            if (this.attributes == null) {
                this.attributes = new HashMap<>();
            }
            this.attributes.put(attribute, value);
        }
        return this;
    }

    public HttpApiCookie attribute(String attribute, long value) {
        attribute(attribute, Long.toString(value));
        return this;
    }

    public HttpApiCookie attribute(String attribute, boolean value) {
        attribute(attribute, Boolean.toString(value));
        return this;
    }
}
