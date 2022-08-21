package com.jd.demo.authorization;

import com.jd.demo.RequestConstants;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Janle on 2022/8/17
 */
public class OAuth2Filter implements WebFilter {
    private SsoSecurityContextRepository ssoSecurityContextRepository;

    public OAuth2Filter(SsoSecurityContextRepository ssoSecurityContextRepository) {
        this.ssoSecurityContextRepository = ssoSecurityContextRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String traceId = exchange.getRequest().getHeaders().getFirst(RequestConstants.TRACE_ID);
        String traceId1 = null == traceId ? UUID.randomUUID().toString() : traceId;
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set(RequestConstants.TRACE_ID, traceId1);
        };
        MDC.put(RequestConstants.TRACE_ID, traceId1);
        ServerHttpRequest mutableReq = exchange.getRequest().mutate().headers(httpHeaders).build();
        exchange = exchange.mutate().request(mutableReq).build();
        Mono<SecurityContext> securityContextMono = ssoSecurityContextRepository.load(exchange);
        exchange = exchange.mutate().principal(securityContextMono.map(v -> v.getAuthentication())).build();
        return chain.filter(exchange);
    }
}
