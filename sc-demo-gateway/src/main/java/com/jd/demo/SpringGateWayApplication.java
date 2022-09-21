package com.jd.demo;

import com.sun.net.httpserver.HttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import java.net.InetSocketAddress;

/**
 * @author Janle on 2022/3/31
 */
@SpringBootApplication
public class SpringGateWayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext app= SpringApplication.run(SpringGateWayApplication.class, args);

        //通过ApplicationContext创建HttpHandler
        HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(app).build();
        ReactorHttpHandlerAdapter httpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
       // HttpServer.create(new InetSocketAddress("localhost",8080),0).newHandler(httpHandlerAdapter).block();
    }
}
