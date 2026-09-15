package com.isuzuki.utils;


public final class SystemUtils {

    public static String getProjectRoot(){
        return System.getProperty(JavaConsts.Property.USER_HOME);
    }

    /**
     * {@link #getCurrentDir()}
     * @return
     */
    public static String getUserDir() {
        return System.getProperty(JavaConsts.Property.USER_DIR);
    }

    /**
     * {@link #getUserDir()}
     * @return
     */
    public static String getCurrentDir() {
        return System.getenv(JavaConsts.Environment.PWD);
    }

    public static String getUserHome(){
        return System.getProperty(JavaConsts.Property.USER_HOME);
    }

    public static String getHome() {
        return System.getenv(JavaConsts.Environment.HOME);
    }

    public static String getJreHome() {
        return System.getProperty(JavaConsts.Property.JAVA_HOME);
    }

    public static String getJaveHome() {
        return System.getenv(JavaConsts.Environment.JAVA_HOME);
    }

    public static String getJavaClassPath() {
        return System.getProperty(JavaConsts.Property.JAVA_CLASS_PATH);
    }


}
