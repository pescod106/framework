package com.ltar.redis.impl;

import com.ltar.redis.constant.DataType;
import com.ltar.redis.core.*;
import com.ltar.redis.core.ops.*;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/24
 * @version: 1.0.0
 */
public class RedisTemplate extends RedisAccessor implements RedisOperations {

    private ClusterOperations ops4Cluster;
    private GeoOperations ops4Geo;
    private HashOperations ops4Hash;
    private HyperLogLogOperations ops4HyperLogLog;
    private ListOperations ops4List;
    private SetOperations ops4Set;
    private SortedSetOperations ops4ZSet;
    private StringOperations ops4String;

    public <K> Boolean exists(K key) {
        return null;
    }

    public <K> Boolean delete(K key) {
        return null;
    }

    public <K> Long delete(Collection<K> keys) {
        return null;
    }

    public <K> DataType type(K key) {
        return null;
    }

    public <K> Set<K> keys(K pattern) {
        return null;
    }

    public <K> void rename(K oldKey, K newKye) {

    }

    public <K> Long renamenx(K oldKey, K newKye) {
        return null;
    }

    public <K> Long expire(K key, long timeout, TimeUnit timeUnit) {
        return null;
    }

    public <K> Long expireAt(K key, Date date) {
        return null;
    }

    public <K> Long persist(K key) {
        return null;
    }

    public <K> Boolean move(K key, int dbIndex) {
        return null;
    }

    public byte[] dump(String key) {
        return new byte[0];
    }

    public <K> void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {

    }

    public <K> Long getExpire(K key) {
        return null;
    }

    public <K> Long getExpire(K key, TimeUnit timeUnit) {
        return null;
    }

    public <K> void watch(K... keys) {

    }

    public void unwatch() {

    }

    public void multi() {

    }

    public void discard() {

    }

    public ClusterOperations ops4Cluster() {
        if (null == ops4Cluster) {
            ops4Cluster = new DefaultClusterOperations(this);
        }
        return ops4Cluster;
    }

    public GeoOperations ops4Geo() {
        if (null == ops4Geo) {
            ops4Geo = new DefaultGeoOperations(this);
        }
        return ops4Geo;
    }

    public HashOperations ops4Hash() {
        if (null == ops4Hash) {
            ops4Hash = new DefaultHashOperations(this);
        }
        return ops4Hash;
    }

    public HyperLogLogOperations ops4HyperLogLog() {
        if (null == ops4HyperLogLog) {
            ops4HyperLogLog = new DefaultHyperLogLogOperations(this);
        }
        return ops4HyperLogLog;
    }

    public ListOperations ops4List() {
        if (null == ops4List) {
            ops4List = new DefaultListOperations(this);
        }
        return ops4List;
    }

    public SetOperations ops4Set() {
        if (null == ops4Set) {
            ops4Set = new DefaultSetOperations(this);
        }
        return ops4Set;
    }

    public StringOperations ops4String() {
        if (null == ops4String) {
            ops4String = new DefaultStringOperations(this);
        }
        return ops4String;
    }

    public SortedSetOperations opsForZSet() {
        if (null == ops4ZSet) {
            ops4ZSet = new DefaultSortedSetOperations(this);
        }
        return ops4ZSet;
    }


}
