package com.xn.demo.log.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DemoController {

    @GetMapping("/demo/test1")
    public String findById() {
        return UUID.randomUUID().toString();
    }

}
