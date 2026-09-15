package com.isuzuki.examples.oss.enums;

/**
 * @author : Guo QuanYing (guoquanying@cmvalue.com)
 * @date : 2024/12/20
 * @description :
 */
public enum ConvertMimeType {

    UNKNOWN(false, "", "", "", false),
    HEIC("image/heic", "heic", "heic", true),
    TIFF("image/tiff", "tiff", "tif", false)
    ;
    private boolean convert;
    private String mimeType;
    private String format;
    private String suffix;
    private boolean useNewObjectKey;

    ConvertMimeType(String mimeType, String format, String suffix, boolean useNewObjectKey) {
        this(true, mimeType, format, suffix, useNewObjectKey);
    }

    ConvertMimeType(boolean convert, String mimeType, String format, String suffix, boolean useNewObjectKey) {
        this.convert = convert;
        this.mimeType = mimeType;
        this.format = format;
        this.suffix = suffix;
        this.useNewObjectKey = useNewObjectKey;
    }

    public boolean isConvert() {
        return convert;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFormat() {
        return format;
    }

    public String getSuffix() {
        return suffix;
    }

    public boolean isUseNewObjectKey() {
        return useNewObjectKey;
    }

    public static ConvertMimeType needConvert(String format, String mimeType) {
        ConvertMimeType[] values = ConvertMimeType.values();
        for (ConvertMimeType value : values) {
            if (value.equals(UNKNOWN)) {
                continue;
            }
            if (value.mimeType.equalsIgnoreCase(mimeType) || value.format.equalsIgnoreCase(format)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
