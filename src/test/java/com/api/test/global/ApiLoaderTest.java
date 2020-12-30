package com.api.test.global;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author jingLv
 * @date 2020/12/30
 */
class ApiLoaderTest {

    public static final Logger logger = LoggerFactory.getLogger(ApiLoaderTest.class);


    @BeforeAll
    static void beforeAll() throws IOException {
        ApiLoader.load("src/main/resources/api");
        logger.info("debugger!");
    }


    @Test
    void getAction() {
        ArrayList<String> actualParameter = new ArrayList<>();
        actualParameter.add("xiaohong");
        actualParameter.add("123123");
        Objects.requireNonNull(ApiLoader.getAction("UserLogin", "get_token")).run(actualParameter);
    }
}