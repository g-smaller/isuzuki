package com.isuzuki.utils;

/**
 * OutStream 直接写入一个 int 类型，会截取其低 8 位，丢弃其高 24 位，所以，需要将基本类型转换为字节流。
 *
 * Java 采用的是 Big Endian {@href https://zh.wikipedia.org/wiki/%E5%AD%97%E8%8A%82%E5%BA%8F} 字节序.
 *
 * 所以的网络协议采用的也是 Big Endian 字节序来进行传输的。
 *
 * 因此，我们在进行数据传输时，需要先将其转换为 Big Endian 字节序；数据接收是，也需要进行相应的转换.
 *
 */
public class ByteUtils {

    public static int byteToInt(byte[] bytes) {
        int num = bytes[3];
        num |= ((bytes[2] << 8) & 0xFF00);
        num |= ((bytes[1] << 16) & 0xFF0000);
        num |= ((bytes[0] << 24) & 0xFF000000);
        return num;
    }

    public static byte[] intToByte(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static void main(String[] args) {
        int i = 100;
        byte[] bytes = intToByte(i);
        System.out.println(byteToInt(bytes));
    }

}

