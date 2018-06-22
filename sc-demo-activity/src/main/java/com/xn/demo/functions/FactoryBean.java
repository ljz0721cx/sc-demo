package com.xn.demo.functions;

/**
 * 构建工厂需要的参数Bean
 *
 * @author Janle
 * @date 2018/6/14 10:10
 */
public interface FactoryBean {

    /**
     * 获得商家的ID
     * @return
     */
    Long getVenderId();
}
