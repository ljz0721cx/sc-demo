package com.xn.demo.functions.finish.behavior;

import com.xn.demo.functions.annos.AppointFinish;
import com.xn.demo.functions.domains.Domains;
import com.xn.demo.functions.finish.BehaviorAction;
import com.xn.demo.functions.finish.dtos.FinishReq;
import com.xn.demo.functions.finish.dtos.FinishRsp;
import org.springframework.stereotype.Service;

/**
 * 默认核销接口接口
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/13 14:03
 */
@AppointFinish(domain = Domains.GO_HOME, venderIds = {1,2})
@Service(value = "fuzhuangDomainFinishAction")
public class FuzhuangDomainFinishAction extends DefaultFinishAction
        implements BehaviorAction<FinishRsp, FinishReq> {

    @Override
    public FinishRsp action(FinishReq s) {
        return new FinishRsp();
    }
}
