package com.ltar.redis.core.ops;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

import java.util.Map;
import java.util.Set;

/**
 * @desc: ZSet/sorted set specific operations.
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface SortedSetOperations {

    /**
     * 将所有指定成员添加到键为key有序集合（sorted set）里面。 添加时可以指定分数/成员（score/member）对。
     * 如果指定添加的成员已经是有序集合里面的成员，则会更新改成员的分数（scrore）并更新到正确的排序位置。
     * 如果key不存在，将会创建一个新的有序集合（sorted set）并将分数/成员（score/member）添加到有序集合，
     * 就像原来存在一个空的有序集合一样。如果key存在，但是类型不是有序集合，将会返回一个错误应答。
     *
     * @param key
     * @param score
     * @param member
     * @param <K>
     * @param <V>
     * @return 添加到有序集合的成员数量，不包括已经存在更新分数的成员。
     */
    <K, V> Long zadd(K key, double score, V member);

    /**
     * 将所有指定成员添加到键为key有序集合（sorted set）里面。 添加时可以指定多个分数/成员（score/member）对。
     * 如果指定添加的成员已经是有序集合里面的成员，则会更新改成员的分数（scrore）并更新到正确的排序位置。
     * <p>
     * 如果key不存在，将会创建一个新的有序集合（sorted set）并将分数/成员（score/member）对添加到有序集合，
     * 就像原来存在一个空的有序集合一样。如果key存在，但是类型不是有序集合，将会返回一个错误应答。
     *
     * @param key
     * @param scoreMembers
     * @param <K>
     * @param <V>
     * @return 添加到有序集合的成员数量，不包括已经存在更新分数的成员。
     */
    <K, V> Long zadd(K key, Map<V, Double> scoreMembers);

    /**
     * 返回key的有序集元素个数。
     *
     * @param key
     * @param <K>
     * @return key存在的时候，返回有序集的元素个数，否则返回0。
     */
    <K> Long zcard(K key);

    /**
     * 返回有序集key中，score值在min和max之间(默认包括score值等于min或max)的成员
     *
     * @param key
     * @param min
     * @param max
     * @param <K>
     * @return 指定分数范围的元素个数
     */
    <K> Long zcount(K key, double min, double max);

    /**
     * 为有序集key的成员member的score值加上增量increment。
     * 如果key中不存在member，就在key中添加一个member，score是increment（就好像它之前的score是0.0）。
     * 如果key不存在，就创建一个只含有指定member成员的有序集合。
     *
     * @param key
     * @param score
     * @param member
     * @param <K>
     * @param <V>
     * @return member成员的新score值
     */
    <K, V> Double zincrby(K key, double score, V member);

    /**
     * 计算给定的numkeys个有序集合的交集，并且把结果放到destination中。
     * 在给定要计算的key和其它参数之前，必须先给定key个数(numberkeys)。
     * <p>
     * 默认情况下，结果中一个元素的分数是有序集合中该元素分数之和，前提是该元素在这些有序集合中都存在。
     * 因为交集要求其成员必须是给定的每个有序集合中的成员，结果集中的每个元素的分数和输入的有序集合个数相等。
     *
     * @param dstkey
     * @param sets
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zinterstore(K dstkey, V... sets);

    /**
     * 计算给定的numkeys个有序集合的交集，并且把结果放到destination中。
     * 在给定要计算的key和其它参数之前，必须先给定key个数(numberkeys)。
     * <p>
     * 默认情况下，结果中一个元素的分数是有序集合中该元素分数之和，前提是该元素在这些有序集合中都存在。
     * 因为交集要求其成员必须是给定的每个有序集合中的成员，结果集中的每个元素的分数和输入的有序集合个数相等。
     *
     * @param dstkey
     * @param params
     * @param sets
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zinterstore(K dstkey, ZParams params, V... sets);

    /**
     * ZLEXCOUNT 命令用于计算有序集合中指定成员之间的成员数量。
     *
     * @param key
     * @param min
     * @param max
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zlexcount(K key, V min, V max);


    /**
     * Returns the specified range of elements in the sorted set stored at key.
     * The elements are considered to be ordered from the lowest to the highest score.
     *
     * @param key
     * @param start
     * @param end
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> zrange(K key, long start, long end);

    /**
     * ZRANGEBYLEX 返回指定成员区间内的成员，按成员字典正序排序, 分数必须相同。
     * 在某些业务场景中,需要对一个字符串数组按名称的字典顺序进行排序时,可以使用Redis中SortSet这种数据结构来处理。
     *
     * @param key
     * @param min
     * @param max
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> zrangeByLex(K key, V min, V max);

    /**
     * ZRANGEBYLEX 返回指定成员区间内的成员，按成员字典正序排序, 分数必须相同。
     * 在某些业务场景中,需要对一个字符串数组按名称的字典顺序进行排序时,可以使用Redis中SortSet这种数据结构来处理。
     *
     * @param key
     * @param min
     * @param max
     * @param offset 返回结果起始位置
     * @param count  返回结果数量
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> zrangeByLex(K key, V min, V max, int offset, int count);

    /**
     * 返回有序集key中，指定区间内的成员。其中成员的位置按score值递减(从大到小)来排列。
     * 具有相同score值的成员按字典序的反序排列。
     *
     * @param key
     * @param start
     * @param end
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Set<V> zrevrange(K key, long start, long end);

    /**
     * ZREVRANGEBYSCORE 返回有序集合中指定分数区间内的成员，分数由高到低排序。
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    <K, V> Set<V> zrevrangeByScore(K key, double max, double min);

    /**
     * 返回key的有序集合中的分数在min和max之间的所有元素（包括分数等于max或者min的元素）。
     * 元素被认为是从低分到高分排序的。
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max);

    /**
     * 返回key的有序集合中的分数在min和max之间的所有元素（包括分数等于max或者min的元素）。
     * 元素被认为是从低分到高分排序的。
     *
     * @param key
     * @param min
     * @param max
     * @param offset 返回结果起始位置
     * @param count  返回结果数量
     * @return
     */
    <K, V> Set<V> zrangeByScore(K key, double min, double max, int offset, int count);

    /**
     * 返回有序集key中成员member的排名。其中有序集成员按score值递增(从小到大)顺序排列。
     * 排名以0为底，也就是说，score值最小的成员排名为0。
     * <p>
     * 使用ZREVRANK命令可以获得成员按score值递减(从大到小)排列的排名。
     *
     * @param key
     * @param member
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zrank(K key, V member);

    /**
     * Remove the specified member from the sorted set value stored at key.
     *
     * @param key
     * @param members
     * @param <K>
     * @param <V>
     * @return 返回的是从有序集合中删除的成员个数，不包括不存在的成员。
     */
    <K, V> Long zrem(K key, V... members);

    /**
     * ZREMRANGEBYLEX 删除名称按字典由低到高排序成员之间所有成员。
     * 不要在成员分数不同的有序集合中使用此命令, 因为它是基于分数一致的有序集合设计的,如果使用,会导致删除的结果不正确。
     * 待删除的有序集合中,分数最好相同,否则删除结果会不正常。
     *
     * @param key
     * @param min
     * @param max
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zremrangeByLex(K key, V min, V max);

    /**
     * 移除有序集key中，指定排名(rank)区间内的所有成员。
     * 下标参数start和stop都以0为底，0处是分数最小的那个元素。
     * 这些索引也可是负数，表示位移从最高分处开始数。
     * 例如，-1是分数最高的元素，-2是分数第二高的，依次类推。
     *
     * @param key
     * @param start
     * @param end
     * @param <K>
     * @return
     */
    <K> Long zremrangeByRank(K key, long start, long end);

    /**
     * 移除有序集key中，所有score值介于min和max之间(包括等于min或max)的成员。
     * <p>
     * 自版本2.1.6开始，score值等于min或max的成员也可以不包括在内
     *
     * @param key
     * @param start
     * @param end
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zremrangeByScore(K key, double start, double end);

    /**
     * 返回有序集key中成员member的排名，其中有序集成员按score值从大到小排列。
     * 排名以0为底，也就是说，score值最大的成员排名为0。
     * <p>
     * 使用ZRANK命令可以获得成员按score值递增(从小到大)排列的排名。
     *
     * @param key
     * @param member
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zrevrank(K key, V member);

    /**
     * 返回有序集key中，成员member的score值。
     * <p>
     * 如果member元素不是有序集key的成员，或key不存在，返回nil。
     *
     * @param key
     * @param member
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Double zscore(K key, V member);

    /**
     * 计算给定的numkeys个有序集合的并集，并且把结果放到destination中。
     * 在给定要计算的key和其它参数之前，必须先给定key个数(numberkeys)。
     * 默认情况下，结果集中某个成员的score值是所有给定集下该成员score值之和。
     *
     * @param dstkey
     * @param sets
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zunionstore(K dstkey, V... sets);

    /**
     * 计算给定的numkeys个有序集合的并集，并且把结果放到destination中。
     * 在给定要计算的key和其它参数之前，必须先给定key个数(numberkeys)。
     * 默认情况下，结果集中某个成员的score值是所有给定集下该成员score值之和。
     *
     * @param dstkey
     * @param params
     * @param sets
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long zunionstore(K dstkey, ZParams params, V... sets);

    /**
     * 用于迭代SortSet集合中的元素和元素对应的分值
     *
     * @param key
     * @param cursor
     * @param <K>
     * @return
     */
    <K> ScanResult<Tuple> zscan(K key, String cursor);

    /**
     * 用于迭代SortSet集合中的元素和元素对应的分值
     *
     * @param key
     * @param cursor
     * @param params
     * @param <K>
     * @return
     */
    <K> ScanResult<Tuple> zscan(K key, String cursor, ScanParams params);
}
