package com.ltar.redis.core;

import com.ltar.redis.connection.RedisConnectionFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
@Getter
@Setter
public class RedisAccessor implements InitializingBean {

    private String ip = "localhost";
    private int port = Protocol.DEFAULT_PORT;
    private int timeout = Protocol.DEFAULT_TIMEOUT;

    private RedisConnectionFactory connectionFactory;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getConnectionFactory(), "RedisConnectionFactory is required");
    }

    public Jedis getJedis() {
        Jedis jedis = connectionFactory.getJedis(ip, port, timeout);
        if (null == jedis) {
            throw new JedisException("can't get jedis with redisIp/redisPort :" + ip + "/" + port);
        }
        return jedis;
    }

    public void closeJedis(Jedis jedis) {
        if (null != jedis) {
            connectionFactory.closeJedis(jedis, ip, port, timeout);
        }
    }
}
