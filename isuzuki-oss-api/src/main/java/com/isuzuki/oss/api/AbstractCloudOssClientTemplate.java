package com.isuzuki.oss.api;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractCloudOssClientTemplate<T> implements CloudOssClientTemplate, CloudOssCredentialTemplate {

    protected static final String PUB_ACL = CloudOssAccessControlList.PublicRead.toString();
    protected static final String PRI_ACL = CloudOssAccessControlList.Private.toString();

    protected final CloudOssProperties cloudOssProperties;
    protected CloudOssBucketProperties defaultBucketProperties;
    protected Map<String, Supplier<T>> CLIENT_MAP;

    public AbstractCloudOssClientTemplate(CloudOssProperties cloudOssProperties) {
        this.cloudOssProperties = cloudOssProperties;
    }

    public void setDefaultBucketProperties(CloudOssBucketProperties defaultBucketProperties) {
        this.defaultBucketProperties = defaultBucketProperties;
    }

    protected CloudOssBucketProperties getBucketProperties(String bucketName, String objectKey) {
        if (StringUtils.isNotBlank(bucketName)) {
            return getBucketProperties(bucketName);
        }
        return getBucketPropertiesAndKey(objectKey);
    }

    protected CloudOssBucketProperties getBucketProperties(String bucketName) {
        if (StringUtils.isNotBlank(bucketName)) {
            List<CloudOssBucketProperties> buckets = cloudOssProperties.getBuckets();
            for (CloudOssBucketProperties bucket : buckets) {
                if (bucket.getBucketName().equals(bucketName)) {
                    return bucket;
                }
            }
        }
        return defaultBucketProperties;
    }

    protected CloudOssBucketProperties getBucketPropertiesAndKey(String objectKey) {
        if (StringUtils.isNotBlank(objectKey)) {
            String newObjectKey = CloudOssUtils.handleFirstCharacter(objectKey, "/");
            List<CloudOssBucketProperties> buckets = cloudOssProperties.getBuckets();
            for (CloudOssBucketProperties bucket : buckets) {
                if (bucket.isPathStyleAccessEnabled()) {
                    if (newObjectKey.startsWith(bucket.getBucketName())) {
                        return bucket;
                    }
                }
            }
        }
        return defaultBucketProperties;
    }

    protected String resolvePathToObjectKey(CloudOssBucketProperties bucketProperties, String path) {
        List<String> prefix = new ArrayList<>(4);
        prefix.add(bucketProperties.getBucketName());

        String key = path;
        for (String s : prefix) {
            key = key.replace(s + "/", "");
        }
        return key;
    }

    protected String handlePreviewUrl(CloudOssBucketProperties bucketProperties, String key) {
        return CloudOssUtils.appendFullPath(bucketProperties.getCustomEndpoint(), key);
    }

    protected CloudOssBucketProperties getBucketPropertiesByAcl(String acl) {
        List<CloudOssBucketProperties> buckets = cloudOssProperties.getBuckets();
        for (CloudOssBucketProperties bucket : buckets) {
            if (bucket.isEnablePresigned() && PRI_ACL.equals(acl)) {
                return bucket;
            }
            if (!bucket.isEnablePresigned() && PUB_ACL.equals(acl)) {
                return bucket;
            }
        }
        return defaultBucketProperties;
    }



    public void load() throws Exception {
        List<CloudOssBucketProperties> buckets = cloudOssProperties.getBuckets();
        if (buckets == null || buckets.isEmpty()) {
            throw new RuntimeException("Missing bucket information");
        }
        CLIENT_MAP = new HashMap<>();

        CloudOssBucketProperties defaultBucketProperties = null;
        for (CloudOssBucketProperties bucketProperties : buckets) {

            if (defaultBucketProperties == null) {
                if (bucketProperties.isEnablePresigned()) {
                    defaultBucketProperties = bucketProperties;
                }
            }

            String region = StringUtils.isBlank(bucketProperties.getRegion()) ?  "us-east-1" : bucketProperties.getRegion();
            bucketProperties.setRegion(region);

            Supplier<T> client = createClient(bucketProperties);

            CLIENT_MAP.put(bucketProperties.getBucketName(), client);
        }

        if (defaultBucketProperties == null) {
            defaultBucketProperties = buckets.get(0);
        }
        setDefaultBucketProperties(defaultBucketProperties);
    }

    protected abstract Supplier<T> createClient(CloudOssBucketProperties bucketProperties);

    protected Supplier<T> getClient(String bucketName) {
        return CLIENT_MAP.get(bucketName);
    }
}
