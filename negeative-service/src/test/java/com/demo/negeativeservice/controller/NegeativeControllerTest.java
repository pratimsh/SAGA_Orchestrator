package com.demo.negeativeservice.controller;

import org.junit.jupiter.api.Test;

class NegeativeControllerTest {

    NegeativeController negeativeController = new NegeativeController();
    @Test
    void randomNumberGenerator() {
        //act
        Integer result = negeativeController.randomNumberGenerator();

    }
}