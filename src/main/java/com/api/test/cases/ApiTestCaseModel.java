package com.api.test.cases;

import com.api.test.steps.StepModel;
import com.api.test.steps.StepResult;
import com.api.test.utils.FakerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * 用例yaml对应的数据对象
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class ApiTestCaseModel {
    public static final Logger logger = LoggerFactory.getLogger(ApiTestCaseModel.class);
    /**
     * 用例名称
     */
    private String name;
    /**
     * 用例描述
     */
    private String description;
    /**
     * 用例步骤集
     */
    private ArrayList<StepModel> steps;
    /**
     * 断言列表
     */
    private ArrayList<Executable> assertList = new ArrayList<>();
    /**
     * 测试过程中变量
     */
    private Map<String, String> testCaseVariables = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepModel> steps) {
        this.steps = steps;
    }

    public ArrayList<Executable> getAssertList() {
        return assertList;
    }

    public void setAssertList(ArrayList<Executable> assertList) {
        this.assertList = assertList;
    }

    public Map<String, String> getTestCaseVariables() {
        return testCaseVariables;
    }

    public void setTestCaseVariables(Map<String, String> testCaseVariables) {
        this.testCaseVariables = testCaseVariables;
    }

    /**
     * 加载所有的用例 (resources/case/***_case.yml)
     *
     * @param path 用例存放路径
     * @return 返回加载所有的case
     * @throws IOException io异常
     */
    public static ApiTestCaseModel load(String path) throws IOException {
        logger.info("test case加载测试用例路径：" + path);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path), ApiTestCaseModel.class);
    }

    /**
     * 测试用例执行
     */
    public void run() {
        // 加载用例层关键字变量
        this.testCaseVariables.put("getRandomInt", FakerUtils.getRandomStringNum(10));
        logger.info("用例变量更新：" + testCaseVariables);
        // 遍历steps进行执行
        steps.forEach(step -> {
            StepResult stepResult = step.run(testCaseVariables);
            // 处理step返回save变量
            if (stepResult.getStepVariables().size() > 0) {
                testCaseVariables.putAll(stepResult.getStepVariables());
                logger.info("testCase变量更新：" + testCaseVariables);
            }
            // 处理assertList,并进行统一断言
            if (stepResult.getAssertList().size() > 0) {
                assertList.addAll(stepResult.getAssertList());
            }
        });
        // 进行统一断言
        assertAll("", assertList.stream());
    }
}
