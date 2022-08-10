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
    public String index1() {

      return   demoFeignClient.test1();
    }

    @GetMapping("/feign/demo/test2")
    public String index2() {
        return   demoFeignClient.test2();
    }

    @GetMapping("/feign/demo/test3")
    public String index3() {

        return   demoFeignClient.test3();
    }

    @GetMapping("/feign/demo/test4")
    public String index4() {

        return   demoFeignClient.test4();
    }

    /**
     * @return
     */
    @GetMapping("/demo/test5")
    public String test5() {
        return "match_path";
    }
}
