package com.isuzuki.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PinyinUtils {

    /**
     * 返回拼音
     *
     * @param chinese
     * @return [allPinyin, firstCharPinyin]
     */
    public static String[] toPinyinAndFirstChar(String chinese) {
        List<String> array = toPinyinArray(chinese);
        if (array == null || array.size() == 0) {
            return new String[]{"", ""};
        }
        StringBuilder firstChar = new StringBuilder();
        for (String s : array) {
            firstChar.append(s.toCharArray()[0]);
        }

        String allPinyin = array.stream().collect(Collectors.joining());
        return new String[]{allPinyin, firstChar.toString()};
    }

    /**
     * 获取字符串拼音的第一个字母
     * @param chinese
     * @return
     */
    public static String toFirstChar(String chinese){
        return toPinyinAndFirstChar(chinese)[1];
    }

    /**
     * 汉字转为拼音
     * @param chinese
     * @return
     */
    public static String toPinyin(String chinese) {
        return toPinyinAndFirstChar(chinese)[0];
    }

    public static List<String> toPinyinArray(String chinese) {
        if (StringUtils.isBlank(chinese)) {
            return Collections.EMPTY_LIST;
        }
        List<String> pingyinList = new ArrayList<>();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    String[] strings = PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat);
                    if (strings == null || strings.length <= 0) {
                        pingyinList.add(newChar[i] + "");
                        continue;
                    }
                    pingyinList.add(strings[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {

                }
            } else {
                pingyinList.add(newChar[i] + "");
            }
        }
        return pingyinList;
    }

}
