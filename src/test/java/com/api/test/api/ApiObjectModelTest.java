package com.api.test.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author jingLv
 * @date 2020/12/30
 */
class ApiObjectModelTest {

    @Test
    void load() throws IOException {
        ArrayList<String> actualParameter = new ArrayList<>();
        actualParameter.add("xiaohong");
        actualParameter.add("123123");

        ApiObjectModel apiObjectModel = ApiObjectModel.load("src/main/resources/api/user_login_api.yml");
        apiObjectModel.getActions().get("get_token").run(actualParameter);
    }
}