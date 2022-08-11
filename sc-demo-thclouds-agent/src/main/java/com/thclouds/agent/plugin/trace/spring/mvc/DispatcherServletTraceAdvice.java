package com.thclouds.agent.plugin.trace.spring.mvc;

import com.thclouds.agent.context.AgentContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import net.bytebuddy.asm.Advice;

import javax.servlet.http.HttpServletRequest;

public class DispatcherServletTraceAdvice {

    public static ILog LOGGER = LogManager.getLogger(DispatcherServletTraceAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.debug("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        HttpServletRequest request = (HttpServletRequest) allArguments[0];
        String traceId = request.getHeader("traceId");
        AgentContext.setTranceId(traceId);

    }


    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        LOGGER.debug("error {}", e);
        LOGGER.debug("tranceId {}", AgentContext.getTranceId());
    }

}
