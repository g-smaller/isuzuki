package com.isuzuki.oss.aliyun;

import com.isuzuki.oss.api.CloudOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AliYunCloudOssProperties.PREFIX)
public class AliYunCloudOssProperties {
    public static final String PREFIX = CloudOssProperties.PREFIX + ".aliyun";
    private String callbackUrl;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
