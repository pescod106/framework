package com.ltar.framework.redis.core;

import com.ltar.framework.redis.impl.RedisTemplate;
import com.ltar.framework.redis.core.ops.ServerOperations;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/27
 * @version: 1.0.0
 */
public class DefaultServerOperations extends AbstractOperations implements ServerOperations {

    public DefaultServerOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public void bgrewriteaof() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.bgrewriteaof();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void bgsave() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.bgsave();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clientKill(String ip, int port) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clientKill(ip + ":" + port);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public String clientList() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clientList();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String clientGetname() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clientGetname();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String clientSetname(String name) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clientSetname(name);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public List<String> configGet(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.configGet(pattern);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String configSet(String parameter, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.configSet(parameter, value);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String configResetStat() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.configResetStat();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long dbSize() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.dbSize();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void flushAll() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushAll();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void flushDB() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushDB();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public String info() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.info();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String info(String section) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.info(section);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public Long lastsave() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lastsave();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void save() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.save();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void shutdown() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.shutdown();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void slaveof(String host, int port) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.slaveof(host, port);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public List<Slowlog> slowlogGet() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogGet();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public List<Slowlog> slowlogGet(long entries) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogGet(entries);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public Long slowlogLen() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogLen();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public String slowlogReset() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogReset();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void sync() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.sync();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public List<String> time() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.time();
        } finally {
            closeJedis(jedis);
        }

    }
}
