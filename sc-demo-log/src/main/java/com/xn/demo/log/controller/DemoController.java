package com.xn.demo.log.controller;

import com.xn.demo.log.feign.DemoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DemoController {

    @Autowired
    DemoFeignClient demoFeignClient;

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
        int i = (int) (1 + Math.random() * (10 - 1 + 1));
        if (i>5){
            throw new RuntimeException("业务报错了");
        }
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
        int i = (int) (1 + Math.random() * (10 - 1 + 1));
        if (i>5){
            throw new RuntimeException("业务报错了");
        }
        return UUID.randomUUID().toString();
    }


    @GetMapping("/demo/test5")
    public String test5() {

        return demoFeignClient.test5();
    }

}
