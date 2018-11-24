package com.ltar.framework.redis;

import com.ltar.framework.redis.impl.RedisTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

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
    public void test() {
        System.out.println(redisTemplate.exists("czg"));
    }

    @Test
    public void testOps() {
        redisTemplate.ops4String().set("czg", "adsfafa");
    }

    @Test
    public void testGet(){
        String value = (String) redisTemplate.ops4String().get("czg");
        System.out.println(value);
    }

    @Test
    public void testInt() {
        Long longValue = System.currentTimeMillis();
        System.out.println(longValue);
        int result = longValue.intValue();
        System.out.println(result);
    }

    @Test
    public void testTimeUnit() {
        TimeUnit timeUnit = TimeUnit.SECONDS;
    }
}
