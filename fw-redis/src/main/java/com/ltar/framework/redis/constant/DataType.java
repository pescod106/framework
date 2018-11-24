package com.ltar.framework.redis.constant;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
@AllArgsConstructor
public enum DataType {
    /**
     * none
     */
    NONE("none"),
    /**
     * String
     */
    STRING("string"),
    /**
     * list
     */
    LIST("list"),
    /**
     * set
     */
    SET("set"),
    /**
     * sorted set
     */
    ZSET("zset"),
    /**
     * hash
     */
    HASH("hash");

    private static final Map<String, DataType> codeLookup = new ConcurrentHashMap<String, DataType>(6);

    static {
        for (DataType type : DataType.values()) {
            codeLookup.put(type.code, type);
        }
    }

    private final String code;

    /**
     * Returns the code associated with the current enum.
     *
     * @return code of this enum
     */
    public String code() {
        return this.code;
    }

    /**
     * Utility method for converting an enum code to an actual enum.
     *
     * @param code enum code
     * @return actual enum corresponding to the given code
     */
    public static DataType fromCode(String code) {
        DataType dataType = codeLookup.get(code);
        if (null == dataType) {
            throw new IllegalArgumentException("unknown data type code");
        }
        return dataType;
    }


}
