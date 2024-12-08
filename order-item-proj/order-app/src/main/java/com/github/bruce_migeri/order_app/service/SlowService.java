package com.github.bruce_migeri.order_app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SlowService {

    private static final Logger log = LoggerFactory.getLogger(SlowService.class);

    public String slowMethod() {
        log.info("Slow Method going to sleep for 30 seconds...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Slow service invoked successfully";
    }
}
