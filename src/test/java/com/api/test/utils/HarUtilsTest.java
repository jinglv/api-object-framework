package com.api.test.utils;

import de.sstoehr.harreader.HarReaderException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author jingLv
 * @date 2020/12/30
 */
class HarUtilsTest {

    @Test
    void generationCase() {
        try {
            HarUtils.generationCase("src/main/resources/har/qyapi.weixin.qq.com.har", "getToken");
        } catch (HarReaderException | IOException e) {
            e.printStackTrace();
        }
    }
}