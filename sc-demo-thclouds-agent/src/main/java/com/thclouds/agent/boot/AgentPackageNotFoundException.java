package com.thclouds.agent.boot;


/**
 * @author lixh
 * @description agent包安装路径异常
 */
public class AgentPackageNotFoundException extends Exception {
    public AgentPackageNotFoundException(String message) {
        super(message);
    }
}