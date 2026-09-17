package com.isuzuki.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberToChineseFormatUtils {

    private static DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.##");

    public static String formatMoney(BigDecimal money) {
        if (money == null) {
            return "";
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(true);
        return numberFormat.format(money);
    }

    public static String formatCurrency(BigDecimal currency) {
        if (currency == null) {
            return "";
        }
        return CURRENCY_FORMAT.format(currency);
    }


    private static final String[] CN_NUM = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    private static final String[] CN_UNIT = { "仟", "佰", "拾", "" };
    private static final String[] CN_SECTION = { "亿", "万", "元" };
    private static final String[] CN_SYMBOL = { "元", "拾元", "佰元", "仟元", "万元", "亿元", "万亿" };

    public static String convertChinese(BigDecimal money) {
        return convertChinese(money.toPlainString());
    }

    public static String convertChinese(String inputStr) {
        StringBuilder resultStrBuld = new StringBuilder();
        String[] split = inputStr.split("\\.");
        String intStr = split[0];
        //处理整数部分
        try {
            if (intStr.matches("^[0]+$")) {
                //如果全是输入的 0 返回
                if(split.length==2 && split[1].matches("^[0]+$")){
                    return "零元零角零分";
                }
            }
            //去掉以整数部分为0开头的情况 eg:0000456 return 456
            intStr = cutFrontZeros(intStr);
            //按照四位进行处理进行转换，length是处理的次数
            int length = intStr.length() / 4;
            if ((intStr.length() % 4) > 0) {
                //有余数次数+1
                length++;
            }
            //获取截取的前索引
            int indexS = 0;
            int indexE = 0;
            //处理四位间隔数据
            for (int i = 0; i < length; i++) {
                //取大单位的索引
                int Bindex = 0;
                if (length < 3) {
                    Bindex = i + (3 - length);
                } else {
                    Bindex = i;
                }
                //分割索引
                if (i == 0 && ((intStr.length() % 4) > 0)) {
                    indexE = intStr.length() % 4;
                } else {
                    indexE = indexE + 4;
                }
                String substrNum = intStr.substring(indexS, indexE);
                //处理四位之间数据
                for (int j = 0; j < substrNum.length(); j++) {
                    //判断截取字符串之后是否全是0
                    boolean subStrAllzero = false;
                    //substrNum后面全是0
                    if (substrNum.substring(j, substrNum.length()).matches("^[0]+$")) {
                        subStrAllzero = true;
                    }
                    //判断这一位是否是0
                    boolean thisCharIs = false;
                    if ("0".equals(String.valueOf(substrNum.charAt(j)))) {
                        thisCharIs = true;
                    } else {
                        thisCharIs = false;
                    }
                    //取小单位索引
                    int Sindex = 0;
                    if (substrNum.length() != 4) {
                        Sindex = Sindex + (4 - substrNum.length() + j);
                    } else {
                        Sindex = j;
                    }
                    /*拼接数字对应汉字
                        如果后面全是0拼接单位，并结束循环(需要剔除0000这种情况)
                        否则拼接大写汉字,如果上一位且这一位是零不拼接汉字
                        */
                    if (subStrAllzero) {
                        if (j != 0 || i == (length - 1)) {
                            resultStrBuld.append(CN_SECTION[Bindex]);
                        }
                        break;
                    } else { //if((!lastCharIs || !thisCharIs))
                        if (resultStrBuld.toString().endsWith("零") && thisCharIs) {

                        } else {
                            resultStrBuld.append(CN_NUM[Integer.parseInt(String.valueOf(substrNum.charAt(j)))]);
                        }
                    }
                    /*
                     * 拼接单位
                     *   如果是最后一位，拼接大单位
                     *   否则拼接小单位
                     *       如果上一位是零则不拼接单位，等非零情况拼接单位
                     * */
                    if ((j == (substrNum.length() - 1))) {
                        resultStrBuld.append(CN_SECTION[Bindex]);
                    } else {
                        if (!resultStrBuld.toString().endsWith("零")) {
                            resultStrBuld.append(CN_UNIT[Sindex]);
                        }
                    }
                }
                //重置字符串截取的开始索引。
                indexS = indexE;
            }
            //@@@@@@@@@@处理整数部分 END

            //@@@@@@@@@@处理小数部分
            if (split.length == 2) {
                String point = split[1];
                if (point.length() == 1) {
                    resultStrBuld.append(CN_NUM[Integer.parseInt(String.valueOf(point.charAt(0)))]).append("角").append("零分");
                } else {
                    resultStrBuld.append(CN_NUM[Integer.parseInt(String.valueOf(point.charAt(0)))]).append("角").append(CN_NUM[Integer.parseInt(String.valueOf(point.charAt(1)))]).append("分");
                }
            } else {
                resultStrBuld.append("整");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
        return resultStrBuld.toString();
    }

    /**
     * 去掉第一位为0的情况
     * 0000000001.02 = 1.02
     *
     * @param str
     * @return
     */
    public static String cutFrontZeros(String str) {
        //如果全为整数部分全为0，保留一个0
        if("".equals(str.replaceAll("0",""))){
            return "0";
        }
        String s = str;
        if (str.startsWith("0")) {
            s = cutFrontZeros(str.substring(1, str.length()));
        }
        return s;
    }

}
