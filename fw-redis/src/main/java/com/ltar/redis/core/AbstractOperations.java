package com.ltar.redis.core;

import com.ltar.redis.impl.RedisTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
abstract class AbstractOperations {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractOperations.class);

    protected RedisTemplate redisTemplate;

    Jedis getJedis() {
        return redisTemplate.getJedis();
    }

    void closeJedis(Jedis jedis) {
        redisTemplate.closeJedis(jedis);
    }

}
