package com.xn.demo.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 声明一个eureka的客户端
 * @author lijizhen1@jd.com
 * @date 2018/5/31 17:09
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.xn.demo")
public class ProviderUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderUserApplication.class, args);
    }

}
