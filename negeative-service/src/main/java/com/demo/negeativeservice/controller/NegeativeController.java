package com.demo.negeativeservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
@RestController
public class NegeativeController {
    @GetMapping("/negeative-service")
    public Integer randomNumberGenerator(){
        int negInt = new Random().nextInt(10);
        System.out.println("Negeative number is"+ -negInt);
        return -negInt;
    }
}
