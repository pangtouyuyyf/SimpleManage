package com.simple.manage.system.util;

/**
 * Description 二进制工具类
 * Author chen
 * CreateTime 2018-12-03 11:01
 **/

public class BinaryUtil {
    /**
     * 将固定数字转化为固定位数二进制
     *
     * @param num    目标数字
     * @param digits 位数
     */
    public static String toBinary(int num, int digits) {
        int value = 1 << digits | num;
        return Integer.toBinaryString(value).substring(1); // 0x20 | 这个是为了保证这个string长度是6位数
    }

    /**
     * 将固定数字转化为固定位数二进制并反转
     *
     * @param num    目标数字
     * @param digits 位数
     * @return
     */
    public static String reverseBinary(int num, int digits) {
        String binary = toBinary(num, digits);
        return new StringBuilder(binary).reverse().toString();
    }
}
