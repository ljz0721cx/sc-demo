package webJunit;


import com.xn.demo.functions.EndAppointFactory;
import com.xn.demo.functions.EndAppointFactoryBean;
import com.xn.demo.functions.FactoryBean;
import com.xn.demo.functions.finish.dtos.FinishReq;
import com.xn.demo.functions.finish.dtos.FinishRsp;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/14 14:37
 */
public class TestEndAppointBean extends JUnit4SpringContextTests {

    @Resource
    private EndAppointFactory<FinishRsp, FinishReq> endAppointFactory;

    @Test
    public void Testcancel() {
        FactoryBean factoryBean =
                new EndAppointFactoryBean(1L);
        //完成预约提交
        FinishRsp finish = endAppointFactory
                .createFinishApi(factoryBean)
                .loadAction()
                .action(new FinishReq());

        System.out.println("=======================" + finish);
    }

}
