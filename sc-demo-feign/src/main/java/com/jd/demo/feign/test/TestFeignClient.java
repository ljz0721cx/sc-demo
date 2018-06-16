package com.jd.demo.feign.test;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/5 17:04
 */
@FeignClient(name = "sc-demo-feign-provider")
public interface TestFeignClient {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable("id") Long id);

}
