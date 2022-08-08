package com.thclouds.agent.plugin.sentinel.feign;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.thclouds.agent.context.EntryContext;
import com.thclouds.agent.context.EntryHolder;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.plugin.sentinel.spring.mvc.ControllerAdvice;
import feign.Request;
import net.bytebuddy.asm.Advice;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FeignAdvice {

    public static ILog LOGGER = LogManager.getLogger(ControllerAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info(Thread.currentThread().getId() + "  className：" + className + " methodName: " + methodName);

        Request request = (Request) allArguments[0];
        Request.Options options = (Request.Options) allArguments[1];
        URI asUri = URI.create(request.url());
        String resourceName = methodName + ":" + asUri.getPath();
        System.out.println(Thread.currentThread().getId() + "  参数：" + request.body() + "   " + request.url() + "   " + asUri.getHost() + "    " + asUri.getPath());

        Entry entry = null;
        try {
            //TODO 根据资源的来源判断
            // 被保护的逻辑
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
        EntryHolder entryHolder = EntryContext.getEntryHolder();

        if (null != entryHolder) {
            entryHolder.getEntry().exit();
            LOGGER.info("{} {} exit",className,methodName);
        }

    }


}
