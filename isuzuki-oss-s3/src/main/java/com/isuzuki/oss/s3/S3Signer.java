package com.isuzuki.oss.s3;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

public class S3Signer {
    /**
     * SigV4 签名链：AWS4 + secretKey → date → region → s3 → aws4_request
     * @param secretKey
     * @param dateStamp
     * @param region
     * @return
     */
    public static String postPresignV4(String secretKey, String dateStamp, String region, String data) {
        byte[] kSecret  = ("AWS4" + secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] kDate    = hmac(kSecret, dateStamp);
        byte[] kRegion  = hmac(kDate, region);
        byte[] kService = hmac(kRegion, "s3");

        byte[] aws4Requests = hmac(kService, "aws4_request");
        return hmacHex(aws4Requests, data);
    }

    private static byte[] hmac(byte[] key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String hmacHex(byte[] key, String data) {
        byte[] sig = hmac(key, data);
        StringBuilder sb = new StringBuilder(sig.length * 2);
        for (byte b : sig) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16));
            sb.append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }

}
