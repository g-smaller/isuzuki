package com.isuzuki.oss.api;

public enum CloudOssAccessControlList {
    Private("private"),


    PublicRead("public-read"),

    ;

    private String acl;

    private CloudOssAccessControlList(String acl) {
        this.acl = acl;
    }

    @Override
    public String toString() {
        return this.acl;
    }

    public String getAcl() {
        return acl;
    }
}
