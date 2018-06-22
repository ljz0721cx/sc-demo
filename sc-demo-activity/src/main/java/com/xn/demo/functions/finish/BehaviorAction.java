package com.xn.demo.functions.finish;

/**
 * 执行完成的行为
 *
 * @author Janle
 * @date 2018/6/13 9:14
 */
public interface BehaviorAction<T, R> {
    /**
     * 执行行为 取消或者完成
     *
     * @return
     */
    T action(R r);
}
