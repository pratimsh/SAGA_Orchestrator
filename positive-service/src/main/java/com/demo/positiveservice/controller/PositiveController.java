package com.demo.positiveservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class PositiveController {
    @GetMapping("/positive-service")
    public Integer randomNumberGenerator(){
        int posInt = new Random().nextInt(20);
        System.out.println("Positive number is"+ posInt);
        return posInt;
    }
}
