package com.isuzuki.oss.api;

public interface CloudOssCredentialTemplate {

    GetUploadObjectCredential generateCredential(GetUploadObjectCredentialRequest request);

}
