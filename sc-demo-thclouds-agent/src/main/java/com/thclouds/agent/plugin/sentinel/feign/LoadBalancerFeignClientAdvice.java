package com.thclouds.agent.plugin.sentinel.feign;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.thclouds.agent.conf.Config;
import com.thclouds.agent.context.EntryContext;
import com.thclouds.agent.context.EntryHolder;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.utils.SentinelAdviceUtil;
import com.thclouds.commons.base.exceptions.CheckedException;
import feign.Request;
import net.bytebuddy.asm.Advice;

import java.net.URI;


public class LoadBalancerFeignClientAdvice {

    public static ILog LOGGER = LogManager.getLogger(LoadBalancerFeignClientAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
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
