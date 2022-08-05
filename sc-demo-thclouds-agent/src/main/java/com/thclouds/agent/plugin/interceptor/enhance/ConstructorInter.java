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

package com.thclouds.agent.plugin.interceptor.enhance;

import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.plugin.PluginException;
import com.thclouds.agent.plugin.interceptor.loader.InterceptorInstanceLoader;
import com.thclouds.agent.plugin.jdk.threading.EnhancedInstance;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;


/**
 * The actual byte-buddy's interceptor to intercept constructor methods. In this class, it provide a bridge between
 * byte-buddy and sky-walking plugin.
 */
public class ConstructorInter {
    private static final ILog LOGGER = LogManager.getLogger(ConstructorInter.class);

    private InstanceConstructorInterceptor interceptor;


    public ConstructorInter(String constructorInterceptorClassName) throws PluginException {
        try {
            interceptor = InterceptorInstanceLoader.load(constructorInterceptorClassName);
        } catch (Throwable t) {
            throw new PluginException("Can't create InstanceConstructorInterceptorV2.", t);
        }
    }

    @RuntimeType
    public void intercept(@This Object obj, @AllArguments Object[] allArguments) {
        try {
            EnhancedInstance targetObject = (EnhancedInstance) obj;

            interceptor.onConstruct(targetObject, allArguments);
        } catch (Throwable t) {
            LOGGER.error("ConstructorInter failure.", t);
        }

    }
}
