package com.ltar.redis.core;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.core.ops.SortedSetOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.*;

import java.util.*;

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

    @Override
    public <K, V> Long zadd(K key, double score, V member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zadd(SerializeUtil.serialize(key), score, SerializeUtil.serialize(member));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zadd(K key, Map<V, Double> scoreMembers) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Map<byte[], Double> inMap = new HashMap<byte[], Double>(scoreMembers.size());
            for (Map.Entry<V, Double> entry : scoreMembers.entrySet()) {
                inMap.put(SerializeUtil.serialize(entry.getKey()), entry.getValue());
            }
            return jedis.zadd(SerializeUtil.serialize(key), inMap);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long zcard(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zcard(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long zcount(K key, double min, double max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zcount(SerializeUtil.serialize(key), min, max);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Double zincrby(K key, double score, V member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zincrby(SerializeUtil.serialize(key), score, SerializeUtil.serialize(member));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zinterstore(K dstkey, V... sets) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] setByte = new byte[sets.length][];
            for (int i = 0; i < sets.length; i++) {
                setByte[i] = SerializeUtil.serialize(sets[i]);
            }
            return jedis.zinterstore(SerializeUtil.serialize(dstkey), setByte);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zinterstore(K dstkey, ZParams params, V... sets) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] setByte = new byte[sets.length][];
            for (int i = 0; i < sets.length; i++) {
                setByte[i] = SerializeUtil.serialize(sets[i]);
            }
            return jedis.zinterstore(SerializeUtil.serialize(dstkey), params, setByte);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zlexcount(K key, V min, V max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zlexcount(SerializeUtil.serialize(key), SerializeUtil.serialize(min), SerializeUtil.serialize(max));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> zrange(K key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.zrange(SerializeUtil.serialize(key), start, end);
            Iterator<byte[]> iterator = byteSet.iterator();
            Set<V> resultSet = new HashSet<V>(byteSet.size());
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> zrangeByLex(K key, V min, V max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.zrangeByLex(SerializeUtil.serialize(key), SerializeUtil.serialize(min), SerializeUtil.serialize(max));
            Iterator<byte[]> iterator = byteSet.iterator();
            Set<V> resultSet = new HashSet<V>(byteSet.size());
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> zrangeByLex(K key, V min, V max, int offset, int count) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.zrangeByLex(
                    SerializeUtil.serialize(key),
                    SerializeUtil.serialize(min),
                    SerializeUtil.serialize(max),
                    offset,
                    count
            );
            Iterator<byte[]> iterator = byteSet.iterator();
            Set<V> resultSet = new HashSet<V>(byteSet.size());
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> zrevrange(K key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.zrevrange(SerializeUtil.serialize(key), start, end);
            Iterator<byte[]> iterator = byteSet.iterator();
            Set<V> resultSet = new HashSet<V>(byteSet.size());
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> zrevrangeByScore(K key, double max, double min) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.zrevrangeByScore(SerializeUtil.serialize(key), max, min);
            Iterator<byte[]> iterator = byteSet.iterator();
            Set<V> resultSet = new HashSet<V>(byteSet.size());
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> zrangeByScore(K key, double min, double max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.zrangeByScore(SerializeUtil.serialize(key), min, max);
            Iterator<byte[]> iterator = byteSet.iterator();
            Set<V> resultSet = new HashSet<V>(byteSet.size());
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> zrangeByScore(K key, double min, double max, int offset, int count) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.zrangeByScore(SerializeUtil.serialize(key), min, max, offset, count);
            Iterator<byte[]> iterator = byteSet.iterator();
            Set<V> resultSet = new HashSet<V>(byteSet.size());
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zrank(K key, V member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrank(SerializeUtil.serialize(key), SerializeUtil.serialize(member));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zrem(K key, V... members) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] setByte = new byte[members.length][];
            for (int i = 0; i < members.length; i++) {
                setByte[i] = SerializeUtil.serialize(members[i]);
            }
            return jedis.zrem(SerializeUtil.serialize(key), setByte);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zremrangeByLex(K key, V min, V max) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zremrangeByLex(SerializeUtil.serialize(key), SerializeUtil.serialize(min), SerializeUtil.serialize(max));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long zremrangeByRank(K key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zremrangeByRank(SerializeUtil.serialize(key), start, end);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long zremrangeByScore(K key, double start, double end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zremrangeByScore(SerializeUtil.serialize(key), start, end);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zrevrank(K key, V member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrevrank(SerializeUtil.serialize(key), SerializeUtil.serialize(member));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Double zscore(K key, V member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zscore(SerializeUtil.serialize(key), SerializeUtil.serialize(member));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zunionstore(K dstkey, V... sets) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] setByte = new byte[sets.length][];
            for (int i = 0; i < sets.length; i++) {
                setByte[i] = SerializeUtil.serialize(sets[i]);
            }
            return jedis.zinterstore(SerializeUtil.serialize(dstkey), setByte);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long zunionstore(K dstkey, ZParams params, V... sets) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] setByte = new byte[sets.length][];
            for (int i = 0; i < sets.length; i++) {
                setByte[i] = SerializeUtil.serialize(sets[i]);
            }
            return jedis.zinterstore(SerializeUtil.serialize(dstkey), params, setByte);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> ScanResult<Tuple> zscan(K key, String cursor) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zscan(SerializeUtil.serialize(key), SerializeUtil.serialize(cursor));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> ScanResult<Tuple> zscan(K key, String cursor, ScanParams params) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zscan(SerializeUtil.serialize(key), SerializeUtil.serialize(cursor), params);
        } finally {
            closeJedis(jedis);
        }
    }
}
