package com.github.bruce_migeri.order_app.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    private static final String ORDER_SERVICE = "orderService";

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/order")
    @CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> createOrder() {
        String response = restTemplate.getForObject("http://localhost:8081/item", String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> orderFallback(Exception e) {
        return new ResponseEntity<>("Item service is down", HttpStatus.OK);
    }
}
