package com.ltar.framework.redis.impl;

import com.ltar.framework.base.util.SerializeUtil;
import com.ltar.framework.redis.constant.DataType;
import com.ltar.framework.redis.core.*;
import com.ltar.framework.redis.core.ops.*;
import com.ltar.framework.redis.utils.TimeoutUtils;
<<<<<<< HEAD:fw-redis/src/main/java/com/ltar/framework/redis/impl/RedisTemplate.java
import com.ltar.redis.core.*;
import com.ltar.redis.core.ops.*;
=======
>>>>>>> master:fw-redis/src/main/java/com/ltar/framework/redis/impl/RedisTemplate.java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;

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
    private ConnectionOperations ops4Connection;
    private HashOperations ops4Hash;
    private HyperLogLogOperations ops4HyperLogLog;
    private ListOperations ops4List;
    private ServerOperations ops4Server;
    private SetOperations ops4Set;
    private SortedSetOperations ops4ZSet;
    private StringOperations ops4String;

    @Override
    public <K> Boolean exists(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Boolean delete(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(SerializeUtil.serialize(key)) > 0 ? true : false;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
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

    @Override
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

    @Override
    public <K, V> ScanResult<Map.Entry<K, V>> scan(String cursor, ScanParams scanParams) {
        return null;
    }

    @Override
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

    @Override
    public <K> void migrate(String host, int port, K key, int destinationDb, int timeout) {

    }

    @Override
    public <K> void rename(K oldKey, K newKey) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.rename(SerializeUtil.serialize(oldKey), SerializeUtil.serialize(newKey));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long renamenx(K oldKey, K newKey) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.renamenx(SerializeUtil.serialize(oldKey), SerializeUtil.serialize(newKey));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
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

    @Override
    public <K> Long expireAt(K key, Date date) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.pexpire(SerializeUtil.serialize(key), date.getTime());
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long persist(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.persist(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Boolean move(K key, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.move(SerializeUtil.serialize(key), dbIndex) > 0 ? true : false;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long objectRefCount(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.objectRefcount(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> V objectEncoding(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.objectEncoding(SerializeUtil.serialize(key));
            return SerializeUtil.deserialize(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long objectIdletime(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.objectIdletime(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> byte[] dump(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.dump(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.restore(SerializeUtil.serialize(key), Long.valueOf(TimeoutUtils.toMillis(timeToLive, unit)).intValue(), value);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public List<String> sort(String key) {
        return null;
    }

    @Override
    public Long sort(String key, String dstkey) {
        return null;
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {
        return null;
    }

    @Override
    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        return null;
    }

    @Override
    public <K> Long ttl(K key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
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

    @Override
    public String randomKey() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.randomKey();
        } finally {
            closeJedis(jedis);
        }
    }

//    @Override
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
//@Override
//    public void unwatch() {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            jedis.unwatch();
//        } finally {
//            closeJedis(jedis);
//        }
//    }
//@Override
//    public void multi() {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            jedis.multi();
//        } finally {
//            closeJedis(jedis);
//        }
//    }
//@Override
//    public void discard() {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            jedis.
//        }finally {
//            closeJedis(jedis);
//        }
//    }

    @Override
    public ClusterOperations ops4Cluster() {
        if (null == ops4Cluster) {
            ops4Cluster = new DefaultClusterOperations(this);
        }
        return ops4Cluster;
    }

    @Override
    public ConnectionOperations ops4Connection() {
        if (null == ops4Connection) {
            ops4Connection = new DefaultConnectionOperations(this);
        }
        return ops4Connection;
    }

    @Override
    public HashOperations ops4Hash() {
        if (null == ops4Hash) {
            ops4Hash = new DefaultHashOperations(this);
        }
        return ops4Hash;
    }

    @Override
    public HyperLogLogOperations ops4HyperLogLog() {
        if (null == ops4HyperLogLog) {
            ops4HyperLogLog = new DefaultHyperLogLogOperations(this);
        }
        return ops4HyperLogLog;
    }

    @Override
    public ListOperations ops4List() {
        if (null == ops4List) {
            ops4List = new DefaultListOperations(this);
        }
        return ops4List;
    }

    @Override
    public ServerOperations ops4Server() {
        if (null == ops4Server) {
            ops4Server = new DefaultServerOperations(this);
        }
        return ops4Server;
    }

    @Override
    public SetOperations ops4Set() {
        if (null == ops4Set) {
            ops4Set = new DefaultSetOperations(this);
        }
        return ops4Set;
    }

    @Override
    public StringOperations ops4String() {
        if (null == ops4String) {
            ops4String = new DefaultStringOperations(this);
        }
        return ops4String;
    }

    @Override
    public SortedSetOperations opsForZSet() {
        if (null == ops4ZSet) {
            ops4ZSet = new DefaultSortedSetOperations(this);
        }
        return ops4ZSet;
    }


}
