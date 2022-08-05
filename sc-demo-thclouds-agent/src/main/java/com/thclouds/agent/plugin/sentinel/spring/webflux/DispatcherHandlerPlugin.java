package com.thclouds.agent.plugin.sentinel.spring.webflux;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class DispatcherHandlerPlugin implements IPlugin {

    @Override
    public String name() {
        return "DispatcherHandlerPlugin";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.nameStartsWith("org.springframework.web.reactive.DispatcherHandler");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.named("handle"))
                                ;
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return DispatcherHandlerAdvice.class;
    }

}
