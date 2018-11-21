package com.ltar.redis.core;

import com.ltar.redis.core.ops.ClusterOperations;
import com.ltar.redis.impl.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/25
 * @version: 1.0.0
 */
public class DefaultClusterOperations extends AbstractOperations implements ClusterOperations {
    public DefaultClusterOperations(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public void clusterAddSlots(int... slots) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterAddSlots(slots);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public Long clusterCountKeysInSlot(int slot) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterCountKeysInSlot(slot);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void clusterDelSlots(int... slots) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterDelSlots(slots);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void clusterFailover() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterFailover();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterFlushSlots() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterFlushSlots();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterForget(String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterForget(nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public List<String> clusterGetKeysInSlot(int slot, int count) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterGetKeysInSlot(slot, count);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String clusterInfo() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterInfo();
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long clusterKeySlot(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterKeySlot(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void clusterMeet(String ip, int port) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterMeet(ip, port);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public String clusterNodes() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterNodes();
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    @Override
    public void clusterReplicate(String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterReplicate(nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterReset(JedisCluster.Reset resetType) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterReset(resetType);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterSaveConfig() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSaveConfig();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterSetSlotImporting(int slot, String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotImporting(slot, nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterSetSlotMigrating(int slot, String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotMigrating(slot, nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterSetSlotNode(int slot, String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotNode(slot, nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void clusterSetSlotStable(int slot) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotStable(slot);
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public List<String> clusterSlaves(String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterSlaves(nodeId);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public List<Object> clusterSlots() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterSlots();
        } finally {
            closeJedis(jedis);
        }
    }
}
