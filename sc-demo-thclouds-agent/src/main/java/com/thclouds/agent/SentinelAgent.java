package com.thclouds.agent;


import com.thclouds.agent.conf.Config;
import com.thclouds.agent.conf.SnifferConfigInitializer;
import com.thclouds.agent.intercept.TimeInterceptor;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InstrumentDebuggingClass;
import com.thclouds.agent.plugin.InterceptPoint;
import com.thclouds.agent.plugin.PluginFactory;
import com.thclouds.agent.plugin.jdk.threading.EnhancedInstance;
import com.thclouds.agent.plugin.mapping.conflicts.impl.HandlerResultConstants;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.Collection;

import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

public class SentinelAgent {
    private static ILog LOGGER = LogManager.getLogger(SentinelAgent.class);
    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        LOGGER.info("============================agnent 开启========================== == ==\r\n");
        //1、添加配置文件和参数解析
        SnifferConfigInitializer.initializeCoreConfig(agentArgs);
        //2、加载插件  TODO 根据配置加载插件
        Collection<IPlugin> pluginGroup = PluginFactory.pluginGroup;
        //3、字节码插装  TODO 看看是不是可以通过环绕加强
        final ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.of(Config.Agent.IS_OPEN_DEBUGGING_CLASS));
        AgentBuilder agentBuilder = new AgentBuilder.Default(byteBuddy).ignore(
                nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("org.slf4j."))
                        .or(nameStartsWith("org.groovy."))
                        .or(nameContains("javassist"))
                        .or(nameContains(".asm."))
                        .or(nameContains(".reflectasm."))
                        .or(nameStartsWith("sun.reflect"))
                        .or(ElementMatchers.isSynthetic()));

        for (IPlugin plugin : pluginGroup) {
            InterceptPoint[] interceptPoints = plugin.buildInterceptPoint();
            for (InterceptPoint point : interceptPoints) {
                AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
                        builder = builder.visit(Advice.to(plugin.adviceClass()).on(point.buildMethodsMatcher()));
                        return builder;
                    };
                agentBuilder = agentBuilder.type(point.buildTypesMatcher()).transform(transformer);
            }
        }

        //监听
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule javaModule, boolean loaded) {
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                if (LOGGER.isDebugEnable()) {
                    LOGGER.debug("On Transformation class {}.", typeDescription.getName());
                }
                InstrumentDebuggingClass.INSTANCE.log(dynamicType);
            }

            @Override
            public void onIgnored(TypeDescription typeDescription,
                                  ClassLoader classLoader, JavaModule javaModule, boolean loaded) {
            }

            @Override
            public void onError(String typeName, ClassLoader classLoader,
                                JavaModule javaModule, boolean b, Throwable throwable) {
                LOGGER.info("{} {} {}",classLoader,javaModule,b);
                LOGGER.error("Enhance class " + typeName + " error.", throwable);
            }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader,
                                   JavaModule javaModule, boolean loaded) {
            }
        };

        agentBuilder.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(listener).installOn(inst);

        //启动监控服务TODO ，后续需要
        //停机回调

    }





}

