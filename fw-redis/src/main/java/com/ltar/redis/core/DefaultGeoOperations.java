package com.ltar.redis.core;

import com.ltar.redis.impl.RedisTemplate;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultGeoOperations extends AbstractOperations {
    public DefaultGeoOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }
}
