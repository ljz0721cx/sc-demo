package com.xn.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/30 21:07
 */
@Configurable
public class DBConfig {

    @Autowired
    private Environment env;

    /*@Bean(name = "dataSource")
    public ComboPooledDataSource dataSource(){

    }*/

}
