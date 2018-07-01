package com.sc.ynk.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Janle qq:645905201
 * @date 2018/6/30 17:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
@ImportResource(value = "classpath:/spring.xml")
@ComponentScan(value = "com.sc.ynk")
public class TestSpring {

    @Test
    public void TestLoad() {

    }
}
