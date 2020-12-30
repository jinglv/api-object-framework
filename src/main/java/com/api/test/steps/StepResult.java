package com.api.test.steps;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * case中step执行结果对象
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class StepResult extends BaseResult {
    /**
     * 断言相关参数列表
     */
    private ArrayList<Executable> assertList;
    /**
     * 执行结果变量
     */
    private Map<String, String> stepVariables = new HashMap<>();

    public ArrayList<Executable> getAssertList() {
        return assertList;
    }

    public void setAssertList(ArrayList<Executable> assertList) {
        this.assertList = assertList;
    }

    public Map<String, String> getStepVariables() {
        return stepVariables;
    }

    public void setStepVariables(Map<String, String> stepVariables) {
        this.stepVariables = stepVariables;
    }
}
