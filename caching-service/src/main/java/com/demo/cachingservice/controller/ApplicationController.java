package com.demo.cachingservice.controller;


import com.demo.cachingservice.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/cache")
public class ApplicationController {

    public static Map<Integer, Integer> CUSTOM_CACHE = new HashMap<>();
    @Autowired
    private CacheService cacheService;

    @GetMapping("/getCachedValue/{key}")
    public Map<String,Integer> getCachedValue(@PathVariable Integer key){

        Map<String,Integer> responseMap = cacheService.getValueFromCache(key);

        return responseMap;
    }

    @PostMapping("/putValue")
    @ResponseBody
    public String putValueInCache(@RequestBody Map<String, Integer> values){

        String response = cacheService.putValueIntoCache(values);

        return response;
    }

    @PostMapping("/clearEntry/{key}")
    public String deleteValueFromCache(@PathVariable Integer key){
        cacheService.deleteValueFromCache(key);
        return "cache cleared";
    }
}
