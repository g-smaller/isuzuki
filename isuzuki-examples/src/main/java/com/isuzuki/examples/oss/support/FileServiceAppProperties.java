package com.isuzuki.examples.oss.support;

import org.springframework.util.unit.DataSize;

public class FileServiceAppProperties {

    /**
     * 私有文件目录
     */
    private String priPath;
    /**
     * 共有文件目录
     */
    private String pubPath;
    /**
     * 文件大小
     */
    private DataSize maxSize = DataSize.parse("5MB");

    public String getPriPath() {
        return priPath;
    }

    public void setPriPath(String priPath) {
        this.priPath = priPath;
    }

    public String getPubPath() {
        return pubPath;
    }

    public void setPubPath(String pubPath) {
        this.pubPath = pubPath;
    }

    public DataSize getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(DataSize maxSize) {
        this.maxSize = maxSize;
    }
}
