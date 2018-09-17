package com.ltar.db.redis.core;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class JedisConnect {
    public static final int DEFAULT_TIMEOUT = 2 * 1000;
    private static Map<String, JedisPool> poolMap = new HashMap<String, JedisPool>();

    public static JedisConnect getInstance() {
        return RedisHolder.instance;
    }

    public redis.clients.jedis.Jedis getJedis(String ip, int port) {
        return this.getJedis(ip, port, DEFAULT_TIMEOUT);
    }

    public redis.clients.jedis.Jedis getJedis(String ip, int port, int timeout) {
        redis.clients.jedis.Jedis jedis = null;
        int count = 0;
        do {
            try {
                jedis = getPool(ip, port, timeout).getResource();
            } catch (Exception e) {
                getPool(ip, port, timeout).returnBrokenResource(jedis);
            }
            ++count;
        } while (null == jedis && count < 5);
        return jedis;
    }

    public void closeJedis(redis.clients.jedis.Jedis jedis, String ip, int port) {
        this.closeJedis(jedis, ip, port, DEFAULT_TIMEOUT);
    }

    public void closeJedis(redis.clients.jedis.Jedis jedis, String ip, int port, int timeout) {
        if (null != jedis) {
            try {
                getPool(ip, port, timeout).returnResource(jedis);
            } catch (Exception e) {
                throw new RuntimeException("close jedis error, error info:" + e);
            }
        }
    }

    private static JedisPool getPool(String ip, int port) {
        return getPool(ip, port, DEFAULT_TIMEOUT);
    }

    private static JedisPool getPool(String ip, int port, int timeout) {
        String key = ip + ":" + port + ":" + timeout;
        JedisPool pool = null;
        if (!poolMap.containsKey(key)) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            pool = new JedisPool(config, ip, port, timeout);
            poolMap.put(key, pool);
        } else {
            pool = poolMap.get(key);
        }
        return pool;
    }


    private static class RedisHolder {
        private static JedisConnect instance = new JedisConnect();

        public RedisHolder() {
        }
    }
}
