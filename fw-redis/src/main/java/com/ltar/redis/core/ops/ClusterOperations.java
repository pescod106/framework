package com.ltar.redis.core.ops;

import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface ClusterOperations {
    void clusterAddSlots(final int... slots);

    Long clusterCountKeysInSlot(final int slot);

    void clusterDelSlots(final int... slots);

    void clusterFailover();

    void clusterFlushSlots();

    void clusterForget(final String nodeId);

    List<String> clusterGetKeysInSlot(final int slot, final int count);

    String clusterInfo();

    Long clusterKeySlot(final String key);

    void clusterMeet(final String ip, final int port);

    String clusterNodes();

    void clusterReplicate(final String nodeId);

    void clusterReset(final JedisCluster.Reset resetType);

    void clusterSaveConfig();

    void clusterSetSlotImporting(final int slot, final String nodeId);

    void clusterSetSlotMigrating(final int slot, final String nodeId);

    void clusterSetSlotNode(final int slot, final String nodeId);

    void clusterSetSlotStable(final int slot);

    List<String> clusterSlaves(final String nodeId);

    List<Object> clusterSlots();

}
