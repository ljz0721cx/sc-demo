package com.sc.ynk.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * DomainFactory
 *
 * @author Janle on 2023/3/11
 */
public class DomainFactory implements EventFactory<DomainEvent> {
    @Override
    public DomainEvent newInstance() {
        return new DomainEvent();
    }
}
