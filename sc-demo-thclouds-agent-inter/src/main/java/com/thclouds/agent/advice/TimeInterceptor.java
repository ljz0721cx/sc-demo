package com.thclouds.agent.advice;

import net.bytebuddy.implementation.bind.annotation.*;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class TimeInterceptor {

    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Throwable {
        System.out.println(" in method :"+method.getName());
        long start = System.currentTimeMillis();
        try {
            return zuper.call();
        } catch (Exception e){
            return "exception";
        }finally {
            long time = System.currentTimeMillis()-start;
            System.out.println(" time :"+time);
        }

    }


}