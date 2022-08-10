package com.thclouds.agent.plugin.sentinel.spring.gateway;

import com.thclouds.agent.conf.Config;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.utils.SentinelAdviceUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

public class NettyRoutingFilterAdvice {

    public static ILog LOGGER = LogManager.getLogger(NettyRoutingFilterAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        //获取到方法上的路径
        ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
        URI requestUrl = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String scheme = requestUrl.getScheme();
        if (!ServerWebExchangeUtils.isAlreadyRouted(exchange) && ("http".equals(scheme) || "https".equals(scheme))) {
            ServerWebExchangeUtils.setAlreadyRouted(exchange);
            String path = exchange.getRequest().getURI().getPath();
            LOGGER.info("resourceName: {} {}", path, Config.Agent.SERVICE_NAME);
            SentinelAdviceUtil.enter(path,LOGGER);
        }
    }




    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        SentinelAdviceUtil.exit(className, methodName, e,LOGGER);
    }



}
