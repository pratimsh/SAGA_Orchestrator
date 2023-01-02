package com.demo.cachingservice.configuration;

import com.demo.cachingservice.service.CacheService;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.demo.cachingservice.constants.CacheConstants.PRODUCTS_CACHE_NAME;

@Configuration
@EnableCaching
public class AppConfiguration {

    @Bean
    public CacheService getCacheService(){
        return new CacheService();
    }
    @Bean
    public CaffeineCacheConfig getCaffeineCacheConfig(){
        return new CaffeineCacheConfig();
    }

    @Bean
    public CacheManager productsCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(PRODUCTS_CACHE_NAME);
        cacheManager.setAllowNullValues(true); //can happen if you get a value from a @Cachable that returns null
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }


    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(15)
                .maximumSize(150)
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .removalListener(new CustomRemovalListener())
                .weakKeys()
                .recordStats();
    }

    class CustomRemovalListener implements RemovalListener<Object, Object> {
        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            System.out.format("removal listerner called with key [%s], cause [%s], evicted [%S]\n",
                    key, cause.toString(), cause.wasEvicted());
        }
    }
}
