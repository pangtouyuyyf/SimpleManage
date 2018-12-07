package com.simple.manage.system.util;

import java.util.Random;

/**
 * Description 获取指定位数随机数字符串
 * Author chen
 * CreateTime 2018-07-27 15:44
 **/

public class RandomNumUtil {
    /**
     * 获取固定位数的随机数字符串
     *
     * @param charCount
     * @return
     */
    public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    /**
     * 获取某区间内随机数
     *
     * @param from
     * @param to
     * @return
     */
    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
