package com.ltar.redis.core.ops;

import com.ltar.redis.constant.DataType;
import org.springframework.lang.Nullable;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * todo randomKey(),sort(),exec(),getClientList(),killClient(),slaveOfNoOne(),convertAndSend()
 *
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface RedisOperations {

    /**
     * Delete given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return
     */
    @Nullable
    <K> Boolean delete(K key);


    /**
     * Delete given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return
     */
    @Nullable
    <K> Long delete(Collection<K> keys);

    /**
     * todo
     *
     * @param key
     * @return
     */
    @Nullable
    <K> byte[] dump(final K key);

    /**
     * Determine if given {@code key} exists.
     *
     * @param key
     * @return true if the key was removed.
     * @see <a href="http://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    @Nullable
    <K> Boolean exists(K key);

    /**
     * Set time to live for given {@code key}..
     *
     * @param key      must not be {@literal null}.
     * @param timeout
     * @param timeUnit must not be {@literal null}.
     * @return
     */
    @Nullable
    <K> Long expire(K key, long timeout, TimeUnit timeUnit);

    /**
     * Set the expiration for given {@code key} as a {@literal date} timestamp.
     *
     * @param key  must not be {@literal null}.
     * @param date must not be {@literal null}.
     * @return Integer reply, specifically: 1: the timeout was set. 0: the
     * timeout was not set since the key already has an associated
     * timeout (this may happen only in Redis versions < 2.1.3, Redis >=
     * 2.1.3 will happily update the timeout), or the key does not
     * exist.
     */
    @Nullable
    <K> Long expireAt(K key, Date date);

    /**
     * Find all keys matching the given {@code pattern}.
     *
     * @param pattern
     * @return
     */
    @Nullable
    <K> Set<K> keys(K pattern);

    /**
     * 将 key 原子性地从当前实例传送到目标实例的指定数据库上，一旦传送成功， key 保证会出现在目标实例上，而当前实例上的 key 会被删除。
     *
     * @param host
     * @param port
     * @param key
     * @param destinationDb
     * @param timeout
     * @param <K>
     * @see <a href="http://www.redis.cn/commands/migrate.html">MIGRATE</a>
     */
    <K> void migrate(String host, int port, K key, int destinationDb, final int timeout);

    /**
     * Move the specified key from the currently selected DB to the specified
     * destination DB. Note that this command returns 1 only if the key was
     * successfully moved, and 0 if the target key was already there or if the
     * source key was not found at all, so it is possible to use MOVE as a
     * locking primitive.
     *
     * @param key
     * @param dbIndex
     * @return Integer reply, specifically: 1 if the key was moved 0 if the key
     * was not moved because already present on the target DB or was not
     * found in the current DB.
     */
    @Nullable
    <K> Boolean move(K key, int dbIndex);

    /**
     * 该命令主要用于调试(debugging)，它能够返回指定key所对应value被引用的次数.
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long objectRefCount(K key);

    /**
     * 该命令返回指定key对应value所使用的内部表示
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V objectEncoding(K key);

    /**
     * 该命令返回指定key对应的value自被存储之后空闲的时间，以秒为单位(没有读写操作的请求) ，
     * 这个值返回以10秒为单位的秒级别时间，这一点可能在以后的实现中改善
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long objectIdletime(K key);

    /**
     * Undo a  expire at turning the expire key into
     * a normal key.
     *
     * @param key
     * @return Integer reply, specifically: 1: the key is now persist. 0: the
     * key is not persist (only happens when key not set).
     */
    @Nullable
    <K> Long persist(K key);

    /**
     * Get the time to live for {@code key} in seconds.
     *
     * @param key
     * @return
     */
    <K> Long ttl(K key);

    /**
     * Get the time to live for {@code key} in and convert it to the given {@link TimeUnit}.
     *
     * @param key
     * @param timeUnit
     * @return
     */
    @Nullable
    <K> Long ttl(K key, TimeUnit timeUnit);

    /**
     * 从当前数据库返回一个随机的key。
     *
     * @return
     */
    String randomKey();

    /**
     * Rename key {@code oldKey} to {@code newKey}.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     */
    @Nullable
    <K> void rename(K oldKey, K newKey);

    /**
     * Rename key {@code oleName} to {@code newKey} only if {@code newKey} does not exist.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     * @return Integer reply, specifically: 1 if the key was renamed 0 if the
     * target key already exist
     */
    @Nullable
    <K> Long renamenx(K oldKey, K newKey);

    /**
     * todo
     *
     * @param key
     * @param value
     * @param timeToLive
     * @param unit
     */
    @Nullable
    <K> void restore(K key, byte[] value, long timeToLive, TimeUnit unit);

    /**
     * @param key
     * @return
     * @see {@link #sort(String, SortingParams, String)}
     */
    List<String> sort(final String key);

    /**
     * @param key
     * @param dstkey
     * @return
     * @see {@link #sort(String, SortingParams, String)}
     */
    Long sort(final String key, final String dstkey);

    /**
     * @param key
     * @param sortingParameters
     * @return
     * @see {@link #sort(String, SortingParams, String)}
     */
    List<String> sort(final String key, final SortingParams sortingParameters);

    /**
     * 返回或存储key的list、 set 或sorted set 中的元素。默认是按照数值类型排序的，并且按照两个元素的双精度浮点数类型值进行比较
     *
     * @param key
     * @param sortingParameters
     * @param dstkey
     * @return
     * @see <a href="http://www.redis.cn/commands/sort.html">sort</a>
     */
    Long sort(final String key, final SortingParams sortingParameters, final String dstkey);

    /**
     * Determine the type stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return
     */
    @Nullable
    <K> DataType type(K key);

    /**
     * 用于迭代当前数据库中的key集合
     *
     * @param cursor
     * @param scanParams
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> ScanResult<Map.Entry<K, V>> scan(String cursor, ScanParams scanParams);


//    /**
//     * todo
//     * Watch given {@code keys} for modifications during transaction started with {@link #multi()}.
//     *
//     * @param keys
//     */
//    @Nullable
//    <K> void watch(K... keys);
//
//    /**
//     * Flushes all the previously {@link #watch(Object)} keys.
//     */
//    void unwatch();
//
//    /**
//     * todo
//     * Mark the start of a transaction block. <br>
//     */
//    void multi();
//
//    /**
//     * Discard all commands issued after {@link #multi()}.
//     */
//    void discard();


    // -------------------------------------------------------------------------
    // Methods to obtain specific operations interface objects.
    // -------------------------------------------------------------------------
    // operation types

    /**
     * Returns geospatial specific operations interface.
     *
     * @return
     */
    ClusterOperations ops4Cluster();

    /**
     * Returns the operations performed on hash values.
     *
     * @return
     */
    HashOperations ops4Hash();

    /**
     * Returns the operations performed on HyperLogLog values.
     *
     * @return
     */
    HyperLogLogOperations ops4HyperLogLog();

    /**
     * Returns the operations performed on list values.
     *
     * @return
     */
    ListOperations ops4List();

    /**
     * Returns the operations performed on set values.
     *
     * @return
     */
    SetOperations ops4Set();

    /**
     * Returns the operations performed on simple values (or Strings in Redis terminology).
     *
     * @return
     */
    StringOperations ops4String();

    /**
     * Returns the operations performed on zset values (also known as sorted sets).
     *
     * @return
     */
    SortedSetOperations opsForZSet();
}
