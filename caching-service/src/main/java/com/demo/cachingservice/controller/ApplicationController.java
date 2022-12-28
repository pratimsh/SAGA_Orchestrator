package com.demo.cachingservice.controller;


import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/cache")
public class ApplicationController {

    public static Map<Integer, Integer> CUSTOM_CACHE = new HashMap<>();


    @GetMapping("/getCachedValue/{key}")
    public Map<String,Integer> getCachedValue(@PathVariable Integer key){
        Integer value = CUSTOM_CACHE.get(key);

        Map<String, Integer> responseMap = new HashMap();
        if(null != value) {

            responseMap.put("key", key);
            responseMap.put("value", value);
        }
        return responseMap;
    }

    @PostMapping("/putValue")
    @ResponseBody
    public String putValueInCache(@RequestBody Map<String, Integer> values){
        if(null == CUSTOM_CACHE.get(values.get("key"))){
            CUSTOM_CACHE.put(values.get("key"),values.get("value"));
            System.out.println(CUSTOM_CACHE.entrySet());

        }else{
            CUSTOM_CACHE.replace(values.get("key"),null,values.get("value"));
        }
        return "was successful";
    }

    @PostMapping("/clearEntry/{key}")
    public String deleteValueFromCache(@PathVariable Integer key){
        CUSTOM_CACHE.remove(key);
        return "cache cleared";
    }
}
