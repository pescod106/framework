package com.ltar.framework.redis.core.ops;

import redis.clients.util.Slowlog;

import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/26
 * @version: 1.0.0
 */
public interface ServerOperations {

    void bgrewriteaof();

    void bgsave();

    void clientKill(String ip, int port);

    String clientList();

    String clientGetname();

    String clientSetname(String name);

    List<String> configGet(final String pattern);

    String configSet(final String parameter, final String value);

    String configResetStat();

    Long dbSize();

    void flushAll();

    void flushDB();

    String info();

    String info(final String section);

    Long lastsave();

    void save();

    void shutdown();

    void slaveof(final String host, final int port);

    List<Slowlog> slowlogGet();

    List<Slowlog> slowlogGet(long entries);

    Long slowlogLen();

    String slowlogReset();

    void sync();

    List<String> time();
}
