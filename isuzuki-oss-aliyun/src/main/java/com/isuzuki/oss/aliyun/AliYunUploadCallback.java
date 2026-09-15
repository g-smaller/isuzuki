package com.isuzuki.oss.aliyun;

import com.aliyun.oss.common.utils.BinaryUtil;
import com.isuzuki.utils.Jsons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @href {https://help.aliyun.com/zh/oss/developer-reference/callback?spm=a2c4g.11186623.0.0.1e73731cslOzTR#19f2e6eb46b27}
 */
public class AliYunUploadCallback {
    private final static List<String> OSS_SYS_FIELD = Arrays.asList("objectKey", "etag", "size", "mimeType", "height", "width", "format", "bucket");
    private String callbackUrl;
    private boolean form = true;
    private Map<String, Object> userProperties;

    private AliYunUploadCallback(boolean form) {
        userProperties = new HashMap<>();
        userProperties.put("object", "${object}");
        userProperties.put("etag", "${etag}");
        userProperties.put("size", "${size}");
        userProperties.put("mimeType", "${mimeType}");
        userProperties.put("height", "${imageInfo.height}");
        userProperties.put("width", "${imageInfo.width}");
        userProperties.put("format", "${imageInfo.format}");
        userProperties.put("clientIp",  "${clientIp}");
        this.form = form;
    }

    public static AliYunUploadCallback json() {
        return new AliYunUploadCallback(false);
    }

    public static AliYunUploadCallback form() {
        return new AliYunUploadCallback(true);
    }

    public AliYunUploadCallback callbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public AliYunUploadCallback put(String key, String value) {
        this.userProperties.put(resolveVar(key), value);
        return this;
    }

    public AliYunUploadCallback put(String key, Integer value) {
        this.userProperties.put(resolveVar(key), value);
        return this;
    }

    public AliYunUploadCallback put(String key, Long value) {
        this.userProperties.put(resolveVar(key), value);
        return this;
    }

    public AliYunUploadCallback put(String key, Double value) {
        this.userProperties.put(resolveVar(key), value);
        return this;
    }

    public AliYunUploadCallback put(String key, Float value) {
        this.userProperties.put(resolveVar(key), value);
        return this;
    }

    private String resolveVar(String key) {
        return "x:" + key;
    }

    public String getBase64CallbackBody() {
        String callbackBody = "";
        String callbackType = form ? "application/x-www-form-urlencoded" : "application/json";

        if (form) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : userProperties.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.append("bucket").append("=").append("${bucket}");
            callbackBody = sb.toString();
        }else {
            StringBuilder sb = new StringBuilder("{");
            for (Map.Entry<String, Object> entry : userProperties.entrySet()) {
                sb.append("\"").append(entry.getKey()).append("\"").append(":");
                if (OSS_SYS_FIELD.contains(entry.getKey())) {
                    sb.append(entry.getValue());
                }else {
                    sb.append("\"").append(entry.getValue()).append("\"");
                }
                sb.append(",");
            }
            sb.append("\"bucket\"").append(":").append("${bucket}");

            sb.append("}");
            callbackBody = sb.toString();
        }

        Map<String, String> callback = new HashMap<>();
        callback.put("callbackUrl", callbackUrl);
        callback.put("callbackBody", callbackBody);
        callback.put("callbackBodyType", callbackType);
        return BinaryUtil.toBase64String(Jsons.toBytes(callback));
    }
}
