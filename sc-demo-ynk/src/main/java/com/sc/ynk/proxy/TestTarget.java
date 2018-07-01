package com.sc.ynk.proxy;

import org.springframework.stereotype.Service;

/**
 * 需要使用AOP切面增强的对象
 *
 * @author Janle qq:645905201
 * @date 2018/7/1 10:52
 */
@Service
public class TestTarget implements TestProxyInterface {

    /**
     * 执行调用的process
     */
    @Override
    public void process() {
        System.out.println("被代理的对象执行process");

        //不能找到被代理的类
        privateProcess();
        //不能找到被代理的类
        publicProcess();
    }


    private void privateProcess(){
        System.out.println("被代理的对象执行,事物使用时候要注意 privateProcess");
    }

    public void publicProcess(){
        System.out.println("被代理的对象执行,事物使用时候要注意 publicProcess");
    }
}
