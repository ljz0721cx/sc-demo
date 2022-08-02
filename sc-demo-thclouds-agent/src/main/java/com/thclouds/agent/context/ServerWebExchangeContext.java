package com.thclouds.agent.context;

import org.springframework.web.server.ServerWebExchange;

public class ServerWebExchangeContext {

    private static final ThreadLocal<ServerWebExchange> exchangeThreadLocal = new ThreadLocal<ServerWebExchange>();

    public static ServerWebExchange getExchange() {
        ServerWebExchange exchange = exchangeThreadLocal.get();
        return exchange;
    }

    public static void setExchange(ServerWebExchange exchange) {
        exchangeThreadLocal.set(exchange);
    }

    public static void removeExchange() {
       exchangeThreadLocal.remove();
    }
}
