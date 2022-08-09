package com.thclouds.agent.transformer;

import com.thclouds.agent.advice.HandlerResultAdvice;
import com.thclouds.agent.advice.TimeInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import static net.bytebuddy.jar.asm.Opcodes.ACC_PRIVATE;
import static net.bytebuddy.jar.asm.Opcodes.ACC_VOLATILE;

public class TransformerDemo3 implements AgentBuilder.Transformer {
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                final TypeDescription typeDescription,
                                                final ClassLoader classLoader,
                                                final JavaModule module) {

            builder = builder.defineField(
                            "CONTEXT_ATTR_NAME", Object.class, ACC_PRIVATE | ACC_VOLATILE)
               ;
            ElementMatcher.Junction<MethodDescription> junction = ElementMatchers.isMethod()
                    .and(ElementMatchers.any());

            return builder.method(junction)
                    .intercept(MethodDelegation.withDefaultConfiguration()
                            .to(new TimeInterceptor()));

        }
    }
