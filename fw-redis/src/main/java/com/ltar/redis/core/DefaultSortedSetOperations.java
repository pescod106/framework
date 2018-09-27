package com.ltar.redis.core;

import com.ltar.redis.core.ops.SortedSetOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

import java.util.Map;
import java.util.Set;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultSortedSetOperations extends AbstractOperations implements SortedSetOperations {
    public DefaultSortedSetOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    public <K, V> Long zadd(K key, double score, V member) {
        return null;
    }

    public <K, V> Long zadd(K key, Map<V, Double> scoreMembers) {
        return null;
    }

    public <K> Long zcard(K key) {
        return null;
    }

    public <K> Long zcount(K key, double min, double max) {
        return null;
    }

    public <K, V> Double zincrby(K key, double score, V member) {
        return null;
    }

    public <K, V> Long zinterstore(K dstkey, V... sets) {
        return null;
    }

    public <K, V> Long zinterstore(K dstkey, ZParams params, V... sets) {
        return null;
    }

    public <K, V> Long zlexcount(K key, V min, V max) {
        return null;
    }

    public <K, V> Set<V> zrange(K key, long start, long end) {
        return null;
    }

    public <K, V> Set<V> zrangeByLex(K key, V min, V max) {
        return null;
    }

    public <K, V> Set<V> zrangeByLex(K key, V min, V max, int offset, int count) {
        return null;
    }

    public <K, V> Set<V> zrevrange(K key, long start, long end) {
        return null;
    }

    public <K, V> Set<V> zrevrangeByScore(K key, double max, double min) {
        return null;
    }

    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    public <K, V> Set<V> zrangeByScore(K key, double min, double max, int offset, int count) {
        return null;
    }

    public <K, V> Long zrank(K key, V member) {
        return null;
    }

    public <K, V> Long zrem(K key, V... members) {
        return null;
    }

    public <K, V> Long zremrangeByLex(K key, V min, V max) {
        return null;
    }

    public <K> Long zremrangeByRank(K key, long start, long end) {
        return null;
    }

    public <K, V> Long zremrangeByScore(K key, double start, double end) {
        return null;
    }

    public <K, V> Long zrevrank(K key, V member) {
        return null;
    }

    public <K, V> Double zscore(K key, V member) {
        return null;
    }

    public <K, V> Long zunionstore(K dstkey, V... sets) {
        return null;
    }

    public <K, V> Long zunionstore(K dstkey, ZParams params, V... sets) {
        return null;
    }

    public <K> ScanResult<Tuple> zscan(K key, String cursor) {
        return null;
    }

    public <K> ScanResult<Tuple> zscan(K key, String cursor, ScanParams params) {
        return null;
    }
}
