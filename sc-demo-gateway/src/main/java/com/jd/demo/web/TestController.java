package com.jd.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
