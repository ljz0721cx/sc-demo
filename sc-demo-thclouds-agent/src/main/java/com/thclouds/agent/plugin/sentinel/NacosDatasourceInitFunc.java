package com.thclouds.agent.plugin.sentinel;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.thclouds.agent.conf.Config;
import com.thclouds.agent.logging.api.ILog;
import com.thclouds.agent.logging.api.LogManager;


import java.util.List;
import java.util.Properties;

/**
 * @author lixh
 */
public class NacosDatasourceInitFunc implements InitFunc {

    public static ILog LOGGER = LogManager.getLogger(NacosDatasourceInitFunc.class);

    @Override
    public void init() {
        LOGGER.debug("NacosDatasourceInitFun init");
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, Config.Rule.REMOVE_ADDRESS);
        properties.put(PropertyKeyConst.NAMESPACE, Config.Rule.NAMESPACE_ID);

        //限流规则初始化
        try {
            ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties, Config.Rule.GROUP_ID, Config.Rule.FLOW_DATA_ID,
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                    }));
            FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

            List<FlowRule> rules = FlowRuleManager.getRules();
            LOGGER.debug("rules {}",rules);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //降级规则初始化
        try {
            ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(properties, Config.Rule.GROUP_ID, Config.Rule.DEGRADE_DATA_ID,
                    source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                    }));
            DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //系统保护规则初始化
        try {
            ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(properties, Config.Rule.GROUP_ID, Config.Rule.SYSTEM_DATA_ID,
                    source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                    }));
            SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //访问控制规则
    }
}
