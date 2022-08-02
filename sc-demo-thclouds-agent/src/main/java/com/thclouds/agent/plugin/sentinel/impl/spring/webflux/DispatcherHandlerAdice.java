package com.thclouds.agent.plugin.sentinel.impl.spring.webflux;

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
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import feign.Request;
import net.bytebuddy.asm.Advice;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import javax.management.monitor.MonitorNotification;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class DispatcherHandlerAdice {

    public static ILog LOGGER = LogManager.getLogger(DispatcherHandlerAdice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        //获取到方法上的路径
        ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
        String path = exchange.getRequest().getURI().getPath();
        LOGGER.info("resourceName:{}",path);
        Entry entry = null;
        try {
            entry = SphU.entry(path, EntryType.IN);
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
