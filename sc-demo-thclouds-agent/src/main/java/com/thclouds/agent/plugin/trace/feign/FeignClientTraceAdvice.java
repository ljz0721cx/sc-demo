package com.thclouds.agent.plugin.trace.feign;

import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.thclouds.agent.context.AgentContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.commons.base.log.appender.LoggerBuilder;
import feign.Request;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Field;
import java.util.*;


public class FeignClientTraceAdvice {

    public static ILog LOGGER = LogManager.getLogger(FeignClientTraceAdvice.class);

    public static Field FIELD_HEADERS_OF_REQUEST;

    static {
        try {
            final Field field = Request.class.getDeclaredField("headers");
            field.setAccessible(true);
            FIELD_HEADERS_OF_REQUEST = field;
        } catch (Exception ignore) {
            FIELD_HEADERS_OF_REQUEST = null;
        }
    }

    @Advice.OnMethodEnter()
    public static  void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {

        Request request = (Request) allArguments[0];

        byte[] body = request.body();
        String logArgs = null != body?new String(body):"";
        String tranceId = AgentContext.getTranceId();
        LOGGER.info("trance: {}, 请求开始参数,url: {}, method: {}, params:{}", tranceId,request.url(), request.method(), logArgs);
        List<String> tranceIds = new ArrayList<>();
        tranceIds.add(tranceId);
        if (FIELD_HEADERS_OF_REQUEST != null) {
            Map<String, Collection<String>> headers = new LinkedHashMap<String, Collection<String>>();
            headers.put("tranceId", tranceIds);
            headers.putAll(request.headers());
            FIELD_HEADERS_OF_REQUEST.set(request, Collections.unmodifiableMap(headers));
        }

    }


    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e , @Advice.Return Object result) {
        if (e != null ) {
            LoggerBuilder.busLogger().error("trance: {},异常信息{}", AgentContext.getTranceId(), e);
        } else {
            LoggerBuilder.busLogger().info("trance: {},请求返回数据result{}",AgentContext.getTranceId(), JSON.toJSONString(result));
        }


    }


}
