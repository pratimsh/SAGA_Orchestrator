package com.demo.orchestratorservice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController

public class OrchestratorController {

    @Autowired
    Environment env;
    public static Map<String,Integer> custom_map = new HashMap<>();



    @GetMapping("/product-service")
    public ResponseEntity<String> findProduct(){
        RestTemplate restTemplate = new RestTemplate();
        //URL for positive-service

        String pos_service_url = env.getProperty("positive.service.url");

        //calling positive-service
        ResponseEntity<Integer> response = null;
        try {
             response = restTemplate.getForEntity(pos_service_url, Integer.class);
        }catch(ResourceAccessException e){
            return ResponseEntity.ok("Positive service is not so positive today, come back later");
        }
        if (HttpStatus.OK != response.getStatusCode()) {
            return ResponseEntity.ok("Positive service is not very positive today, come back later");

        }
        //URL for caching-service
        String get_cached_data_url = env.getProperty("get.cached.data.url");
        String put_data_in_cache_url = env.getProperty("put.data.in.cache.url");

        //putting positive value in cache as key
        custom_map.put("key", response.getBody());

        ResponseEntity<String> postResponse = null;
        postResponse =restTemplate.postForEntity(put_data_in_cache_url, custom_map,String.class);
        System.out.println("Response Of positive Service"+ postResponse.getBody());


        //URL for negeative-service
        String neg_service_url = env.getProperty("neg.service.url");
        String delete_data_from_cache_url = env.getProperty("delete.data.from.cache.url");

        try {
            response = restTemplate.getForEntity(neg_service_url, Integer.class);
        }catch(ResourceAccessException e){

            //delete the data of key from map
            postResponse =restTemplate.postForEntity(delete_data_from_cache_url+"/"+ custom_map.get("key"), custom_map.get("key"),String.class);
            return ResponseEntity.ok("Negative service is totally negative today, come back later");

        }

        if (HttpStatus.OK != response.getStatusCode()) {
            //delete the data of key from map
            postResponse =restTemplate.postForEntity(delete_data_from_cache_url+"/"+ custom_map.get("key"), custom_map.get("key"),String.class);
            return ResponseEntity.ok("Negative service is totally negative today, come back later");

        }
        System.out.println("Response Of negative Service"+ response);

        //updating negeative value in cache against the key
        custom_map.put("value", response.getBody());


        postResponse =restTemplate.postForEntity(put_data_in_cache_url, custom_map,String.class);


        ResponseEntity<Map> getResponse = restTemplate.getForEntity(get_cached_data_url+"/"+ custom_map.get("key"),Map.class);
        System.out.println("The data in cache is as follows : key :"+getResponse.getBody().get("key")+", value: "+getResponse.getBody().get("value"));

        //retrieving data from cache
        Integer key = null , value = null;
        if(getResponse.getBody().get("key") instanceof Integer) {
             key = (Integer) getResponse.getBody().get("key");
        }
        if(getResponse.getBody().get("value") instanceof Integer){
             value = (Integer) getResponse.getBody().get("value");
        }


        Integer product = key * value;
        if(null != product){
            //clearing cache as product was successful
            postResponse =restTemplate.postForEntity(delete_data_from_cache_url+"/"+ custom_map.get("key"), custom_map.get("key"),String.class);

        }

        custom_map.clear();
        return  ResponseEntity.ok(String.valueOf(product));


    }
}
