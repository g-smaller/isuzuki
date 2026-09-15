package com.isuzuki.oss.api;


import java.util.List;

public class CloudOssProperties {
    public static final String PREFIX = "isuzuki.oss.s3";
    public static final String PROVIDER = "provider";
    public static final String ENABLED = "enabled";

    public static final String PROVIDER_FULL = PREFIX + "." + PROVIDER;
    public static final String ENABLED_FULL = PREFIX + "." +  ENABLED;

    private boolean enabled = false;
    private String provider = "s3";
    private List<CloudOssBucketProperties> buckets;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<CloudOssBucketProperties> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<CloudOssBucketProperties> buckets) {
        this.buckets = buckets;
    }
}
