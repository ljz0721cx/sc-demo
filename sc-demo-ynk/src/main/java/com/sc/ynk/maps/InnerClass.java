package com.sc.ynk.maps;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 通过日志来记录异常现场。缩小定位问题范围。
 * 线上问题排查，场景不知道哪里有问题，只能看代码。
 * 制定测试类，以及模拟测试过程进行分析。
 *
 * 总结：
 * 必须有一个非静态的类来进行实例化。
 * 多线程的安全其实就是对内存的安全访问。
 * 分析jvm中内存的分布和类的存储。
 *
 * @author Janle on 2022/7/6
 */
public class InnerClass extends HashMap<String, String> {
    public InnerClass(ChildClass childClass) {
        this.clear();
        this.putAll(childClass.getMap());
    }

    /**
     * @return
     */
    public String get() {
        return get("key");
    }

    /**
     * 操作构建类
     */
    public static ChildClass builder() {
        return new ChildClass();
    }


    /**
     * 内部类
     */
    public static class ChildClass {
        private static Map map = new ConcurrentHashMap();

        public void addI(String i) {
            map.put("key", i);
        }

        public Map<String, String> getMap() {
            return map;
        }

        public InnerClass build() {
            return new InnerClass(this);
        }
    }


    /**
     * 执行处理
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            final String iStr = "" + i;
            new Thread(() -> {
                ChildClass childClass = InnerClass.builder();
                childClass.addI("" + iStr);
                InnerClass innerClass = childClass.build();

                if (!iStr.equals(innerClass.get())) {
                    System.out.println("线程名称 "+Thread.currentThread().getName()+";待处理的值:" + iStr + ";线程获取到的值：" + innerClass.get());
                }
            }, "thread" + i).start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
