package com.api.test.steps;

import io.restassured.response.Response;

/**
 * 用于保存结果的对象基类
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class BaseResult {
    /**
     * 执行响应结果
     */
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
