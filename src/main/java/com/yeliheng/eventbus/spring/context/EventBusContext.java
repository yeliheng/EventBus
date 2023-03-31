package com.yeliheng.eventbus.spring.context;

import com.yeliheng.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;

/**
 * Spring 上下文事件处理，只有当使用了Spring框架时才会执行这个类
 * Spring context event handler, only if you are using the Spring framework this class be executed
 * @author Liam Ye
 */
public class EventBusContext implements ApplicationListener<ApplicationContextEvent> {

    public static final Logger logger = LoggerFactory.getLogger(EventBusContext.class);

    private ApplicationContext applicationContext;

    private static EventBusContext instance;

    public static EventBusContext getEventContext() {
        return instance;
    }

    public static ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    private synchronized void shutdownSafely() {
        try {
            Field executorField = EventBus.class.getDeclaredField("executor");
            executorField.setAccessible(true);
            ExecutorService executorService = (ExecutorService) executorField.get(null);
            executorService.shutdown();
        } catch (Exception e) {
            logger.error("Failed to shutdown, Unexpected exception: " + e.getMessage());
            return;
        }
        logger.info("EventBus successfully shutdown.");
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if(event instanceof ContextRefreshedEvent) {
            // 初始化上下文
            EventBusContext.instance = this;
            applicationContext = event.getApplicationContext();
            logger.info("EventBus initialized.");
        } else if (event instanceof ContextClosedEvent) {
            // 关闭上下文
            shutdownSafely();
        }
    }
}
