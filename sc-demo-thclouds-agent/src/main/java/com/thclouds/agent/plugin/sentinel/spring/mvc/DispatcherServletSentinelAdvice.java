package com.thclouds.agent.plugin.sentinel.spring.mvc;


import com.thclouds.agent.conf.Config;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.utils.SentinelAdviceUtil;
import net.bytebuddy.asm.Advice;

import javax.servlet.http.HttpServletRequest;

public class DispatcherServletSentinelAdvice {

    public static ILog LOGGER = LogManager.getLogger(DispatcherServletSentinelAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        //获取到方法上的路径
        HttpServletRequest request = (HttpServletRequest) allArguments[0];
        String path = request.getRequestURI();
        LOGGER.info("resourceName: {} {}", path, Config.Agent.SERVICE_NAME);
        SentinelAdviceUtil.enter(path,LOGGER);
    }


    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        SentinelAdviceUtil.exit(className, methodName, e,LOGGER);
    }

}
