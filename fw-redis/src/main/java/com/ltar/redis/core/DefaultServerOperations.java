package com.ltar.redis.core;

import com.ltar.redis.core.ops.ServerOperations;
import com.ltar.redis.impl.RedisTemplate;
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

    public void bgrewriteaof() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.bgrewriteaof();
        } finally {
            closeJedis(jedis);
        }
    }

    public void bgsave() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.bgsave();
        } finally {
            closeJedis(jedis);
        }

    }

    public void clientKill(String ip, int port) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clientKill(ip + ":" + port);
        } finally {
            closeJedis(jedis);
        }

    }

    public String clientList() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clientList();
        } finally {
            closeJedis(jedis);
        }
    }

    public String clientGetname() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clientGetname();
        } finally {
            closeJedis(jedis);
        }
    }

    public String clientSetname(String name) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clientSetname(name);
        } finally {
            closeJedis(jedis);
        }
    }

    public List<String> configGet(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.configGet(pattern);
        } finally {
            closeJedis(jedis);
        }
    }

    public String configSet(String parameter, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.configSet(parameter, value);
        } finally {
            closeJedis(jedis);
        }
    }

    public String configResetStat() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.configResetStat();
        } finally {
            closeJedis(jedis);
        }
    }

    public Long dbSize() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.dbSize();
        } finally {
            closeJedis(jedis);
        }
    }

    public void flushAll() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushAll();
        } finally {
            closeJedis(jedis);
        }

    }

    public void flushDB() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushDB();
        } finally {
            closeJedis(jedis);
        }

    }

    public String info() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.info();
        } finally {
            closeJedis(jedis);
        }
    }

    public String info(String section) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.info(section);
        } finally {
            closeJedis(jedis);
        }

    }

    public Long lastsave() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.lastsave();
        } finally {
            closeJedis(jedis);
        }

    }

    public void save() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.save();
        } finally {
            closeJedis(jedis);
        }

    }

    public void shutdown() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.shutdown();
        } finally {
            closeJedis(jedis);
        }

    }

    public void slaveof(String host, int port) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.slaveof(host, port);
        } finally {
            closeJedis(jedis);
        }

    }

    public List<Slowlog> slowlogGet() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogGet();
        } finally {
            closeJedis(jedis);
        }

    }

    public List<Slowlog> slowlogGet(long entries) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogGet(entries);
        } finally {
            closeJedis(jedis);
        }

    }

    public Long slowlogLen() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogLen();
        } finally {
            closeJedis(jedis);
        }

    }

    public String slowlogReset() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.slowlogReset();
        } finally {
            closeJedis(jedis);
        }

    }

    public void sync() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.sync();
        } finally {
            closeJedis(jedis);
        }

    }

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
