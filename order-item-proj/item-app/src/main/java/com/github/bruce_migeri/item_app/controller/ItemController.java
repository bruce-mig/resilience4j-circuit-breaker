package com.github.bruce_migeri.item_app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @GetMapping("/item")
    public String getItem(){
        /*try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            log.error("getItem call returned {}",e.getMessage());
        }
        log.info("getItem call returned");*/
        return "Item Selected";
    }
}
