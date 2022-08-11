package com.thclouds.agent.utils;

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
import com.thclouds.agent.context.AgentContext;
import com.thclouds.agent.context.EntryHolder;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.commons.base.exceptions.CheckedException;

/**
 * @desciption 限流相关代码工具
 * @author lixh
 */
public class SentinelAdviceUtil {


    public static void enter(String path, ILog LOGGER) {
        Entry entry = null;
        try {
            ContextUtil.enter(Config.Agent.SERVICE_NAME);
            entry = SphU.entry(path, EntryType.OUT);
        } catch (FlowException e) {
            LOGGER.debug("限流 {}", e);
            throw new CheckedException("系统限流了，请稍后再试!");
        } catch (DegradeException e) {
            LOGGER.debug("降级 {}", e);
            throw new CheckedException("接口降级了，请稍后再试!");
        } catch (SystemBlockException e) {
            LOGGER.debug("系统规则(负载/...不满足要求) {}", e);
            throw new CheckedException("系统规则(负载/...不满足要求)");
        } catch (AuthorityException e) {
            LOGGER.debug("授权规则不通过 {}", e);
            throw new CheckedException("授权规则不通过");
        } catch (Exception e) {
            LOGGER.debug("未知异常 {}", e);
            throw new CheckedException("系统限流了，请稍后再试!");
        }
        AgentContext.putEntryHolder(new EntryHolder(entry, null));
    }

    public static void exit(String className, String methodName, Throwable e, ILog LOGGER) {
        if (e != null && !BlockException.isBlockException(e)) {
            Tracer.trace(e);
            LOGGER.error(e, "{} {} exit", className, methodName);
        }

        EntryHolder entryHolder = AgentContext.getEntryHolder();
        if (null != entryHolder) {
            entryHolder.getEntry().exit();
            LOGGER.debug("{} {} exit", className, methodName);
        }
    }
}
