package com.sc.ynk.maps;

import java.util.HashMap;
import java.util.Map;

/**
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


    public static class ChildClass {
        private static Map map = new HashMap();

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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ChildClass childClass = InnerClass.builder();
                    childClass.addI("" + iStr);
                    InnerClass innerClass = childClass.build();

                    if (!iStr.equals(innerClass.get())) {
                        System.out.println("待处理的值:" + iStr + ";线程获取到的值：" + innerClass.get());
                    }
                }
            }, "thread" + i).start();
        }

    }
}
