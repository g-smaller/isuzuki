package com.isuzuki.oss.api;

public enum CloudOssProvider {

    S3("s3"),
    ALIYUN("aliyun"),
    MINIO("minio"),
    ;

    private String provider;
    CloudOssProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
