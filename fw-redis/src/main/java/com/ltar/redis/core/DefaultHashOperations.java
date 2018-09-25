package com.ltar.redis.core;

import com.ltar.redis.core.ops.HashOperations;
import com.ltar.redis.impl.RedisTemplate;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultHashOperations extends AbstractOperations implements HashOperations {
    public DefaultHashOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }
}
