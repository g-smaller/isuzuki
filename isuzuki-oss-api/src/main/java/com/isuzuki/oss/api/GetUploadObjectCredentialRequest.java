package com.isuzuki.oss.api;

import java.util.HashMap;
import java.util.Map;

public class GetUploadObjectCredentialRequest {
    private String objectKey;
    private long lowerLimit = 0;
    private long upperLimit = 1024;
    private Boolean isPrivate;
    private Map<String, String> condition;

    public String getObjectKey() {
        return objectKey;
    }

    public long getLowerLimit() {
        return lowerLimit;
    }

    public long getUpperLimit() {
        return upperLimit;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Map<String, String> getCondition() {
        return condition;
    }

    public GetUploadObjectCredentialRequest addCondition(String field, String condition) {
        if (this.condition == null) {
            this.condition = new HashMap<>();
        }
        this.condition.put(field, condition);
        return this;
    }

    public GetUploadObjectCredentialRequest objectKey(String objectKey) {
        this.objectKey = objectKey;
        return this;
    }

    public GetUploadObjectCredentialRequest enablePrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
        return this;
    }

    public GetUploadObjectCredentialRequest lengthLimit(long upperLimit) {
        lengthLimit(0, upperLimit);
        return this;
    }

    public GetUploadObjectCredentialRequest lengthLimit(long lowerLimit, long upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        return this;
    }
}
