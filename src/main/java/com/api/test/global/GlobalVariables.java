package com.api.test.global;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储全局变量
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class GlobalVariables {

    static private Map<String, String> globalVariables = new HashMap<>();

    public static Map<String, String> getGlobalVariables() {
        return globalVariables;
    }

    public static void setGlobalVariables(Map<String, String> globalVariables) {
        GlobalVariables.globalVariables = globalVariables;
    }
}
