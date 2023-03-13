package com.sc.ynk.disruptor;

/**
 * 测试列表
 *
 * @author Janle on 2023/3/11
 */
public class DisruptorTest {
    public static void main(String[] args) {
        final DomainHandler domainHandler = new DomainHandler();
        final DomainDisruptorQueue domainDisruptorQueue = new DomainDisruptorQueue(domainHandler);
        for (int i = 0; i < 10000; i++) {
            domainDisruptorQueue.publishEvent("执行队列元素" + i);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
