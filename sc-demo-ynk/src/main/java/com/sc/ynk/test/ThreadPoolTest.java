package com.sc.ynk.test;

import java.util.concurrent.*;

/**
 * @author lijizhen1@jd.com
 * @date 2018/9/12 9:04
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000));
        for (int i = 0; i < 10; i++) {
            Thread t=new Thread(() -> {
                try {
                    System.out.println("Thread ID "+Thread.currentThread().getId());
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            executor.submit(t);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
