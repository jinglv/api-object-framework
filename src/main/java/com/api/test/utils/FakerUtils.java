package com.api.test.utils;

import java.util.Random;

/**
 * 数据伪造工具类
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class FakerUtils {
    private final static int DELTA = 0x9fa5 - 0x4e00 + 1;

    private FakerUtils() {
    }

    /**
     * 随机获取指定长度的数字
     *
     * @param length 指定长度
     * @return 返回随机数
     */
    public static int getRandomInt(int length) {
        length = length - 1;
        return (int) ((Math.random() * 9 + 1) * Math.pow(10, (double) length));
    }

    /**
     * 随机获取指定长度的数字
     *
     * @param length 指定长度
     * @return 返回随机数
     */
    public static String getRandomStringNum(int length) {
        length = length - 1;
        return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, (double) length)));
    }

    /**
     * 随机获取指定范围的数字
     *
     * @param min 获取的随机数左边界
     * @param max 获取的随机数右边界
     * @return 返回随机数
     */
    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 随机获取指定范围的数字
     *
     * @param start 获取的随机数开始值
     * @param end   获取的随机数结束值
     * @return 随机数
     */
    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 14位订单号生成器
     *
     * @return 订单号
     */
    public static String orderNo() {
        StringBuilder cardNo = new StringBuilder("123456");
        for (int i = 0; i < 8; i++) {
            cardNo.append(getNum(0, 9));
        }
        return cardNo.toString();
    }

    private static final String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,188,185,181".split(",");

    /**
     * 电话号码生成器
     *
     * @return 电话号码
     */
    public static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;
    }

    /**
     * 获取随机汉字
     *
     * @return char
     */
    public static char getRandomHan() {
        Random ran = new Random();
        return (char) (0x4e00 + ran.nextInt(DELTA));
    }
}
