package com.isuzuki.utils;

import java.io.File;
import java.net.URL;

public class ClassResourceUtils {

    private static final URL ROOT_URL;
    private static final String CLASS_PATH;

    private static final String FILE_SCHEME_PATH;

    static {
        ROOT_URL = ClassResourceUtils.class.getResource("/");
        File file = new File(ROOT_URL.getPath());
        CLASS_PATH = file.getAbsolutePath();
        FILE_SCHEME_PATH = ROOT_URL.toString();
    }

    public static String getFileSchemePath() {
        return FILE_SCHEME_PATH;
    }

    public static String getClassPath() {
        return CLASS_PATH;
    }

}
