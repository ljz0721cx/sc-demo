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

package com.thclouds.agent.plugin;

import com.thclouds.agent.SentinelAgent;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import com.thclouds.agent.plugin.bytebuddy.AbstractJunction;
import com.thclouds.agent.plugin.interceptor.match.ClassMatch;
import com.thclouds.agent.plugin.interceptor.match.IndirectMatch;
import com.thclouds.agent.plugin.interceptor.match.NameMatch;
import com.thclouds.agent.plugin.interceptor.match.ProtectiveShieldMatcher;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.hasSuperType;
import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * The <code>PluginFinder</code> represents a finder , which assist to find the one from the given {@link
 * AbstractClassEnhancePluginDefine} list.
 */
public class PluginFinder {

    private static ILog LOGGER = LogManager.getLogger(PluginFinder.class);

    private final Map<String, LinkedList<AbstractClassEnhancePluginDefine>> nameMatchDefine = new HashMap<String, LinkedList<AbstractClassEnhancePluginDefine>>();
    private final List<AbstractClassEnhancePluginDefine> signatureMatchDefine = new ArrayList<AbstractClassEnhancePluginDefine>();

    public PluginFinder(List<AbstractClassEnhancePluginDefine> plugins) {
        for (AbstractClassEnhancePluginDefine plugin : plugins) {
            ClassMatch match = plugin.enhanceClass();

            if (match == null) {
                continue;
            }

            if (match instanceof NameMatch) {
                NameMatch nameMatch = (NameMatch) match;
                LinkedList<AbstractClassEnhancePluginDefine> pluginDefines = nameMatchDefine.get(nameMatch.getClassName());
                if (pluginDefines == null) {
                    pluginDefines = new LinkedList<AbstractClassEnhancePluginDefine>();
                    LOGGER.info("nameMatch {} {}",nameMatch.getClassName(),plugin.getClass());
                    nameMatchDefine.put(nameMatch.getClassName(), pluginDefines);
                }
                pluginDefines.add(plugin);
            } else {
                LOGGER.info("plugin {}",plugin.getClass());
                signatureMatchDefine.add(plugin);
            }
        }
    }

    public List<AbstractClassEnhancePluginDefine> find(TypeDescription typeDescription) {
        List<AbstractClassEnhancePluginDefine> matchedPlugins = new LinkedList<AbstractClassEnhancePluginDefine>();
        String typeName = typeDescription.getTypeName();
        if (nameMatchDefine.containsKey(typeName)) {
            matchedPlugins.addAll(nameMatchDefine.get(typeName));
        }

        for (AbstractClassEnhancePluginDefine pluginDefine : signatureMatchDefine) {
            IndirectMatch match = (IndirectMatch) pluginDefine.enhanceClass();
            if (match.isMatch(typeDescription)) {
                matchedPlugins.add(pluginDefine);
            }
        }

        return matchedPlugins;
    }

    public ElementMatcher<? super TypeDescription> buildMatch() {
//        ElementMatcher.Junction judge = new AbstractJunction<NamedElement>() {
//            @Override
//            public boolean matches(NamedElement target) {
//                return nameMatchDefine.containsKey(target.getActualName());
//            }
//        };
      return   ElementMatchers.nameStartsWith("com.thclouds.company").or(hasSuperType(named("java.lang.Runnable")).and(not(isInterface())))
                .or( ElementMatchers.nameStartsWith("com.thclouds.company").or(hasSuperType(named("java.util.concurrent.Callable")).and(not(isInterface()))));

    }
}
