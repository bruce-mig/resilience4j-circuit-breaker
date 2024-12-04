package com.github.bruce_migeri.order_app.controller;

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
    public ResponseEntity<String> createOrder() {
        String response = restTemplate.getForObject("http://localhost:8081/item", String.class);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> orderFallback(Exception e) {
        return new ResponseEntity<String>("Item service is down", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
