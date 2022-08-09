package com.jd.demo.web;

import com.jd.demo.feign.DemoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janle on 2022/3/31
 */
@RestController
public class TestController {

    @Autowired
    DemoFeignClient demoFeignClient;
    /**
     * @return
     */
    @GetMapping("/feign/demo/test1")
    public String index() {
      return   demoFeignClient.test1();
    }

    /**
     * @return
     */
    @GetMapping("testPath")
    public String testPath() {
        return "match_path";
    }
}
