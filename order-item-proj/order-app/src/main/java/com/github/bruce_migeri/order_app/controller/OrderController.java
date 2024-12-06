package com.github.bruce_migeri.order_app.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private static final String ORDER_SERVICE = "orderService";

    private final RestTemplate restTemplate;

    private Integer attempts = 1;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/order")
    @CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> createOrder() {
        String response = restTemplate.getForObject("http://localhost:8081/item", String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order-retry")
    @Retry(name = ORDER_SERVICE, fallbackMethod = "fallback_retry")
    public ResponseEntity<String> createOrderRetry() {
        log.info("Item service call attempted:::{}", attempts++);
        String response = restTemplate.getForObject("http://localhost:8081/item", String.class);
        log.info("Item service called");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/bulk-head")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "bulkHeadFallback")
    public ResponseEntity<String> createOrderBulkHead() {
        String response = restTemplate.getForObject("http://localhost:8081/item", String.class);
        log.info("{} Call processing finished = {}", LocalTime.now(), Thread.currentThread().getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rate-limiter")
    @RateLimiter(name = ORDER_SERVICE, fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<String> createOrderRateLimiter() {
        String response = restTemplate.getForObject("http://localhost:8081/item", String.class);
        log.info("{} Call processing finished = {}", LocalTime.now(), Thread.currentThread().getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> orderFallback(Exception e) {
        return new ResponseEntity<>("Item service is down", HttpStatus.OK);
    }

    public ResponseEntity<String> fallback_retry(Exception e) {
        attempts = 1;
        return new ResponseEntity<>("Item service is down", HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadFallback(Exception e) {
        return new ResponseEntity<>("Order service does not permit further calls", HttpStatus.TOO_MANY_REQUESTS);
    }

    public ResponseEntity<String> rateLimiterFallback(Exception e) {
        return new ResponseEntity<>("Order service does not permit further calls", HttpStatus.TOO_MANY_REQUESTS);
    }


}
