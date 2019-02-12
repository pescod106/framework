package com.ltar.framework.db.redis;

import com.ltar.framework.base.helper.CollectionHelper;
import com.ltar.framework.base.util.ConfigPropertiesUtils;
import com.ltar.framework.base.util.SerializeUtil;
import com.ltar.framework.db.redis.core.RedisManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 * @desc: Redis 操作工具类
 * <a href="http://www.redis.cn/commands.html">Click</a> to see redis commands
 * @author: changzhigao
 * @date: 2018/9/19
 * @version: 1.0.0
 */
public class Redis {
    private static final Logger LOGGER = LoggerFactory.getLogger(Redis.class);
    /**
     * the keys default timeout is one month
     */
    public static final int KEY_DEFAULT_EXPIRE_TIMEOUT = 60 * 60 * 24 * 30;


    private static String redisIp;
    private static int redisPort;
    private static int redisTimeout;

    private static String REDIS_DEFAULT_IP = "127.0.0.1";
    private static int REDIS_DEFAULT_PORT = Protocol.DEFAULT_PORT;
    private static int REDIS_DEFAULT_TIMEOUT = Protocol.DEFAULT_TIMEOUT;


    static {
        redisIp = null == ConfigPropertiesUtils.getValue("redis.ip") ? REDIS_DEFAULT_IP : ConfigPropertiesUtils.getValue("redis.ip");
        redisPort = null == ConfigPropertiesUtils.getValue("redis.port") ? REDIS_DEFAULT_PORT : Integer.parseInt(ConfigPropertiesUtils.getValue("redis.port"));
        redisTimeout = null == ConfigPropertiesUtils.getValue("redis.timeout") ? REDIS_DEFAULT_TIMEOUT : Integer.parseInt(ConfigPropertiesUtils.getValue("redis.timeout"));
    }

    public static Jedis getJedis() {
        Jedis jedis = RedisManger.getInstance().getJedis(redisIp, redisPort, redisTimeout);
        if (null == jedis) {
            throw new JedisException("can't get jedis with redisIp/redisPort :" + redisIp + "/" + redisPort);
        }
        return jedis;
    }

    public static void closeJedis(Jedis jedis) {
        if (null != jedis) {
            RedisManger.getInstance().closeJedis(jedis, redisIp, redisPort, redisTimeout);
        }
    }


    /**
     * see {@link #set(Object, Object)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/set.html">set</a> to see detail
     *
     * @param key
     * @param value
     * @return
     */
    public static void set(Object key, Object value) {
        set(key, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the serialize object value as value of the key.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/set.html">set</a> to see detail
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static void set(Object key, Object value, int seconds) {

        Jedis jedis = null;
        boolean success = false;
        try {
            jedis = getJedis();
            jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key), seconds);
            success = true;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Get an object as value of specified key.
     * If the key does not exist null is returned.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/get.html">get</a> to see detail
     *
     * @param key
     * @return
     */
    public static <T> T get(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.get(SerializeUtil.serialize(key));
            return SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * GETSET is an atomic set this value and return the old value command. Set
     * key to the string value and return the old value stored at key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/getset.html">getset</a> to see detail
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T getSet(Object key, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.getSet(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            return SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * GETSET is an atomic set this value and return the old value command. Set
     * key to the string value and return the old value stored at key
     * <p>
     * Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/getset.html">getset</a> to see detail
     *
     * @param key
     * @param value
     * @param seconds
     * @param <T>
     * @return
     */
    public static <T> T getSet(Object key, Object value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.getSet(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
    }


    /**
     * see {@link #setnx(Object, Object, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/setnx.html">setnx</a> to see detail
     *
     * @param key
     * @param value
     * @return
     */
    public static void setnx(Object key, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long result = jedis.setnx(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            if (1L == result) {
                jedis.expire(SerializeUtil.serialize(key), KEY_DEFAULT_EXPIRE_TIMEOUT);
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * if key don't exist,will set the string value as value of the key,and set expire time
     * if not, the value will not be set of the key,but expire time will be set
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/setnx.html">setnx</a> to see detail
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static void setnx(Object key, Object value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setnx(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key), seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * See {@link #mset(int, Object...)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/mset.html">mset</a> to see detail
     *
     * @param keysvalues
     */
    public static void mset(Object... keysvalues) {
        mset(KEY_DEFAULT_EXPIRE_TIMEOUT, keysvalues);
    }

    /**
     * Set the the respective keys to the respective values. MSET will replace
     * old values with new values,
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/mset.html">mset</a> to see detail
     *
     * @param seconds
     * @param keysvalues key value pair, param count must be even
     */
    public static void mset(int seconds, Object... keysvalues) {
        if (keysvalues.length == 0 || keysvalues.length % 2 != 0) {
            throw new IllegalArgumentException("param count must be even,please check");
        }
        Jedis jedis = null;
        try {
            byte[][] bytes = new byte[keysvalues.length][];
            for (int i = 0; i < keysvalues.length; i++) {
                bytes[i] = SerializeUtil.serialize(keysvalues[i]);
            }
            jedis = getJedis();
            jedis.mset(bytes);
            for (int i = 0; i < bytes.length; i++) {
                jedis.expire(bytes[i], KEY_DEFAULT_EXPIRE_TIMEOUT);
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * See {@link #msetnx(int, Object...)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/msetnx.html">msetnx</a> to see detail
     *
     * @param keysvalues
     */
    public static void msetnx(Object... keysvalues) {
        msetnx(KEY_DEFAULT_EXPIRE_TIMEOUT, keysvalues);
    }

    /**
     * Set the the respective keys to the respective values.
     * {@link #mset(int, Object...) MSET} will replace old values with new values,
     * while MSETNX will not perform any operation at all even if just a single
     * key already exists.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/msetnx.html">msetnx</a> to see detail
     *
     * @param keysvalues
     */
    public static void msetnx(int seconds, Object... keysvalues) {
        if (keysvalues.length == 0 || keysvalues.length % 2 != 0) {
            throw new IllegalArgumentException("param count must be even,please check");
        }
        Jedis jedis = null;
        try {
            byte[][] bytes = new byte[keysvalues.length][];
            for (int i = 0; i < keysvalues.length; i++) {
                bytes[i] = SerializeUtil.serialize(keysvalues[i]);
            }
            jedis = getJedis();
            jedis.msetnx(bytes);
            for (int i = 0; i < bytes.length; i++) {
                jedis.expire(bytes[i], KEY_DEFAULT_EXPIRE_TIMEOUT);
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * see {@link #hset(Object, Object, Object, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hset.html">hset</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     */
    public static void hset(Object key, Object field, Object value) {
        hset(key, field, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the specified hash field to the specified value.
     * Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hset.html">hset</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     * @param seconds
     */
    public static void hset(Object key, Object field, Object value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(SerializeUtil.serialize(key), SerializeUtil.serialize(field), SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key), seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Set the specified hash field to the specified value if the field not exists.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hsetnx.html">hsetnx</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     */
    public static void hsetnx(Object key, Object field, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long result = jedis.hsetnx(SerializeUtil.serialize(key), SerializeUtil.serialize(field), SerializeUtil.serialize(value));
            if (result == 1L) {
                jedis.expire(SerializeUtil.serialize(key), KEY_DEFAULT_EXPIRE_TIMEOUT);
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Set the specified hash field to the specified value if the field not exists.
     * Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hsetnx.html">hsetnx</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     * @param seconds
     */
    public static void hsetnx(Object key, Object field, Object value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hsetnx(SerializeUtil.serialize(key), SerializeUtil.serialize(field), SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key), seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * If key holds a hash, retrieve the value associated to the specified
     * field.
     * <p>
     * If the field is not found or the key does not exist, null is returned.
     * <p>
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hget.html">hget</a> to see detail
     *
     * @param key
     * @param field
     * @return
     */
    public static <T> T hget(Object key, Object field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] bytes = jedis.hget(SerializeUtil.serialize(key), SerializeUtil.serialize(field));
            return SerializeUtil.deserialize(bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Return the number of items in a hash.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hlen.html">hlen</a> to see detail
     *
     * @param key
     * @return The number of entries (fields) contained in the hash stored at
     * key. If the specified key does not exist, 0 is returned assuming
     * an empty hash.
     */
    public static Long hlen(Object key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.hlen(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Test for existence of a specified field in a hash.
     * <p>
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hexists.html">hexists</a> to see detail
     *
     * @param key
     * @param field
     * @return Return 1 if the hash stored at key contains the specified field.
     * Return 0 if the key is not found or the field is not present.
     */
    public static Boolean hexists(Object key, Object field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hexists(SerializeUtil.serialize(key), SerializeUtil.serialize(field));
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * see {@link #hmset(Object, Map, int)}
     * <p>
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hmset.html">hmset</a> to see detail
     *
     * @param key
     * @param beanMap
     */
    public static void hmset(Object key, Map<? extends Object, ? extends Object> beanMap) {
        hmset(key, beanMap, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the respective fields to the respective values. HMSET replaces old
     * values with new values.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hmset.html">hmset</a> to see detail
     *
     * @param key
     * @param beanMap
     * @param seconds
     */
    public static void hmset(Object key, Map<? extends Object, ? extends Object> beanMap, int seconds) {
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
            jedis.expire(SerializeUtil.serialize(key), seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Retrieve one value associated to the specified field.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hmget.html">hgset</a> to see detail
     *
     * @param key
     * @param field
     * @return
     */
    public static <T> T hmget(Object key, Object field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] bytes = CollectionHelper.getMostOneFromCollection(
                    jedis.hmget(SerializeUtil.serialize(key), SerializeUtil.serialize(field))
            );
            return SerializeUtil.deserialize(bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Retrieve the values associated to the specified fields.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hmget.html">hgset</a> to see detail
     *
     * @param key
     * @param fields
     * @param <T>
     * @return
     */
    public static <T> List<T> hmget(Object key, Object... fields) {
        Jedis jedis = null;
        List<T> result = null;
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
                result = new ArrayList<T>();
                for (int i = 0; i < bytes.size(); i++) {
                    result.add((T) SerializeUtil.deserialize(bytes.get(i)));
                }
            }
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/expire.html">expire</a> to see detail
     *
     * @param key
     * @param seconds duration seconds
     * @return 1: the redisTimeout was set. 0: the redisTimeout was not set
     */
    public static Long expire(Object key, int seconds) {
        Jedis jedis = null;
        Long result;
        try {
            jedis = getJedis();
            result = jedis.expire(SerializeUtil.serialize(key), seconds);

        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Set a expired time on the specified key
     * the key will be  automatically deleted by the server at unixTime
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/expireat.html">expireat</a> to see detail
     *
     * @param key
     * @param unixTime Number of seconds elapsed since 1 Gen 1970
     * @return
     */
    public static Long expireAt(Object key, long unixTime) {
        Jedis jedis = null;
        Long result;
        try {
            jedis = getJedis();
            result = jedis.expireAt(SerializeUtil.serialize(key), unixTime);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * returns the remaining time to live in seconds of a key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/ttl.html">ttl</a> to see detail
     *
     * @param key
     * @return
     */
    public static Long ttl(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * returns the remaining time to live in millisecond of a key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/pttl.html">pttl</a> to see detail
     *
     * @param key
     * @return
     */
    public static Long pttl(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.pttl(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Test if the specified key exists.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/exists.html">exists</a> to see detail
     *
     * @param key
     * @return
     */
    public static Boolean exists(Object key) {
        Jedis jedis = null;
        Boolean result = null;
        try {
            jedis = getJedis();
            result = jedis.exists(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Remove the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/del.html">del</a> to see detail
     *
     * @param key
     * @return the number of key removed.
     */
    public static Long del(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Remove the specified keys. If a given key does not exist no operation is
     * performed for this key.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/del.html">del</a> to see detail
     *
     * @param keys
     * @return the number of keys removed.
     */
    public static Long del(Object... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            return jedis.del(bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Remove the specified field from an hash stored at key.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hdel.html">hdel</a> to see detail
     *
     * @param key
     * @param field
     * @return If the field was present in the hash it is deleted and 1 is
     * returned, otherwise 0 is returned and no operation is performed.
     */
    public static Long hdel(Object key, Object field) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.hdel(SerializeUtil.serialize(key), SerializeUtil.serialize(field));
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Remove the  fields from an hash stored at key.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hdel.html">hdel</a> to see detail
     *
     * @param key
     * @param fields
     * @return If the field was present in the hash it is deleted and 1 is
     * returned, otherwise 0 is returned and no operation is performed.
     */
    public static Long hdel(Object key, Object... fields) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[fields.length][];
            for (int i = 0; i < fields.length; i++) {
                bytes[i] = SerializeUtil.serialize(fields[i]);
            }
            result = jedis.hdel(SerializeUtil.serialize(key), bytes);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }


    /**
     * Returns all the keys matching the glob-style pattern as space separated
     * strings. For example if you have in the database the keys "foo" and
     * "foobar" the command "KEYS foo*" will return "foo foobar".
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/keys.html">keys</a> to see detail
     *
     * @param pattern
     * @return
     */
    public static <T> Set<T> keys(Object pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();

            Set<byte[]> bytes = jedis.keys(SerializeUtil.serialize(pattern));
            Set<T> sets = new HashSet<T>(bytes.size());
            Iterator iterator = bytes.iterator();
            while (iterator.hasNext()) {
                sets.add((T) SerializeUtil.deserialize((byte[]) iterator.next()));
            }
            return sets;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Delete all the keys of the currently selected DB. This command never fails.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/flushdb.html">flushdb</a> to see detail
     */
    public static void fulshDB() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushDB();
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * see {@link #incrBy(Object, long)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/incr.html">incr</a> to see detail
     *
     * @param key
     * @return
     */
    public static Long incr(Object key) {
        return incrBy(key, 1L);
    }

    /**
     * See {@link #incrBy(Object, long, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/incr.html">incr</a> to see detail
     *
     * @param key
     * @param seconds
     * @return
     */
    public static Long incr(Object key, int seconds) {
        return incrBy(key, 1L, seconds);
    }

    /**
     * Increment the number stored at key by 'number'. If the key does not exist or
     * contains a value of a wrong type, set the key to the value of "number" before
     * to perform the increment operation.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/incrby.html">incrby</a> to see detail
     *
     * @param key
     * @param number
     * @return
     */
    public static Long incrBy(Object key, long number) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incrBy(SerializeUtil.serialize(key), number);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Increment the number stored at key by 'number'. If the key does not exist or
     * contains a value of a wrong type, set the key to the value of "0" before
     * to perform the increment operation.
     * <p>
     * And Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/incrby.html">incrby</a> to see detail
     *
     * @param key
     * @param number
     * @param seconds
     * @return
     */
    public static Long incrBy(Object key, long number, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.incrBy(SerializeUtil.serialize(key), number);
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * See {@link #decrBy(Object, long)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/decr.html">decr</a> to see detail
     *
     * @param key
     * @return
     */
    public static Long decr(String key) {
        return decrBy(key, 1L);
    }

    /**
     * See {@link #decrBy(Object, long, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/decr.html">decr</a> to see detail
     *
     * @param key
     * @param seconds
     * @return
     */
    public static Long decr(Object key, int seconds) {
        return decrBy(key, 1L, seconds);
    }

    /**
     * Decrement the number stored at key by 'number'. If the key does not exist or
     * contains a value of a wrong type, set the key to the value of "0" before
     * to perform the increment operation.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/decrby.html">decrby</a> to see detail
     *
     * @param key
     * @param number
     * @return
     */
    public static Long decrBy(Object key, long number) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.decrBy(SerializeUtil.serialize(key), number);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Decrement the number stored at key by 'number'. If the key does not exist or
     * contains a value of a wrong type, set the key to the value of "0" before
     * to perform the increment operation.
     * And Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/decrby.html">decrby</a> to see detail
     *
     * @param key
     * @param number
     * @param seconds
     * @return
     */
    public static Long decrBy(Object key, long number, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.decrBy(SerializeUtil.serialize(key), number);
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * See {@link #hincrBy(Object, Object, long)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hincrby.html">hincrby</a> to see detail
     *
     * @param key
     * @param field
     * @return
     */
    public static Long hincr(Object key, Object field) {
        return hincrBy(key, field, 1L);
    }

    /**
     * Increment the number stored at field in the hash at key by value. If key
     * does not exist, a new key holding a hash is created. If field does not
     * exist or holds a string, the value is set to 0 before applying the
     * operation. Since the value argument is signed you can use this command to
     * perform both increments and decrements.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hincrby.html">hincrby</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     * @return Integer reply The new value at field after the increment
     * operation.
     */
    public static Long hincrBy(Object key, Object field, long value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hincrBy(SerializeUtil.serialize(key), SerializeUtil.serialize(field), value);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * See {@link #hincrBy(Object, Object, long, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hincrby.html">hincrby</a> to see detail
     *
     * @param key
     * @param field
     * @param seconds
     * @return
     */
    public static Long hincr(Object key, Object field, int seconds) {
        return hincrBy(key, field, 1L, seconds);
    }

    /**
     * Increment the number stored at field in the hash at key by value. If key
     * does not exist, a new key holding a hash is created. If field does not
     * exist or holds a string, the value is set to 0 before applying the
     * operation. Since the value argument is signed you can use this command to
     * perform both increments and decrements.
     * <p>
     * Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hincrby.html">hincrby</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     * @param seconds
     * @return Integer reply The new value at field after the increment
     * operation.
     */
    public static Long hincrBy(Object key, Object field, long value, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.hincrBy(SerializeUtil.serialize(key), SerializeUtil.serialize(field), value);
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Increment the number stored at field in the hash at key by value. If key
     * does not exist, a new key holding a hash is created. If field does not
     * exist or holds a string, the value is set to 0 before applying the
     * operation. Since the value argument is signed you can use this command to
     * perform both increments and decrements.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hincrbyfloat.html">hincrbyfloat</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     * @return Double precision floating point reply The new value at field
     * after the increment operation.
     */
    public static Double hincrBy(Object key, Object field, double value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hincrByFloat(SerializeUtil.serialize(key), SerializeUtil.serialize(field), value);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Increment the number stored at field in the hash at key by value. If key
     * does not exist, a new key holding a hash is created. If field does not
     * exist or holds a string, the value is set to 0 before applying the
     * operation. Since the value argument is signed you can use this command to
     * perform both increments and decrements.
     * <p>
     * Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/hincrbyfloat.html">hincrbyfloat</a> to see detail
     *
     * @param key
     * @param field
     * @param value
     * @param seconds
     * @return Double precision floating point reply The new value at field
     * after the increment operation.
     */
    public static Double hincrBy(Object key, Object field, double value, int seconds) {
        Jedis jedis = null;
        Double result = null;
        try {
            jedis = getJedis();
            result = jedis.hincrByFloat(SerializeUtil.serialize(key), SerializeUtil.serialize(field), value);
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Return the specified elements of the list stored at the specified key.
     * Start and end are zero-based indexes. 0 is the first element of the list
     * (the list head), 1 the next element, and -1 is the last element, -2 is the
     * second last element and so on
     * <p>
     * start should be less than end
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/lrange.html">lrange</a> to see detail
     *
     * @param key
     * @param start
     * @param end
     * @return specifically a list of elements in the specified range
     */
    public static List<? extends Object> lrange(Object key, long start, long end) {
        Jedis jedis = null;
        List list;
        try {
            jedis = getJedis();
            List<byte[]> bytes = jedis.lrange(SerializeUtil.serialize(key), start, end);
            if (!CollectionUtils.isEmpty(bytes)) {
                list = new ArrayList();
                Iterator iterator = bytes.iterator();
                while (iterator.hasNext()) {
                    list.add(SerializeUtil.deserialize((byte[]) iterator.next()));
                }
                return list;
            } else {
                LOGGER.warn("lrange {},{},{} list is null or empty. return null", key, start, end);
                return null;
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * See {@link #rpush(Object, Object, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/rpush.html">rpush</a> to see detail
     *
     * @param key
     * @param value
     * @return
     */
    public static Long rpush(Object key, Object value) {
        return rpush(key, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/rpush.html">rpush</a> to see detail
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static Long rpush(Object key, Object value, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.rpush(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }

    }

    /**
     * See {@link #rpush(Object, int, Object...)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/rpush.html">rpush</a> to see detail
     *
     * @param key
     * @param values
     * @return
     */
    public static Long rpush(Object key, Object... values) {
        return rpush(key, KEY_DEFAULT_EXPIRE_TIMEOUT, values);
    }

    /**
     * See {@link #rpush(Object, Object, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/rpush.html">rpush</a> to see detail
     *
     * @param key
     * @param seconds
     * @param values
     * @return
     */
    public static Long rpush(Object key, int seconds, Object... values) {
        Jedis jedis = null;
        Long result = null;
        try {
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            jedis = getJedis();
            result = jedis.rpush(SerializeUtil.serialize(key), bytes);
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }

    }


    /**
     * See {@link #lpush(Object, Object, int)}
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/lpush.html">lpush</a> to see detail
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lpush(Object key, Object value) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.lpush(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            if (result == 1L) {
                // result = values element length,indicate the list is new to create
                jedis.expire(SerializeUtil.serialize(key), KEY_DEFAULT_EXPIRE_TIMEOUT);
            }
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/lpush.html">lpush</a> to see detail
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static Long lpush(Object key, Object value, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.lpush(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/lpush.html">lpush</a> to see detail
     *
     * @param key
     * @param values
     * @return
     */
    public static Long lpush(Object key, Object... values) {
        Jedis jedis = null;
        Long result = null;
        try {
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            jedis = getJedis();
            result = jedis.lpush(SerializeUtil.serialize(key), bytes);
            if (result == values.length) {
                // result = values element length,indicate the list is new to create
                jedis.expire(SerializeUtil.serialize(key), KEY_DEFAULT_EXPIRE_TIMEOUT);
            }
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
     * <p>
     * Set a timeout on the specified key
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/lpush.html">lpush</a> to see detail
     *
     * @param key
     * @param seconds
     * @param values
     * @return
     */
    public static Long lpushWithExpire(int seconds, Object key, Object... values) {
        Jedis jedis = null;
        Long result = null;
        try {
            byte[][] bytes = new byte[values.length][];
            for (int i = 0; i < values.length; i++) {
                bytes[i] = SerializeUtil.serialize(values[i]);
            }
            jedis = getJedis();
            result = jedis.lpush(SerializeUtil.serialize(key), bytes);
            jedis.expire(SerializeUtil.serialize(key), seconds);
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Atomically return and remove the first (LPOP) or last (RPOP) element of
     * the list. For example if the list contains the elements "a","b","c" LPOP
     * will return "a" and the list will become "b","c".
     *
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/rpop.html">rpop</a> to see detail
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T rpop(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.rpop(SerializeUtil.serialize(key));
            return (T) SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Atomically return and remove the first (LPOP) or last (RPOP) element of
     * the list. For example if the list contains the elements "a","b","c" LPOP
     * will return "a" and the list will become "b","c".
     *
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/lpop.html">lpop</a> to see detail
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T lpop(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.lpop(SerializeUtil.serialize(key));
            return (T) SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/blpop.html">blpop</a> to see detail
     *
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> T blpop(Object... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.blpop(bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (T) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * BLPOP (and BRPOP) is a blocking list pop primitive. You can see this
     * commands as blocking versions of LPOP and RPOP able to block if the
     * specified keys don't exist or contain empty lists.
     *
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/blpop.html">blpop</a> to see detail
     *
     * @param timeout
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> T blpopWithTimeout(int timeout, Object... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.blpop(timeout, bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (T) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }


    /**
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/brpop.html">brpop</a> to see detail
     *
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> T brpop(Object... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.brpop(bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (T) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * BLPOP (and BRPOP) is a blocking list pop primitive. You can see this
     * commands as blocking versions of LPOP and RPOP able to block if the
     * specified keys don't exist or contain empty lists.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/brpop.html">brpop</a> to see detail
     *
     * @param timeout
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> T brpopWithTimeout(int timeout, Object... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            List<byte[]> obj = jedis.brpop(timeout, bytes);
            if (!CollectionUtils.isEmpty(obj)) {
                return (T) SerializeUtil.deserialize(obj.get(1));
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * Return the length of the list stored at the specified key. If the key
     * does not exist zero is returned (the same behaviour as for empty lists).
     * If the value stored at key is not a list an error is returned.
     * <p>
     * <p>
     * click  <a href="http://www.redis.cn/commands/llen.html">llen</a> to see detail
     *
     * @param key
     * @return
     */
    public static Long llen(Object key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.llen(SerializeUtil.serialize(key));
        } finally {
            closeJedis(jedis);
        }
    }
}

