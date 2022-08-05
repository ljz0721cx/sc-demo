package com.thclouds.agent.plugin.jdk.threading;

import com.thclouds.agent.context.ContextCarrier;
import com.thclouds.agent.context.ServerWebExchangeContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import net.bytebuddy.asm.Advice;

/**
 * @author lixh
 */
public class ThreadingConstructorAdvice {

    public static ILog LOGGER = LogManager.getLogger(ThreadingConstructorAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {

    }

}
