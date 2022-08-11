package com.thclouds.agent.plugin.sentinel.spring.gateway;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author lixh
 * @description gateway NettyRoutingFilter拦截请求转发，限流加强
 */

public class NettyRoutingFilterlPlugin implements IPlugin {

    @Override
    public String name() {
        return "NettyRoutingFilterlPlugin";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.nameStartsWith("org.springframework.cloud.gateway.filter.NettyRoutingFilter");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.named("filter"))
                                ;
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return NettyRoutingFilterAdvice.class;
    }

}
