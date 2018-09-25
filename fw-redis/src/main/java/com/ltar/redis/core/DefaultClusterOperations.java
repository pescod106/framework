package com.ltar.redis.core;

import com.ltar.redis.core.ops.ClusterOperations;
import com.ltar.redis.impl.RedisTemplate;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultClusterOperations extends AbstractOperations implements ClusterOperations {
    public DefaultClusterOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }
}
