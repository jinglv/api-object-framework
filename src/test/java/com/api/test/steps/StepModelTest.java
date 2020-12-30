package com.api.test.steps;

import com.api.test.global.ApiLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author jingLv
 * @date 2020/12/30
 */
class StepModelTest {

    public static final Logger logger = LoggerFactory.getLogger(StepModelTest.class);

    @BeforeAll
    static void beforeAll() throws IOException {
        ApiLoader.load("src/main/resources/api");
        logger.info("debugger!");
    }

    @Test
    void run() {
        //实参
        ArrayList<String> actualParameter = new ArrayList<>();
        actualParameter.add("xiaohong");
        actualParameter.add("123123");
        //断言
        ArrayList<AssertModel> asserts = new ArrayList<>();
        AssertModel assertModel = new AssertModel();
        assertModel.setActual("code");
        assertModel.setExpect("00001");
        assertModel.setMatcher("equalTo");
        assertModel.setReason("成功获取token");
        asserts.add(assertModel);
        //save
        HashMap<String, String> save = new HashMap<>();
        save.put("token", "data.token");
        //globalsave
        HashMap<String, String> globalsave = new HashMap<>();
        globalsave.put("token", "data.token");

        StepModel stepModel = new StepModel();
        stepModel.setApi("UserLogin");
        stepModel.setAction("get_token");
        stepModel.setActualParameter(actualParameter);
        stepModel.setAsserts(asserts);
        stepModel.setSave(save);
        stepModel.setSaveGlobal(globalsave);

        stepModel.run(null);
        logger.info("Debugger!");
    }
}