package com.isuzuki.oss.api;

public class UploadObjectRequest extends PutObjectReqBase<UploadObjectRequest> {

    private String filename;

    public String getFilename() {
        return filename;
    }

    public UploadObjectRequest filename(String filename) {
        this.filename = filename;
        return this;
    }
}
