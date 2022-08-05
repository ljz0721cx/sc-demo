package com.thclouds.agent.plugin.jdk.threading;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author lixh
 */
public class ThreadingConstructorPlugin implements IPlugin {

    @Override
    public String name() {
        return "ThreadingConstructorPlugin";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return   ElementMatchers.hasGenericSuperType
                                (ElementMatchers.named("java.util.concurrent.Callable")).and(ElementMatchers.nameStartsWith("com.etms"));
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isConstructor();
                    }
                },
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {

                        return   ElementMatchers.hasGenericSuperType(ElementMatchers.named("java.lang.Runnable")).and(ElementMatchers.nameStartsWith("com.etms"));
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isConstructor();
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return ThreadingConstructorAdvice.class;
    }
}
