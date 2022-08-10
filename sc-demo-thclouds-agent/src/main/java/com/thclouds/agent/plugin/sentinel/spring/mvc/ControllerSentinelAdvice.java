package com.thclouds.agent.plugin.sentinel.spring.mvc;

import com.thclouds.agent.conf.Config;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.utils.SentinelAdviceUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ControllerSentinelAdvice {

    public static ILog LOGGER = LogManager.getLogger(ControllerSentinelAdvice.class);

    @Advice.OnMethodEnter()
    public static <ParamFlowException> void enter(@Advice.This Object objInst, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName, @Advice.AllArguments Object[] allArguments) throws Exception {
        LOGGER.info("enter  className：" + objInst.getClass() + " methodName: " + methodName);
        //获取到方法上的路径
        String path = getSubPath(objInst, methodName);
        LOGGER.info("path: {} ", path);
        RequestMapping basePathRequestMapping = AnnotationUtils.findAnnotation(objInst.getClass(), RequestMapping.class);
        if (basePathRequestMapping != null) {
            if (basePathRequestMapping.value().length > 0) {
                path = basePathRequestMapping.value()[0] + "/" + path;
            } else if (basePathRequestMapping.path().length > 0) {
                path = basePathRequestMapping.path()[0] + "/" + path;
            }
        }
        LOGGER.info("resourceName: {} {}", path, Config.Agent.SERVICE_NAME);
        SentinelAdviceUtil.enter(path,LOGGER);
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName, @Advice.Thrown Throwable e) {
        SentinelAdviceUtil.exit(className, methodName, e,LOGGER);
    }

    /**
     * 获取到注解上的路径
     *
     * @param objInst
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     */
    public static String getSubPath(Object objInst, String methodName) throws NoSuchMethodException {
        String subPath = "";
        Class<?> aClass = objInst.getClass();
        Method method = aClass.getDeclaredMethod(methodName);
        Annotation[] annotations = AnnotationUtils.getAnnotations(method);
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == RequestMapping.class) {
                subPath = ((RequestMapping) annotation).value()[0];
            }
            if (annotation.annotationType() == GetMapping.class) {
                subPath = ((GetMapping) annotation).value()[0];
            }
            if (annotation.annotationType() == PostMapping.class) {
                subPath = ((PostMapping) annotation).value()[0];
            }
            if (annotation.annotationType() == PutMapping.class) {
                subPath = ((PutMapping) annotation).value()[0];
            }
            if (annotation.annotationType() == DeleteMapping.class) {
                subPath = ((DeleteMapping) annotation).value()[0];
            }
            if (annotation.annotationType() == PatchMapping.class) {
                subPath = ((PatchMapping) annotation).value()[0];
            }
        }
        return subPath;
    }
}
