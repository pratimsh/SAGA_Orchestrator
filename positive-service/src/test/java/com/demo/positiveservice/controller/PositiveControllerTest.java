package com.demo.positiveservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PositiveControllerTest {

    PositiveController positiveController = new PositiveController();
    @Test
    void randomNumberGenerator() {
        //act
        Integer result = positiveController.randomNumberGenerator();

    }
}