package com.thclouds.agent.plugin.mapping.conflicts;

import com.thclouds.agent.context.ServerWebExchangeContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import net.bytebuddy.asm.Advice;
import org.springframework.web.reactive.result.view.ViewResolutionResultHandler;
import org.springframework.web.server.ServerWebExchange;


public class HandlerResultAdvice {

    public static ILog LOGGER = LogManager.getLogger(HandlerResultAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        if (HandlerResultConstants.HANDLE_RESULT.equals(methodName)) {
            ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
            ServerWebExchangeContext.setExchange(exchange);
        }
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Return Object result, @Advice.Thrown Throwable e) {

        if (HandlerResultConstants.GET_RESULT_HANDLER.equals(methodName) &&
                (null != e || result instanceof ViewResolutionResultHandler)) {
            ServerWebExchange exchange = ServerWebExchangeContext.getExchange();
            String path = exchange.getRequest().getURI().getPath();
            throw new RuntimeException(path + " 地址可能有冲突", e);
        }
        ServerWebExchangeContext.removeExchange();
    }

}
