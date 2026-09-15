package com.isuzuki.utils;

import org.apache.commons.lang3.StringUtils;


/**
 * @author :
 * @date : 2023/11/3
 * @description :
 */
public final class Strings {

    public static String emptyToDefault(String str, String def) {
        return StringUtils.isBlank(str) ? def : str;
    }

    public static String emptyToBlank(String str) {
        return emptyToDefault(str, "");
    }


    public static String phoneSafeguard(String cellphone) {
        if (StringUtils.isBlank(cellphone)) {
            return "";
        }
        return subReplace(cellphone, "****", 3, 4);
    }

    public static String idCardSafeguard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return "";
        }
        return subReplace(idCard, "************", 3, 12);
    }

    public static String nameSafeguard(String username) {
        if (StringUtils.isBlank(username)) {
            return "";
        }
        int length = username.length();
        if (length == 1) {
            return username + "*";
        }
        if (length == 2) {
            return username.charAt(0) + "*";
        }
        int subLength = length - 2;
        String replace = StringUtils.repeat('*', subLength);
        return subReplace(username, replace, 1, subLength);
    }

    public static String subReplace(String text, String replace, int offset, int length) {
        if (text == null) {
            return "";
        }
        if (text.length() < offset || text.length() < length || text.length() < (offset + length)) {
            return text;
        }
        return text.substring(0, offset) + replace + text.substring(offset + length);
    }
}
