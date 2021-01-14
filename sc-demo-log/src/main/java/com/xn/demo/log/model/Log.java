package com.xn.demo.log.model;

/**
 * @author Janle on 2021/1/14
 */
public class Log {
    /**
     * 轨迹id
     */
    private String traceId;
    /**
     * 类的全路径名称
     */
    private String loggerName;
    /**
     * 打印的消息
     */
    private String message;
    /**
     * 线程名称
     */
    private String threadName;
    /**
     * 时间戳 日志打印的时间
     */
    private String timeStamp;
    /**
     * 日志打印级别
     */
    private String level;

    public Log(String loggerName, String message, String threadName, String timeStamp, String level) {
        this.loggerName = loggerName;
        this.message = message;
        this.threadName = threadName;
        this.timeStamp = timeStamp;
        this.level = level;
    }


    public Log(String traceId, String loggerName, String message, String threadName, String timeStamp, String level) {
        this.traceId = traceId;
        this.loggerName = loggerName;
        this.message = message;
        this.threadName = threadName;
        this.timeStamp = timeStamp;
        this.level = level;
    }

    public String getLoggerName() {
        return loggerName;
    }


    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
