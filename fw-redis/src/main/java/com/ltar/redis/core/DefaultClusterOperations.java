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

    public void clusterAddSlots(int... slots) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterAddSlots(slots);
        } finally {
            closeJedis(jedis);
        }

    }

    public Long clusterCountKeysInSlot(int slot) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterCountKeysInSlot(slot);
        } finally {
            closeJedis(jedis);
        }
    }

    public void clusterDelSlots(int... slots) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterDelSlots(slots);
        } finally {
            closeJedis(jedis);
        }
    }

    public void clusterFailover() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterFailover();
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterFlushSlots() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterFlushSlots();
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterForget(String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterForget(nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    public List<String> clusterGetKeysInSlot(int slot, int count) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterGetKeysInSlot(slot, count);
        } finally {
            closeJedis(jedis);
        }
    }

    public String clusterInfo() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterInfo();
        } finally {
            closeJedis(jedis);
        }
    }

    public Long clusterKeySlot(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterKeySlot(key);
        } finally {
            closeJedis(jedis);
        }
    }

    public void clusterMeet(String ip, int port) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterMeet(ip, port);
        } finally {
            closeJedis(jedis);
        }

    }

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

    public void clusterReplicate(String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterReplicate(nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterReset(JedisCluster.Reset resetType) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterReset(resetType);
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterSaveConfig() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSaveConfig();
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterSetSlotImporting(int slot, String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotImporting(slot, nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterSetSlotMigrating(int slot, String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotMigrating(slot, nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterSetSlotNode(int slot, String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotNode(slot, nodeId);
        } finally {
            closeJedis(jedis);
        }

    }

    public void clusterSetSlotStable(int slot) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.clusterSetSlotStable(slot);
        } finally {
            closeJedis(jedis);
        }

    }

    public List<String> clusterSlaves(String nodeId) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.clusterSlaves(nodeId);
        } finally {
            closeJedis(jedis);
        }
    }

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
