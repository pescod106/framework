package com.ltar.redis.core;

import com.ltar.redis.core.ops.ClusterOperations;
import com.ltar.redis.impl.RedisTemplate;
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

    }

    public Long clusterCountKeysInSlot(int slot) {
        return null;
    }

    public void clusterDelSlots(int... slots) {

    }

    public void clusterFailover() {

    }

    public void clusterFlushSlots() {

    }

    public void clusterForget(String nodeId) {

    }

    public List<String> clusterGetKeysInSlot(int slot, int count) {
        return null;
    }

    public String clusterInfo() {
        return null;
    }

    public Long clusterKeySlot(String key) {
        return null;
    }

    public void clusterMeet(String ip, int port) {

    }

    public String clusterNodes() {
        return null;
    }

    public void clusterReplicate(String nodeId) {

    }

    public void clusterReset(JedisCluster.Reset resetType) {

    }

    public void clusterSaveConfig() {

    }

    public void clusterSetSlotImporting(int slot, String nodeId) {

    }

    public void clusterSetSlotMigrating(int slot, String nodeId) {

    }

    public void clusterSetSlotNode(int slot, String nodeId) {

    }

    public void clusterSetSlotStable(int slot) {

    }

    public List<String> clusterSlaves(String nodeId) {
        return null;
    }

    public List<Object> clusterSlots() {
        return null;
    }
}
