package com.api.test.steps;

import com.api.test.global.ApiLoader;
import com.api.test.global.GlobalVariables;
import com.api.test.utils.PlaceholderUtils;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * case中的step对象
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class StepModel {
    public static final Logger logger = LoggerFactory.getLogger(StepModel.class);

    // 定义AssertModel
    /**
     * 接口
     */
    private String api;
    /**
     * 接口行为
     */
    private String action;
    /**
     * 实际参数列表
     */
    private ArrayList<String> actualParameter;
    /**
     * 断言列表
     */
    private ArrayList<AssertModel> asserts;
    /**
     * 保存关联值
     */
    private Map<String, String> save;
    /**
     * 保存关联值为公共值
     */
    private Map<String, String> saveGlobal;

    // 定义StepResult
    /**
     * 实际参数列表
     */
    private final ArrayList<String> finalActualParameter = new ArrayList<>();
    /**
     * 执行结果变量
     */
    private final Map<String, String> stepVariables = new HashMap<>();
    /**
     * 执行结果
     */
    private final StepResult stepResult = new StepResult();
    /**
     * 执行断言列表
     */
    private final ArrayList<Executable> assertList = new ArrayList<>();

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<String> getActualParameter() {
        return actualParameter;
    }

    public void setActualParameter(ArrayList<String> actualParameter) {
        this.actualParameter = actualParameter;
    }

    public ArrayList<AssertModel> getAsserts() {
        return asserts;
    }

    public void setAsserts(ArrayList<AssertModel> asserts) {
        this.asserts = asserts;
    }

    public Map<String, String> getSave() {
        return save;
    }

    public void setSave(Map<String, String> save) {
        this.save = save;
    }

    public Map<String, String> getSaveGlobal() {
        return saveGlobal;
    }

    public void setSaveGlobal(Map<String, String> saveGlobal) {
        this.saveGlobal = saveGlobal;
    }

    public StepResult run(Map<String, String> testCaseVariables) {
        logger.info("测试用例变量列表：" + testCaseVariables);
        // 需要定义AssertModel类
        if (actualParameter != null) {
            finalActualParameter.addAll(PlaceholderUtils.resolveList(actualParameter, testCaseVariables));
            logger.info("测试用例替换变量列表：" + finalActualParameter);
        }
        // 根据case中配置的API对象和action信息，取出并执行相应的action
        Response response = Objects.requireNonNull(ApiLoader.getAction(api, action)).run(finalActualParameter);
        // 存储接口上下文关联数据
        if (save != null) {
            save.forEach((variablesName, path) -> {
                String value = response.path(path).toString();
                stepVariables.put(variablesName, value);
                logger.info("step变量更新：" + stepVariables);
            });
        }
        // 存储接口上下文关联公共数据
        if (saveGlobal != null) {
            saveGlobal.forEach((variablesName, path) -> {
                String value = response.path(path).toString();
                GlobalVariables.getGlobalVariables().put(variablesName, value);
                logger.info("全局变量更新：" + stepVariables);
            });
        }
        // 根据case中的配置对返回结果进行软断言，但不会终结测试将断言结果存入断言结果列表中，在用例最后进行统一结果输出
//        if (asserts != null) {
//            asserts.forEach(assertModel ->
//                    assertList.add(() ->
//                            assertThat(assertModel.getReason(), response.path(assertModel.getActual()).toString(), equalTo(assertModel.getExpect()))));
//        }
        if (asserts != null) {
            asserts.forEach(assertModel -> {
//                获取断言方法，如果没有断言方法默认使用equalTo
                Matcher matcher = null;
                if (assertModel.getMatcher() != null) {
                    logger.info("获取断言方法" + assertModel.getMatcher() + "返回Matcher类型");
                    try {
                        matcher = matcherMethod(assertModel.getMatcher(), assertModel.getExpect());
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    matcher = equalTo(assertModel.getExpect());
                }
                Matcher finalMatcher = matcher;
                assertList.add(() -> {
                    assertThat(assertModel.getReason(), response.path(assertModel.getActual()).toString(), finalMatcher);
                });
            });
        }
        // 将response和断言结果存储到stepResult对象中并返回
        stepResult.setAssertList(assertList);
        stepResult.setStepVariables(stepVariables);
        stepResult.setResponse(response);
        return stepResult;
    }

    /**
     * 反射找Matchers静态方法
     *
     * @param methodValue
     * @param expectValue
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    static Matcher matcherMethod(String methodValue, String expectValue) throws InvocationTargetException, IllegalAccessException {
        Method method = Arrays.stream(org.hamcrest.Matchers.class.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodValue))
                .findFirst()
                .get();
        return (Matcher) method.invoke(null, expectValue);
    }
}
