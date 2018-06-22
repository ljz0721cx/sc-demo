package com.xn.demo.functions.cancel.behavior;


import com.xn.demo.functions.annos.AppointCancal;
import com.xn.demo.functions.domains.Domains;
import com.xn.demo.functions.finish.BehaviorAction;
import org.springframework.stereotype.Service;

/**
 * 默认核销接口接口
 *
 * @author Janle
 * @date 2018/6/13 14:03
 */
@AppointCancal(domain = Domains.GO_HOME, venderIds = {1})
@Service(value = "fuzhuangDomainCancelAction")
public class FuzhuangDomainCancelAction extends DefaultCancelAction
        implements BehaviorAction<Boolean, String> {

    /**
     * 卡密解锁
     */
    Boolean cancelCav() {
        return Boolean.TRUE;
    }

    /**
     * 回滚库存
     */
    Boolean rollBackStore() {
        return Boolean.TRUE;
    }

    /**
     * 调用商家取消
     *
     * @return
     */
    Boolean remoteCancel() {
        return Boolean.TRUE;
    }


    /**
     * 默认执行返回true
     *
     * @return
     */
    @Override
    public Boolean action(String s) {
        //包装在事物中
        cancelCav();
        rollBackStore();
        remoteCancel();
        //修改取消状态
        sendMessage();
        return true;
    }
}
