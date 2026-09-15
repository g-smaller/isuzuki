package com.isuzuki.oss.api;

public class ObjectKeyGeneratorArgs {
    
    private String parentPath;
    private String filenamePrefix;
    private String originFilename;

    public String getParentPath() {
        return parentPath;
    }

    public String getFilenamePrefix() {
        return filenamePrefix;
    }

    public String getOriginFilename() {
        return originFilename;
    }

    public static ObjectKeyGeneratorArgs of() {
        return new ObjectKeyGeneratorArgs();
    }

    public ObjectKeyGeneratorArgs parentPath(String parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    public ObjectKeyGeneratorArgs filenamePrefix(String filenamePrefix) {
        this.filenamePrefix = filenamePrefix;
        return this;
    }

    /**
     * 推荐赋值文件名,否则上传的文件可能没有后缀
     *
     * @param originFilename
     * @return
     */
    public ObjectKeyGeneratorArgs originFilename(String originFilename) {
        this.originFilename = originFilename;
        return this;
    }
}
