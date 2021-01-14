package com.xn.demo.log.model;

import com.xn.demo.log.context.DataContext;

/**
 * 日志任务
 *
 * @author Janle on 2021/1/14
 */
public class LogTaskConsumer implements Runnable {


    @Override
    public void run() {
        Log log = null;
        while ((log = DataContext.tempLogQueue.poll()) != null) {
           /* SystemConfig systemConfig = UKTools.getSystemConfig();
            if (systemConfig != null && systemConfig.isSavelog()) {
                System.out.println(log);
            }*/
            System.out.println(log);
        }
    }
}
