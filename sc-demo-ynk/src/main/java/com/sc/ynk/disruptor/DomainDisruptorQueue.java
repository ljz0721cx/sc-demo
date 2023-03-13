package com.sc.ynk.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @date 2023-03-11
 * @auth Janle
 */
public class DomainDisruptorQueue {

    private ThreadFactory executor = Executors.defaultThreadFactory();

    private DomainFactory factory = new DomainFactory();

    private int bufferSize = 2 * 1024;

    private Disruptor<DomainEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);

    private RingBuffer<DomainEvent> ringBuffer;


    DomainDisruptorQueue(DomainHandler domainHandler) {
        disruptor.handleEventsWith(domainHandler);
        //为什么首次需亚欧拿到RingBuffer
        this.ringBuffer = disruptor.getRingBuffer();
        disruptor.start();
    }

    public void publishEvent(String log) {
        long sequence = ringBuffer.next();
        try {
            DomainEvent event = ringBuffer.get(sequence);
            event.setData(log);
        } finally {
            ringBuffer.publish(sequence);
        }
    }


}
