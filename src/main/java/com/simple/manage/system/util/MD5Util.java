package com.simple.manage.system.util;

import java.security.MessageDigest;

/**
 * Description MD5 工具类
 * Author chen
 * CreateTime 2018-06-07 16:14
 **/

public class MD5Util {
    private static final String MD5 = "MD5";

    /**
     * 获取 md5
     *
     * @param inStr
     * @return
     */
    public static String getMD5(String inStr) {
        /** 提供信息摘要算法的功能，如 MD5 或 SHA 算法 **/
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(MD5);
        } catch (Exception e) {
            LogUtil.error(MD5Util.class, e.toString());
            return null;
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
