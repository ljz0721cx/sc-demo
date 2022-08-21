package com.jd.demo.config;

import com.jd.demo.GateWayRequestSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Objects;

/**
 * @author Janle on 2022/8/21
 */
@Service
public class GateWayRequestSpiImpl implements GateWayRequestSpi {
    protected final Logger logger = LoggerFactory.getLogger(GateWayRequestSpiImpl.class);

    @Override
    public void clearContext() {

    }

    @Override
    public String build(ServerWebExchange request) {
        try {
            Mono<Principal> principal = request.getPrincipal();
            return principal.map(principal1 -> {
                if (Objects.isNull(principal1)) {
                    //放行接口，无用户信息
                    return null;
                }
                String userMap = (String) ((UsernamePasswordAuthenticationToken) principal1).getPrincipal();

                return userMap;
            }).toProcessor().block();
        } catch (Exception e) {
            logger.error("系统未知错误，检查是否正常登录e{}{}", e.getMessage(), e);
            throw new RuntimeException("检查是否正常登录");
        }
    }
}
