package com.api.test.cases;

import com.api.test.global.ApiLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author jingLv
 * @date 2020/12/30
 */
class ApiTestCaseModelTest {
    public static final Logger logger = LoggerFactory.getLogger(ApiTestCaseModelTest.class);

    @BeforeAll
    static void beforeAll() throws IOException {
        ApiLoader.load("src/main/resources/api");
        logger.info("debugger!");
    }

    @Test
    void loadApiTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ApiTestCaseModel testCaseModel = ApiTestCaseModel.load("src/main/resources/case/create_user_info_case.yml");
        String str = mapper.writeValueAsString(testCaseModel);
        System.out.println(str);
    }

    @Test
    void run() throws IOException {
        ApiTestCaseModel testCaseModel = ApiTestCaseModel.load("src/main/resources/case/create_user_info_case.yml");
        testCaseModel.run();
    }
}