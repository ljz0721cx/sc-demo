package com.jd.demo.feign.web;

import com.jd.demo.feign.test.TestFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janle qq:645905201
 * @date 2018/6/5 17:07
 */
@RestController
public class TestFeignController {
    @Autowired
    private TestFeignClient testFeignClient;


    @GetMapping("/feign/{id}")
    public String findById(@PathVariable("id") Long id) {
        return this.testFeignClient.findById(id);
    }
}
