package com.ltar.framework.redis.core.ops;

import org.springframework.data.util.Pair;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @desc: Redis map specific operations working on a hash.
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface HashOperations {
    /**
     * 从 key 指定的哈希集中移除指定的域。在哈希集中不存在的域将被忽略。
     * <p>
     * 如果 key 指定的哈希集不存在，它将被认为是一个空的哈希集，该命令将返回0。
     *
     * @param key
     * @param fields
     * @param <K>
     * @param <HK>
     * @return 返回从哈希集中成功移除的域的数量，不包括指出但不存在的那些域
     */
    <K, HK> Long hdel(K key, HK... fields);

    /**
     * 返回hash里面field是否存在
     *
     * @param key
     * @param field
     * @param <K>
     * @param <HK>
     * @return 1 hash里面包含该field。
     * 0 hash里面不包含该field或者key不存在。
     */
    <K, HK> Boolean hexists(K key, HK field);

    /**
     * 返回 key 指定的哈希集中该字段所关联的值
     *
     * @param key
     * @param field
     * @param <K>
     * @param <HK>
     * @param <V>
     * @return
     */
    <K, HK, V> V hget(K key, HK field);

    /**
     * 返回 key 指定的哈希集中所有的字段和值。
     *
     * @param key
     * @param <K>
     * @param <HK>
     * @param <V>
     * @return
     */
    <K, HK, V> Map<HK, V> hgetAll(K key);

    /**
     * 增加 key 指定的哈希集中指定字段的数值。
     * 如果 key 不存在，会创建一个新的哈希集并与 key 关联。如果字段不存在，则字段的值在该操作执行前被设置为 0
     *
     * @param key
     * @param field
     * @param increment
     * @param <K>
     * @param <HK>
     * @return
     */
    <K, HK> Long hincrBy(K key, HK field, long increment);

    /**
     * 为指定key的hash的field字段值执行float类型的increment加。如果field不存在，则在执行该操作前设置为0.
     * <p>
     * 如果出现下列情况之一，则返回错误:
     * field的值包含的类型错误(不是字符串)。
     * 当前field或者increment不能解析为一个float类型
     *
     * @param key
     * @param field
     * @param increment
     * @param <K>
     * @param <HK>
     * @return
     */
    <K, HK> Double hincrByFloat(K key, HK field, double increment);

    /**
     * 返回 key 指定的哈希集中所有字段的名字。
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> hkeys(K key);

    /**
     * 返回 key 指定的哈希集包含的字段的数量。
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long hlen(K key);

    /**
     * 返回 key 指定的哈希集中指定字段的值。
     * <p>
     * 对于哈希集中不存在的每个字段，返回 nil 值。因为不存在的keys被认为是一个空的哈希集，
     * 对一个不存在的 key 执行 HMGET 将返回一个只含有 nil 值的列表
     *
     * @param key
     * @param fields
     * @param <K>
     * @param <HK>
     * @param <V>
     * @return
     */
    <K, HK, V> List<V> hmget(K key, HK... fields);

    /**
     * 设置 key 指定的哈希集中指定字段的值。
     * 该命令将重写所有在哈希集中存在的字段。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联
     *
     * @param key
     * @param keysValues
     * @param <K>
     */
    <K> void hmset(K key, Object... keysValues);

    <K, HK, V> void hmset(K key, Map<HK, V> beanMap);

    /**
     * 设置 key 指定的哈希集中指定字段的值。
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。
     * 如果字段在哈希集中存在，它将被重写。
     *
     * @param key
     * @param field
     * @param value
     * @param <K>
     * @param <HK>
     * @param <V>
     * @return 1如果field是一个新的字段
     * 0如果field原来在map里面已经存在
     */
    <K, HK, V> void hset(K key, HK field, V value);

    /**
     * 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。如果字段已存在，该操作无效果。
     *
     * @param key
     * @param field
     * @param value
     * @param <K>
     * @param <HK>
     * @param <V>
     * @return 1：如果字段是个新的字段，并成功赋值
     * 0：如果哈希集中已存在该字段，没有操作被执行
     */
    <K, HK, V> Boolean hsetNx(K key, HK field, V value);

    /**
     * 返回 key 指定的哈希集中所有字段的值。
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return 哈希集中的值的列表，当 key 指定的哈希集不存在时返回空列表。
     */
    <K, V> List<V> hvals(K key);

    /**
     * 用于增量迭代一个集合元素。
     *
     * @param key
     * @param cursor
     * @param scanParams
     * @param <K>
     * @param <HK>
     * @param <HV>
     * @return
     */
    <K, HK, HV> ScanResult<Pair<HK, HV>> hscan(K key, String cursor, ScanParams scanParams);


}
