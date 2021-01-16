package com.xn.demo.log.lobdata;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 日志构建器
 *
 * @author Janle on 2021/1/15
 */
public class LoggerBuilder {
    private static final Set<String> appenderName = new HashSet<>();
    private static volatile Logger logLogger = null;
    private static volatile Logger busLogger = null;

    /**
     * 构建不同的appender
     *
     * @return
     */
    public static Logger logLogger() {
        if (null == logLogger) {
            synchronized (LoggerBuilder.class) {
                if (null == logLogger) {
                    ConsoleAppender errorAppender = new GetTheAppender().getAppender("qy566log", Level.ERROR);
                    ConsoleAppender infoAppender = new GetTheAppender().getAppender("qy566log", Level.INFO);
                    ConsoleAppender warnAppender = new GetTheAppender().getAppender("qy566log", Level.WARN);
                    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
                    logLogger = context.getLogger("qy566log");
                    //设置不向上级打印信息
                    logLogger.setAdditive(false);
                    logLogger.addAppender(errorAppender);
                    logLogger.addAppender(infoAppender);
                    logLogger.addAppender(warnAppender);
                }
            }
        }
        return logLogger;
    }
}
