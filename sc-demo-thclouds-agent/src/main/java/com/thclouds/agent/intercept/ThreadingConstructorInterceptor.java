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

package com.thclouds.agent.intercept;

import com.thclouds.agent.context.ContextCarrier;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.plugin.interceptor.enhance.InstanceConstructorInterceptor;
import com.thclouds.agent.plugin.jdk.threading.EnhancedInstance;

/**
 * @author lixh
 */
public class ThreadingConstructorInterceptor implements InstanceConstructorInterceptor {

    private static ILog LOGGER = LogManager.getLogger(ThreadingMethodInterceptor.class);

    @Override
    public void onConstruct(final EnhancedInstance objInst, final Object[] allArguments) {
        ContextCarrier lixh = ContextCarrier.builder().traceId("lixh").traceSegmentId("1").build();
        LOGGER.info("content {}",lixh);
        objInst.setSkyWalkingDynamicField(lixh);
    }

}
