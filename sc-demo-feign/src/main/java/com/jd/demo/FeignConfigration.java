package com.jd.demo;

import feign.Contract;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * 该类为Feign类的配置类：不应该在主应用程序上下文的#ComponentScan
 *
 * @author Janle qq:645905201
 * @date 2018/6/5 20:16
 */
@Configurable
public class FeignConfigration {
    @Bean
    public Contract feignContract() {
        //return new feign.Contract().Default();
        return null;
    }
}
