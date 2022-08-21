package com.jd.demo.filter;

import com.jd.demo.GateWayRequestSpi;
import com.jd.demo.RequestConstants;
import com.jd.demo.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Janle on 2022/8/18
 */
public class ReactiveRequestContextFilter implements WebFilter {
    protected final Logger logger = LoggerFactory.getLogger(ReactiveRequestContextFilter.class);
    /**
     * 对应请求上下文的spi
     */
    protected GateWayRequestSpi gateWayRequestSpi;

    public ReactiveRequestContextFilter(GateWayRequestSpi gateWayRequestSpi) {
        this.gateWayRequestSpi = gateWayRequestSpi;
    }

    /**
     * @param exchange
     * @param filterChain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain filterChain) {
        preHandle(exchange);
        return filterChain.filter(exchange)
                .doFinally(signalType -> afterCompletion(exchange, signalType));
    }

    /**
     * 回收内存
     *
     * @param request
     */
    private void afterCompletion(ServerWebExchange exchange, SignalType request) {
        //回收线程
        MDC.clear();
        T.t.remove();
        if (null != gateWayRequestSpi) {
            gateWayRequestSpi.clearContext();
        }
    }

    /**
     * 前置处理
     *
     * @param request
     */
    protected void preHandle(ServerWebExchange request) {
        //获取request中的header信息
        String traceId = request.getRequest().getHeaders().getFirst(RequestConstants.TRACE_ID);
        String token = gateWayRequestSpi.build(request);
        Map<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("token", token);
        T.t.set(stringHashMap);
        //logger.info("绑定用户上下文信息{},token信息{}", traceId, token);
        if (logger.isDebugEnabled()) {
            logger.debug("获取的上下文调用上下文信息:{}", token);
        }
    }

}
