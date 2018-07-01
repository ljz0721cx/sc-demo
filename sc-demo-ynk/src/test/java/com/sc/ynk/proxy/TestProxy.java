package com.sc.ynk.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;
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
public class TestProxy {

    @Resource
    private TestProxyInterface testProxyInterface;

    @Resource
    private TestCglibTarget testCglibTarget;

    @Test
    public void TestJdkProxy() {
        testProxyInterface.process();
    }


    @Test
    public void TestCglibProxy() {
        testCglibTarget.process();
    }


}
