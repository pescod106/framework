package com.ltar.framework.redis.core.ops;

import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface ClusterOperations {

    /**
     * 这个命令是用于修改某个节点上的集群配置。
     * 具体的说它把一组hash slots分配给接收命令的节点。
     * 如果命令执行成功，节点将指定的hash slots映射到自身，节点将获得指定的hash slots，同时开始向集群广播新的配置。
     *
     * @param slots
     */
    void clusterAddSlots(int... slots);

    /**
     * 返回连接节点负责的指定hash slot的key的数量。
     * 该命令只查询连接节点的数据集，所以如果连接节点指派到该hash slot会返回0。
     *
     * @param slot
     * @return
     */
    Long clusterCountKeysInSlot(int slot);

    /**
     * 在Redis Cluster中，每个节点都会知道哪些主节点正在负责哪些特定的哈希槽
     * <p>
     * DELSLOTS命令使一个特定的Redis Cluster节点去忘记一个主节点正在负责的哈希槽，这些哈希槽通过参数指定。
     *
     * @param slots
     * @see <a href="http://www.redis.cn/commands/cluster-delslots.html">CLUSTER DELSLOTS</a>
     */
    void clusterDelSlots(int... slots);

    /**
     * @see <a href="http://www.redis.cn/commands/cluster-failover.html">CLUSTER FAILOVER</a>
     */
    void clusterFailover();

    void clusterFlushSlots();

    /**
     * @param nodeId
     * @see <a href="http://www.redis.cn/commands/cluster-forget.html">CLUSTER FORGET </a>
     */
    void clusterForget(String nodeId);

    /**
     * 本命令返回存储在连接节点的指定hash slot里面的key的列表。
     * key的最大数量通过count参数指定，所以这个API可以用作keys的批处理。
     * <p>
     * 这个命令的主要是用于rehash期间slot从一个节点移动到另外一个节点。
     *
     * @param slot
     * @param count
     * @return
     */
    List<String> clusterGetKeysInSlot(int slot, int count);

    /**
     * CLUSTER INFO 命令使用 INFO 风格的形式展现了关于Redis集群的重要参数。
     *
     * @return
     */
    String clusterInfo();

    /**
     * 返回一个整数，用于标识指定键所散列到的哈希槽。该命令主要用来调试和测试，
     * 因为它通过一个API来暴露Redis底层哈希算法的实现。该命令的使用示例：
     * <p>
     * 客户端库可能会使用Redis来测试他们自己的哈希算法，
     * 生成随机的键并且使用他们自己实现的算法和Redis的CLUSTER KEYSLOT命令来散列这些键，然后检查结果是否相同。
     * <p>
     * 人们会使用这个命令去检查哈希槽是哪个，
     * 然后关联Redis Cluster的节点，并且负责一个给定的键。
     *
     * @param key
     * @return
     */
    Long clusterKeySlot(String key);

    /**
     * CLUSTER MEET命令被用来连接不同的开启集群支持的 Redis 节点，以进入工作集群。
     * <p>
     * 基本的思想是每个节点默认都是相互不信任的，并且被认为是未知的节点，
     * 以便万一因为系统管理错误或地址被修改，而不太可能将多个不同的集群节点混成一个集群。
     *
     * @param ip
     * @param port
     * @see <a href="http://www.redis.cn/commands/cluster-meet.html">CLUSTER MEET</a>
     */
    void clusterMeet(String ip, int port);

    /**
     * 集群中的每个节点都有当前集群配置的一个视图（快照），
     * 视图的信息由该节点所有已知节点提供，包括与每个节点的连接状态，
     * 每个节点的标记位（flags)，属性和已经分配的哈希槽等等。
     *
     * @return
     * @see <a href="http://www.redis.cn/commands/cluster-nodes.html">CLUSTER NODES </a>
     */
    String clusterNodes();

    /**
     * @param nodeId
     * @see <a href="http://www.redis.cn/commands/cluster-replicate.html">CLUSTER REPLICATE</a>
     */
    void clusterReplicate(String nodeId);

    /**
     * @param resetType
     * @see <a href="http://www.redis.cn/commands/cluster-reset.html">CLUSTER RESET</a>
     */
    void clusterReset(JedisCluster.Reset resetType);

    /**
     * @see <a href="http://www.redis.cn/commands/cluster-saveconfig.html">CLUSTER SAVECONFIG</a>
     */
    void clusterSaveConfig();

    /**
     * @param slot
     * @param nodeId
     * @see <a href="http://www.redis.cn/commands/cluster-setslot.html">CLUSTER SETSLOT</a>
     */
    void clusterSetSlotImporting(int slot, String nodeId);

    /**
     * @param slot
     * @param nodeId
     * @see <a href="http://www.redis.cn/commands/cluster-setslot.html">CLUSTER SETSLOT</a>
     */
    void clusterSetSlotMigrating(int slot, String nodeId);

    /**
     * @param slot
     * @param nodeId
     * @see <a href="http://www.redis.cn/commands/cluster-setslot.html">CLUSTER SETSLOT</a>
     */
    void clusterSetSlotNode(int slot, String nodeId);

    /**
     * @param slot
     * @see <a href="http://www.redis.cn/commands/cluster-setslot.html">CLUSTER SETSLOT</a>
     */
    void clusterSetSlotStable(int slot);

    /**
     * @param nodeId
     * @return
     * @see <a href="http://www.redis.cn/commands/cluster-slaves.html">CLUSTER SLAVES</a>
     */
    List<String> clusterSlaves(String nodeId);

    /**
     * CLUSTER SLOTS命令返回哈希槽和Redis实例映射关系。
     * 这个命令对客户端实现集群功能非常有用，使用这个命令可以获得哈希槽与节点（由IP和端口组成）的映射关系，
     * 这样，当客户端收到（用户的）调用命令时，可以根据（这个命令）返回的信息将命令发送到正确的Redis实例.
     *
     * @return
     * @see <a href="http://www.redis.cn/commands/cluster-slots.html">CLUSTER SLOTS </a>
     */
    List<Object> clusterSlots();

}
