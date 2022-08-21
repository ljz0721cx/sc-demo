package com.jd.demo.authorization;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.SecurityContextServerWebExchange;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ThcloudsAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        //转化为web的
        return ((SecurityContextServerWebExchange) authorizationContext.getExchange())
                .getDelegate()
                .getPrincipal()
                .map(v -> (UsernamePasswordAuthenticationToken) v)
                .map(v -> new AuthorizationDecision(true)).defaultIfEmpty(new AuthorizationDecision(false));
    }

}
