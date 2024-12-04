package com.github.bruce_migeri.item_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @GetMapping("/item")
    public String getItem(){
        return "Item Selected";
    }
}
