package com.sc.ynk.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;

/**
 * @author Janle on 2023/3/11
 */
public class DomainHandler implements EventHandler<DomainEvent> {

    @Override
    public void onEvent(DomainEvent domainEvent, long l, boolean b) throws Exception {
        System.out.println(JSON.toJSONString(domainEvent));
    }
}
