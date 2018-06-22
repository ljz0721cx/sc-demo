package com.xn.demo.functions;


/**
 * @author Janle
 * @date 2018/6/14 10:32
 */
public class EndAppointFactoryBean implements FactoryBean {
    private Long venderId;

    public EndAppointFactoryBean(Long venderId) {
        this.venderId = venderId;
    }

    @Override
    public Long getVenderId() {
        return venderId;
    }
}
