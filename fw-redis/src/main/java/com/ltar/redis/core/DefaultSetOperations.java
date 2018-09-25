package com.ltar.redis.core;

import com.ltar.redis.core.ops.SetOperations;
import com.ltar.redis.impl.RedisTemplate;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultSetOperations extends AbstractOperations implements SetOperations {
    public DefaultSetOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }
}
