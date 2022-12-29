package com.demo.orchestratorservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrchestratorControllerIntegrationTest {

    public static final String ORCHESTRATOR_END_POINT_URL="/product-service";
    public static final String ORCHESTRATOR_BASE_URL="http://localhost:";

    @Value(value="${local.server.port}")
    private int port;

    @Test
    public void testOrchestratorHealthCheckWhenNoDataBaseAvailable() {
        //assemble
        final String EXPECTED_EXCEPTION_RESPONSE="Positive service is not so positive today, come back later";
        //act
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.
                getForEntity(ORCHESTRATOR_BASE_URL+port+ORCHESTRATOR_END_POINT_URL, String.class);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(EXPECTED_EXCEPTION_RESPONSE,response.getBody());
    }
}