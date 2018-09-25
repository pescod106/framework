package com.ltar.redis.connection.jedis;

import com.ltar.redis.connection.RedisConnectionFactory;
import lombok.Setter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
@Setter
public class JedisConnectionFactory implements RedisConnectionFactory {


    private static Map<String, JedisPool> poolMap = new HashMap<String, JedisPool>();

    private JedisPoolConfig poolConfig;

    private JedisShardInfo shardInfo;

    public Jedis getJedis(String ip, int port, int timeout) {
        Jedis jedis = null;
        int retryNum = 0;
        do {
            try {
                jedis = getPool(ip, port, timeout).getResource();
            } catch (Exception e) {
                getPool(ip, port, timeout).returnBrokenResource(jedis);
            }
            ++retryNum;
        } while (null == jedis && retryNum < 5);
        return jedis;
    }

    public void closeJedis(Jedis jedis, String ip, int port, int timeout) {
        if (null != jedis) {
            try {
                getPool(ip, port, timeout).returnResource(jedis);
            } catch (Exception e) {
                throw new RuntimeException("close jedis error, error info:" + e);
            }
        }
    }

    public JedisPool getPool(String ip, int port, int timeout) {
        String jedisKey = ip + ":" + port + ":" + timeout;
        JedisPool jedisPool = null;
        if (!poolMap.containsKey(jedisKey)) {
            if (null == poolConfig) {
                poolConfig = new JedisPoolConfig();
            }
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            jedisPool = new JedisPool(poolConfig, ip, port, timeout);
            poolMap.put(jedisKey, jedisPool);
        }
        return poolMap.get(jedisKey);
    }

}
