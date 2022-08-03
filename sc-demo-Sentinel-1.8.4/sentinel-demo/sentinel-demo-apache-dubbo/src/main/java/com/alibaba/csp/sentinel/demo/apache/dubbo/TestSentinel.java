package com.alibaba.csp.sentinel.demo.apache.dubbo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class TestSentinel {

    public static void main(String[] args) {
        initFlowRules();
        Entry entry = null;
        try {
            entry = SphU.entry("HelloWorld");
            // token acquired, means pass
            entry = SphU.entry("HelloWorld");
            entry = SphU.entry("HelloWorld");
            entry = SphU.entry("HelloWorld");
            entry = SphU.entry("HelloWorld");
        } catch (BlockException e1) {
            // 处理被流控的逻辑
            System.out.println("blocked!");
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }


    public static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(1);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
