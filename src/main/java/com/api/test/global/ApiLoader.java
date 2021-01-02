package com.api.test.global;

import com.api.test.api.ApiActionModel;
import com.api.test.api.ApiObjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 接口对象加载器
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class ApiLoader {
    public static final Logger logger = LoggerFactory.getLogger(ApiLoader.class);
    /**
     * 加载所有api Object对象，并保存到本列表中
     */
    private static final List<ApiObjectModel> APIS = new ArrayList<>();

    /**
     * 加载**_api.yml的所有接口， resources/api/**_api.yml
     *
     * @param dir 接口存放目录
     */
    public static void load(String dir) {
        Arrays.stream(Objects.requireNonNull(new File(dir).list())).forEach(path -> {
            try {
                APIS.add(ApiObjectModel.load(dir + "/" + path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * api与case结合，通过api name与case name相等，case找到所执行的api
     *
     * @param apiName    api中的name
     * @param actionName case中的name
     * @return ApiActionModel
     */
    public static ApiActionModel getAction(String apiName, String actionName) {
        return APIS.stream()
                .filter(api -> api.getName().equals(apiName))
                .map(api -> api.getActions().get(actionName))
                .findAny()
                .orElseThrow(() -> new RuntimeException("没有找到接口对象： " + apiName + "中的action: " + actionName));
    }
}
