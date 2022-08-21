package com.jd.demo.config;

import com.jd.demo.GateWayRequestSpi;
import com.jd.demo.authorization.OAuth2Filter;
import com.jd.demo.authorization.SsoSecurityContextRepository;
import com.jd.demo.filter.ReactiveRequestContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.reactive.config.WebFluxConfigurer;


/**
 * 核心配置类 @EnableWebFluxSecurity
 */
@EnableWebFluxSecurity
public class OAuth2Config implements WebFluxConfigurer {


    /**
     * 注入ThcloudsAuthorizationManager
     */
    @Autowired
    private SsoSecurityContextRepository ssoSecurityContextRepository;
    @Autowired
    private ReactiveAuthorizationManager thcloudsAuthorizationManager;
    @Autowired
    private GateWayRequestSpi gateWayRequestSpi;


    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {

        http.requestCache().requestCache(NoOpServerRequestCache.getInstance())
                .and().headers().frameOptions().disable()
                .and().csrf().disable()
                //绑定授权信息，配置 认证管理器
                .addFilterAt(oAuth2Filter(), SecurityWebFiltersOrder.HTTP_BASIC)
                //绑定用户信息
                .addFilterAt(new ReactiveRequestContextFilter(gateWayRequestSpi), SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers("/**").access(thcloudsAuthorizationManager)
                .anyExchange().authenticated();
        return http.build();
    }

    /**
     * @return
     */
    public OAuth2Filter oAuth2Filter() {
        return new OAuth2Filter(ssoSecurityContextRepository);
    }
}
