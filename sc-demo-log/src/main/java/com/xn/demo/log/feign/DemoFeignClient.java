package com.xn.demo.log.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sc-demo-feign-provider",url ="http://127.0.0.1:9001" )
public interface DemoFeignClient {

    @RequestMapping(value = "/demo/test5", method = RequestMethod.GET)
    public String test5();

}