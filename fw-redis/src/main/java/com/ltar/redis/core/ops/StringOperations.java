package com.ltar.redis.core.ops;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface StringOperations {

    <K, V> void set(K key, V value);

}
