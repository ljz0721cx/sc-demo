package com.xn.demo.functions.finish.behavior;

import com.jd.appoint.service.order.functions.annos.AppointFinish;
import com.jd.appoint.service.order.functions.domains.Domains;
import com.jd.appoint.service.order.functions.finish.BehaviorAction;
import com.jd.appoint.service.order.functions.finish.dtos.FinishReq;
import com.jd.appoint.service.order.functions.finish.dtos.FinishRsp;
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
