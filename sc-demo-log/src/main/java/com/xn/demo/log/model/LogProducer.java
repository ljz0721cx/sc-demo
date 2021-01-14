package com.xn.demo.log.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.LocalDateTime;

/**
 * @author Janle on 2021/1/14
 */
public class LogProducer implements Runnable {
    Logger logger = LoggerFactory.getLogger(LogProducer.class);


    @Override
    public void run() {
        while (true) {
            MDC.put("traceId", "" + LocalDateTime.now().getNano());
            logger.info("哈哈哈哈哈哈info");
            logger.warn("哈哈哈哈哈哈warn");
            logger.debug("哈哈哈哈哈哈debug");
            logger.error("哈哈哈哈哈哈error");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
