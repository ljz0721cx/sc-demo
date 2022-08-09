package com.thclouds.agent.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.web.server.ServerWebExchange;

public class ServerWebExchangeContext {

    private static final TransmittableThreadLocal<ServerWebExchange> exchangeThreadLocal = new TransmittableThreadLocal<ServerWebExchange>();
    private static final TransmittableThreadLocal<String> tranceThreadLocal = new TransmittableThreadLocal();

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

    public static String getTranceId() {
        return tranceThreadLocal.get();
    }

    public static void setTranceId(String exchange) {
        tranceThreadLocal.set(exchange);
    }

    public static void removeTranceId() {
        tranceThreadLocal.remove();
    }
}
