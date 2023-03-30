package com.yeliheng.eventbus;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * 事件总线线程工厂
 * @author Liam Ye
 */
public class EventBusThreadFactory implements ThreadFactory {

    public final Logger logger = Logger.getLogger(EventBusThreadFactory.class.getName());

    private final int poolNumber;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    EventBusThreadFactory(int poolNumber) {
        this.poolNumber = poolNumber;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String threadName = "eventbus-" + poolNumber + "-thread-" + threadNumber.getAndIncrement();
        Thread thread = new Thread(runnable, threadName);
        thread.setDaemon(false);
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.setUncaughtExceptionHandler((t, e) -> logger.warning(t.toString() + e.toString()));
        return thread;
    }
}
