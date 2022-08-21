package com.jd.demo;

import java.util.Map;

/**
 * @author Janle on 2022/8/21
 */
public class T {
    public static final ThreadLocal<Map> t = new InheritableThreadLocal<Map>();


}
