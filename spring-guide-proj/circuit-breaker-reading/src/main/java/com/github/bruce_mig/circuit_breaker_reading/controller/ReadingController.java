package com.github.bruce_mig.circuit_breaker_reading.controller;

import com.github.bruce_mig.circuit_breaker_reading.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ReadingController {

    private final BookService bookService;

    public ReadingController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/to-read")
    public Mono<String> toRead() {
        return bookService.readingList();
    }
}
