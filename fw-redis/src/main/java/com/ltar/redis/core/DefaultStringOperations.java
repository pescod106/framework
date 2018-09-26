package com.ltar.redis.core;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.core.ops.StringOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultStringOperations extends AbstractOperations implements StringOperations {
    public DefaultStringOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }


    public <K, V> Long append(K key, V value) {
        return null;
    }

    public <K> Long bitCount(K key, long start, long end) {
        return null;
    }

    public <K> Long bitPos(K key, boolean value, long start, long end) {
        return null;
    }

    public <K> Long bitpos(K key, boolean value, long start, long end) {
        return null;
    }

    public <K> Long decr(K key) {
        return null;
    }

    public <K> Long decrBy(K key, int decrement) {
        return null;
    }

    public <K> Long decrBy(K key) {
        return null;
    }

    public <T, K> T get(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return SerializeUtil.deserialize(jedis.get(SerializeUtil.serialize(key)));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Boolean getBit(K key) {
        return null;
    }

    public <K, V> V getRange(K key, long start, long end) {
        return null;
    }

    public <K, V> V getSet(K key, V value) {
        return null;
    }

    public <K> Long incr(K key) {
        return null;
    }

    public <K> Long incrBy(K key, int increment) {
        return null;
    }

    public <K> Double incrByFloat(K key, double increment) {
        return null;
    }

    public <K, V> List<V> mget(K... keys) {
        return null;
    }

    public Long mset(Object... keysValues) {
        return null;
    }

    public <K, V> void psetex(K key, V value, long milliseconds) {

    }

    public <K, V> void set(K key, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K, V> void setEx(K key, V value, int seconds) {

    }

    public <K, V> Long setNx(K key, V value) {
        return null;
    }

    public <K> Boolean setBit(K key, long offset, boolean value) {
        return null;
    }

    public <K, V> Long setRange(K key, V value, long offset) {
        return null;
    }

    public <K> Long strLen(K key) {
        return null;
    }
}
