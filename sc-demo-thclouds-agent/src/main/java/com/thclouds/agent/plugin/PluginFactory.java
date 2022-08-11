package com.thclouds.agent.plugin;

import com.thclouds.agent.conf.Config;

import java.util.*;

/**
 * 插件工厂，判断加载哪些插件
 * @author lixh
 */
public class PluginFactory {
    public static Collection<IPlugin> pluginGroup = null;

    static {
        //获取到所有的插件
        Map<String, IPlugin> allPlugins = new HashMap();
        for (IPlugin iPlugin : ServiceLoader.load(IPlugin.class)) {
            allPlugins.put(iPlugin.name(), iPlugin);
        }
        //排除不需要的插件
        String[] plugins = Config.Plugin.EXCLUDE_PLUGINS.split(",");
        for (String plugin : plugins) {
            allPlugins.remove(plugin);
        }
        if (allPlugins.size() > 0) {
            pluginGroup = allPlugins.values();
        }

    }

}
