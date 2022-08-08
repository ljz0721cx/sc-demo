package com.thclouds.agent.plugin.trace.spring.webflux;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.thclouds.agent.context.EntryContext;
import com.thclouds.agent.context.EntryHolder;
import com.thclouds.agent.context.ServerWebExchangeContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import net.bytebuddy.asm.Advice;
import org.springframework.web.server.ServerWebExchange;

public class DispatcherHandlerAdvice {

    public static ILog LOGGER = LogManager.getLogger(DispatcherHandlerAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
        String traceId = exchange.getRequest().getHeaders().getFirst("traceId");
        LOGGER.info("traceId:{}",traceId);
        ServerWebExchangeContext.setTranceId(traceId);
    }



    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        LOGGER.info("tranceId {}" ,ServerWebExchangeContext.getTranceId());
    }

}
