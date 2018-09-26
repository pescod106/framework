package com.ltar.redis.impl;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.constant.DataType;
import com.ltar.redis.core.*;
import com.ltar.redis.core.ops.*;
import com.ltar.redis.utils.TimeoutUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public class RedisTemplate extends RedisAccessor implements RedisOperations {

    private ClusterOperations ops4Cluster;
    private HashOperations ops4Hash;
    private HyperLogLogOperations ops4HyperLogLog;
    private ListOperations ops4List;
    private SetOperations ops4Set;
    private SortedSetOperations ops4ZSet;
    private StringOperations ops4String;

    public <K> Boolean exists(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Boolean delete(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(SerializeUtil.serialize(key)) > 0 ? true : false;
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long delete(Collection<K> keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.size()][];
            int index = 0;
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                bytes[index] = SerializeUtil.serialize(iterator.next());
            }
            return jedis.del(bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> DataType type(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String type = jedis.type(SerializeUtil.serialize(key));
            return DataType.fromCode(type);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K, V> ScanResult<Map.Entry<K, V>> scan(String cursor, ScanParams scanParams) {
        return null;
    }

    public <K> Set<K> keys(K pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();

            Set<byte[]> bytes = jedis.keys(SerializeUtil.serialize(pattern));
            Set<K> sets = new HashSet<K>(bytes.size());
            Iterator iterator = bytes.iterator();
            while (iterator.hasNext()) {
                sets.add((K) SerializeUtil.deserialize((byte[]) iterator.next()));
            }
            return sets;
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> void migrate(String host, int port, K key, int destinationDb, int timeout) {

    }

    public <K> void rename(K oldKey, K newKey) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.rename(SerializeUtil.serialize(oldKey), SerializeUtil.serialize(newKey));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long renamenx(K oldKey, K newKey) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.renamenx(SerializeUtil.serialize(oldKey), SerializeUtil.serialize(newKey));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long expire(K key, long timeout, TimeUnit timeUnit) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            try {
                return jedis.pexpire(SerializeUtil.serialize(key), TimeoutUtils.toMillis(timeout, timeUnit));
            } catch (Exception e) {
                return jedis.expire(SerializeUtil.serialize(key), TimeoutUtils.toSeconds(timeout, timeUnit));
            }
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long expireAt(K key, Date date) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.pexpire(SerializeUtil.serialize(key), date.getTime());
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long persist(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.persist(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Boolean move(K key, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.move(SerializeUtil.serialize(key), dbIndex) > 0 ? true : false;
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long objectRefCount(K key) {
        return null;
    }

    public <K, V> V objectEncoding(K key) {
        return null;
    }

    public <K> Long objectIdletime(K key) {
        return null;
    }

    public <K> byte[] dump(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.dump(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.restore(SerializeUtil.serialize(key), Long.valueOf(TimeoutUtils.toMillis(timeToLive, unit)).intValue(), value);
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long ttl(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    public <K> Long ttl(K key, TimeUnit timeUnit) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long pttl = jedis.pttl(SerializeUtil.serialize(key));
            return TimeoutUtils.getTimeUnitNum(pttl, timeUnit);
        } finally {
            closeJedis(jedis);
        }
    }

//    public <K> void watch(K... keys) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            byte[][] bytes = new byte[keys.length][];
//            for (int i = 0; i < keys.length; i++) {
//                bytes[i] = SerializeUtil.serialize(keys[i]);
//            }
//            jedis.watch(bytes);
//        } finally {
//            closeJedis(jedis);
//        }
//    }
//
//
//    public void unwatch() {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            jedis.unwatch();
//        } finally {
//            closeJedis(jedis);
//        }
//    }
//
//    public void multi() {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            jedis.multi();
//        } finally {
//            closeJedis(jedis);
//        }
//    }
//
//    public void discard() {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            jedis.
//        }finally {
//            closeJedis(jedis);
//        }
//    }

    public ClusterOperations ops4Cluster() {
        if (null == ops4Cluster) {
            ops4Cluster = new DefaultClusterOperations(this);
        }
        return ops4Cluster;
    }

    public HashOperations ops4Hash() {
        if (null == ops4Hash) {
            ops4Hash = new DefaultHashOperations(this);
        }
        return ops4Hash;
    }

    public HyperLogLogOperations ops4HyperLogLog() {
        if (null == ops4HyperLogLog) {
            ops4HyperLogLog = new DefaultHyperLogLogOperations(this);
        }
        return ops4HyperLogLog;
    }

    public ListOperations ops4List() {
        if (null == ops4List) {
            ops4List = new DefaultListOperations(this);
        }
        return ops4List;
    }

    public SetOperations ops4Set() {
        if (null == ops4Set) {
            ops4Set = new DefaultSetOperations(this);
        }
        return ops4Set;
    }

    public StringOperations ops4String() {
        if (null == ops4String) {
            ops4String = new DefaultStringOperations(this);
        }
        return ops4String;
    }

    public SortedSetOperations opsForZSet() {
        if (null == ops4ZSet) {
            ops4ZSet = new DefaultSortedSetOperations(this);
        }
        return ops4ZSet;
    }


}
