package com.isuzuki.oss.api;

public class CloudOssBucketProperties extends CloudOssKeyProperties {
    /**
     * pathStyleAccess 是 S3 协议里控制‌访问地址格式‌的配置项，跟医生没关系。
     *
     * S3 协议访问对象存储时，桶（Bucket）在 URL 里有两种写法：
     *
     * Path-Style（路径风格）‌：https://域名/桶名/对象，比如 http://192.168.1.100:9000/mybucket/file.txt。
     * Virtual-Host（虚拟主机风格）‌：https://桶名.域名/对象，比如 https://mybucket.s3.amazonaws.com/file.txt。‌
     * pathStyleAccess 设为 true 就是用第一种路径风格，设为 false 就是用第二种虚拟主机风格。‌
     *
     * 什么时候要特别注意？用 MinIO、RustFS 这类自建存储，或者 IP+端口 访问‌时，通常必须设成 true，否则请求会找不到桶。而 AWS S3、阿里云 OSS 这类云厂商，一般用虚拟主机风格，设 false 就行
     */
    private boolean pathStyleAccessEnabled = true;

    private String region;
    private String bucketName;
    /**
     * 自定义域名
     * 如果没有设置, 会使用 bucketName.endpoint 拼接使用
     */
    private String customEndpoint;
    private int presignedExpireTime = 360000;
    /**
     * 是否启动签名
     * true: 私有文件
     * false: 共有文件
     */
    private boolean enablePresigned = true;

    private int uploadExpireTime = 360000;

    public boolean isPathStyleAccessEnabled() {
        return pathStyleAccessEnabled;
    }

    public void setPathStyleAccessEnabled(boolean pathStyleAccessEnabled) {
        this.pathStyleAccessEnabled = pathStyleAccessEnabled;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getCustomEndpoint() {
        return customEndpoint;
    }

    public void setCustomEndpoint(String customEndpoint) {
        this.customEndpoint = customEndpoint;
    }

    public int getPresignedExpireTime() {
        return presignedExpireTime;
    }

    public void setPresignedExpireTime(int presignedExpireTime) {
        this.presignedExpireTime = presignedExpireTime;
    }

    public boolean isEnablePresigned() {
        return enablePresigned;
    }

    public void setEnablePresigned(boolean enablePresigned) {
        this.enablePresigned = enablePresigned;
    }

    public int getUploadExpireTime() {
        return uploadExpireTime;
    }

    public void setUploadExpireTime(int uploadExpireTime) {
        this.uploadExpireTime = uploadExpireTime;
    }
}
