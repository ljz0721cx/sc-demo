package com.thclouds.agent.plugin.sentinel.feign;

import com.thclouds.agent.conf.Config;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.utils.SentinelAdviceUtil;
import feign.Request;
import net.bytebuddy.asm.Advice;

import java.net.URI;


/**
 * @author lixh
 */
public class LoadBalancerFeignClientAdvice {

    public static ILog LOGGER = LogManager.getLogger(LoadBalancerFeignClientAdvice.class);

    @Advice.OnMethodEnter()
    public static  void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.debug(Thread.currentThread().getId() + "  className：" + className + " methodName: " + methodName);
        Request request = (Request) allArguments[0];
        URI asUri = URI.create(request.url());
        String path = asUri.getPath();
        System.out.println(Thread.currentThread().getId() + "  参数：" + request.body() + "   " + request.url() + "   " + asUri.getHost() + "    " + asUri.getPath());
        LOGGER.debug("resourceName: {} {}", path, Config.Agent.SERVICE_NAME);
        SentinelAdviceUtil.enter(path, LOGGER);
    }


    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        SentinelAdviceUtil.exit(className, methodName, e, LOGGER);
    }


}
