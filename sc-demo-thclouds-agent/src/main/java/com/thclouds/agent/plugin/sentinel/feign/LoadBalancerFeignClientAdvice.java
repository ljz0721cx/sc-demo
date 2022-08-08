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
import feign.Request;
import net.bytebuddy.asm.Advice;

import java.net.URI;


public class LoadBalancerFeignClientAdvice {

    public static ILog LOGGER = LogManager.getLogger(LoadBalancerFeignClientAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info(Thread.currentThread().getId() + "  className：" + className + " methodName: " + methodName);

        Request request = (Request) allArguments[0];
        URI asUri = URI.create(request.url());
        String resourceName = asUri.getPath();
        System.out.println(Thread.currentThread().getId() + "  参数：" + request.body() + "   " + request.url() + "   " + asUri.getHost() + "    " + asUri.getPath());
        LOGGER.info("resourceName: {} {}",resourceName,Config.Agent.SERVICE_NAME);
        Entry entry = null;
        try {
            // 被保护的逻辑
            ContextUtil.enter(Config.Agent.SERVICE_NAME);
            entry = SphU.entry(resourceName, EntryType.OUT);
            //系统规则只对 IN 生效
//            entry = SphU.entry("methodA", EntryType.IN);
            System.out.println(Thread.currentThread().getId() + "  enter");
        } catch (FlowException e) {
            LOGGER.info("限流 {}",e);
            throw new RuntimeException("BlockException 系统限流了，请稍后再试!");
        } catch (DegradeException e) {
            LOGGER.info("降级 {}",e);
            throw new RuntimeException("BlockException 接口降级了，请稍后再试!");
        } catch (SystemBlockException e) {
            LOGGER.info("系统规则(负载/...不满足要求) {}",e);
            throw new RuntimeException("BlockException 系统规则(负载/...不满足要求)");
        } catch (AuthorityException e) {
            LOGGER.info("授权规则不通过 {}",e);
            throw new RuntimeException("BlockException 授权规则不通过");
        } catch (Exception e) {
            LOGGER.error("位置异常 {}",e);
            throw new RuntimeException("BlockException");
        }
        EntryContext.putEntryHolder(new EntryHolder(entry, null));
    }


    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {

        if (e != null && !BlockException.isBlockException(e)) {
            Tracer.trace(e);
            LOGGER.error(e,"{} {} exit",className,methodName);
        }
        LOGGER.warn("error {}",e);
        EntryHolder entryHolder = EntryContext.getEntryHolder();

        if (null != entryHolder) {
            entryHolder.getEntry().exit();
            LOGGER.info("{} {} exit",className,methodName);
        }

    }


}
