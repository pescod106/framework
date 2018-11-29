package com.ltar.framework.json.util;


import com.alibaba.fastjson.JSONObject;


/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/11/27
 * @version: 1.0.0
 */
public class FastJsonUtils {

    public static String toJSONString(Object object) {
        return JSONObject.toJSONString(object);
    }

//    public static Map load(String json) {
//        JSONObject.parse
//    }

    /**
     * 将对象转化成json串
     *
     * @param object
     * @param inclusion
     * @return
     */
//    public static String dump(Object object, JsonInclude.Include inclusion) {
//
//    }
}
