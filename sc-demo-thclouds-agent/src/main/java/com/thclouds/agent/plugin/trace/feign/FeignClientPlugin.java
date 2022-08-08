package com.thclouds.agent.plugin.trace.feign;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class FeignClientPlugin implements IPlugin {
    @Override
    public String name() {
        return "traceFeignPlugin";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.nameStartsWith("feign.Client$Default")
                                ;
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.nameStartsWith("execute"))
                                ;
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return FeignClientAdvice.class;
    }

}
