package com.ltar.redis.core.ops;


/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface HyperLogLogOperations {
    /**
     * 将除了第一个参数以外的参数存储到以第一个参数为变量名的HyperLogLog结构中.
     * <p>
     * 这个命令的一个副作用是它可能会更改这个HyperLogLog的内部来反映在每添加一个唯一的对象时估计的基数(集合的基数).
     * <p>
     * 如果一个HyperLogLog的估计的近似基数在执行命令过程中发了变化，
     * PFADD 返回1，否则返回0，如果指定的key不存在，这个命令会自动创建一个空的HyperLogLog结构（指定长度和编码的字符串）.
     * <p>
     * 如果在调用该命令时仅提供变量名而不指定元素也是可以的，
     * 如果这个变量名存在，则不会有任何操作，如果不存在，则会创建一个数据结构（返回1）.
     *
     * @param key
     * @param elements
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long pfadd(K key, V... elements);

    /**
     * 当参数为一个key时,返回存储在HyperLogLog结构体的该变量的近似基数，如果该变量不存在,则返回0.
     * <p>
     * 当参数为多个key时，返回这些HyperLogLog并集的近似基数，
     * 这个值是将所给定的所有key的HyperLoglog结构合并到一个临时的HyperLogLog结构中计算而得到的.
     *
     * @param keys
     * @param <K>
     * @return
     */
    <K> Long pfcount(K... keys);

    /**
     * 将多个 HyperLogLog 合并（merge）为一个 HyperLogLog ，
     * 合并后的 HyperLogLog 的基数接近于所有输入 HyperLogLog 的可见集合（observed set）的并集.
     * <p>
     * 合并得出的 HyperLogLog 会被储存在目标变量（第一个参数）里面，
     * 如果该键并不存在， 那么命令在执行之前， 会先为该键创建一个空的.
     *
     * @param destkey
     * @param sourcekeys
     * @param <K>
     * @param <V>
     */
    <K, V> void pfmerge(K destkey, V... sourcekeys);

}
