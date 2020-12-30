package com.api.test.api;

import com.api.test.global.GlobalVariables;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author jingLv
 * @date 2020/12/30
 */
class ApiActionModelTest {

    @Test
    void run() {
        ApiActionModel apiActionModel = new ApiActionModel();
        apiActionModel.setMethod("get");
        apiActionModel.setContentType("application/json");
        apiActionModel.setUrl("http://localhost:8888/v1/info/user");

        HashMap<String, String> globalVariables = new HashMap<>();
        globalVariables.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJ1c2VyTmFtZSI6InhpYW9ob25nIiwiZXhwIjoxNjA5OTE0Njg5fQ._CYRxeeXHJS7zXAEnxq6b21ZcM9MECg9d-s8-Oe3gsM");
        GlobalVariables.setGlobalVariables(globalVariables);

        HashMap<String, String> query = new HashMap<>();
        query.put("token", "${token}");

        apiActionModel.setHeaders(query);

        Response response = apiActionModel.run(null);
        System.out.println(response);
    }
}