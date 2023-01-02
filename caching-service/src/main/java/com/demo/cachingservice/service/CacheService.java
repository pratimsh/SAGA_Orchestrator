package com.demo.cachingservice.service;

import com.demo.cachingservice.configuration.CaffeineCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.demo.cachingservice.constants.CacheConstants.PRODUCTS_CACHE_NAME;

@Service
public class CacheService {

    @Autowired
    CaffeineCacheConfig caffeineCacheConfig;
    @Autowired
    CacheManager productsCacheManager;

    public Map<String, Integer> getValueFromCache(Integer key){
        Cache.ValueWrapper valueFromMap = productsCacheManager.getCache(PRODUCTS_CACHE_NAME).get(key);

        Map<String, Integer> responseMap = new HashMap();
        if(null != valueFromMap) {

            responseMap.put("key", key);
            responseMap.put("value", (Integer.valueOf(valueFromMap.get().toString())));
        }
        return responseMap;
    }


    public String putValueIntoCache(Map<String, Integer> map) {
        if(null == map.get("value")) {
            productsCacheManager.getCache(PRODUCTS_CACHE_NAME).put(map.get("key"), null);

        }else if(null != map.get("value")){
            deleteValueFromCache(map.get("key"));
            productsCacheManager.getCache(PRODUCTS_CACHE_NAME).put(map.get("key"),map.get("value"));
        }

      return "was successful";

    }

    public void deleteValueFromCache(Integer key) {
        productsCacheManager.getCache(PRODUCTS_CACHE_NAME).evictIfPresent(key);
    }

    public void inspectCache(String cacheName) {

        CaffeineCache caffeineCache = (CaffeineCache) productsCacheManager.getCache(PRODUCTS_CACHE_NAME);
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
        for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
            System.out.println("Key = " + entry.getKey());
            System.out.println("Value = " + entry.getValue());
        }
    }
}
