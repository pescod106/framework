package com.ltar.framework.redis.core.ops;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Set;

/**
 * @desc: Redis set specific operations.
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface SetOperations {

    /**
     * 添加一个或多个指定的member元素到集合的 key中.
     * 指定的一个或者多个元素member 如果已经在集合key中存在则忽略.
     * 如果集合key 不存在，则新建集合key,并添加member元素到集合key中.
     *
     * @param key
     * @param values
     * @param <K>
     * @param <V>
     * @return 返回新成功添加到集合里元素的数量，不包括已经存在于集合中的元素.
     */
    <K, V> Long sadd(K key, V... values);

    /**
     * 返回集合存储的key的基数 (集合元素的数量).
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long scard(K key);

    /**
     * 返回一个集合与给定集合的差集的元素.
     *
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> sdiff(K... keys);

    /**
     * 该命令类似于 SDIFF, 不同之处在于该命令不返回结果集，而是将结果存放在destination集合中.
     * <p>
     * 如果destination已经存在, 则将其覆盖重写.
     *
     * @param dstkey
     * @param keys
     * @param <K>
     * @return 该命令类似于 SDIFF, 不同之处在于该命令不返回结果集，而是将结果存放在destination集合中.
     * <p>
     * 如果destination已经存在, 则将其覆盖重写.
     */
    <K> Long sdiffstore(K dstkey, K... keys);

    /**
     * 返回指定所有的集合的成员的交集.
     *
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> sinter(K... keys);

    /**
     * 这个命令与SINTER命令类似, 但是它并不是直接返回结果集,而是将结果保存在 destination集合中.
     * <p>
     * 如果destination 集合存在, 则会被重写.
     *
     * @param dstkey
     * @param keys
     * @param <K>
     * @return
     */
    <K> Long sinterstore(K dstkey, K... keys);

    /**
     * 返回成员 member 是否是存储的集合 key的成员.
     *
     * @param key
     * @param member
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Boolean sismember(K key, V member);

    /**
     * 返回key集合所有的元素.
     * <p>
     * 该命令的作用与使用一个参数的SINTER 命令作用相同.
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> smembers(K key);

    /**
     * 将member从source集合移动到destination集合中.
     * 对于其他的客户端,在特定的时间元素将会作为source或者destination集合的成员出现.
     * <p>
     * 如果source 集合不存在或者不包含指定的元素,这smove命令不执行任何操作并且返回0.
     * 否则对象将会从source集合中移除，并添加到destination集合中去，
     * 如果destination集合已经存在该元素，则smove命令仅将该元素充source集合中移除.
     * 如果source 和destination不是集合类型,则返回错误.
     *
     * @param srckey
     * @param dstkey
     * @param member
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long smove(K srckey, K dstkey, V member);

    /**
     * Removes and returns one or more random elements from the set value store at key.
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V spop(K key);

    /**
     * 仅提供key参数,那么随机返回key集合中的一个元素.
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V srandmember(K key);

    /**
     * 随机返回含有 count 个不同的元素的数组
     *
     * @param key
     * @param count
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> List<V> srandmember(K key, final int count);

    /**
     * 在key集合中移除指定的元素.
     * 如果指定的元素不是key集合中的元素则忽略 如果key集合不存在则被视为一个空的集合，该命令返回0.
     *
     * @param key
     * @param member
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long srem(K key, V... member);

    /**
     * 返回给定的多个集合的并集中的所有成员.
     *
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> sunion(K... keys);

    /**
     * 该命令作用类似于SUNION命令,不同的是它并不返回结果集,而是将结果存储在destination集合中.
     * <p>
     * 如果destination 已经存在,则将其覆盖.
     *
     * @param dstkey
     * @param keys
     * @param <K>
     * @return
     */
    <K> Long sunionstore(K dstkey, K... keys);

    /**
     * 用于迭代SET集合中的元素
     *
     * @param key
     * @param cursor
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> ScanResult<V> sscan(K key, String cursor);

    /**
     * 用于迭代SET集合中的元素
     *
     * @param key
     * @param cursor
     * @param params
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> ScanResult<V> sscan(K key, String cursor, ScanParams params);
}
