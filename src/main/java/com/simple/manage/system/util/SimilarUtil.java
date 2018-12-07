package com.simple.manage.system.util;

/**
 * Description 字符串相似度比较工具类
 * Author chen
 * CreateTime 2018-06-07 16:20
 **/

public class SimilarUtil {
    /**
     * 比较两个字符串相似程度
     *
     * @param str1
     * @param str2
     * @return
     */
    public static float levenshtein(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        //建立数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];

        //赋初值
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }

        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }

        //取数组右下角的值，同样不同位置代表不同字符串的比较，计算相似度
        float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        return similarity;
    }

    /**
     * 得到最小值
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static int min(int a, int b, int c) {
        return a >= b ? (b >= c ? c : b) : (a >= c ? c : a);
    }
}
