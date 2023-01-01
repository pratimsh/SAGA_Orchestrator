package com.demo.cachingservice.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.caffeine.CaffeineCacheManager;


import java.util.concurrent.TimeUnit;

@CacheConfig
public class CaffeineCacheConfig {



    public CacheManager cacheManager(){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("number-cache");
        caffeineCacheManager.setCaffeine(caffeineCacheBuilder());
        return caffeineCacheManager;
    }

    Caffeine<Object, Object> caffeineCacheBuilder(){
        return Caffeine.newBuilder()
                .initialCapacity(20)
                .maximumSize(20)
                .expireAfterAccess(20, TimeUnit.MINUTES)
                .recordStats();
    }
}
