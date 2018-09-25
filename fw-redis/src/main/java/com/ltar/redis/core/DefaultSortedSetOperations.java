package com.ltar.redis.core;

import com.ltar.redis.core.ops.SortedSetOperations;
import com.ltar.redis.impl.RedisTemplate;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultSortedSetOperations extends AbstractOperations implements SortedSetOperations {
    public DefaultSortedSetOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }
}
