package com.xn.demo.functions.annos;


import com.xn.demo.functions.domains.Domains;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/13 9:38
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AppointCancal {
    /**
     * 业务领域
     *
     * @return
     */
    Domains domain() default Domains.GO_HOME;

    /**
     * 商家ID
     *
     * @return
     */
    long[] venderIds();
}
