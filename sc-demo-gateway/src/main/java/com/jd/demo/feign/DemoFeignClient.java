package com.jd.demo.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sc-demo-feign-provider",url ="http://127.0.0.1:9000" )
public interface DemoFeignClient {

    @RequestMapping(value = "/demo/test1", method = RequestMethod.GET)
    public String test1() ;

    @RequestMapping(value = "/demo/test2", method = RequestMethod.GET)
    public String test2() ;

    @RequestMapping(value = "/demo/test3", method = RequestMethod.GET)
    public String test3() ;

    @RequestMapping(value = "/demo/test4", method = RequestMethod.GET)
    public String test4() ;
}