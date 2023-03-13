package com.sc.ynk.disruptor;

/**
 * 事件
 *
 * @author Janle on 2023/3/11
 */
public class DomainEvent {
    Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
