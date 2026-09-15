package com.isuzuki.oss.api;

import java.io.InputStream;

public class PutObjectRequest extends PutObjectReqBase<PutObjectRequest> {
    private InputStream inputStream;
    private long objectSize;

    public static PutObjectRequest builder() {
        return new PutObjectRequest();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public long getObjectSize() {
        return objectSize;
    }

    public PutObjectRequest inputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public PutObjectRequest objectSize(Long objectSize) {
        this.objectSize = objectSize;
        return this;
    }
}
