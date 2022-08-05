/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.thclouds.agent.intercept.threading.define;

import com.thclouds.agent.intercept.ThreadingConfig;
import com.thclouds.agent.plugin.interceptor.ConstructorInterceptPoint;
import com.thclouds.agent.plugin.interceptor.InstanceMethodsInterceptPoint;
import com.thclouds.agent.plugin.interceptor.StaticMethodsInterceptPoint;
import com.thclouds.agent.plugin.interceptor.enhance.ClassEnhancePluginDefine;
import com.thclouds.agent.plugin.interceptor.match.ClassMatch;
import com.thclouds.agent.plugin.interceptor.match.HierarchyMatch;
import com.thclouds.agent.plugin.interceptor.match.IndirectMatch;
import com.thclouds.agent.plugin.interceptor.match.logical.LogicalMatchOperation;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;


import static net.bytebuddy.matcher.ElementMatchers.*;

public class RunnableInstrumentation extends ClassEnhancePluginDefine {
    private static final String RUNNABLE_CLASS = "java.lang.Runnable";
    private static final String RUNNABLE_CLASS_INTERCEPTOR = "com.thclouds.agent.intercept.ThreadingConstructorInterceptor";

    private static final String RUNNABLE_RUN_METHOD = "run";
    private static final String RUNNABLE_RUN_METHOD_INTERCEPTOR = "com.thclouds.agent.intercept.ThreadingMethodInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        final IndirectMatch prefixMatches = ThreadingConfig.prefixesMatchesForJdkThreading();

        if (prefixMatches == null) {
            return null;
        }

        return LogicalMatchOperation.and(prefixMatches, HierarchyMatch.byHierarchyMatch(RUNNABLE_CLASS));
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[0];
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named(RUNNABLE_RUN_METHOD).and(takesArguments(0));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return RUNNABLE_RUN_METHOD_INTERCEPTOR;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

    @Override
    public StaticMethodsInterceptPoint[] getStaticMethodsInterceptPoints() {
        return new StaticMethodsInterceptPoint[0];
    }

}