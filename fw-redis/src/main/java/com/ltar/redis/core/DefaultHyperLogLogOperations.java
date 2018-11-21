package com.ltar.redis.core;

import com.ltar.base.util.SerializeUtil;
import com.ltar.redis.core.ops.HyperLogLogOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultHyperLogLogOperations extends AbstractOperations implements HyperLogLogOperations {

    public DefaultHyperLogLogOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public <K, V> Long pfadd(K key, V... elements) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[elements.length][];
            for (int i = 0; i < elements.length; i++) {
                bytes[i] = SerializeUtil.serialize(elements[i]);
            }
            return jedis.pfadd(SerializeUtil.serialize(key), bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K> Long pfcount(K... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[keys.length][];
            for (int i = 0; i < keys.length; i++) {
                bytes[i] = SerializeUtil.serialize(keys[i]);
            }
            return jedis.pfcount(bytes);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <K, V> void pfmerge(K destkey, V... sourcekeys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[][] bytes = new byte[sourcekeys.length][];
            for (int i = 0; i < sourcekeys.length; i++) {
                bytes[i] = SerializeUtil.serialize(sourcekeys[i]);
            }
            jedis.pfmerge(SerializeUtil.serialize(destkey), bytes);
        } finally {
            closeJedis(jedis);
        }

    }
}
