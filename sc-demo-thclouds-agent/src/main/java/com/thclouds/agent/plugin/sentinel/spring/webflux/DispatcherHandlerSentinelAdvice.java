package com.thclouds.agent.plugin.sentinel.spring.webflux;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.thclouds.agent.conf.Config;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.plugin.common.ServerWebExchangeItemParser;
import com.thclouds.agent.utils.SentinelAdviceUtil;
import com.thclouds.agent.utils.StringUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Objects;

public class DispatcherHandlerSentinelAdvice {

    public static ILog LOGGER = LogManager.getLogger(DispatcherHandlerSentinelAdvice.class);

    public static ServerWebExchangeItemParser parser = new ServerWebExchangeItemParser();

    public static String[] paramMappings = null;

    @Advice.OnMethodEnter()
    public static void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.debug("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        //获取到方法上的路径
        ServerWebExchange exchange = (ServerWebExchange) allArguments[0];
        String path = exchange.getRequest().getURI().getPath();


        Object[] params = null;
        if (ParamFlowRuleManager.hasRules(path)) {
            params = getParams(exchange, path);
        }
        LOGGER.debug("resourceName: {} {} {} ", path, Config.Agent.SERVICE_NAME,params);
        SentinelAdviceUtil.enter(path, LOGGER, params);
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        SentinelAdviceUtil.exit(className, methodName, e, LOGGER);
    }


    public static Object[] getParams(ServerWebExchange exchange, String path) {
        Object[] params;
        //1、初始化参数映射关系
        if (StringUtil.isNotEmpty(Config.Sentinel.PARAM_IDX_MAPPING) && Objects.isNull(paramMappings)) {
            initParamMapping(Config.Sentinel.PARAM_IDX_MAPPING);
        }
        //2、获取地址匹配到规则
        List<ParamFlowRule> rulesOfResource = ParamFlowRuleManager.getRulesOfResource(path);
        //3、构建参数数组
        Integer integer = rulesOfResource.stream().map(ParamFlowRule::getParamIdx).max((x1, x2) -> Integer.compare(x1.intValue(), x2.intValue())).get();
        params = new Object[integer.intValue()+1];
        //4、通过paramIdx 获取到配置的参数映射关系
        for (int i = 0; i < rulesOfResource.size(); i++) {
            ParamFlowRule paramFlowRule = rulesOfResource.get(i);
            int paramIdx = paramFlowRule.getParamIdx().intValue();
            String[] mapping = null;
            if (Objects.nonNull(paramMappings) && paramIdx < paramMappings.length) {
                mapping = paramMappings[paramIdx].split("\\_");
            } else {
                LOGGER.warn("resource:{} 参数索引:{} 没有匹配到映射规则", path, paramIdx);
            }
            //5、装配params,通过paramIdx来获取响应的参数值
            if (Objects.nonNull(mapping)) {
                if ("header".equals(mapping[0])) {
                    params[paramIdx] = parser.getHeader(exchange, mapping[1]);
                }
                if ("cookie".equals(mapping[0])) {
                    params[paramIdx] = parser.getCookieValue(exchange, mapping[1]);
                }
                if ("param".equals(mapping[0])) {
                    params[paramIdx] = parser.getUrlParam(exchange, mapping[1]);
                }
            }
        }
        return params;
    }

    public static void initParamMapping(String paramIdxMapping) {
        paramMappings = paramIdxMapping.split("\\|");
        LOGGER.debug("paramMappings {}" ,paramMappings);
    }
}
