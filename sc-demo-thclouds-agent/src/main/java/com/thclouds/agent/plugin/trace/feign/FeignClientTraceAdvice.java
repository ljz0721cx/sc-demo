package com.thclouds.agent.plugin.trace.feign;

import com.thclouds.agent.context.ServerWebExchangeContext;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
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
    public static <ParamFlowException> void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info(Thread.currentThread().getId() + "  className：" + className + " methodName: " + methodName);
        Request request = (Request) allArguments[0];

        String tranceId = ServerWebExchangeContext.getTranceId();
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
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        LOGGER.warn("error {}", e);
        LOGGER.info("tranceId {}", ServerWebExchangeContext.getTranceId());
    }


}
