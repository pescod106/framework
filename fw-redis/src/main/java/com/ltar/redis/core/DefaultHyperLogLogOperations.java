package com.ltar.redis.core;

import com.ltar.redis.core.ops.HyperLogLogOperations;
import com.ltar.redis.impl.RedisTemplate;

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
}
