package com.isuzuki.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * 平摊算法
 * @author : 
 * @date : 2025/7/25
 * @description :
 */
public class AverageAndPadRemainingToTail {
    /**
     * 将金额平均分配给指定数量的人
     * @param totalAmount 总金额
     * @param size 人数
     * @return 每个人分到的金额数组
     */
    public static BigDecimal[] splitAmountEvenly(BigDecimal totalAmount, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("人数必须大于0");
        }

        BigDecimal[] result = new BigDecimal[size];
        // 计算平均值，保留2位小数，四舍五入
        BigDecimal average = totalAmount.divide(new BigDecimal(size), 2, RoundingMode.HALF_UP);

        // 前n-1个人都分到平均值
        for (int i = 0; i < size - 1; i++) {
            result[i] = average;
        }

        // 最后一个人分到剩余金额，避免因四舍五入导致总额不一致
        BigDecimal allocated = average.multiply(new BigDecimal(size - 1));
        result[size - 1] = totalAmount.subtract(allocated);

        return result;
    }

    /**
     * 将金额平均分配给指定数量的人（处理分钱问题）
     * @param totalAmount 总金额（以分为单位，避免浮点精度问题）
     * @param size 数量
     * @return 每个人分到的金额（分）
     */
    public static long[] splitAmountInCents(long totalAmount, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("人数必须大于0");
        }

        long[] result = new long[size];
        long average = totalAmount / size;
        long remainder = totalAmount % size;

        // 前remainder个人多分1分钱
        for (int i = 0; i < size; i++) {
            result[i] = i < remainder ? average + 1 : average;
        }

        return result;
    }

    /**
     * 将金额平均分配给指定数量的人（处理分钱问题）
     * @param totalAmount 总金额（以分为单位，避免浮点精度问题）
     * @param size 数量
     * @return 每个人分到的金额（分）
     */
    public static int[] splitAmountInCents(int totalAmount, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("人数必须大于0");
        }

        int[] result = new int[size];
        int average = totalAmount / size;
        int remainder = totalAmount % size;

        // 前remainder个人多分1分钱
        for (int i = 0; i < size; i++) {
            result[i] = i < remainder ? average + 1 : average;
        }

        return result;
    }

    public static void main(String[] args) {
        // 测试BigDecimal版本
        BigDecimal total = new BigDecimal("100.00");
        int people = 3;
        BigDecimal[] amounts = splitAmountEvenly(total, people);
        System.out.println("BigDecimal版本分配结果: " + Arrays.toString(amounts));

        // 测试分版本（以分为单位）
        int totalCents = 10000; // 100元=10000分
        int people2 = 3;
        int[] cents = splitAmountInCents(totalCents, people2);
        System.out.println("分版本分配结果: " + Arrays.toString(cents));
    }
}
