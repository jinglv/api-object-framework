package com.api.test.api;

import com.api.test.global.GlobalVariables;
import com.api.test.utils.PlaceholderUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * 接口执行对象
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class ApiActionModel {
    public static final Logger logger = LoggerFactory.getLogger(ApiActionModel.class);

    /**
     * 接口请求方式
     */
    private String method;
    /**
     * 接口请求地址
     */
    private String url;
    /**
     * 接口请求内容类型
     */
    private String contentType;
    /**
     * 请求头信息
     */
    private Map<String, String> headers;
    /**
     * 请求参数json方式
     */
    public String body;
    /**
     * 请求参数params方式
     */
    public Map<String, String> query;
    /**
     * 请求参数变量列表
     */
    private ArrayList<String> formalParam;
    /**
     * 接口响应返回
     */
    private Response response;
    /**
     * 接口中变量存放Map
     */
    private Map<String, String> actionVariables = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public ArrayList<String> getFormalParam() {
        return formalParam;
    }

    public void setFormalParam(ArrayList<String> formalParam) {
        this.formalParam = formalParam;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Map<String, String> getActionVariables() {
        return actionVariables;
    }

    public void setActionVariables(Map<String, String> actionVariables) {
        this.actionVariables = actionVariables;
    }

    /**
     * 接口执行方式
     *
     * @param actualParameter 实例参数列表
     * @return response
     */
    public Response run(ArrayList<String> actualParameter) {
        logger.info("测试用例执行传入的实参列表：" + actualParameter);
        Map<String, String> finalQuery = new HashMap<>(16);
        String runBody = this.body;
        String runUrl = this.url;

        // 请求参数、URL中全局变量替换， PS:这里需要编写占位符工具类PlaceholderUtils
        if (query != null) {
            finalQuery.putAll(PlaceholderUtils.resolveMap(query, GlobalVariables.getGlobalVariables()));
        }
        // body全局变量替换
        runBody = PlaceholderUtils.resolveString(runBody, GlobalVariables.getGlobalVariables());

        // url全局变量替换
        runUrl = PlaceholderUtils.resolveString(runUrl, GlobalVariables.getGlobalVariables());

        // 请求参数的变量列表，例如：formalParam: ["username","password"]
        if (formalParam != null && actualParameter != null && formalParam.size() > 0 && actualParameter.size() > 0) {
            // 循环遍历，根据形参和实参构建内部变量存入变量Map中
            for (int index = 0; index < formalParam.size(); index++) {
                actionVariables.put(formalParam.get(index), actualParameter.get(index));
            }
            // 请求参数不为空，将请求参数中的变量替换
            if (query != null) {
                finalQuery.putAll(PlaceholderUtils.resolveMap(query, actionVariables));
            }
            runBody = PlaceholderUtils.resolveString(body, actionVariables);
            runUrl = PlaceholderUtils.resolveString(runUrl, actionVariables);
        }

        // 拿到了上面完成了变量替换的请求数据，我们接下来要进行请求并返回结果
        RequestSpecification requestSpecification = given().log().all();

        if (contentType != null) {
            requestSpecification.contentType(contentType);
        }
        if (headers != null) {
            headers = PlaceholderUtils.resolveMap(headers, GlobalVariables.getGlobalVariables());
            requestSpecification.headers(headers);
        }
        if (finalQuery.size() > 0) {
            requestSpecification.formParams(finalQuery);
        } else if (runBody != null) {
            requestSpecification.body(runBody);
        }
        // 将拼装好的接口进行请求
        Response response = requestSpecification.request(method, runUrl).then().log().all().extract().response();
        this.response = response;

        return response;
    }
}
