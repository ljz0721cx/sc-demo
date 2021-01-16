package com.xn.demo.log.lobdata;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.core.ConsoleAppender;
import com.xn.demo.log.model.LogAd;
import org.slf4j.LoggerFactory;

import static ch.qos.logback.core.spi.FilterReply.ACCEPT;
import static ch.qos.logback.core.spi.FilterReply.DENY;

/**
 * 这个类是给日志动态提供appender
 *
 * @author Janle on 2021/1/15
 */
public class GetTheAppender {
    /**
     * 通过传入的名字和级别，动态设置appender
     *
     * @param name
     * @param level
     * @return
     */
    public ConsoleAppender getAppender(String name, Level level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        //指定日志的append
        LogAd appender = new LogAd();
        LevelFilter levelFilter = getLevelFilter(level);
        levelFilter.start();

        appender.addFilter(levelFilter);
        appender.setContext(context);
        appender.setName(name);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%X{traceId},%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n");
        encoder.start();

        appender.setEncoder(encoder);
        appender.start();
        return appender;
    }

    /**
     * 通过level设置过滤器
     *
     * @param level
     * @return
     */
    private LevelFilter getLevelFilter(Level level) {
        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(level);
        levelFilter.setOnMatch(ACCEPT);
        levelFilter.setOnMismatch(DENY);
        return levelFilter;
    }
}
