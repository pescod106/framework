package com.ltar.redis.core;

import com.ltar.redis.core.ops.ServerOperations;
import com.ltar.redis.impl.RedisTemplate;
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

    }

    public void bgsave() {

    }

    public void clientKill(String ip, int port) {

    }

    public String clientList() {
        return null;
    }

    public String clientGetname() {
        return null;
    }

    public String clientSetname(String name) {
        return null;
    }

    public List<String> configGet(String pattern) {
        return null;
    }

    public String configSet(String parameter, String value) {
        return null;
    }

    public String configResetStat() {
        return null;
    }

    public Long dbSize() {
        return null;
    }

    public void flushAll() {

    }

    public void flushDB() {

    }

    public String info() {
        return null;
    }

    public String info(String section) {
        return null;
    }

    public Long lastsave() {
        return null;
    }

    public void save() {

    }

    public void shutdown() {

    }

    public void slaveof(String host, int port) {

    }

    public List<Slowlog> slowlogGet() {
        return null;
    }

    public List<Slowlog> slowlogGet(long entries) {
        return null;
    }

    public Long slowlogLen() {
        return null;
    }

    public String slowlogReset() {
        return null;
    }

    public void sync() {

    }

    public List<String> time() {
        return null;
    }
}
