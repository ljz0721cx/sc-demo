package com.xn.demo.log.context;

import com.xn.demo.log.model.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 数据的上下文
 *
 * @author Janle on 2021/1/14
 */
public class DataContext {
    public static BlockingQueue<Log> tempLogQueue = new LinkedBlockingQueue<>();
}
