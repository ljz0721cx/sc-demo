package com.sc.ynk.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Janle qq:645905201
 * @date 2018/6/30 17:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
@ImportResource(value = "classpath:/spring-aop.xml")
@ComponentScan(value = "com.sc.ynk")
public class TestProxy {

    @Autowired
    private TestProxyInterface testProxyInterface;

    @Resource
    private TestCglibTarget testCglibTarget;

    @Test
    public void TestJdkProxy() {
        System.out.println("JDK 代理开始执行 start");
        testProxyInterface.process();
        System.out.println("JDK 代理开始执行 end");
    }


    @Test
    public void TestCglibProxy() {
        System.out.println("cglib 代理开始执行 start");
        testCglibTarget.process();
        System.out.println("cglib 代理开始执行 end");
    }


}
