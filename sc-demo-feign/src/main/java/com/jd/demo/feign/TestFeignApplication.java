package com.jd.demo.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Janle qq:645905201
 * @date 2018/6/5 17:12
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@ComponentScan(value = "com.jd.demo.feign")
public class TestFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestFeignApplication.class, args);
    }
}
