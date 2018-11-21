package com.ltar.net.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ltar.json.util.JsonUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/11/21
 * @version: 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponse implements Serializable {
    private int code;
    private boolean success;
    private String message;
    private Map<String, Object> data;

    public JsonResponse() {
        this.code = 0;
        this.success = true;
        this.data = new HashMap();
    }

    public JsonResponse(boolean success) {
        this(success, (String) null);
    }

    public JsonResponse(String message) {
        this(false, message);
    }

    public JsonResponse(boolean success, String message) {
        this.code = 0;
        this.success = success;
        this.message = message;
        this.data = new HashMap<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public JsonResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public JsonResponse setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public JsonResponse appendData(Map<String, Object> data) {
        this.data.putAll(data);
        return this;
    }

    public JsonResponse set(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Object get(String key) {
        return this.data.get(key);
    }

    public JsonResponse setEntityData(Object entity) {
        return this.setEntityData(entity, true);
    }

    public JsonResponse setEntityData(Object entity, boolean keepOldMap) {
        if (!(entity instanceof Boolean)
                && !(entity instanceof Number)
                && !(entity instanceof CharSequence)
                && !(entity instanceof List)
                && !entity.getClass().isArray()
                && !entity.getClass().isPrimitive()) {
            String json = JsonUtils.dump(entity);
            if (!StringUtils.isEmpty(json) && json.length() >= 5) {
                Map entityData = JsonUtils.load(json);
                if (keepOldMap) {
                    return this.setData(entityData);
                } else {
                    return this.appendData(entityData);
                }
            } else {
                throw new RuntimeException("无法将值\"" + entity + "\"转为Map类型，请改用set(key,value)方法");
            }
        } else {
            throw new RuntimeException("Boolean/Number/CharSequence/List/Array及基本数据类型的数据:" + entity + ",不能设置为data字段的值。请改用set(key,value)方法");
        }

    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
