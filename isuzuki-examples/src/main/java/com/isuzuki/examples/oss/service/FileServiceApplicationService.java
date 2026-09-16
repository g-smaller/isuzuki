package com.isuzuki.examples.oss.service;

import com.isuzuki.examples.oss.api.FileUploadPolicyVo;
import com.isuzuki.examples.oss.enums.ResolvePolicyEnum;
import com.isuzuki.examples.oss.support.FileServiceAppProperties;
import com.isuzuki.examples.oss.support.FileServiceProperties;
import com.isuzuki.oss.api.*;
import com.isuzuki.utils.Jsons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 
 * @date : 2021/11/16
 * @description :
 */
@Component
public class FileServiceApplicationService {

    private final Logger log = LoggerFactory.getLogger(FileServiceApplicationService.class);

    private CloudOssCredentialTemplate cloudOssCredentialTemplate;
    private FileServiceProperties fileServiceProperties;

    public FileServiceApplicationService(CloudOssCredentialTemplate cloudOssCredentialTemplate,
                                         FileServiceProperties fileServiceProperties) {
        this.cloudOssCredentialTemplate = cloudOssCredentialTemplate;
        this.fileServiceProperties = fileServiceProperties;
    }

    public FileUploadPolicyVo handleOnlinePrivate(String appId, String filename, String bizType) {

        return handle(appId, filename, bizType, ResolvePolicyEnum.ONLINE_PRIVATE);
    }

    public FileUploadPolicyVo handleGuestPrivate(String appId, String filename, String bizType) {
        return handle(appId, filename, bizType, ResolvePolicyEnum.GUEST_PRIVATE);
    }

    public FileUploadPolicyVo handleOnlinePublic(String appId, String filename, String bizType) {
        return handle(appId, filename, bizType, ResolvePolicyEnum.ONLINE_PUBLIC);
    }

    public FileUploadPolicyVo handleGuestPublic(String appId, String filename, String bizType) {
        return handle(appId, filename, bizType, ResolvePolicyEnum.GUEST_PUBLIC);
    }

    private FileUploadPolicyVo handle(String appId, String filename, String bizType, ResolvePolicyEnum policy) {
        FileServiceAppProperties fileServiceAppProperties = getFileServiceProperties(appId);

        if (fileServiceAppProperties == null) {
            throw new RuntimeException("上传凭证失败");
        }
        String requestId = "";
        String fileId = "";
        String patentPath = policy.doPrivate() ? fileServiceAppProperties.getPriPath() : fileServiceAppProperties.getPubPath();
        String objectKey = ObjectKeyGenerator.DEFAULT.generateObjectKey(ObjectKeyGeneratorArgs.of().parentPath(patentPath).originFilename(filename));

        GetUploadObjectCredentialRequest request = new GetUploadObjectCredentialRequest();

        request.objectKey(objectKey)
                .lengthLimit(0, fileServiceAppProperties.getMaxSize().toBytes())
                .enablePrivate(policy.doPrivate())
                .addCondition(GetObjectPolicyCondition.META_FILE_Id, fileId)
                .addCondition(GetObjectPolicyCondition.META_PRINCIPAL, getCurrentUserId())
                .addCondition(GetObjectPolicyCondition.META_APP_ID, appId)
                .addCondition(GetObjectPolicyCondition.META_TRACE_ID, requestId)
                .addCondition(GetObjectPolicyCondition.META_FILE_NAME, filename)
                .addCondition(GetObjectPolicyCondition.META_UPLOAD_TYPE, "WEB");

        GetUploadObjectCredential getCloudOssUploadCredential = cloudOssCredentialTemplate.generateCredential(request);

        Map<String, String> headers = new HashMap<>();
        if (getCloudOssUploadCredential.getHeaders() != null) {
            headers.putAll(getCloudOssUploadCredential.getHeaders());
        }
        // headers.put("x-amz-request-id", requestId);

        FileUploadPolicyVo vo = new FileUploadPolicyVo();
        vo.setUploadUrl(getCloudOssUploadCredential.getUrl());
        vo.setUploadMethod(getCloudOssUploadCredential.getMethod());
        vo.setUploadMaxSize(fileServiceAppProperties.getMaxSize().toKilobytes());
        vo.setObjectKey(getCloudOssUploadCredential.getObjectKey());
        vo.setFileId(fileId);
        vo.setFileUrl(getCloudOssUploadCredential.getFileUrl());
        vo.setSuccessStatusCode(getCloudOssUploadCredential.getSuccessStatusCode());
        vo.setHeaders(headers);
        vo.setMetas(getCloudOssUploadCredential.getMetas());

        log.info("{}", Jsons.toString(vo));
        return vo;
    }

    public String getCurrentUserId() {
        return "GUEST";
    }

    private FileServiceAppProperties getFileServiceProperties(String appId) {
        FileServiceAppProperties fileServiceAppProperties = null;
        if (fileServiceProperties.getApp() != null) {
            fileServiceAppProperties = fileServiceProperties.getApp().get(appId);
        }
        if (fileServiceAppProperties == null) {
            fileServiceAppProperties = fileServiceProperties.getApp().get("default");
        }
        return fileServiceAppProperties;
    }
}
