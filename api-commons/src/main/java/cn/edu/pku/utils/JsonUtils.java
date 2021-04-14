package cn.edu.pku.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

    public static String objectToString(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static Object stringToObject(String objectString, Class<?> objClass) {
        return JSON.parseObject(objectString, objClass);
    }
}
