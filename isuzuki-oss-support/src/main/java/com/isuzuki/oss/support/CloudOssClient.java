package com.isuzuki.oss.support;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface CloudOssClient {

    /**
     * 上传文件至默认的bucket下。
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     * @param file        文件信息
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(MultipartFile file, String objectKey, boolean privateFile);
    /**
     * 上传文件至指定的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     * @param bucketName  储存文件的bucket信息
     * @param file        文件信息
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(MultipartFile file, String bucketName, String objectKey, boolean privateFile);


    /**
     * 从指定url抓取文件并上传文件至默认的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     * @param url         源文件url
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(String url, String objectKey, boolean privateFile);

    /**
     * 从指定url抓取文件并上传文件至指定的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     * @param url         源文件url
     * @param bucketName  储存文件的bucket信息
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(String url, String bucketName, String objectKey, boolean privateFile);


    /**
     * 上传文件至默认的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     * @param bytes       文件内容字节
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(byte[] bytes, String objectKey, boolean privateFile);

    /**
     * 上传文件至指定的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     *
     * @param bytes       文件内容字节
     * @param bucketName  储存文件的bucket信息
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(byte[] bytes, String bucketName, String objectKey, boolean privateFile);

    /**
     * 上传文件至默认的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     * @param file        文件
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(File file, String objectKey, boolean privateFile);

    /**
     * 上传文件至指定的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     *
     * @param file        文件
     * @param bucketName  储存文件的bucket信息
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(File file, String bucketName, String objectKey, boolean privateFile);

    /**
     * 上传文件至默认的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     * @param inputStream 文件流信息
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(InputStream inputStream, String objectKey, boolean privateFile);

    /**
     * 上传文件至指定的bucket下
     * 若上传失败会直接抛出异常，不会返回上传结果信息。
     *
     *
     * @param inputStream 文件流信息
     * @param bucketName  储存文件的bucket信息
     * @param objectKey   存储文件的目录地址
     * @param privateFile 是否为隐私文件
     * @return 文件在OSS中bucket下的目录地址, 形如：file/666f726d/2022/0209/7062666709991161862.jpg
     */
    ObjectUploadResult uploadFile(InputStream inputStream, String bucketName, String objectKey, boolean privateFile);


    String generateSignedUrl(ObjectSignedRequest request);

}
