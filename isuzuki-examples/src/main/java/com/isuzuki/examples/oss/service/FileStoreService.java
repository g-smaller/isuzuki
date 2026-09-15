package com.isuzuki.examples.oss.service;

import com.isuzuki.oss.api.event.CloudOssUploadEvent;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2025/11/12
 * @description :
 */
public interface FileStoreService {

    void store(CloudOssUploadEvent event);

}
