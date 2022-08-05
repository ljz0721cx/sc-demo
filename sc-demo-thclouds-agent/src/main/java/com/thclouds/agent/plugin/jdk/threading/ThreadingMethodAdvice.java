package com.thclouds.agent.plugin.jdk.threading;

import com.thclouds.agent.context.ServerWebExchangeContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

/**
 * @author lixh
 */
public class ThreadingMethodAdvice {

    public static ILog LOGGER = LogManager.getLogger(ThreadingMethodAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info("className {} in {} tranceId {}",className,methodName,ServerWebExchangeContext.getTranceId());
//        System.out.println("in classNmme:"+className+"tranceId {}"+ServerWebExchangeContext.getTranceId());
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object returned) {

        LOGGER.info("className {} exit {} tranceId {}",className,methodName,ServerWebExchangeContext.getTranceId());
//                System.out.println("out classNmme:"+className+"tranceId {}"+ServerWebExchangeContext.getTranceId());
    }
}
