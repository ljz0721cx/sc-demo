package com.sc.ynk.ttl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lijizhen1@jd.com
 * @date 2018/9/7 18:11
 */
public class TestInheritableThreadLocal {
    static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {

        //没有使用线程池的情况没有问题。
        //noUserThreadPool(10);

        //System.out.println("使用线程池的处理方式。");
        //使用线程池的时候就会出现问题

        userThreadPool(10);
    }

    private static void noUserThreadPool(int c) {
        for (int i = 0; i < c; i++) {
            Integer var1 = (int) (Math.random() * 100);
            Integer var2 = (int) (Math.random() * 100);
            //设置线程共享的值
            TestInheritableThreadLocal.ThreadHolder.set(var1);
            //创建一个子线程
            new Thread(() -> System.out.println(TestInheritableThreadLocal.ThreadHolder.get() * var2 == var1 * var2)).start();
        }
    }

    private static void userThreadPool(int c) {
        for (int i = 0; i < c; i++) {
            Integer var1 = (int) (Math.random() * 100);
            Integer var2 = (int) (Math.random() * 100);
            //设置线程共享的值
            TestInheritableThreadLocal.ThreadHolder.set(var1);
            //线程池中线程会复用
            executorService.submit(() -> System.out.println(TestInheritableThreadLocal.ThreadHolder.get() * var2 == var1 * var2));
        }
    }

    /**
     *
     */
    public static class ThreadHolder {
        private static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

        public static void set(Integer data) {
            threadLocal.set(data);
        }

        public static Integer get() {
            return threadLocal.get();
        }
    }
}
