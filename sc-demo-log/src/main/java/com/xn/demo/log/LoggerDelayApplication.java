package com.xn.demo.log;

import com.xn.demo.log.model.LogProducer;
import com.xn.demo.log.model.LogTaskConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 延时收集系统日志。
 *
 * @author Janle on 2021/1/14
 */
@SpringBootApplication
public class LoggerDelayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoggerDelayApplication.class, args);
        new Thread(new LogProducer()).start();
        new Thread(new LogTaskConsumer()).start();
    }

}
