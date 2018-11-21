package com.ltar.redis.core;

import com.ltar.redis.core.ops.ConnectionOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/27
 * @version: 1.0.0
 */
public class DefaultConnectionOperations extends AbstractOperations implements ConnectionOperations {

    public DefaultConnectionOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String auth(String password) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.auth(password);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String echo(String string) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.echo(string);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String ping() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ping();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String quit() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.quit();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String select(int index) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.select(index);
        } finally {
            closeJedis(jedis);
        }
    }
}
