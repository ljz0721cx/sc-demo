package com.xn.demo.log.model;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import com.alibaba.fastjson.JSON;
import org.slf4j.MDC;

/**
 * @author Janle on 2021/1/14
 */
public class LogAd extends ConsoleAppender<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {
        Log log = new Log(MDC.get("traceId"),eventObject.getLoggerName(), eventObject.getMessage(), eventObject.getThreadName(), String.valueOf(eventObject.getTimeStamp()), eventObject.getLevel().toString());
        //添加到阻塞队列
        System.err.println(JSON.toJSONString(log, true));
    }
}
