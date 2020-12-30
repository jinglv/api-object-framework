package com.api.test.cases;

import com.api.test.global.ApiLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 管理测试用例的运行
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class ApiParameterizedTest {
    public static final Logger logger = LoggerFactory.getLogger(ApiParameterizedTest.class);

    @ParameterizedTest(name = "{index} {1}")
    @MethodSource
    void apiTest(ApiTestCaseModel apiTestCaseModel, String name, String description) {
        logger.info("【用例开始执行】");
        logger.info("用例名称： " + name);
        logger.info("用例描述： " + description);
        apiTestCaseModel.run();
    }

    static List<Arguments> apiTest() {
        // 加载api下所有的yml文件
        ApiLoader.load("src/main/resources/api");
        //用来传递给参数化用例
        List<Arguments> testCases = new ArrayList<>();
        //读取所有的测试用例
        String testCaseDir = "src/main/resources/case";
        // 遍历目录下所有的用例文件，并组装成参数列表
        Arrays.stream(Objects.requireNonNull(new File(testCaseDir).list()))
                .forEach(name -> {
                    String path = testCaseDir + "/" + name;
                    try {
                        ApiTestCaseModel apiTestCase = ApiTestCaseModel.load(path);
                        testCases.add(arguments(apiTestCase, apiTestCase.getName(), apiTestCase.getDescription()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return testCases;
    }
}
