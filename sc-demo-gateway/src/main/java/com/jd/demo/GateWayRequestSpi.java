package com.jd.demo;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author Janle on 2022/8/21
 */
public interface GateWayRequestSpi {
    public void clearContext();

    public String build(ServerWebExchange request) ;
}
