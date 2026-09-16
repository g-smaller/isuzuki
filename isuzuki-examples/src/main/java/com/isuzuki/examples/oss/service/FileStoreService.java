package com.isuzuki.examples.oss.service;

import com.isuzuki.oss.api.event.CloudOssUploadEvent;

/**
 * @author : 
 * @date : 2025/11/12
 * @description :
 */
public interface FileStoreService {

    void store(CloudOssUploadEvent event);

}
