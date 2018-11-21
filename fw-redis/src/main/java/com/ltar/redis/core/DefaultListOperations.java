package com.ltar.redis.core;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.core.ops.ListOperations;
import com.ltar.redis.impl.RedisTemplate;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultListOperations extends AbstractOperations implements ListOperations {
    public DefaultListOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public <K, V> V blpop(K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.blpop(bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (V) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <K, V> V blpop(int timeout, K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.blpop(timeout, bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (V) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <K, V> V brpop(K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.brpop(bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (V) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <K, V> V brpop(int timeout, K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.brpop(timeout, bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (V) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public <K, V> V brpoplpush(K sourceKey, K destinationKey, int timeout) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.brpoplpush(SerializeUtil.serialize(sourceKey), SerializeUtil.serialize(destinationKey), timeout);
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> V lindex(K key, long index) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.lindex(SerializeUtil.serialize(key), index);
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long linsertBefore(K key, V pivot, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.linsert(
                    SerializeUtil.serialize(key),
                    BinaryClient.LIST_POSITION.BEFORE,
                    SerializeUtil.serialize(pivot),
                    SerializeUtil.serialize(value)
            );
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long linsertAfter(K key, V pivot, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.linsert(
                    SerializeUtil.serialize(key),
                    BinaryClient.LIST_POSITION.AFTER,
                    SerializeUtil.serialize(pivot),
                    SerializeUtil.serialize(value)
            );
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long llen(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.llen(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> V lpop(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.lpop(SerializeUtil.serialize(key));
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long lpush(K key, V... values) {
        Jedis jedis = null;
        try {
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            jedis = getJedis();
            return jedis.lpush(SerializeUtil.serialize(key), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long lpushx(K key, V... values) {
        Jedis jedis = null;
        try {
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            jedis = getJedis();
            return jedis.lpushx(SerializeUtil.serialize(key), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> List<V> lrange(K key, long start, long stop) {
        Jedis jedis = null;
        List list;
        try {
            jedis = getJedis();
            List<byte[]> bytes = jedis.lrange(SerializeUtil.serialize(key), start, stop);
            if (!CollectionUtils.isEmpty(bytes)) {
                list = new ArrayList();
                Iterator iterator = bytes.iterator();
                while (iterator.hasNext()) {
                    list.add(SerializeUtil.deserialize((byte[]) iterator.next()));
                }
                return list;
            } else {
                LOGGER.warn("lrange {},{},{} list is null or empty. return null", key, start, stop);
                return null;
            }
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long lrem(K key, long count, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lrem(SerializeUtil.serialize(key), count, SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> void lset(K key, long index, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.lset(SerializeUtil.serialize(key), index, SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> void ltrim(K key, long start, long stop) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.ltrim(SerializeUtil.serialize(key), start, stop);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> V rpop(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.rpop(SerializeUtil.serialize(key));
            return (V) SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> V rpoplpush(K sourceKey, K destinationKey) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.rpoplpush(SerializeUtil.serialize(sourceKey), SerializeUtil.serialize(destinationKey));
            return (V) SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long rpush(K key, V... values) {
        Jedis jedis = null;
        Long result = null;
        try {
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            jedis = getJedis();
            result = jedis.rpush(SerializeUtil.serialize(key), bytes);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Long rpushx(K key, V... values) {
        Jedis jedis = null;
        Long result = null;
        try {
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            jedis = getJedis();
            result = jedis.rpushx(SerializeUtil.serialize(key), bytes);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }
}
