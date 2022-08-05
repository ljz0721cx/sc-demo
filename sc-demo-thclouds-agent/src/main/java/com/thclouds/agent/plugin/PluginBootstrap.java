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

import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.TypeVariableSource;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.modifier.*;
import net.bytebuddy.description.type.*;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PluginBootstrap {
    private static final ILog LOGGER = LogManager.getLogger(PluginBootstrap.class);

    /**
     * load all plugins.
     *
     * @return plugin definition list.
     */
    public static List<AbstractClassEnhancePluginDefine> loadPlugins() {

        List<AbstractClassEnhancePluginDefine> plugins = new ArrayList<AbstractClassEnhancePluginDefine>();
        try {
//            LOGGER.debug("loading plugin class {}.", pluginDefine.getDefineClass());
            AbstractClassEnhancePluginDefine plugin = (AbstractClassEnhancePluginDefine) Class.forName("com.thclouds.agent.intercept.threading.define.CallableInstrumentation").newInstance();
            plugins.add(plugin);
            AbstractClassEnhancePluginDefine plugin2 = (AbstractClassEnhancePluginDefine) Class.forName("com.thclouds.agent.intercept.threading.define.RunnableInstrumentation").newInstance();
            plugins.add(plugin2);
            System.out.println("加载增强插件的定义" + plugins.size());
        } catch (Throwable t) {
//            LOGGER.error(t, "load plugin [{}] failure.", pluginDefine.getDefineClass());
        }


        return plugins;

    }


    public static void main(String[] args) {
        PluginFinder pluginFinder= new PluginFinder(PluginBootstrap.loadPlugins());
        ElementMatcher elementMatcher=pluginFinder.buildMatch();
        pluginFinder.find(new TypeDescription() {
            @Override
            public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
                return null;
            }

            @Override
            public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
                return null;
            }

            @Override
            public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
                return null;
            }

            @Override
            public boolean isInstance(Object value) {
                return false;
            }

            @Override
            public boolean isAssignableFrom(Class<?> type) {
                return false;
            }

            @Override
            public boolean isAssignableFrom(TypeDescription typeDescription) {
                return false;
            }

            @Override
            public boolean isAssignableTo(Class<?> type) {
                return false;
            }

            @Override
            public boolean isAssignableTo(TypeDescription typeDescription) {
                return false;
            }

            @Override
            public boolean isInHierarchyWith(Class<?> type) {
                return false;
            }

            @Override
            public boolean isInHierarchyWith(TypeDescription typeDescription) {
                return false;
            }

            @Override
            public TypeDescription getComponentType() {
                return null;
            }

            @Override
            public TypeDescription getDeclaringType() {
                return null;
            }

            @Override
            public TypeList getDeclaredTypes() {
                return null;
            }

            @Override
            public MethodDescription.InDefinedShape getEnclosingMethod() {
                return null;
            }

            @Override
            public TypeDescription getEnclosingType() {
                return null;
            }

            @Override
            public int getActualModifiers(boolean superFlag) {
                return 0;
            }

            @Override
            public String getSimpleName() {
                return null;
            }

            @Override
            public String getLongSimpleName() {
                return null;
            }

            @Override
            public String getCanonicalName() {
                return null;
            }

            @Override
            public boolean isAnonymousType() {
                return false;
            }

            @Override
            public boolean isLocalType() {
                return false;
            }

            @Override
            public boolean isMemberType() {
                return false;
            }

            @Override
            public PackageDescription getPackage() {
                return null;
            }

            @Override
            public AnnotationList getInheritedAnnotations() {
                return null;
            }

            @Override
            public boolean isSamePackage(TypeDescription typeDescription) {
                return false;
            }

            @Override
            public boolean isPrimitiveWrapper() {
                return false;
            }

            @Override
            public boolean isAnnotationReturnType() {
                return false;
            }

            @Override
            public boolean isAnnotationValue() {
                return false;
            }

            @Override
            public boolean isAnnotationValue(Object value) {
                return false;
            }

            @Override
            public boolean isPackageType() {
                return false;
            }

            @Override
            public int getInnerClassCount() {
                return 0;
            }

            @Override
            public boolean isInnerClass() {
                return false;
            }

            @Override
            public boolean isNestedClass() {
                return false;
            }

            @Override
            public TypeDescription asBoxed() {
                return null;
            }

            @Override
            public TypeDescription asUnboxed() {
                return null;
            }

            @Override
            public Object getDefaultValue() {
                return null;
            }

            @Override
            public TypeDescription getNestHost() {
                return null;
            }

            @Override
            public TypeList getNestMembers() {
                return null;
            }

            @Override
            public boolean isNestHost() {
                return false;
            }

            @Override
            public boolean isNestMateOf(Class<?> type) {
                return false;
            }

            @Override
            public boolean isNestMateOf(TypeDescription typeDescription) {
                return false;
            }

            @Override
            public boolean isCompileTimeConstant() {
                return false;
            }

            @Override
            public TypeList getPermittedSubtypes() {
                return null;
            }

            @Override
            public boolean isSealed() {
                return false;
            }

            @Override
            public ClassFileVersion getClassFileVersion() {
                return null;
            }

            @Override
            public boolean isVisibleTo(TypeDescription typeDescription) {
                return false;
            }

            @Override
            public boolean isAccessibleTo(TypeDescription typeDescription) {
                return false;
            }

            @Override
            public String getDescriptor() {
                return null;
            }

            @Override
            public String getGenericSignature() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getInternalName() {
                return null;
            }

            @Override
            public TypeList.Generic getTypeVariables() {
                return null;
            }

            @Override
            public TypeVariableSource getEnclosingSource() {
                return null;
            }

            @Override
            public boolean isInferrable() {
                return false;
            }

            @Override
            public Generic findVariable(String symbol) {
                return null;
            }

            @Override
            public Generic findExpectedVariable(String symbol) {
                return null;
            }

            @Override
            public <T> T accept(Visitor<T> visitor) {
                return null;
            }

            @Override
            public boolean isGenerified() {
                return false;
            }

            @Override
            public AnnotationList getDeclaredAnnotations() {
                return null;
            }

            @Override
            public Generic asGenericType() {
                return null;
            }

            @Override
            public TypeDescription asErasure() {
                return null;
            }

            @Override
            public Generic getSuperClass() {
                return null;
            }

            @Override
            public TypeList.Generic getInterfaces() {
                return null;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public String getTypeName() {
                return null;
            }

            @Override
            public StackSize getStackSize() {
                return null;
            }

            @Override
            public boolean isArray() {
                return false;
            }

            @Override
            public boolean isRecord() {
                return false;
            }

            @Override
            public boolean isPrimitive() {
                return false;
            }

            @Override
            public boolean represents(Type type) {
                return false;
            }

            @Override
            public Iterator<TypeDefinition> iterator() {
                return null;
            }

            @Override
            public boolean isInterface() {
                return false;
            }

            @Override
            public boolean isAnnotation() {
                return false;
            }

            @Override
            public TypeManifestation getTypeManifestation() {
                return null;
            }

            @Override
            public boolean isAbstract() {
                return false;
            }

            @Override
            public boolean isEnum() {
                return false;
            }

            @Override
            public EnumerationState getEnumerationState() {
                return null;
            }

            @Override
            public boolean isPublic() {
                return false;
            }

            @Override
            public boolean isProtected() {
                return false;
            }

            @Override
            public boolean isPackagePrivate() {
                return false;
            }

            @Override
            public boolean isPrivate() {
                return false;
            }

            @Override
            public boolean isStatic() {
                return false;
            }

            @Override
            public boolean isDeprecated() {
                return false;
            }

            @Override
            public Ownership getOwnership() {
                return null;
            }

            @Override
            public Visibility getVisibility() {
                return null;
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public boolean isFinal() {
                return false;
            }

            @Override
            public boolean isSynthetic() {
                return false;
            }

            @Override
            public SyntheticState getSyntheticState() {
                return null;
            }

            @Override
            public String getActualName() {
                return null;
            }
        });

    }

}
