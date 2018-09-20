package com.ltar.db.redis;

import com.ltar.base.helper.CollectionHelper;
import com.ltar.base.util.ConfigPropertiesUtils;
import com.ltar.base.util.SerializeUtil;
import com.ltar.db.redis.core.RedisManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/19
 * @version: 1.0.0
 */
//todo getSet(String,String)
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
    private static int REDIS_DEFAULT_PORT = 6379;
    private static int REDIS_DEFAULT_TIMEOUT = 2000;


    static {
        redisIp = null == ConfigPropertiesUtils.getValue("redis.ip") ? REDIS_DEFAULT_IP : ConfigPropertiesUtils.getValue("redis.ip");
        redisPort = null == ConfigPropertiesUtils.getValue("redis.port") ? REDIS_DEFAULT_PORT : Integer.valueOf(ConfigPropertiesUtils.getValue("redis.port"));
        redisTimeout = null == ConfigPropertiesUtils.getValue("redis.timeout") ? REDIS_DEFAULT_TIMEOUT : Integer.valueOf(ConfigPropertiesUtils.getValue("redis.timeout"));
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
     * Set the string value as value of the key
     * Survival time is default {@link #KEY_DEFAULT_EXPIRE_TIMEOUT}
     *
     * @param key
     * @param value
     * @return
     */
    public static void setString(String key, String value) {
        setString(key, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the string value as value of the key
     *
     * @param key
     * @param value
     * @param seconds Survival time
     * @return
     */
    public static void setString(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * see {@link #setStringIfNotExists(String, String, int)}
     *
     * @param key
     * @param value
     * @return
     */
    public static void setStringIfNotExists(String key, String value) {
        setStringIfNotExists(key, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * if key don't exist,will set the string value as value of the key,and set expire time
     * if not, the value will not be set of the key,but expire time will be set
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static void setStringIfNotExists(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setnx(key, value);
            jedis.expire(key, seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Get the value of the specified key. If the key does not exist null is
     * returned. If the value stored at key is not a string an error is returned
     * because GET can only handle string values.
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.get(key);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * see {@link #setObject(String, Object, int)}
     *
     * @param key
     * @param value
     * @return
     */
    public static void setObject(String key, Object value) {
        setObject(key, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the serialize object value as value of the key.
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static void setObject(String key, Object value, int seconds) {

        Jedis jedis = null;
        boolean success = false;
        try {
            jedis = getJedis();
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            jedis.expire(key.getBytes(), seconds);
            success = true;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Get an object as value of specified key.
     * If the key does not exist null is returned.
     *
     * @param key
     * @return
     */
    public static Object getObject(String key) {
        Jedis jedis = null;
        Object result = null;
        try {
            jedis = getJedis();
            byte[] obj = jedis.get(key.getBytes());
            result = null == obj ? null : SerializeUtil.deserialize(obj);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * see {@link #hsetString(String, String, String, int)}
     *
     * @param key
     * @param field
     * @param value
     */
    public static void hsetString(String key, String field, String value) {
        hsetString(key, field, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the specified hash field to the specified value.
     * <p>
     * If key does not exist, a new key holding a hash is created.
     * <p>
     *
     * @param key
     * @param field
     * @param value
     * @param seconds
     * @return If the field already exists, and the HSET just produced an update of the value
     */
    public static void hsetString(String key, String field, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(key, field, value);
            jedis.expire(key, seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * @param key
     * @param field
     */
    public static String hgetString(String key, String field) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.hget(key, field);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * see {@link #hsetObject(Object, Object, Object, int)}
     *
     * @param key
     * @param field
     * @param value
     */
    public static void hsetObject(Object key, Object field, Object value) {
        hsetObject(key, field, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * @param key
     * @param field
     * @param value
     * @param seconds
     */
    public static void hsetObject(Object key, Object field, Object value, int seconds) {
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
     * If key holds a hash, retrieve the value associated to the specified
     * field.
     * <p>
     * If the field is not found or the key does not exist, null is returned.
     * <p>
     *
     * @param key
     * @param field
     * @return
     */
    public static Object hgetObject(Object key, Object field) {
        Jedis jedis = null;
        Object result = null;
        try {
            jedis = getJedis();
            byte[] bytes = jedis.hget(SerializeUtil.serialize(key), SerializeUtil.serialize(field));
            result = SerializeUtil.deserialize(bytes);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * {@link #hmsetStrings(String, Map, int)}
     *
     * @param key
     * @param map
     */
    public static void hmsetStrings(String key, Map<String, String> map) {
        hmsetStrings(key, map, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the respective fields to the respective values. HMSET replaces old
     * values with new values.
     *
     * @param key
     * @param map
     * @param seconds
     */
    public static void hmsetStrings(String key, Map<String, String> map, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key, map);
            jedis.expire(key, seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Retrieve the values associated to the specified fields.
     * <p>
     * If some of the specified fields do not exist, null values are returned.
     * Non existing keys are considered like empty hashes.
     * <p>
     *
     * @param key
     * @param fields
     * @return
     */
    public static List<String> hmgetStrings(String key, String... fields) {
        Jedis jedis = null;
        List<String> stringList = null;
        try {
            jedis = getJedis();
            stringList = jedis.hmget(key, fields);
        } finally {
            closeJedis(jedis);
        }
        return stringList;
    }

    /**
     * Retrieve one value associated to the specified field.
     *
     * @param key
     * @param field
     * @return
     */
    public static String hmgetString(String key, String field) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = CollectionHelper.getMostOneFromCollection(jedis.hmget(key, field));
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Return the number of items in a hash.
     *
     * @param key
     * @return The number of entries (fields) contained in the hash stored at
     * key. If the specified key does not exist, 0 is returned assuming
     * an empty hash.
     */
    public static Long hlen(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.hlen(key);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Test for existence of a specified field in a hash.
     *
     * @param key
     * @param field
     * @return Return 1 if the hash stored at key contains the specified field.
     * Return 0 if the key is not found or the field is not present.
     */
    public static Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean result = null;
        try {
            jedis = getJedis();
            result = jedis.hexists(key, field);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * see {@link #hmsetObjects(Object, Map, int)}
     *
     * @param key
     * @param beanMap
     */
    public static void hmsetObjects(Object key, Map<? extends Object, ? extends Object> beanMap) {
        hmsetObjects(key, beanMap, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Set the respective fields to the respective values. HMSET replaces old
     * values with new values.
     *
     * @param key
     * @param beanMap
     * @param seconds
     */
    public static void hmsetObjects(Object key, Map<? extends Object, ? extends Object> beanMap, int seconds) {
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
     *
     * @param key
     * @param field
     * @return
     */
    public static Object hmgetObject(Object key, Object field) {
        Jedis jedis = null;
        Object result = null;
        try {
            jedis = getJedis();
            byte[] bytes = CollectionHelper.getMostOneFromCollection(
                    jedis.hmget(SerializeUtil.serialize(key), SerializeUtil.serialize(field))
            );
            result = SerializeUtil.deserialize(bytes);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Retrieve the values associated to the specified fields.
     *
     * @param key
     * @param kclass
     * @param fields
     * @param <T>
     * @return
     */
    public static <T> List<T> hmgetObjects(Object key, Class<T> kclass, Object... fields) {
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
     * Retrieve the values associated to the specified fields.
     *
     * @param key
     * @param kclass
     * @param fieldList
     * @param <T>
     * @return
     */
    public static <T> List<T> hmgetObjects(Object key, Class<T> kclass, List<? extends Object> fieldList) {
        Jedis jedis = null;
        List<T> result = null;
        if (CollectionUtils.isEmpty(fieldList)) {
            return null;
        }

        try {
            byte[][] inBytes = new byte[fieldList.size()][];
            for (int i = 0; i < fieldList.size(); i++) {
                inBytes[i] = (SerializeUtil.serialize(fieldList.get(i)));
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
     *
     * @param key
     * @param unixTime Number of seconds elapsed since 1 Gen 1970
     * @return
     */
    public static Long expireAt(String key, long unixTime) {
        Jedis jedis = null;
        Long result;
        try {
            jedis = getJedis();
            result = jedis.expireAt(key, unixTime);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }


    /**
     * Test if the specified key exists.
     *
     * @param key
     * @return
     */
    public static Boolean exists(String key) {
        Jedis jedis = null;
        Boolean result = null;
        try {
            jedis = getJedis();
            result = jedis.exists(key);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * Remove the specified key
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
     * see {@link #del(Object...)}
     *
     * @param keys
     * @return
     */
    public static Long del(List<? extends Object> keys) {
        return del(keys.toArray());
    }

    /**
     * Remove the specified keys. If a given key does not exist no operation is
     * performed for this key.
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
     * Returns all the keys matching the glob-style pattern as space separated
     * strings. For example if you have in the database the keys "foo" and
     * "foobar" the command "KEYS foo*" will return "foo foobar".
     *
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.keys(pattern);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Return the specified elements of the list stored at the specified key.
     * Start and end are zero-based indexes. 0 is the first element of the list
     * (the list head), 1 the next element and so on.
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
     * see {@link #incrBy(Object, long)}
     *
     * @param key
     * @return
     */
    public static Long incr(Object key) {
        return incrBy(key, 1L);
    }

    /**
     * See {@link #incrBy(Object, long, int)}
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
     *
     * @param key
     * @return
     */
    public static Long decr(String key) {
        return decrBy(key, 1L);
    }

    /**
     * See {@link #decrBy(Object, long, int)}
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
     * See {@link #rpush(Object, Object, int)}
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
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lpush(Object key, Object value) {
        return lpush(key, value, KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
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
     * See {@link #lpush(Object, int, Object...)}
     *
     * @param key
     * @param values
     * @return
     */
    public static Long lpush(Object key, Object... values) {
        return lpush(key, KEY_DEFAULT_EXPIRE_TIMEOUT, values);
    }

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
     *
     * @param key
     * @param seconds
     * @param values
     * @return
     */
    public static Long lpush(Object key, int seconds, Object... values) {
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
     * @param timeout
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> T blpop(int timeout, Object... keys) {
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
     *
     * @param timeout
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> T brpop(int timeout, Object... keys) {
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

