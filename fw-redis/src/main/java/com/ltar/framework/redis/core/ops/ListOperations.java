package com.ltar.framework.redis.core.ops;

import java.util.List;

/**
 * @desc: Redis list specific operations.
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface ListOperations {
    /**
     * BLPOP 是阻塞式列表的弹出原语。
     * 它是命令 LPOP 的阻塞版本，这是因为当给定列表内没有任何元素可供弹出的时候， 连接将被 BLPOP 命令阻塞。
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
     *
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V blpop(K... keys);

    /**
     * BLPOP 是阻塞式列表的弹出原语。
     * 它是命令 LPOP 的阻塞版本，这是因为当给定列表内没有任何元素可供弹出的时候， 连接将被 BLPOP 命令阻塞。
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
     *
     * @param timeout
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V blpop(int timeout, K... keys);

    /**
     * BRPOP 是一个阻塞的列表弹出原语。
     * 它是 RPOP 的阻塞版本，因为这个命令会在给定list无法弹出任何元素的时候阻塞连接。
     * 该命令会按照给出的 key 顺序查看 list，并在找到的第一个非空 list 的尾部弹出一个元素。
     *
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V brpop(K... keys);

    /**
     * BRPOP 是一个阻塞的列表弹出原语。
     * 它是 RPOP 的阻塞版本，因为这个命令会在给定list无法弹出任何元素的时候阻塞连接。
     * 该命令会按照给出的 key 顺序查看 list，并在找到的第一个非空 list 的尾部弹出一个元素。
     *
     * @param timeout
     * @param keys
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V brpop(int timeout, K... keys);

    /**
     * BRPOPLPUSH 是 RPOPLPUSH 的阻塞版本。
     * 当 source 包含元素的时候，这个命令表现得跟 RPOPLPUSH 一模一样。
     * 当 source 是空的时候，Redis将会阻塞这个连接，直到另一个客户端 push 元素进入或者达到 timeout 时限。
     * timeout 为 0 能用于无限期阻塞客户端。
     *
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V brpoplpush(K sourceKey, K destinationKey, int timeout);

    /**
     * 返回列表里的元素的索引 index 存储在 key 里面。
     * <p>
     * 负数索引用于指定从列表尾部开始索引的元素。在这种方法下，-1 表示最后一个元素，-2 表示倒数第二个元素，并以此往前推。
     *
     * @param key
     * @param index
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V lindex(K key, long index);

    /**
     * 把 value 插入存于 key 的列表中在基准值 pivot 的前面
     * 当 key 不存在时，这个list会被看作是空list，任何操作都不会发生。
     * 当 key 存在，但保存的不是一个list的时候，会返回error。
     *
     * @param key
     * @param pivot
     * @param value
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long linsertBefore(K key, V pivot, V value);

    /**
     * 把 value 插入存于 key 的列表中在基准值 pivot 的后面
     * 当 key 不存在时，这个list会被看作是空list，任何操作都不会发生。
     * 当 key 存在，但保存的不是一个list的时候，会返回error。
     *
     * @param key
     * @param pivot
     * @param value
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long linsertAfter(K key, V pivot, V value);

    /**
     * 返回存储在 key 里的list的长度。
     * 如果 key 不存在，那么就被看作是空list，并且返回长度为 0。
     * 当存储在 key 里的值不是一个list的话，会返回error。
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long llen(K key);

    /**
     * 移除并且返回 key 对应的 list 的第一个元素。
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return 返回第一个元素的值，或者当 key 不存在时返回 nil。
     */
    <K, V> V lpop(K key);

    /**
     * 将所有指定的值插入到存于 key 的列表的头部。
     * 如果 key 不存在，那么在进行 push 操作前会创建一个空列表。
     * 如果 key 对应的值不是一个 list 的话，那么会返回一个错误。
     *
     * @param key
     * @param values
     * @param <K>
     * @param <V>
     * @return 在 push 操作后的 list 长度。
     */
    <K, V> Long lpush(K key, V... values);

    /**
     * 只有当 key 已经存在并且存着一个 list 的时候，在这个 key 下面的 list 的头部插入 value。
     * 与 LPUSH 相反，当 key 不存在的时候不会进行任何操作。
     *
     * @param key
     * @param values
     * @param <K>
     * @param <V>
     * @return 在 push 操作后的 list 长度。
     */
    <K, V> Long lpushx(K key, V... values);

    /**
     * 返回存储在 key 的列表里指定范围内的元素。
     * <p>
     * start 和 end 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推。
     * 偏移量也可以是负数，表示偏移量是从list尾部开始计数。 例如， -1 表示列表的最后一个元素，-2 是倒数第二个，以此类推。
     *
     * @param key
     * @param start
     * @param stop
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> List<V> lrange(K key, long start, long stop);

    /**
     * 从存于 key 的列表里移除前 count 次出现的值为 value 的元素。
     * <p>
     * 这个 count 参数通过下面几种方式影响这个操作：
     * count > 0: 从头往尾移除值为 value 的元素。
     * count < 0: 从尾往头移除值为 value 的元素。
     * count = 0: 移除所有值为 value 的元素。
     * 比如， LREM list -2 “hello” 会从存于 list 的列表里移除最后两个出现的 “hello”。
     * <p>
     * 需要注意的是，如果list里没有存在key就会被当作空list处理，所以当 key 不存在的时候，这个命令会返回 0。
     *
     * @param key
     * @param count
     * @param value
     * @param <K>
     * @param <V>
     * @return 被移除的元素个数。
     */
    <K, V> Long lrem(K key, long count, V value);

    /**
     * 设置 index 位置的list元素的值为 value。
     *
     * @param key
     * @param index
     * @param value
     * @param <K>
     * @param <V>
     */
    <K, V> void lset(K key, long index, V value);

    /**
     * 修剪(trim)一个已存在的 list，这样 list 就会只包含指定范围的指定元素。
     * <p>
     * start 和 stop 都是由0开始计数的， 这里的 0 是列表里的第一个元素（表头），1 是第二个元素，以此类推
     * 例如： LTRIM foobar 0 2 将会对存储在 foobar 的列表进行修剪，只保留列表里的前3个元素。
     * start 和 end 也可以用负数来表示与表尾的偏移量，比如 -1 表示列表里的最后一个元素， -2 表示倒数第二个
     *
     * @param key
     * @param start
     * @param stop
     * @param <K>
     * @see <a href="http://www.redis.cn/commands/ltrim.html">ltrim</a>
     */
    <K> void ltrim(K key, long start, long stop);

    /**
     * 移除并返回存于 key 的 list 的最后一个元素。
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return 最后一个元素的值，或者当 key 不存在的时候返回 nil。
     */
    <K, V> V rpop(K key);

    /**
     * 原子性地返回并移除存储在 source 的列表的最后一个元素（列表尾部元素），
     * 并把该元素放入存储在 destination 的列表的第一个元素位置（列表头部）。
     * <p>
     * 例如：假设 source 存储着列表 a,b,c， destination存储着列表 x,y,z。
     * 执行 RPOPLPUSH 得到的结果是 source 保存着列表 a,b ，而 destination 保存着列表 c,x,y,z。
     * <p>
     * 如果 source 不存在，那么会返回 nil 值，并且不会执行任何操作。
     * 如果 source 和 destination 是同样的，那么这个操作等同于移除列表最后一个元素并且把该元素放在列表头部，
     * 所以这个命令也可以当作是一个旋转列表的命令。
     *
     * @param sourceKey
     * @param destinationKey
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V rpoplpush(K sourceKey, K destinationKey);

    /**
     * 向存于 key 的列表的尾部插入所有指定的值。如果 key 不存在，那么会创建一个空的列表然后再进行 push 操作。
     * 当 key 保存的不是一个列表，那么会返回一个错误。
     *
     * @param key
     * @param values
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long rpush(K key, V... values);

    /**
     * 将值 value 插入到列表 key 的表尾, 当且仅当 key 存在并且是一个列表。
     * 和 RPUSH 命令相反, 当 key 不存在时，RPUSHX 命令什么也不做。
     *
     * @param key
     * @param values
     * @param <K>
     * @param <V>
     * @return RPUSHX 命令执行之后，表的长度。
     */
    <K, V> Long rpushx(K key, V... values);
}
