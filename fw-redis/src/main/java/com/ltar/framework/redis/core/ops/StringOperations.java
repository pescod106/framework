package com.ltar.framework.redis.core.ops;

import java.util.List;

/**
 * @desc: Redis operations for simple (or in Redis terminology 'string') values.
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public interface StringOperations {

    /**
     * 如果 key 已经存在，并且值为字符串，那么这个命令会把 value 追加到原来值（value）的结尾。
     * 如果 key 不存在，那么它将首先创建一个空字符串的key，再执行追加操作，这种情况 APPEND 将类似于 SET 操作。
     *
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @return 返回append后字符串值（value）的长度。
     */
    <K, V> Long append(K key, V value);

    /**
     * @param key
     * @param <K>
     * @return
     * @see {@link #bitCount(Object, long, long)}
     */
    <K> Long bitCount(K key);

    /**
     * 统计字符串被设置为1的bit数.
     * <p>
     * 一般情况下，给定的整个字符串都会被进行计数，通过指定额外的 start 或 end 参数，可以让计数只在特定的位上进行。
     * <p>
     * start 和 end 参数的设置和 GETRANGE 命令类似，都可以使用负数值：比如 -1 表示最后一个位，而 -2 表示倒数第二个位，以此类推。
     * <p>
     * 不存在的 key 被当成是空字符串来处理，因此对一个不存在的 key 进行 BITCOUNT 操作，结果为 0 。
     *
     * @param key
     * @param start
     * @param end
     * @param <K>
     * @return 被设置为 1 的位的数量。
     */
    <K> Long bitCount(K key, long start, long end);

    <K> Long bitPos(K key, boolean value);

    /**
     * 返回字符串里面第一个被设置为1或者0的bit位。
     * <p>
     * 返回一个位置，把字符串当做一个从左到右的字节数组，第一个符合条件的在位置0，其次在位置8，等等。
     * <p>
     * GETBIT 和 SETBIT 相似的也是操作字节位的命令。
     * <p>
     * 默认情况下整个字符串都会被检索一次，只有在指定start和end参数(指定start和end位是可行的)，
     * 该范围被解释为一个字节的范围，而不是一系列的位。所以start=0 并且 end=2是指前三个字节范围内查找。
     * <p>
     * 注意，返回的位的位置始终是从0开始的，即使使用了start来指定了一个开始字节也是这样。
     * <p>
     * 和GETRANGE命令一样，start和end也可以包含负值，负值将从字符串的末尾开始计算，-1是字符串的最后一个字节，-2是倒数第二个，等等。
     * <p>
     * 不存在的key将会被当做空字符串来处理。
     *
     * @param key
     * @param value
     * @param start
     * @param end
     * @param <K>
     * @return
     */
    <K> Long bitPos(K key, boolean value, long start, long end);

    /**
     * 对key对应的数字做减1操作。如果key不存在，那么在操作之前，这个key对应的值会被置为0。
     * 如果key有一个错误类型的value或者是一个不能表示成数字的字符串，就返回错误。这个操作最大支持在64位有符号的整型数字。
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long decr(K key);

    /**
     * 将key对应的数字减decrement。如果key不存在，操作之前，key就会被置为0。
     * 如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。这个操作最多支持64位有符号的正型数字。
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long decrBy(K key, int decrement);

    /**
     * 返回key的value。
     *
     * @param key
     * @param <T>
     * @param <K>
     * @return
     */
    <T, K> T get(K key);

    /**
     * 返回key对应的string在offset处的bit值
     * <p>
     * 当offset超出了字符串长度的时候，这个字符串就被假定为由0比特填充的连续空间。
     * 当key不存在的时候，它就认为是一个空字符串，所以offset总是超出范围，然后value也被认为是由0比特填充的连续空间。到内存分配。
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Boolean getBit(K key, long offset);

    /**
     * 返回key对应的字符串value的子串，这个子串是由start和end位移决定的（两者都在string内）。
     * 可以用负的位移来表示从string尾部开始数的下标。所以-1就是最后一个字符，-2就是倒数第二个，以此类推。
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V getRange(K key, long start, long end);

    /**
     * 自动将key对应到value并且返回原来key对应的value。如果key存在但是对应的value不是字符串，就返回错误。
     * <p>
     * 设计模式
     * GETSET可以和INCR一起使用实现支持重置的计数功能。
     * 举个例子：
     * 每当有事件发生的时候，一段程序都会调用INCR给key mycounter加1，但是有时我们需要获取计数器的值，并且自动将其重置为0。
     * 这可以通过GETSET mycounter “0”来实现：
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> V getSet(K key, V value);

    /**
     * 对存储在指定key的数值执行原子的加1操作。
     * <p>
     * 如果指定的key不存在，那么在执行incr操作之前，会先将它的值设定为0
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Long incr(K key);

    /**
     * 将key对应的数字加increment。如果key不存在，操作之前，key就会被置为0。
     *
     * @param key
     * @param increment
     * @param <K>
     * @return
     */
    <K> Long incrBy(K key, long increment);

    /**
     * 通过指定浮点数key来增长浮点数(存放于string中)的值. 当键不存在时,先将其值设为0再操作.下面任一情况都会返回错误:
     * key 包含非法值(不是一个string).
     * 当前的key或者相加后的值不能解析为一个双精度的浮点值.(超出精度范围了)
     * <p>
     * 如果操作命令成功, 相加后的值将替换原值存储在对应的键值上, 并以string的类型返回.
     *
     * @param key
     * @param increment
     * @param <K>
     * @return
     */
    <K> Double incrByFloat(K key, double increment);

    /**
     * 返回所有指定的key的value。对于每个不对应string或者不存在的key，都返回特殊值nil。正因为此，这个操作从来不会失败。
     *
     * @param keys
     * @param <K>
     * @param <V>
     * @return 指定的key对应的values的list
     */
    <K, V> List<V> mget(K... keys);

    /**
     * 对应给定的keys到他们相应的values上。只要有一个key已经存在，MSETNX一个操作都不会执行。
     * 由于这种特性，MSETNX可以实现要么所有的操作都成功，要么一个都不执行，这样可以用来设置不同的key，来表示一个唯一的对象的不同字段。
     * <p>
     * MSETNX是原子的，所以所有给定的keys是一次性set的。客户端不可能看到这种一部分keys被更新而另外的没有改变的情况。
     *
     * @param keysValues
     * @return 1 如果所有的key被set
     * 0 如果没有key被set(至少其中有一个key是存在的)
     */
    void mset(Object... keysValues);

    /**
     * PSETEX和SETEX一样，唯一的区别是到期时间以毫秒为单位,而不是秒。
     *
     * @param key
     * @param value
     * @param milliseconds 毫秒
     * @param <K>
     * @param <V>
     */
    <K, V> void psetex(K key, V value, long milliseconds);

    /**
     * 将键key设定为指定的“字符串”值。
     * <p>
     * 如果	key	已经保存了一个值，那么这个操作会直接覆盖原来的值，并且忽略原始类型。
     * <p>
     * 当set命令执行成功之后，之前设置的过期时间都将失效
     * <p>
     * <p>
     * 从2.6.12版本开始，redis为SET命令增加了一系列选项:
     * EX seconds – 设置键key的过期时间，单位时秒
     * PX milliseconds – 设置键key的过期时间，单位时毫秒
     * NX – 只有键key不存在的时候才会设置key的值
     * XX – 只有键key存在的时候才会设置key的值
     *
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     */
    <K, V> void set(K key, V value);

    /**
     * 设置key对应字符串value，并且设置key在给定的seconds时间之后超时过期。
     *
     * @param key
     * @param value
     * @param seconds
     * @param <K>
     * @param <V>
     */
    <K, V> void setEx(K key, V value, int seconds);

    /**
     * 将key设置值为value，如果key不存在，这种情况下等同SET命令。
     * 当key存在时，什么也不做。SETNX是”SET if Not eXists”的简写。
     *
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @return 1 如果key被设置了
     * 0 如果key没有被设置
     */
    <K, V> Long setNx(K key, V value);

    /**
     * 设置或者清空key的value(字符串)在offset处的bit值。
     * 那个位置的bit要么被设置，要么被清空，这个由value（只能是0或者1）来决定。
     * 当key不存在的时候，就创建一个新的字符串value。
     * 要确保这个字符串大到在offset处有bit值。
     * 参数offset需要大于等于0，并且小于232(限制bitmap大小为512)。
     * 当key对应的字符串增大的时候，新增的部分bit值都是设置为0。
     *
     * @param key
     * @param offset
     * @param value
     * @param <K>
     * @return
     */
    <K> Boolean setBit(K key, long offset, boolean value);

    /**
     * 这个命令的作用是覆盖key对应的string的一部分，从指定的offset处开始，覆盖value的长度。
     * 如果offset比当前key对应string还要长，那这个string后面就补0以达到offset。
     * 不存在的keys被认为是空字符串，
     * 所以这个命令可以确保key有一个足够大的字符串，能在offset处设置value。
     *
     * @param key
     * @param value
     * @param offset
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Long setRange(K key, V value, long offset);

    /**
     * 返回key的string类型value的长度。如果key对应的非string类型，就返回错误。
     *
     * @param key
     * @param <K>
     * @return key对应的字符串value的长度，或者0（key不存在）
     */
    <K> Long strLen(K key);


}
