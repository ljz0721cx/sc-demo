package com.thclouds.agent.plugin.trace.spring.webflux;

import com.thclouds.agent.context.AgentContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import net.bytebuddy.asm.Advice;
import org.springframework.web.server.ServerWebExchange;

public class DispatcherHandlerTraceAdvice {

    public static ILog LOGGER = LogManager.getLogger(DispatcherHandlerTraceAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.debug("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
        String traceId = exchange.getRequest().getHeaders().getFirst("traceId");
        LOGGER.debug("traceId:{}", traceId);
        AgentContext.setTranceId(traceId);
    }


    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        LOGGER.debug("error {}", e);
        LOGGER.debug("tranceId {}", AgentContext.getTranceId());
    }

}
