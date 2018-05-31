package com.xn.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/30 20:26
 */
@SpringBootApplication
@ComponentScan("com.xn.demo")
public class ProciderUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProciderUserApplication.class, args);
    }
}
