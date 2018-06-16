package com.xn.demo.functions;


import com.xn.demo.functions.cancel.CancelBehavior;
import com.xn.demo.functions.finish.FinishBehavior;

/**
 * 预约单终态执行器生产工厂
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/13 8:19
 */
public abstract class EndAppointFactory<T, R> {

    /**
     * 创建对应的取消
     *
     * @return
     */
    public abstract CancelBehavior<T, R> createCancelApi(FactoryBean factoryBean);

    /**
     * 创建对应的完成
     *
     * @return
     */
    public abstract FinishBehavior<T, R> createFinishApi(FactoryBean factoryBean);
}
