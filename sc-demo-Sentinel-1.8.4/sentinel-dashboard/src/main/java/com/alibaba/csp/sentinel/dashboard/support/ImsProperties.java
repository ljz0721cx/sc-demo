package com.alibaba.csp.sentinel.dashboard.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @author lime
 * @since 2019/1217
 */
@ConfigurationProperties(prefix = "ims.datasource")
public class ImsProperties {
    /**
     * 数据库连接地址
     */
    private String jdbcUrl;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    public ImsProperties() {
    }
    public String getJdbcUrl() {
        return jdbcUrl;
    }
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}