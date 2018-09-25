package com.ltar.redis;

import com.ltar.redis.impl.RedisTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class RedisTest extends BaseTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        System.out.println(redisTemplate.exists("czg"));
    }

    @Test
    public void testOps(){
        redisTemplate.ops4String().set("nihao","czg");
    }
}
