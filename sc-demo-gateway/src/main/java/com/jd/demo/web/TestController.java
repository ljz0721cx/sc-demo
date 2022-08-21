package com.jd.demo.web;

import com.jd.demo.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Janle on 2022/3/31
 */
@RestController
public class TestController {
    /**
     * @return
     */
    @GetMapping("")
    public String index() {
        return "match_path";
    }

    /**
     * @return
     */
    @GetMapping("testPath")
    public String testPath() {
        return "match_path";
    }


    /**
     * 测试token串的问题
     *
     * @param token
     * @return
     */
    @GetMapping("auth/{token}")
    public String auth2(@PathVariable("token") String token) throws InterruptedException {
        String token1 = ((Map<String, String>) T.t.get()).get("token");
        if (!token1.equals(token)) {
            System.out.println("======================" + Thread.currentThread().getName() + token1 + "!=" + token);
        }
        if (token.endsWith("2") ) {
            Thread.sleep(1000000);
        }
        return "match_path";
    }


}
