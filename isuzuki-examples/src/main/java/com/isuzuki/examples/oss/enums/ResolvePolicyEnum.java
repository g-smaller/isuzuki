package com.isuzuki.examples.oss.enums;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2021/11/16
 * @description :
 */
public enum ResolvePolicyEnum {

    ONLINE_PRIVATE(false),
    GUEST_PRIVATE(false),
    ONLINE_PUBLIC(true),
    GUEST_PUBLIC(true),
    WEB_CDN(true),
    ;

    private boolean publics;

    ResolvePolicyEnum(boolean publics) {
        this.publics = publics;
    }

    public boolean doPublic() {
        return publics;
    }

    public boolean doPrivate() {
        return !publics;
    }

    public boolean eq(String acl) {
        return toString().equals(acl);
    }

}
