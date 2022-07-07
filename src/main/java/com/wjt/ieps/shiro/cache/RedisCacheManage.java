package com.wjt.ieps.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManage implements CacheManager {
    //cacheName认证或授权的统一名称
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);
        RedisCache<K, V> redisCache = new RedisCache<>(cacheName);
        return redisCache;
    }
}
