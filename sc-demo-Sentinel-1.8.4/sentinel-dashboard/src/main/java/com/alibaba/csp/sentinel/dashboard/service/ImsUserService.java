package com.alibaba.csp.sentinel.dashboard.service;

import com.alibaba.csp.sentinel.dashboard.support.ImsProperties;
import com.alibaba.csp.sentinel.dashboard.support.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
/**
 * @author lime
 */
@Service
@Scope("singleton")
@EnableConfigurationProperties(ImsProperties.class)
public class ImsUserService {
    private static final String DEFAULT_MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private volatile JdbcTemplate template = null;
    private volatile TransactionTemplate transactionTemplate = null;
    @Autowired
    private ImsProperties imsProperties;
    @PostConstruct
    private void init() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DEFAULT_MYSQL_DRIVER);
        dataSource.setUrl(imsProperties.getJdbcUrl());
        dataSource.setUsername(imsProperties.getUsername());
        dataSource.setPassword(imsProperties.getPassword());
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(2);
        dataSource.setMaxIdle(5);
        dataSource.setMaxWait(6000L);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(10L));
        dataSource.setTestWhileIdle(true);
        template = new JdbcTemplate();
        template.setMaxRows(50000);
        template.setQueryTimeout(5000);
        template.setDataSource(dataSource);
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        transactionTemplate = new TransactionTemplate(tm);
        tm.setDataSource(dataSource);
        transactionTemplate.setTimeout(5000);
    }
    /**
     * 通过用户名
     *
     * @param userName
     * @return
     */
    public User findUserByUsername(String userName) {
        final String queryUserSql = "select user_id as userId ,username as username" +
                ", concat(salt,'@',password) as password  from  sys_user where username = ?";
        return template.queryForObject(queryUserSql, new Object[]{userName}, new BeanPropertyRowMapper<>(User.class));
    }
}