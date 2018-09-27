package com.ltar.redis.core;

import com.ltar.redis.core.ops.ConnectionOperations;
import com.ltar.redis.impl.RedisTemplate;

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
    public String auth(String password) {
        return null;
    }

    public String echo(String string) {
        return null;
    }

    public String ping() {
        return null;
    }

    public String quit() {
        return null;
    }

    public String select(int index) {
        return null;
    }
}
