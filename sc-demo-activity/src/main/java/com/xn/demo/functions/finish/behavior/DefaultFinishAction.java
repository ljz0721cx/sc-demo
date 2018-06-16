package com.xn.demo.functions.finish.behavior;

/**
 * 默认的取消行为
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/14 9:46
 */
public abstract class DefaultFinishAction {
    /**
     * 发送取消短信
     *
     * @return
     */
    Boolean sendMessage() {
        return Boolean.TRUE;
    }
}
