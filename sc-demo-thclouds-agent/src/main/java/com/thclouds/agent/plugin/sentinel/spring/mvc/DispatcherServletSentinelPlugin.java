package com.thclouds.agent.plugin.sentinel.spring.mvc;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;


/**
 * @author lixh
 * @description mvc DispatcherServlet拦截，限流加强
 */
public class DispatcherServletSentinelPlugin implements IPlugin {

    @Override
    public String name() {
        return "sentinelDispatcherServletPlugin";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.nameStartsWith("org.springframework.web.servlet.DispatcherServlet");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.named("doService"))
                                ;
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return DispatcherServletSentinelAdvice.class;
    }

}
