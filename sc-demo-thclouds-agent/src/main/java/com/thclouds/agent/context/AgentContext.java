package com.thclouds.agent.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author lixh
 * @description agent的上下文
 */
public class AgentContext {

    private static final TransmittableThreadLocal<ServerWebExchange> exchangeThreadLocal = new TransmittableThreadLocal<ServerWebExchange>();

    private static final TransmittableThreadLocal<String> tranceThreadLocal = new TransmittableThreadLocal();

    private static final TransmittableThreadLocal<EntryHolder> holderThreadLocal = new TransmittableThreadLocal<EntryHolder>();

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



    public static EntryHolder getEntryHolder() {
        EntryHolder entryHolder = holderThreadLocal.get();
        holderThreadLocal.remove();
        return entryHolder;
    }

    public static void putEntryHolder(EntryHolder entryHolder) {
        holderThreadLocal.set(entryHolder);
    }
}
