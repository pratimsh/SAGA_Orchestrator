package com.demo.cachingservice.service;

import com.demo.cachingservice.configuration.CaffeineCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {

    @Autowired
    CaffeineCacheConfig caffeineCacheConfig;

    public Map<String, Integer> getValueFromCache(Integer key){
        Cache.ValueWrapper valueFromMap = caffeineCacheConfig.cacheManager().getCache("number-cache").get(key);


        Map<String, Integer> responseMap = new HashMap();
        if(null != valueFromMap) {

            responseMap.put("key", key);
            responseMap.put("value", (Integer.valueOf(valueFromMap.toString())));
        }
        return responseMap;
    }


    public String putValueIntoCache(Map<String, Integer> map) {
        if(null == map.get("value")) {
            caffeineCacheConfig.cacheManager().getCache("number-cache").put(map.get("key"), null);

        }else if(null != map.get("value")){
            deleteValueFromCache(map.get("key"));
            caffeineCacheConfig.cacheManager().getCache("number-cache").put(map.get("key"),map.get("value"));
        }

      return "was successful";

    }

    public void deleteValueFromCache(Integer key) {
        caffeineCacheConfig.cacheManager().getCache("number-cache").evictIfPresent(key);
    }
}
