package com.sc.ynk.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * @author lijizhen1@jd.com
 * @date 2018/7/1 16:49
 */
public class SpringLoad implements InitializingBean, BeanPostProcessor, BeanNameAware, ApplicationContextAware {


    public SpringLoad() {
        System.out.println("1 constrct SpringLoad");
    }

    @PostConstruct
    public void set() {
        System.out.println("3 beanname生成后构建整合上下文的bean");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("4 加载上下文的动态参数");
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessBeforeInitialization");
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessAfterInitialization");
        return o;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("2 生成设置BeanName");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("3 将加载的所有参数放入上下文环境");
    }


    public void init() {
        System.out.println("5 上下文生成后初始化bean中的参数");
    }
}
