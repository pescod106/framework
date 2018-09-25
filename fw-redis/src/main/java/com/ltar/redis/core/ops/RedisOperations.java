package com.ltar.redis.core.ops;

import com.ltar.redis.constant.DataType;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
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
     * Determine if given {@code key} exists.
     *
     * @param key
     * @return true if the key was removed.
     * @see <a href="http://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    @Nullable
    <K> Boolean exists(K key);

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
     * Determine the type stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return
     */
    @Nullable
    <K> DataType type(K key);

    /**
     * Find all keys matching the given {@code pattern}.
     *
     * @param pattern
     * @return
     */
    @Nullable
    <K> Set<K> keys(K pattern);

    /**
     * Rename key {@code oldKey} to {@code newKey}.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKye must not be {@literal null}.
     */
    @Nullable
    <K> void rename(K oldKey, K newKye);

    /**
     * Rename key {@code oleName} to {@code newKey} only if {@code newKey} does not exist.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKye must not be {@literal null}.
     * @return Integer reply, specifically: 1 if the key was renamed 0 if the
     * target key already exist
     */
    @Nullable
    <K> Long renamenx(K oldKey, K newKye);

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
     * todo
     *
     * @param key
     * @return
     */
    @Nullable
    byte[] dump(final String key);

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
     * Get the time to live for {@code key} in seconds.
     *
     * @param key
     * @return
     */
    <K> Long getExpire(K key);

    /**
     * Get the time to live for {@code key} in and convert it to the given {@link TimeUnit}.
     *
     * @param key
     * @param timeUnit
     * @return
     */
    @Nullable
    <K> Long getExpire(K key, TimeUnit timeUnit);

    /**
     * todo
     * Watch given {@code keys} for modifications during transaction started with {@link #multi()}.
     *
     * @param keys
     */
    @Nullable
    <K> void watch(K... keys);

    /**
     * Flushes all the previously {@link #watch(Object)} keys.
     */
    void unwatch();

    /**
     * todo
     * Mark the start of a transaction block. <br>
     */
    void multi();

    /**
     * Discard all commands issued after {@link #multi()}.
     */
    void discard();


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
     * Returns geospatial specific operations interface.
     *
     * @return
     */
    GeoOperations ops4Geo();

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
