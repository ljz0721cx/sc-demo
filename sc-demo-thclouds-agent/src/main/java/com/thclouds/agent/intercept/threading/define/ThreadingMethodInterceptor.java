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

import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.plugin.interceptor.enhance.EnhancedInstance;
import com.thclouds.agent.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import com.thclouds.agent.plugin.interceptor.enhance.MethodInterceptResult;



import java.lang.reflect.Method;

public class ThreadingMethodInterceptor implements InstanceMethodsAroundInterceptor {
    private static ILog LOGGER = LogManager.getLogger(ThreadingMethodInterceptor.class);
    @Override
    public void beforeMethod(final EnhancedInstance objInst, final Method method, final Object[] allArguments,
                             final Class<?>[] argumentsTypes, final MethodInterceptResult result) {

    }

    @Override
    public Object afterMethod(final EnhancedInstance objInst, final Method method, final Object[] allArguments,
        final Class<?>[] argumentsTypes, final Object ret) {

        return ret;
    }

    @Override
    public void handleMethodException(final EnhancedInstance objInst, final Method method, final Object[] allArguments,
        final Class<?>[] argumentsTypes, final Throwable t) {


    }

    private String generateOperationName(final EnhancedInstance objInst, final Method method) {
        return "Threading/" + objInst.getClass().getName() + "/" + method.getName();
    }

}
