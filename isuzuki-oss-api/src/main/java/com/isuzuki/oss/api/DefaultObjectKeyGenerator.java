package com.isuzuki.oss.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

public class DefaultObjectKeyGenerator implements ObjectKeyGenerator {


    @Override
    public String generateObjectKey(ObjectKeyGeneratorArgs args) {
        String parentPath = args.getParentPath();
        String filenamePrefix = StringUtils.isBlank(args.getFilenamePrefix()) ? UUID.randomUUID().toString().replace("-", "") + "-" + System.nanoTime() : args.getFilenamePrefix();
        String originFilename = args.getOriginFilename();

        if (StringUtils.isBlank(parentPath) || "/".equals(parentPath)) {
            return String.format("%s/%s", generateSubPath(), generateFileName(filenamePrefix, originFilename));
        }
        return String.format("%s/%s/%s", parentPath, generateSubPath(), generateFileName(filenamePrefix, originFilename));
    }


    private static String generateSubPath() {
        return DateFormatUtils.format(new Date(),"yyyyMMdd");
    }

    private static String generateFileName(String filenamePrefix, String originFilename) {
        final String filenameSuffix = generateFilenameSuffix(originFilename);
        if (StringUtils.isBlank(originFilename)) {
            return String.format("%s-%s", filenamePrefix,
                    Integer.toUnsignedLong(hash(filenameSuffix)));
        }
        String fileExt = getFileExt(originFilename);

        return String.format("%s-%s.%s", filenamePrefix,
                Integer.toUnsignedLong(hash(filenameSuffix)),
                fileExt);
    }

    public static String generateFilenameSuffix(String originFilename) {
        String randomKey = RandomStringUtils.secureStrong().nextAlphanumeric(128);
        return (StringUtils.isBlank(originFilename) ? "" : originFilename) + randomKey + System.currentTimeMillis();
    }

    public static int hash(String value) {
        if (StringUtils.isNotBlank(value)) {
            int hash = 0;
            char val[] = value.toCharArray();
            for (char aVal : val) {
                hash = 31 * hash + aVal;
            }
            return hash;
        }
        return 0;
    }

    private static String getFileExt(String originFilename) {
        if (StringUtils.isBlank(originFilename)) {
            return "";
        }
        int i = originFilename.lastIndexOf(".");
        if (i > 0) {
            return originFilename.substring(i + 1);
        }
        return "";
    }
}
