package com.demo.orchestratorservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class OrchestratorControllerTest {

    OrchestratorController orchestratorController = new OrchestratorController();

    @Test
    void findProduct() {
        //act
        ResponseEntity response = orchestratorController.findProduct();
        //assert
        assertNotNull(response);
    }
}