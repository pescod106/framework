package com.ltar.redis.core;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.core.ops.StringOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.BitPosParams;
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
public class DefaultStringOperations extends AbstractOperations implements StringOperations {
    public DefaultStringOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }


    public <K, V> Long append(K key, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.append(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long bitCount(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.bitcount(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long bitCount(K key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.bitcount(SerializeUtil.serialize(key), start, end);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long bitPos(K key, boolean value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.bitpos(SerializeUtil.serialize(key), value);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long bitPos(K key, boolean value, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.bitpos(SerializeUtil.serialize(key), value, new BitPosParams(start, end));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long decr(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.decr(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long decrBy(K key, int decrement) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.decrBy(SerializeUtil.serialize(key), decrement);
        } finally {
            closeJedis(jedis);
        }
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

    public <K> Boolean getBit(K key, long offset) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.getbit(SerializeUtil.serialize(key), offset);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K, V> V getRange(K key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.getrange(SerializeUtil.serialize(key), start, end);
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K, V> V getSet(K key, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.getSet(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long incr(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incr(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long incrBy(K key, long increment) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incrBy(SerializeUtil.serialize(key), increment);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Double incrByFloat(K key, double increment) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incrByFloat(SerializeUtil.serialize(key), increment);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K, V> List<V> mget(K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> byteList = jedis.mget(bytes);
            Iterator<byte[]> iterator = byteList.iterator();
            List<V> resultList = new ArrayList<V>(byteList.size());
            while (iterator.hasNext()) {
                resultList.add((V) SerializeUtil.deserialize(iterator.next()));
            }
            return resultList;
        } finally {
            closeJedis(jedis);
        }
    }

    public void mset(Object... keysValues) {
        Jedis jedis = null;
        if (keysValues.length == 0 || keysValues.length % 2 != 0) {
            throw new IllegalArgumentException("param keysValues error ,param's count must be even and not be 0");
        }
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keysValues.length][];
            for (int i = 0; i < keysValues.length; i++) {
                bytes[i] = SerializeUtil.serialize(keysValues[i]);
            }
            jedis.mset(bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K, V> void psetex(K key, V value, int milliseconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.psetex(SerializeUtil.serialize(key), milliseconds, SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }

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
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(SerializeUtil.serialize(key), seconds, SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }

    }

    public <K, V> Long setNx(K key, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setnx(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Boolean setBit(K key, long offset, boolean value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setbit(SerializeUtil.serialize(key), offset, value);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K, V> Long setRange(K key, V value, long offset) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setrange(SerializeUtil.serialize(key), offset, SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long strLen(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.strlen(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }
}
