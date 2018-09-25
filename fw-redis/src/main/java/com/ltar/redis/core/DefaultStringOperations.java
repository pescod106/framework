package com.ltar.redis.core;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.core.ops.StringOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.Jedis;

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


    public <K, V> void set(K key, V value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        } finally {
            closeJedis(jedis);
        }
    }
}
