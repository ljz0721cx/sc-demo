package com.thclouds.agent.plugin.mapping.conflicts;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author lixh
 * @description 地址冲突检测
 */
public class HandlerResultPlugin implements IPlugin {
    @Override
    public String name() {
        return "viewResultHandlerPlugin";
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
                                .and(ElementMatchers.named(HandlerResultConstants.HANDLE_RESULT))
                                .or(ElementMatchers.named(HandlerResultConstants.GET_RESULT_HANDLER))
                                ;
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return HandlerResultAdvice.class;
    }

}
