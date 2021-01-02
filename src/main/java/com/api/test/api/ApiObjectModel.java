package com.api.test.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口对象类
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class ApiObjectModel {
    public static final Logger logger = LoggerFactory.getLogger(ApiObjectModel.class);

    /**
     * 接口的名称
     */
    private String name;
    /**
     * 接口的执行对象
     */
    private HashMap<String, ApiActionModel> actions;
    /**
     * 接口变量
     */
    private Map<String, String> objectVariables = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ApiActionModel> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, ApiActionModel> actions) {
        this.actions = actions;
    }

    public Map<String, String> getObjectVariables() {
        return objectVariables;
    }

    public void setObjectVariables(Map<String, String> objectVariables) {
        this.objectVariables = objectVariables;
    }

    /**
     * 加载api object yaml文件（目录：resources/api/**_api.yaml）
     *
     * @param path 路径
     * @return 返回yaml文件内容
     * @throws IOException io异常
     */
    public static ApiObjectModel load(String path) throws IOException {
        logger.info("api object加载路径：" + path);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path), ApiObjectModel.class);
    }
}
