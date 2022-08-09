package com.xn.demo.log.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DemoController {

    /**
     * mvc 限流
     * @return
     */
    @GetMapping(value = "/demo/test1")
    public String test1() {

        return UUID.randomUUID().toString();
    }

    /**
     * mvc 熔断
     * @return
     */
    @GetMapping("/demo/test2")
    public String test2() {

        return UUID.randomUUID().toString();
    }

    /**
     * webFlux 限流
     * @return
     */
    @GetMapping("/demo/test3")
    public String test3() {
        return UUID.randomUUID().toString();
    }

    /**
     * webFlux 熔断
     * @return
     */
    @GetMapping("/demo/test4")
    public String test4() {
        return UUID.randomUUID().toString();
    }

}
