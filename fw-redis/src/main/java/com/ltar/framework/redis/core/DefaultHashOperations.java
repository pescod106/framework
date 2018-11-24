package com.ltar.framework.redis.core;

import com.ltar.framework.base.util.SerializeUtil;
import com.ltar.framework.redis.impl.RedisTemplate;
import com.ltar.framework.redis.core.ops.HashOperations;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;
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
public class DefaultHashOperations extends AbstractOperations implements HashOperations {

    public DefaultHashOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public <K, HK> Long hdel(K key, HK... fields) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[fields.length][];
            for (int i = 0; i < fields.length; i++) {
                bytes[i] = SerializeUtil.serialize(fields[i]);
            }
            return jedis.hdel(SerializeUtil.serialize(key), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK> Boolean hexists(K key, HK field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hexists(SerializeUtil.serialize(key), SerializeUtil.serialize(field));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK, V> V hget(K key, HK field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] bytes = jedis.hget(SerializeUtil.serialize(key), SerializeUtil.serialize(field));
            return SerializeUtil.deserialize(bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK, V> Map<HK, V> hgetAll(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Map<byte[], byte[]> byteMap = jedis.hgetAll(SerializeUtil.serialize(key));
            Map<HK, V> resultMap = new HashMap<HK, V>(byteMap.size());
            for (Map.Entry<byte[], byte[]> entry : byteMap.entrySet()) {
                resultMap.put((HK) SerializeUtil.deserialize(entry.getKey()), (V) SerializeUtil.deserialize(entry.getValue()));
            }
            return resultMap;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK> Long hincrBy(K key, HK field, long increment) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hincrBy(SerializeUtil.serialize(key), SerializeUtil.serialize(field), increment);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK> Double hincrByFloat(K key, HK field, double increment) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hincrByFloat(SerializeUtil.serialize(key), SerializeUtil.serialize(field), increment);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> Set<V> hkeys(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<byte[]> byteSets = jedis.hkeys(SerializeUtil.serialize(key));
            Iterator iterator = byteSets.iterator();
            Set<V> results = new HashSet<V>(byteSets.size());
            while (iterator.hasNext()) {
                results.add((V) SerializeUtil.serialize(iterator.next()));
            }
            return results;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long hlen(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hlen(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK, V> List<V> hmget(K key, HK... fields) {
        Jedis jedis = null;
        List<V> result = null;
        if (null == fields || fields.length == 0) {
            return null;
        }

        try {
            byte[][] inBytes = new byte[fields.length][];
            for (int i = 0; i < fields.length; i++) {
                inBytes[i] = SerializeUtil.serialize(fields[i]);
            }

            jedis = getJedis();
            List<byte[]> bytes = jedis.hmget(SerializeUtil.serialize(key), inBytes);
            if (null != bytes) {
                result = new ArrayList<V>();
                for (int i = 0; i < bytes.size(); i++) {
                    result.add((V) SerializeUtil.deserialize(bytes.get(i)));
                }
            }
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    @Override
    public <K> void hmset(K key, Object... keysValues) {
        if (keysValues.length == 0 || keysValues.length % 2 != 0) {
            throw new IllegalArgumentException("param is error,param keysValues must be even");
        }
        Jedis jedis = null;
        try {
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>(keysValues.length / 2);
            for (int i = 0; i < keysValues.length; i += 2) {
                map.put(SerializeUtil.serialize(keysValues[i]), SerializeUtil.serialize(keysValues[i + 1]));
            }
            jedis = getJedis();
            jedis.hmset(SerializeUtil.serialize(key), map);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK, V> void hmset(K key, Map<HK, V> beanMap) {
        Jedis jedis = null;
        if (CollectionUtils.isEmpty(beanMap)) {
            LOGGER.warn("param 'beanMap' is empty");
            return;
        }
        try {
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>(beanMap.size());
            Iterator iterator = beanMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                map.put(SerializeUtil.serialize(entry.getKey()), SerializeUtil.serialize(entry.getValue()));
            }

            jedis = getJedis();
            jedis.hmset(SerializeUtil.serialize(key), map);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK, V> void hset(K key, HK field, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(SerializeUtil.serialize(key), SerializeUtil.serialize(field), SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, HK, V> Boolean hsetNx(K key, HK field, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hsetnx(SerializeUtil.serialize(key), SerializeUtil.serialize(field), SerializeUtil.serialize(value)) > 0 ? true : false;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> List<V> hvals(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<byte[]> byteList = jedis.hvals(SerializeUtil.serialize(key));
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

    @Override
    public <K, HK, HV> ScanResult<Pair<HK, HV>> hscan(K key, String cursor, ScanParams scanParams) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            ScanResult<Map.Entry<byte[], byte[]>> scanResult = jedis.hscan(
                    SerializeUtil.serialize(key), SerializeUtil.serialize(cursor), scanParams);
            List<Pair<HK, HV>> entryList = new ArrayList<Pair<HK, HV>>(scanResult.getResult().size());
            final Iterator<Map.Entry<byte[], byte[]>> iterator = scanResult.getResult().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<byte[], byte[]> mapEntry = iterator.next();
                Pair<HK, HV> pair = Pair.of((HK) SerializeUtil.deserialize(mapEntry.getKey()), (HV) SerializeUtil.deserialize(mapEntry.getValue()));
                entryList.add(pair);
            }
            return new ScanResult<Pair<HK, HV>>(scanResult.getStringCursor(), entryList);
        } finally {
            closeJedis(jedis);
        }
    }
}
