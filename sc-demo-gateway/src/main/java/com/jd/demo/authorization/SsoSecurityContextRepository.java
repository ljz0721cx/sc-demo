package com.jd.demo.authorization;

import com.jd.demo.RequestConstants;
import com.jd.demo.filter.ReactiveRequestContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * @author Janle on 2022/8/15
 */
@Component
public class SsoSecurityContextRepository implements ServerSecurityContextRepository {
    protected final Logger logger = LoggerFactory.getLogger(ReactiveRequestContextFilter.class);

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getPrincipal()
                .switchIfEmpty(getSecurityContextMono(serverWebExchange))
                .map(principal -> new SecurityContextImpl((Authentication) principal));
    }


    /**
     * 获取调用信息
     *
     * @param serverWebExchange
     * @return
     */
    private Mono<Authentication> getSecurityContextMono(ServerWebExchange serverWebExchange) {
        //从请求头获取令牌：authorization:[Bearer 4ca647ff-6255-482e-85d3-3a108702a211]
        String token = serverWebExchange.getRequest().getHeaders().getFirst("token");
        Authentication authentication = new UsernamePasswordAuthenticationToken(token, "password", new ArrayList());
        MDC.put(RequestConstants.TRACE_ID, serverWebExchange.getRequest().getHeaders().getFirst(RequestConstants.TRACE_ID));
        //logger.info("加载用户数据user{}", token);
        return Mono.just(authentication);
    }
}
