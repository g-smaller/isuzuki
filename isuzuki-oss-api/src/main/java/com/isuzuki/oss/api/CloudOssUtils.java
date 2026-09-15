package com.isuzuki.oss.api;

import org.apache.commons.lang3.StringUtils;

public class CloudOssUtils {

    public static String handleFirstCharacter(String str, String character) {
        if (StringUtils.isBlank(str)) {
            return "";
        }

        if (StringUtils.isBlank(character)) {
            return str;
        }

        if (str.length() < character.length()) {
            return str;
        }

        String first = str.substring(0, character.length());
        if (first.equals(character)) {
            return str.substring(character.length());
        }
        return str;
    }

    public static CloudOssObjectKey parseObjectKey(String objectKey) {
        if (StringUtils.isBlank(objectKey)) {
            return CloudOssObjectKey.EMPTY;
        }
        String path = objectKey;
        if (objectKey.contains("http://") || objectKey.contains("https://")) {
            path = objectKey.replace("http://", "")
                    .replace("https://", "");


            int i = path.indexOf("/");
            if (i > 0) {
                path = path.substring(i+1);
            }
        }

        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        int i = path.indexOf("/");
        String bucketName = path.substring(0, i);
        String newObjectKey = path.substring(i + 1);
        newObjectKey = resolveObjectKey(newObjectKey);
        return new CloudOssObjectKey(bucketName, newObjectKey);

    }

    public static String resolveObjectKey(String objectKey) {
        if (StringUtils.isBlank(objectKey)) {
            return "";
        }
        String newObjectKey = objectKey;
        int i = newObjectKey.indexOf("?");
        if (i > 0) {
            newObjectKey = newObjectKey.substring(0, i);
        }

        if (newObjectKey.startsWith("/")) {
            newObjectKey = newObjectKey.substring(1);
        }
        return newObjectKey;
    }

    public static String appendFullPath(String endpoint, String context, String subPath) {
        boolean hasLatestSeparator = false;
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(endpoint)) {
            sb.append(endpoint);
            hasLatestSeparator = endpoint.endsWith("/");
        }
        if (StringUtils.isNotBlank(context)) {
            boolean hasFirstSeparator = context.startsWith("/");

            if (hasFirstSeparator) {
                if (hasLatestSeparator) {
                    sb.append(context.substring(1));
                }else {
                    sb.append(context);
                }
            }else {
                if (hasLatestSeparator) {
                    sb.append(context);
                }else {
                    if (sb.length() > 0) {
                        sb.append("/");
                    }
                    sb.append(context);
                }
            }
            hasLatestSeparator = context.endsWith("/");
        }
        if (StringUtils.isNotBlank(subPath)) {
            boolean hasFirstSeparator = subPath.startsWith("/");

            if (hasFirstSeparator) {
                if (hasLatestSeparator) {
                    sb.append(subPath.substring(1));
                }else {
                    sb.append(subPath);
                }
            }else {
                if (hasLatestSeparator) {
                    sb.append(subPath);
                }else {
                    if (sb.length() > 0) {
                        sb.append("/");
                    }
                    sb.append(subPath);
                }
            }
        }
        String full = sb.toString();
        if (StringUtils.isNotBlank(full)) {
            if (full.startsWith("/")) {
                full = full.substring(1);
            }
        }
        return full;
    }

    public static String appendFullPath(String endpoint, String subPath) {
        return appendFullPath(endpoint, "", subPath);
    }

    public static String appendPath(String bucketName, String objectKey) {
        return appendFullPath("", bucketName, objectKey);
    }
}
