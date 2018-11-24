package com.ltar.framework.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class MapUtils {

    /**
     * 创建空的HashMap,未设置初识容量
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * 创建空的HashMap,设置初识容量
     *
     * @param initialCapacity 设置初识容量
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> newHashMap(int initialCapacity) {
        return new HashMap<K, V>(initialCapacity);
    }

    /**
     * 设置容量为1的HashMap，并设置k v
     *
     * @param k   map中的key
     * @param v   map中的value
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> newHashMap(K k, V v) {
        Map<K, V> map = new HashMap(1);
        map.put(k, v);
        return map;
    }

    /**
     * 创建包含多个键值对的map
     *
     * @param extraKeyValues 键值对组合 格式是 k,v,k,v....
     * @return
     */
    public static Map<Object, Object> newHashMap(Object... extraKeyValues) {
        if (extraKeyValues.length % 2 != 0) {
            throw new IllegalArgumentException("extraKeyValues's count must be even");
        }
        Map<Object, Object> map = new HashMap(extraKeyValues.length / 2);
        for (int i = 0; i < extraKeyValues.length; i += 2) {
            map.put(extraKeyValues[i], extraKeyValues[i + 1]);
        }
        return map;
    }

}
