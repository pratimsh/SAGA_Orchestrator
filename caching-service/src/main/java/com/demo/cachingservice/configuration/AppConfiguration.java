package com.demo.cachingservice.configuration;

import com.demo.cachingservice.service.CacheService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public CacheService getCacheService(){
        return new CacheService();
    }
    @Bean
    public CaffeineCacheConfig getCaffeineCacheConfig(){
        return new CaffeineCacheConfig();
    }
}
