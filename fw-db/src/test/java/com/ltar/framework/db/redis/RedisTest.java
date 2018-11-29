package com.ltar.framework.db.redis;

//import cn.huoqiu.base.util.JsonUtil;
<<<<<<< HEAD:fw-db/src/test/java/com/ltar/framework/db/redis/RedisTest.java
import com.ltar.framework.json.util.JsonUtils;
=======
import com.ltar.framework.json.util.JacksonUtils;
>>>>>>> master:fw-db/src/test/java/com/ltar/framework/db/redis/RedisTest.java
import org.junit.After;
import org.junit.Test;

import java.util.*;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/19
 * @version: 1.0.0
 */

public class RedisTest extends BaseTest {

    public List<String> generateStrList() {
        return Arrays.asList("czg", "pescod", "nihao");
    }

    public List<Student> generateStuList() {
        return Arrays.asList(
                new Student("czg", 12),
                new Student("pescod", 23),
                new Student("changzhigao", 24),
                new Student("changzg", 23)
        );
    }

    public Student generateStudent() {
        return new Student("苌志高", 25);
    }


    public Map<String, String> generateStrMap() {
        Map map = new HashMap();
        map.put("string", "value");
        map.put("czg", "pscod");
        map.put("test", "test");
        return map;
    }


    @Test
    public void set() {
        Redis.set("one", generateStrList());
        Redis.set("two", generateStuList());
        Redis.set("three", generateStrMap());
        Redis.set("four", new Student("nihao", 123));
    }

    //    @After()
    public void setAfter() {
        System.out.println("four info:");
        System.out.println(Redis.get("four").toString());
        System.out.println(Redis.ttl("four"));
        System.out.println("---------------------");
    }


    @Test
    public void get() {
        List<String> stringList = Redis.get("one");
        List<Student> studentList = Redis.get("two");
        Map<String, String> strMap = Redis.get("three");

    }

    @Test
    public void getSet() {
        Student student = Redis.getSet("four", new Student("nihao34", 123));
        System.out.println(student);

    }

    @Test
    public void getSet1() {
        Student student2 = Redis.getSet("four", new Student("nihao1234", 123), 123);
        System.out.println(student2);
    }

    @Test
    public void setnx() {
        Redis.setnx("four", new Student("nihao34121", 123));
    }

    @Test
    public void setnx1() {
        Redis.setnx("four", new Student("nihao34121", 123), Redis.KEY_DEFAULT_EXPIRE_TIMEOUT);
    }

    @Test
    public void hset() {
        Redis.hset("five", "student", generateStudent(), 120);
    }

    @Test
    public void hset1() {
        Redis.hset("five", "student", generateStudent());
        Redis.hset("five", "student1", generateStudent());
    }

    //    @After
    public void afterH() {
        System.out.println("Five info:");
        System.out.println(Redis.hget("five", "student").toString());
        System.out.println(Redis.ttl("five"));
        System.out.println("---------------------");
    }


    @Test
    public void hlen() {
//        System.out.println(Redis.hlen("four"));
        System.out.println(Redis.hlen("five"));
    }

    @Test
    public void hexists() {
        System.out.println(Redis.exists("changzhigao"));
        System.out.println(Redis.exists("one"));
    }

    @Test
    public void hmset() {
        Redis.hmset("six", generateStrMap(), 1230);
    }

    @After
    public void mapAfter() {
        System.out.println("six info:");
        System.out.println(Redis.hmget("six", "string").toString());
        System.out.println("===========");
        System.out.println(Redis.hmget("six", "czg").toString());
        System.out.println("===========");
        System.out.println(Redis.hmget("six", "changzhigao").toString());
        System.out.println("++++++++++");
        System.out.println("----------------------");
    }

    @Test
    public void hmset1() {
        Redis.hmset("six", generateStrMap());
    }

    @Test
    public void hmget() {
        System.out.println("six info:");
        System.out.println(Redis.hmget("six", "string").toString());
        System.out.println("===========");
        System.out.println(Redis.hmget("six", "czg").toString());
        System.out.println("===========");
        System.out.println(Redis.hmget("six", "changzhigao").toString());
        System.out.println("++++++++++");
        System.out.println("----------------------");
    }

    @Test
    public void hmget1() {
        System.out.println("hmget1 info:");
        System.out.println(Redis.hmget("six", "string", "czg"));
        System.out.println("----------------------");
    }

    @Test
    public void expire1() {
        Redis.expire("two", -10);
    }

    @Test
    public void expireAt() {
        Redis.expireAt("two", 1537447620);
    }

    @Test
    public void exists() {
        System.out.println(Redis.exists("changzhigao"));
    }

    @Test
    public void del() {
        System.out.println(Redis.del("one"));
    }

    @Test
    public void del1() {
        System.out.println(Redis.del("three", "four"));
    }

    @Test
    public void hdel() {
        System.out.println(Redis.hdel("five", "student"));
    }

    @Test
    public void hdel1() {
        System.out.println(Redis.hdel("six", "czg", "string"));
    }

    @Test
    public void keys() {
        Set<Object> sets = Redis.keys("*");
        for (Object object : sets) {
            System.out.println(object);
        }
    }


    @Test
    public void init() {
        /**
         * seven
         */
        Redis.rpush("seven", generateStuList().toArray());

        Redis.set("14", JacksonUtils.dump(generateStrMap()));
    }

    @Test
    public void lrange() {
        System.out.println(Redis.lrange("seven", -2, -1));
    }

    @Test
    public void incr() {
        System.out.println(Redis.incr("eight"));
    }

    @Test
    public void incr1() {
        System.out.println(Redis.incr("night", 1234));
    }

    @Test
    public void incrBy() {
        System.out.println(Redis.incrBy("ten", 12));
    }

    @Test
    public void incrBy1() {
        System.out.println(Redis.incrBy("11", 13, 12123));
    }

    @Test
    public void decr() {
        System.out.println(Redis.decr("11"));
    }

    @Test
    public void decr1() {
        System.out.println(Redis.decr("11", 900));
    }

    @Test
    public void decrBy() {
        System.out.println(Redis.decrBy("11", 3));
    }

    @Test
    public void decrBy1() {
        System.out.println(Redis.decrBy("11", 3, 780));
    }

    @Test
    public void hincr() {
        System.out.println(Redis.hincr("12", "czg"));
    }

    @Test
    public void hincrBy() {
        System.out.println(Redis.hincrBy("12", "czg", 13, 1234));
    }

    @Test
    public void hincr1() {
        System.out.println(Redis.hincr("12", "czg", 13));
    }

    @Test
    public void hincrBy1() {
        System.out.println(Redis.hincrBy("12", "czg", 13));
    }

    @Test
    public void hincrBy2() {
        System.out.println(Redis.hincrBy("13", "czg", 13.21));

    }

    @Test
    public void hincrBy3() {
        System.out.println(Redis.hincrBy("13", "czg", 13.33, 4321));
    }

    @Test
    public void rpush() {
        Redis.rpush("seven", new Student("sdfafa", 234), 1234);

    }

    @Test
    public void rpush1() {
        Redis.rpush("15", 1234578, generateStuList().toArray());
    }


    @Test
    public void lpush() {
        Redis.lpush("15", new Student("first0", 123));
    }

    @Test
    public void lpush1() {
        Redis.lpush("15", new Student("first0", 123), 321);
    }

    @Test
    public void lpush2() {
        Redis.lpushWithExpire(3210, "15", new Student("first02", 123), new Student("first01", 123));
    }

    @Test
    public void lpush3() {
        Redis.lpush("15", new Student("first002", 123), new Student("first001", 123));
    }

    @Test
    public void rpop() {
        System.out.println(Redis.rpop("15").toString());
    }

    @Test
    public void lpop() {
        System.out.println(Redis.lpop("15").toString());
    }

    @Test
    public void blpop() {
        System.out.println(Redis.blpop("15", "12").toString());

    }

    @Test
    public void blpop1() {
        System.out.println(Redis.blpopWithTimeout(1, "15", "12").toString());
    }

    @Test
    public void brpop() {
        System.out.println(Redis.brpop("15", "12").toString());
    }

    @Test
    public void brpop1() {
        System.out.println(Redis.brpopWithTimeout(1, "15", "12").toString());
    }

    @Test
    public void llen() {
        System.out.println(Redis.llen("seven"));
    }

    @Test
    public void mset() {
        Redis.mset("one", "112");
    }

    @Test
    public void msetnx() {
        Redis.msetnx("one", "1+1", "two", "2+2");
    }

    @Test
    public void testNullable() {
        Student student = new Student();
        student.setName(null);
        student.setAge(12);
        System.out.println(student);
    }
}