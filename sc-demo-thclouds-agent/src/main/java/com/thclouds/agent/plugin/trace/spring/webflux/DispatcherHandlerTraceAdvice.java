package com.thclouds.agent.plugin.trace.spring.webflux;

import com.alibaba.fastjson.JSON;
import com.thclouds.agent.context.AgentContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.commons.base.log.appender.LoggerBuilder;
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
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e, @Advice.Return Object result) {
        if (e != null ) {
            LoggerBuilder.busLogger().error("trance: {},异常信息{}", AgentContext.getTranceId(), e);
        } else {
            LoggerBuilder.busLogger().info("trance: {},请求返回数据result{}",AgentContext.getTranceId(), JSON.toJSONString(result));
        }
    }

}
