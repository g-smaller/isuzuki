package com.isuzuki.oss.s3;

import com.isuzuki.utils.Jsons;
import org.apache.commons.lang3.StringUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class S3PolicyContext {
    public static final ZoneId UTC = ZoneId.of("Z");
    public static final DateTimeFormatter SIGNER_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US).withZone(UTC);
    public static final DateTimeFormatter AMZ_DATE_FORMAT  = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'", Locale.US).withZone(UTC);
    public static final DateTimeFormatter EXPIRATION_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).withZone(UTC);

    public static final DateTimeFormatter RESPONSE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss'.'SSS'Z'", Locale.US).withZone(UTC);
    public static final String ALGORITHM = "AWS4-HMAC-SHA256";

    private static final String EQ = "eq";
    private static final String STARTS_WITH = "starts-with";

    private String ak;
    private String sk;
    private String securityToken;
    private String region;
    private String bucketName;
    private ZonedDateTime expiration;
    private Map<String, Map<String, String>> conditions;
    private Long lowerLimit = null;
    private Long upperLimit = null;

    private S3PolicyContext (String bucketName, ZonedDateTime expiration) {
        this.bucketName = bucketName;
        this.expiration = expiration;

        Map<String, Map<String, String>> conditions = new LinkedHashMap<>();
        conditions.put(EQ, new LinkedHashMap<>());
        conditions.put(STARTS_WITH, new LinkedHashMap<>());
        this.conditions =  conditions;
    }

    public static S3PolicyContext builder(String bucketName, ZonedDateTime expiration) {
        return new S3PolicyContext(bucketName, expiration);
    }

    public S3PolicyContext expiration(ZonedDateTime expiration) {
        this.expiration = expiration;
        return this;
    }

    public S3PolicyContext accessKey(String ak) {
        this.ak = ak;
        return this;
    }

    public S3PolicyContext secretKey(String sk) {
        this.sk = sk;
        return this;
    }

    public S3PolicyContext region(String region) {
        this.region = region;
        return this;
    }

    public S3PolicyContext securityToken(String securityToken) {
        this.securityToken = securityToken;
        return this;
    }

    public S3PolicyContext addEqualsCondition(String element, String value) {
        if (element.isEmpty()) {
            throw new IllegalArgumentException("condition element cannot be empty");
        }
        conditions.get(EQ).put(element, value);
        return this;
    }

    public void addStartsWithCondition(String element, String value) {
        if (element.isEmpty()) {
            throw new IllegalArgumentException("condition element cannot be empty");
        }

        conditions.get(STARTS_WITH).put(element, value);
    }

    public S3PolicyContext addContentLengthRangeCondition(long lowerLimit, long upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        return this;
    }

    public Map<String, String> formData() {
        if (ak == null || sk == null || ak.isEmpty() || sk.isEmpty()) {
            throw new IllegalArgumentException("credentials cannot be null");
        }

        if (region == null || region.isEmpty()) {
            throw new IllegalArgumentException("region cannot be empty");
        }

        if (!conditions.get(EQ).containsKey("key") && !conditions.get(STARTS_WITH).containsKey("key")) {
            throw new IllegalArgumentException("key condition must be set");
        }

        Map<String, Object> policyMap = new HashMap<>();
        policyMap.put("expiration", expiration.format(EXPIRATION_DATE_FORMAT));
        List<List<Object>> conditionList = new LinkedList<>();
        conditionList.add(Arrays.asList(new Object[] {"eq", "$bucket", bucketName}));
        for (Map.Entry<String, Map<String, String>> condition : conditions.entrySet()) {
            for (Map.Entry<String, String> entry : condition.getValue().entrySet()) {
                conditionList.add(
                        Arrays.asList(
                                new Object[] {condition.getKey(), "$" + entry.getKey(), entry.getValue()}));
            }
        }
        if (lowerLimit != null && upperLimit != null) {
            conditionList.add(
                    Arrays.asList(new Object[] {"content-length-range", lowerLimit, upperLimit}));
        }

        ZonedDateTime utcNow = ZonedDateTime.now(UTC);

        String dateStamp = utcNow.format(SIGNER_DATE_FORMAT);
        String credential = ak + "/" + dateStamp + "/" + region + "/s3/aws4_request";

        String amzDate = utcNow.format(AMZ_DATE_FORMAT);

        conditionList.add(Arrays.asList(new Object[] {"eq", "$x-amz-algorithm", ALGORITHM}));
        conditionList.add(Arrays.asList(new Object[] {"eq", "$x-amz-credential", credential}));
        if (StringUtils.isNotBlank(securityToken)) {
            conditionList.add(
                    Arrays.asList(new Object[] {"eq", "$x-amz-security-token", securityToken}));
        }
        conditionList.add(Arrays.asList(new Object[] {"eq", "$x-amz-date", amzDate}));
        policyMap.put("conditions", conditionList);

        byte[] policyBytes = Jsons.toBytes(policyMap);

        String policy = Base64.getEncoder().encodeToString(policyBytes);

        String signature = S3Signer.postPresignV4(sk, dateStamp, region, policy);


        Map<String, String> formData = new HashMap<>();
        formData.put("x-amz-algorithm", ALGORITHM);
        formData.put("x-amz-credential", credential);
        if (StringUtils.isNotBlank(securityToken)) {
            formData.put("x-amz-security-token", securityToken);
        }
        formData.put("x-amz-date", amzDate);
        formData.put("policy", policy);
        formData.put("x-amz-signature", signature);
        return formData;
    }


}
