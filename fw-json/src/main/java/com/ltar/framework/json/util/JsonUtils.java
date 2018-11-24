package com.ltar.framework.json.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class JsonUtils {

    /**
     * 将对象转化成json串
     *
     * @param object
     * @return
     */
    public static String dump(Object object) {
        return dump(object, null);
    }

    /**
     * 将对象转化成json串
     *
     * @param object
     * @param inclusion
     * @return
     */
    public static String dump(Object object, JsonInclude.Include inclusion) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.setSerializationInclusion(inclusion);
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, object);
        } catch (Exception e) {
            throw new JsonException("can't dump object :" + object, e);
        }
        return writer.toString();
    }

    /**
     * 将json串转化成map
     *
     * @param json
     * @return
     */
    public static Map load(String json) {
        return load(json, HashMap.class);
    }

    /**
     * 将json串转化成type对应的实体类
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T load(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException("parse content error, content: " + json, e);
        }
    }

    /**
     * 将json串转化成List
     *
     * @param jsonArr
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> loadList(String jsonArr, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (List) mapper.readValue(jsonArr, mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (Exception e) {
            throw new JsonException("parse content error, content: " + jsonArr, e);
        }
    }

    public static class JsonException extends RuntimeException {
        public JsonException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
