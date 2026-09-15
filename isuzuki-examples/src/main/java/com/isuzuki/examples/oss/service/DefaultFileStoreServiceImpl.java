package com.isuzuki.examples.oss.service;


import com.isuzuki.examples.oss.repository.FileStoreInfo;
import com.isuzuki.examples.oss.repository.FileStoreInfoRepository;
import com.isuzuki.oss.api.event.CloudOssUploadEvent;
import com.isuzuki.oss.api.event.CloudOssUploadHandler;
import com.isuzuki.oss.api.event.CloudOssUploadObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2025/11/12
 * @description :
 */
@Service
public class DefaultFileStoreServiceImpl implements FileStoreService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final FileStoreInfoRepository repository;
    private final List<CloudOssUploadHandler>  handlers;

    public DefaultFileStoreServiceImpl(FileStoreInfoRepository repository,
                                       List<CloudOssUploadHandler>  handlers) {
        this.repository = repository;
        this.handlers = handlers;
    }

    @Override
    public void store(CloudOssUploadEvent event) {

        CloudOssUploadObjectResult objectResult = null;
        for (CloudOssUploadHandler handler : handlers) {
            if (handler.support(event)) {
                objectResult = handler.handle(event);
            }
        }

        FileStoreInfo fsFileInfo = new FileStoreInfo();
        fsFileInfo.setProvider(objectResult.getProvider());
        fsFileInfo.setFileId(objectResult.getFileId());
        fsFileInfo.setAppId(objectResult.getAppId());
        fsFileInfo.setBizType(objectResult.getBizType());
        fsFileInfo.setFilename(objectResult.getFilename());
        fsFileInfo.setBucket(objectResult.getBucket());
        fsFileInfo.setObjectKey(objectResult.getObjectKey());
        fsFileInfo.setSize(objectResult.getSize());
        fsFileInfo.setWidth(objectResult.getWidth());
        fsFileInfo.setHeight(objectResult.getHeight());
        fsFileInfo.setFormat(objectResult.getFormat());
        fsFileInfo.setMimeType(objectResult.getMimeType());
        fsFileInfo.setEtag(objectResult.getEtag());
        fsFileInfo.setFileMd5(objectResult.getFileMd5());
        fsFileInfo.setVersionId(objectResult.getVersionId());
        fsFileInfo.setAcl(objectResult.getAcl());
        fsFileInfo.setClientIp(objectResult.getClientIp());
        fsFileInfo.setS3RequestId(objectResult.getS3RequestId());
        fsFileInfo.setTraceId(objectResult.getTraceId());
        fsFileInfo.setCreateBy(objectResult.getPrincipal());
        fsFileInfo.setUpdateBy(objectResult.getPrincipal());
        fsFileInfo.setRecordStatus(1);
        repository.save(fsFileInfo);
    }

}
