package com.thclouds.agent.plugin.sentinel.spring.mvc;

import com.thclouds.agent.plugin.IPlugin;
import com.thclouds.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lixh
 * @description mvc Controller拦截，限流加强
 */
public class ControllerSentinelPlugin implements IPlugin {

    @Override
    public String name() {
        return "sentinelControllerPlugin";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.isAnnotatedWith(RestController.class).or(ElementMatchers.isAnnotatedWith(Controller.class));
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers
                                .isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.GetMapping"))
                                .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.PostMapping")))
                                .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.PutMapping")))
                                .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.DeleteMapping")))
                                .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.PatchMapping")))
                                .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.RequestMapping")))
                                ;
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return ControllerSentinelAdvice.class;
    }

}
