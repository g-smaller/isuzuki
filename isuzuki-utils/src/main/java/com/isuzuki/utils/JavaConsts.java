package com.isuzuki.utils;

public class JavaConsts {

    public interface Property {
        String JAVA_RUNTIME_NAME                = "java.runtime.name";
        String JAVA_RUNTIME_VERSION             = "java.runtime.version";
        String JAVA_VERSION                     = "java.version";

        String SUN_BOOT_LIBRARY_PATH            = "sun.boot.library.path";
        String JAVA_VM_VERSION                  = "java.vm.version";
        String GOPHER_PROXY_SET                 = "gopherProxySet";
        String JAVA_VM_VENDOR                   = "java.vm.vendor";
        String JAVA_VENDOR_URL                  = "java.vendor.url";

        // like :
        String PATH_SEPARATOR                   = "path.separator";
        // like \n
        String LINE_SEPARATOR                   = "line.separator";
        // like /
        // windows \\
        String FILE_SEPARATOR                   = "file.separator";

        String JAVA_VM_NAME                     = "java.vm.name";
        String FILE_ENCODING_PKG                = "file.encoding.pkg";

        String SUN_JAVA_LAUNCHER                = "sun.java.launcher";
        String SUN_OS_PATCH_LEVEL               = "sun.os.patch.level";
        String JAVA_VM_SPECIFICATION_NAME       = "java.vm.specification.name";


        String JAVA_AWT_GRAPHICSENV             = "java.awt.graphicsenv";
        String ORG_JBOSS_LOGGING_PROVIDER       = "org.jboss.logging.provider";
        String JAVA_ENDORSED_DIRS               = "java.endorsed.dirs";

        String JAVA_IO_TMPDIR                   = "java.io.tmpdir";

        String JAVA_VM_SPECIFICATION_VENDOR     = "java.vm.specification.vendor";

        // 所属地区 CN
        String USER_COUNTRY                     = "user.country";
        // 当前项目目录
        String USER_DIR                         = "user.dir";
        // china default Asia/Shanghai
        String USER_TIMEZONE                    = "user.timezone";
        // 当前用户主目录
        String USER_HOME                        = "user.home";
        // 当前用户名字
        String USER_NAME                        = "user.name";
        // 当前用户使用的系统语言
        String USER_LANGUAGE                    = "user.language";

        String OS_ARCH                          = "os.arch";
        String OS_NAME                          = "os.name";
        String OS_VERSION                       = "os.version";
        // like UTF-8
        String SUN_JNU_ENCODING                 = "sun.jnu.encoding";
        // default UTF-8
        String FILE_ENCODING                    = "file.encoding";

        String JAVA_LIBRARY_PATH                = "java.library.path";
        String JAVA_SPECIFICATION_NAME          = "java.specification.name";
        String JAVA_CLASS_VERSION               = "java.class.version";
        String SUN_MANAGEMENT_COMPILER          = "sun.management.compiler";
        String HTTP_NON_PROXY_HOSTS             = "http.nonProxyHosts";
        String JAVA_AWT_PRINTERJOB              = "java.awt.printerjob";

        String JAVA_SPECIFICATION_VERSION       = "java.specification.version";
        String JAVA_VM_SPECIFICATION_VERSION    = "java.vm.specification.version";

        String JAVA_CLASS_PATH                  = "java.class.path";
        String SUN_JAVA_COMMAND                 = "sun.java.command";
        /**
         * @see {@link Environment#JAVA_HOME}
         */
        String JAVA_HOME                        = "java.home";
        String SUN_ARCH_DATA_MODEL              = "sun.arch.data.model";
        String JAVA_specification_VENDOR        = "java.specification.vendor";
        String AWT_TOOLKIT                      = "awt.toolkit";
        String JAVA_VM_INFO                     = "java.vm.info";
        // 扩展目录
        String JAVA_EXT_DIRS                    = "java.ext.dirs";
        String SUN_BOOT_CLASS_PATH              = "sun.boot.class.path";
        String JAVA_AWT_HEADLESS                = "java.awt.headless";
        String JAVA_VENDOR                      = "java.vendor";
        String JAVA_VENDOR_URL_BUG              = "java.vendor.url.bug";
        String SUN_IO_UNICODE_ENCODING          = "sun.io.unicode.encoding";
        // like little
        String SUN_CPU_ENDIAN                   = "sun.cpu.endian";
        String SOCKS_NON_PROXY_HOSTS            = "socksNonProxyHosts";
        String FTP_NON_PROXY_HOSTS              = "ftp.nonProxyHosts";
        String SUN_CPU_ISALIST                  = "sun.cpu.isalist";
    }

    public interface Environment {
        String PATH                             = "PATH";
        /**
         * @see {@link Property#JAVA_HOME}
         */
        String JAVA_HOME                        = "JAVA_HOME";
        String COMMAND_MODE                     = "COMMAND_MODE";
        String VERSIONER_PYTHON_VERSION         = "VERSIONER_PYTHON_VERSION";
        /**
         * @see {@link Property#USER_NAME}
         */
        String LOGNAME                          = "LOGNAME";
        String PWD                              = "PWD";
        String SHELL                            = "SHELL";
        String PAGER                            = "PAGER";
        String LSCOLORS                         = "LSCOLORS";
        String OLDPWD                           = "OLDPWD";
        String SECURITYSESSIONID                = "SECURITYSESSIONID";
        /**
         * @see {@link Property#USER_NAME}
         */
        String USER                             = "USER";
        /**
         * @see {@link Property#JAVA_IO_TMPDIR}
         */
        String TMPDIR                           = "TMPDIR";
        String LESS                             = "LESS";
        /**
         * @see {@link Property#USER_HOME}
         */
        String HOME                             = "HOME";
    }


}
