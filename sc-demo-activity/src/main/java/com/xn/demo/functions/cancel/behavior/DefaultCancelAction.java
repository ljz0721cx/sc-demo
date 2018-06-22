package com.xn.demo.functions.cancel.behavior;

/**
 * 默认的取消行为
 *
 * @author Janle
 * @date 2018/6/14 9:46
 */
public abstract class DefaultCancelAction {
    /**
     * 发送取消短信
     *
     * @return
     */
    Boolean sendMessage() {
        return Boolean.TRUE;
    }
}
