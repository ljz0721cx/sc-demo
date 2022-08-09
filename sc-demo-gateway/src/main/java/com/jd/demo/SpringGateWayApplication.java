package com.jd.demo;



import com.thclouds.commons.base.request.RequestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author Janle on 2022/3/31
 */
@SpringBootApplication(exclude = RequestConfig.class)
@EnableFeignClients
public class SpringGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringGateWayApplication.class, args);
    }


}
