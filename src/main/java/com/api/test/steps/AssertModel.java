package com.api.test.steps;

/**
 * 用来存储断言数据的对象
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class AssertModel {
    /**
     * 实际结果
     */
    private String actual;
    /**
     * 断言匹配方式
     */
    private String matcher;
    /**
     * 预期结果
     */
    private String expect;
    /**
     * 断言完成的结论
     */
    private String reason;

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
