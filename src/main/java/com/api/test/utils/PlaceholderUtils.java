package com.api.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置文件或模板中的占位符替换工具类
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class PlaceholderUtils {
    public static final Logger logger = LoggerFactory.getLogger(PlaceholderUtils.class);

    private PlaceholderUtils() {
    }

    /**
     * Prefix for system property placeholders: "${"
     */
    public static final String PLACEHOLDER_PREFIX = "${";
    /**
     * Suffix for system property placeholders: "}"
     */
    public static final String PLACEHOLDER_SUFFIX = "}";

    /**
     * 字符串解析
     *
     * @param text      需解析内容
     * @param parameter 参数
     * @return 返回已解析内容
     */
    public static String resolveString(String text, Map<String, String> parameter) {
        // 参数为空直接返回text
        if (parameter == null || parameter.isEmpty() || text == null || text.isEmpty()) {
            return text;
        }
        StringBuilder buf = new StringBuilder(text);
        // 计算变量名开始位置
        int startIndex = buf.indexOf(PLACEHOLDER_PREFIX);
        while (startIndex != -1) {
            // 计算变量名结束的位置
            int endIndex = buf.indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());
            if (endIndex != -1) {
                // 取出要替换的变量名
                String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();
                try {
                    // 取出变量map中的真实值
                    String propVal = parameter.get(placeholder);
                    if (propVal != null) {
                        // 替换变量
                        buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
                        nextIndex = startIndex + propVal.length();
                    } else {
                        logger.error("Could not resolve placeholder '" + placeholder + "' in [" + text + "] ");
                    }
                } catch (Exception ex) {
                    logger.error("Could not resolve placeholder '" + placeholder + "' in [" + text + "]: " + ex);
                }
                startIndex = buf.indexOf(PLACEHOLDER_PREFIX, nextIndex);
            } else {
                startIndex = -1;
            }
        }
        return buf.toString();
    }

    /**
     * 列表解析
     *
     * @param list      需解析列表
     * @param parameter 参数
     * @return 返回已解析列表
     */
    public static ArrayList<String> resolveList(ArrayList<String> list, Map<String, String> parameter) {
        if (parameter == null || parameter.isEmpty() || list == null || list.isEmpty()) {
            return list;
        }
        ArrayList<String> returnList = new ArrayList<>();
        list.forEach(str -> {
            if (str.contains(PLACEHOLDER_PREFIX)) {
                returnList.add(resolveString(str, parameter));
            } else {
                returnList.add(str);
            }
        });
        return returnList;
    }

    /**
     * 字典解析
     *
     * @param map       需解析字典
     * @param parameter 参数
     * @return 返回已解析字典
     */
    public static Map<String, String> resolveMap(Map<String, String> map, Map<String, String> parameter) {
        if (parameter == null || parameter.isEmpty() || map == null || map.isEmpty()) {
            return map;
        }
        HashMap<String, String> returnMap = new HashMap<>(16);
        map.forEach((key, value) -> {
            if (value.contains(PLACEHOLDER_PREFIX)) {
                returnMap.put(key, resolveString(value, parameter));

            }
        });
        return returnMap;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("token", "${token}");
        Map<String, String> parameter = new HashMap<>();
        map.put("token", "123123");
        Map<String, String> stringStringHashMap = resolveMap(map, parameter);
        System.out.println(stringStringHashMap);
    }
}
