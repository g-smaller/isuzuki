package com.isuzuki.oss.aliyun.event;

import com.aliyun.oss.common.utils.BinaryUtil;
import com.isuzuki.oss.api.event.CloudOssUploadEvent;
import com.isuzuki.oss.api.event.CloudOssUploadHandler;
import com.isuzuki.oss.api.event.CloudOssUploadObjectResult;
import com.isuzuki.utils.Jsons;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class AliYunCloudOssUploadHandler implements CloudOssUploadHandler {
    private Logger logger = LoggerFactory.getLogger(AliYunCloudOssUploadHandler.class);

    private static final String ALI_CDN_HTTPS ="https://gosspublic.alicdn.com/";
    private static final String ALI_CDN_HTTP ="http://gosspublic.alicdn.com/";

    private final RestClient restClient;
    public AliYunCloudOssUploadHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * @href {https://help.aliyun.com/zh/oss/developer-reference/callback?spm=a2c4g.11186623.0.0.1e73731cslOzTR#19f2e6eb46b27}
     * @param event
     * @return
     */
    @Override
    public CloudOssUploadObjectResult handle(CloudOssUploadEvent event) {
        String md5 = event.getHeader("content-md5");
        String requestId = event.getHeader("x-oss-request-id");

        String callbackBodyJson = Jsons.toString(event.getBody());
        boolean success = preHandle(event, callbackBodyJson);
        if (!success) {
            logger.warn("[AliYun] {} OSS callback Failure, body: {}, header: {}", requestId, callbackBodyJson, Jsons.toString(event.getHeaders()));
            throw new RuntimeException("AliYun Callback Decryption Failure!");
        }

        callbackBodyJson = callbackBodyJson.replace("\"width\":,", "")
                .replace("\"height\":,", "")
                .replace("\"size\":,", "");


        AliYunObjectCallback callbackResult = Jsons.parseObject(callbackBodyJson, AliYunObjectCallback.class);

        CloudOssUploadObjectResult objectResult = new CloudOssUploadObjectResult();
        objectResult.setProvider("AliYun");
        objectResult.setFileId(callbackResult.getFileId());
        objectResult.setAppId(callbackResult.getAppId());
        objectResult.setBizType(callbackResult.getBizType());
        objectResult.setFilename(callbackResult.getFilename());
        objectResult.setBucket(callbackResult.getBucket());
        objectResult.setObjectKey(callbackResult.getObject());
        objectResult.setSize(callbackResult.getSize());
        objectResult.setWidth(callbackResult.getWidth());
        objectResult.setHeight(callbackResult.getHeight());
        objectResult.setFormat(callbackResult.getFormat());
        objectResult.setMimeType(callbackResult.getMimeType());
        objectResult.setEtag(callbackResult.getEtag());
        objectResult.setFileMd5(md5);
        objectResult.setVersionId(callbackResult.getEtag());
        objectResult.setAcl(callbackResult.getAcl());
        objectResult.setClientIp(callbackResult.getClientIp());
        objectResult.setPrincipal(callbackResult.getPrincipal());
        objectResult.setTraceId(callbackResult.getTraceId());
        objectResult.setS3RequestId(requestId);
        return objectResult;
    }

    private boolean preHandle(CloudOssUploadEvent event, String callbackBodyJson) {
        String authorization = event.getHeader("Authorization");
        String pubKeyUrl = event.getHeader("x-oss-pub-key-url");
        byte[] authorizationByte = BinaryUtil.fromBase64String(authorization);
        byte[] pubKeyUrlByte = BinaryUtil.fromBase64String(pubKeyUrl);
        String pubKeyAddr = new String(pubKeyUrlByte);
        if (!pubKeyAddr.startsWith(ALI_CDN_HTTPS) && !pubKeyAddr.startsWith(ALI_CDN_HTTP)) {
            logger.info("[AliYun] pub key addr must be oss address");
            return false;
        }
        String publicKey = getPublicKey(pubKeyAddr);
        publicKey = publicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKey = publicKey.replace("-----END PUBLIC KEY-----", "");
        String queryString = event.getQueryString();
        String uri = event.getUri();
        String decodeUri = null;
        try {
            decodeUri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info("[AliYun] uri decode error", e);
            throw new RuntimeException(e);
        }
        String authStr = decodeUri;
        if (StringUtils.isNotBlank(queryString)) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + callbackBodyJson;
        return doCheck(authStr, authorizationByte, publicKey);
    }

    private boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(sign);
        } catch (Exception e) {
            logger.error("[AliYun] check public key error", e);
            throw new RuntimeException(e);
        }
    }

    private String getPublicKey(String url) {
        return restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }


    @Override
    public boolean support(CloudOssUploadEvent event) {
        return "AliYun".equals(event.getProvider());
    }
}
