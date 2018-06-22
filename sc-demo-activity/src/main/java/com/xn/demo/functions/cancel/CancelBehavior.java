package com.xn.demo.functions.cancel;


import com.xn.demo.functions.finish.BehaviorAction;

/**
 * 取消行为接口,目前只是声明。如果需要在装载执行时候扩展可以日后扩展
 *
 * @author Janle
 * @date 2018/6/12 20:29
 */
public interface CancelBehavior<T, R> {
    /**
     * 装载取消的行为
     */
    BehaviorAction<T, R> loadAction();
}
