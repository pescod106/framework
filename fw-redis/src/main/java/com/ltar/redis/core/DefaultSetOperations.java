package com.ltar.redis.core;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.core.ops.SetOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.*;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultSetOperations extends AbstractOperations implements SetOperations {

    public DefaultSetOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public <K, V> Long sadd(K key, V... values) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            return jedis.sadd(SerializeUtil.serialize(key), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long scard(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.scard(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> sdiff(K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            Set<byte[]> byteSet = jedis.sdiff(bytes);
            Set<V> resultSet = new HashSet<V>(keys.length);
            Iterator<byte[]> iterator = byteSet.iterator();
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long sdiffstore(K dstkey, K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            return jedis.sdiffstore(SerializeUtil.serialize(dstkey), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> sinter(K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            Set<byte[]> byteSet = jedis.sinter(bytes);
            Set<V> resultSet = new HashSet<V>(keys.length);
            Iterator<byte[]> iterator = byteSet.iterator();
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long sinterstore(K dstkey, K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            return jedis.sinterstore(SerializeUtil.serialize(dstkey), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Boolean sismember(K key, V member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.sismember(SerializeUtil.serialize(key), SerializeUtil.serialize(member));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> smembers(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSet = jedis.smembers(SerializeUtil.serialize(key));
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
    public <K, V> Long smove(K srckey, K dstkey, V member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.smove(SerializeUtil.serialize(srckey), SerializeUtil.serialize(dstkey), SerializeUtil.serialize(member));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> V spop(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.spop(SerializeUtil.serialize(key));
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> V srandmember(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.srandmember(SerializeUtil.serialize(key));
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> List<V> srandmember(K key, int count) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<byte[]> byteList = jedis.srandmember(SerializeUtil.serialize(key), count);
            List<V> resultList = new ArrayList<V>(byteList.size());
            Iterator<byte[]> iterator = byteList.iterator();
            while (iterator.hasNext()) {
                resultList.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultList;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long srem(K key, V... member) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[member.length][];
            for (int i = 0; i < member.length; i++) {
                bytes[i] = SerializeUtil.serialize(member[i]);
            }
            return jedis.srem(SerializeUtil.serialize(member), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> sunion(K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            Set<byte[]> byteSet = jedis.sunion(bytes);
            Set<V> resultSet = new HashSet<V>(keys.length);
            Iterator<byte[]> iterator = byteSet.iterator();
            while (iterator.hasNext()) {
                resultSet.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultSet;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long sunionstore(K dstkey, K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            return jedis.sunionstore(SerializeUtil.serialize(dstkey), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> ScanResult<V> sscan(K key, String cursor) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            ScanResult<byte[]> scanResult = jedis.sscan(SerializeUtil.serialize(key), SerializeUtil.serialize(key));
            List<V> resultList = new ArrayList<V>(scanResult.getResult().size());
            Iterator iterator = scanResult.getResult().iterator();
            while (iterator.hasNext()) {
                resultList.add((V) SerializeUtil.serialize(iterator.next()));
            }
            return new ScanResult<V>(scanResult.getStringCursor(), resultList);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> ScanResult<V> sscan(K key, String cursor, ScanParams params) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            ScanResult<byte[]> scanResult = jedis.sscan(SerializeUtil.serialize(key), SerializeUtil.serialize(key), params);
            List<V> resultList = new ArrayList<V>(scanResult.getResult().size());
            Iterator iterator = scanResult.getResult().iterator();
            while (iterator.hasNext()) {
                resultList.add((V) SerializeUtil.serialize(iterator.next()));
            }
            return new ScanResult<V>(scanResult.getStringCursor(), resultList);
        } finally {
            closeJedis(jedis);
        }
    }
}
