package com.thclouds.agent.transformer;

import com.thclouds.agent.advice.HandlerResultAdvice;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class TransformerDemo2 implements AgentBuilder.Transformer {
        @Override
        public DynamicType.Builder<?> transform(final DynamicType.Builder<?> builder,
                                                final TypeDescription typeDescription,
                                                final ClassLoader classLoader,
                                                final JavaModule module) {
            ElementMatcher.Junction<MethodDescription> matcher = ElementMatchers.isMethod()
                    .and(ElementMatchers.any());
            return builder.visit(Advice.to(HandlerResultAdvice.class).on(matcher));
        }
    }
