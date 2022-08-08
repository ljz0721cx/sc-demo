package com.thclouds.agent.plugin.jdk.threading;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.matcher.ElementMatchers.isInterface;

/**
 * @author lixh
 */
public class ThreadingMethodPlugin implements IPlugin {

    @Override
    public String name() {
        return "ThreadingMethodPlugin";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
//                        return   ElementMatchers.hasGenericSuperType(ElementMatchers.named("java.util.concurrent.Callable")).and(ElementMatchers.nameStartsWith("com.thclouds"));
                        return hasSuperType(named("java.util.concurrent.Callable")).and(not(isInterface()))
                                .and(not(nameStartsWith("com.thclouds.agent")))
                                .and(not(nameStartsWith("com.alibaba.nacos")))
                                .and(not(nameStartsWith("java.util.concurrent")))
                                .and(not(nameStartsWith("sun")))
                                .and(not(nameStartsWith("sun.misc.Cleaner")))

                                .and(not(nameStartsWith("sun.net.www.http.KeepAliveCache")))
                                .and(not(nameStartsWith("java.lang.Thread")))
                                .and(not(nameStartsWith("java.util.TimerThread")))
                                .and(not(nameStartsWith("com.alibaba.csp.sentinel")))
                                .and(not(nameStartsWith("reactor.core")))
                                .and(not(nameStartsWith("io.nett")))
                                .and(not(nameStartsWith("org.springframework.cglib.core.internal.LoadingCache")))
                                .and(not(nameStartsWith("nz.net.ultraq.thymeleaf")))
                                .and(not(nameStartsWith("java.nio.DirectByteBuffer")))
                                .and(not(nameStartsWith("groovy.lang.Closure")))

                                ;
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.named("call");
                    }
                },
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return hasSuperType(named("java.lang.Runnable")).and(not(isInterface()))
                                .and(not(nameStartsWith("com.thclouds.agent")))
                                .and(not(nameStartsWith("com.alibaba.nacos")))
                                .and(not(nameStartsWith("java.util.concurrent")))
                                .and(not(nameStartsWith("sun")))
                                .and(not(nameStartsWith("sun.misc.Cleaner")))
                                .and(not(nameStartsWith("io.nett")))
                                .and(not(nameStartsWith("sun.net.www.http.KeepAliveCache")))
                                .and(not(nameStartsWith("java.lang.Thread")))
                                .and(not(nameStartsWith("java.util.TimerThread")))
                                .and(not(nameStartsWith("com.alibaba.csp.sentinel")))
                                .and(not(nameStartsWith("reactor.core")))
                                .and(not(nameStartsWith("org.springframework.cglib.core.internal.LoadingCache")))
                                .and(not(nameStartsWith("nz.net.ultraq.thymeleaf")))
                                .and(not(nameStartsWith("java.nio.DirectByteBuffer")))
                                .and(not(nameStartsWith("groovy.lang.Closure")))
                       ;
                 }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.named("run");
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return ThreadingMethodAdvice.class;
    }
}
