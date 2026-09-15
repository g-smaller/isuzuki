package com.isuzuki.oss.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.common.auth.ServiceSignature;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.PolicyConditions;
import com.isuzuki.oss.api.CloudOssBucketProperties;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AliYunClientUtils {

    public static Map<String, String> getPresignedPostFormData(CloudOssBucketProperties bucketProperties, String objectKey, long maxSizeBytes, Map<String, String> meta) {
        long expireTimeMillis = System.currentTimeMillis() + bucketProperties.getUploadExpireTime();
        String encodePostPolicy = getEncodePostPolicy(bucketProperties.getBucketName(), objectKey, expireTimeMillis, maxSizeBytes, meta);
        String postSignature = getPostSignature(encodePostPolicy, bucketProperties.getSecretKey());

        Map<String, String> formData = new HashMap<>();
        formData.put(AliYunFields.FORM_POLICY, encodePostPolicy);
        formData.put(AliYunFields.FORM_SIGNATURE, postSignature);
        return formData;
    }

    private static String getPostSignature(String postPolicy, String secretKey) {
        return ServiceSignature.create().computeSignature(secretKey, postPolicy);
    }

    private static String getEncodePostPolicy(String bucket, String objectKey, long expireTimeMillis, long maxSizeBytes, Map<String, String> meta) {

        PolicyConditions policyConds = new PolicyConditions();

        if (maxSizeBytes > 0) {
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSizeBytes);
        }
        // policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, handlePolicyCondCodeKey(dir));
        policyConds.addConditionItem(PolicyConditions.COND_KEY, objectKey);
        // policyConds.addConditionItem("bucket", bucket);
        if (meta != null && meta.size() > 0) {
            Set<Map.Entry<String, String>> entries = meta.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                policyConds.addConditionItem(entry.getKey(), entry.getValue());
            }
        }

        // policyConds.addConditionItem();
        Date expiration = new Date(expireTimeMillis);
        String formatedExpiration = DateUtil.formatIso8601Date(expiration);
        String jsonizedExpiration = String.format("\"expiration\":\"%s\"", formatedExpiration);
        String jsonizedConds = policyConds.jsonize();

        StringBuilder postPolicy = new StringBuilder();
        postPolicy.append(String.format("{%s,%s}", jsonizedExpiration, jsonizedConds));


        try {
            byte[] binaryData = postPolicy.toString().getBytes(OSSConstants.DEFAULT_CHARSET_NAME);
            return BinaryUtil.toBase64String(binaryData);
        } catch (UnsupportedEncodingException e) {
            throw new ClientException("Unsupported charset: " + e.getMessage());
        }
    }
}
