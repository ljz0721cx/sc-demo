package com.sc.ynk.proxy;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 配置通知器，通知器的实现定义了需要对目标对象进行的增强行为
 *
 * @author Janle qq:645905201
 * @date 2018/7/1 10:52
 */
public class TestAdvisor implements MethodBeforeAdvice, AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行process之前先要准备一下");
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行process之前先要处理一下");
    }
}
