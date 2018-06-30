package com.sc.ynk.proxy;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/30 17:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(value = "spring-aop.xml")
public class TestProxy {
}
