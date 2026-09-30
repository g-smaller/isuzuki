package com.isuzuki.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressUtils {
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {

        }
        return "unknown host name";
    }

    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
        return "unknown host name";
    }
}
