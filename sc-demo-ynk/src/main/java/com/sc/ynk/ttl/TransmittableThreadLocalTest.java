package com.sc.ynk.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * https://www.cnblogs.com/sweetchildomine/p/8807059.html
 *
 * @author lijizhen1@jd.com
 * @date 2018/8/30 17:08
 */
public class TransmittableThreadLocalTest {
    static ExecutorService executorService = Executors.newFixedThreadPool(1);


    public static void main(String[] args) {

        //没有使用线程池的情况没有问题。
        noUserThreadPool(10);

        System.out.println("使用线程池的处理方式。");
        //使用线程池的时候就会出现问题

        userThreadPool(10);
    }

    private static void userThreadPool(int c) {
        for (int i = 0; i < c; i++) {
            Integer var1 = (int) (Math.random() * 100);
            Integer var2 = (int) (Math.random() * 100);
            ThreadContextHolder.set(var1);
            //线程池中线程会复用
           // executorService.submit(() -> assert1(var1, var2));
            //使用TransmittableThreadLocal 解决正常打印
            executorService.execute(TtlRunnable.get(()->assert1(var1,var2)));
        }
    }

    private static void noUserThreadPool(int c) {
        for (int i = 0; i < c; i++) {
            Integer var1 = (int) (Math.random() * 100);
            Integer var2 = (int) (Math.random() * 100);
            ThreadContextHolder.set(var1);
            //创建一个子线程
            new Thread(() -> assert1(var1, var2)).start();
        }
    }


    public static void assert1(Integer var1, Integer var2) {
        System.out.println(ThreadContextHolder.get() * var2 == var1 * var2);
    }

    /**
     *
     */
    public static class ThreadContextHolder {
        //private static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
        private static ThreadLocal<Integer> threadLocal = new TransmittableThreadLocal<>();


        public static void set(Integer data) {
            threadLocal.set(data);
        }

        public static Integer get() {
            return threadLocal.get();
        }
    }
}

