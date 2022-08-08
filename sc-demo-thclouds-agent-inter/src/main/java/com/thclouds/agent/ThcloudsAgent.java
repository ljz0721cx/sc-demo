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

package com.thclouds.agent;

import com.thclouds.agent.demo.ToString;
import com.thclouds.agent.transformer.TransformerDemo1;
import com.thclouds.agent.transformer.TransformerDemo2;
import com.thclouds.agent.transformer.TransformerDemo3;
import com.thclouds.agent.utils.InstrumentDebuggingClass;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;
import java.lang.instrument.Instrumentation;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.*;


public class ThcloudsAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        //demo1
//        new AgentBuilder.Default()
//                .type(named("com.thclouds.agent.demo.TestDomeToString"))
//                .transform(new TransformerDemo1())
//                .with(new Listener())
//                .installOn(instrumentation);
        //demo2
//        new AgentBuilder.Default()
//                .type(named("com.thclouds.agent.demo.TestDomeToString"))
//                .transform(new TransformerDemo2())
//                .with(new Listener())
//                .installOn(instrumentation);


        new AgentBuilder.Default()
                .type(named("com.thclouds.agent.demo.TestDomeToString"))
                .transform(new TransformerDemo3())
                .with(new Listener())
                .installOn(instrumentation);

    }


    private static class Listener implements AgentBuilder.Listener {
        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onTransformation(final TypeDescription typeDescription,
                                     final ClassLoader classLoader,
                                     final JavaModule module,
                                     final boolean loaded,
                                     final DynamicType dynamicType) {

            System.out.println("On Transformation class "+typeDescription.getName());

            InstrumentDebuggingClass.INSTANCE.log(dynamicType);
        }

        @Override
        public void onIgnored(final TypeDescription typeDescription,
                              final ClassLoader classLoader,
                              final JavaModule module,
                              final boolean loaded) {

        }

        @Override
        public void onError(final String typeName,
                            final ClassLoader classLoader,
                            final JavaModule module,
                            final boolean loaded,
                            final Throwable throwable) {
            throwable.printStackTrace();
            System.err.println("Enhance class " + typeName + " error.");
        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        }
    }

    private static class RedefinitionListener implements AgentBuilder.RedefinitionStrategy.Listener {

        @Override
        public void onBatch(int index, List<Class<?>> batch, List<Class<?>> types) {
            /* do nothing */
        }

        @Override
        public Iterable<? extends List<Class<?>>> onError(int index, List<Class<?>> batch, Throwable throwable, List<Class<?>> types) {

            return Collections.emptyList();
        }

        @Override
        public void onComplete(int amount, List<Class<?>> types, Map<List<Class<?>>, Throwable> failures) {
            /* do nothing */
        }
    }
}
