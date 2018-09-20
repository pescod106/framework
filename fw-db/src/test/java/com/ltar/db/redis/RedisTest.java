package com.ltar.db.redis;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/19
 * @version: 1.0.0
 */

public class RedisTest extends BaseTest {

    @Test
    public void expire() {
    }

    @Test
    public void testException() {
        testList("nihao");
    }

    public String exception() {
        String result = "default";
        try {
            System.out.println("start!");
            System.out.println("end");
            return "1";
        } finally {
            System.out.println("finally!");
        }
    }

    public void testList(Object object){
        System.out.println("single");
    }

    public void testList(Object... fields) {
        System.out.println("multi");
    }
}