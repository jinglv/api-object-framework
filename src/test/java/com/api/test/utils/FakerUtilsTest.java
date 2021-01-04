package com.api.test.utils;

import org.junit.jupiter.api.Test;

/**
 * @author jingLv
 * @date 2021/01/04
 */
class FakerUtilsTest {

    @Test
    void getRandomStringNum() {
        String randomStringNum = FakerUtils.getRandomStringNum(2);
        System.out.println(randomStringNum);
    }
}