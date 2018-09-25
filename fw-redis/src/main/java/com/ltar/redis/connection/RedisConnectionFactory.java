package com.ltar.redis.connection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public interface RedisConnectionFactory {

    Jedis getJedis(String ip, int port, int timeout);

    void closeJedis(Jedis jedis, String ip, int port, int timeout);

    JedisPool getPool(String ip, int port, int timeout);
}
