package com.academy.cinemaxx.repositories;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping(name = "/hello")
    public String hello() {
        return "Hello World!";
    }
}
