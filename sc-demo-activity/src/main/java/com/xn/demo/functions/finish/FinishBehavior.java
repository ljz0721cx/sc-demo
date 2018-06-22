package com.xn.demo.functions.finish;

/**
 * 完成预约的行为
 *
 * @author Janle
 * @date 2018/6/12 21:05
 */
public interface FinishBehavior<T,R> {

    /**
     * 装载完成预约行为
     */
    BehaviorAction<T,R> loadAction();
}
