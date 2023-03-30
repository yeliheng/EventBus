package com.yeliheng.eventbus;

import com.yeliheng.eventbus.enums.ThreadType;
import com.yeliheng.eventbus.interfaces.IEvent;
import com.yeliheng.eventbus.interfaces.ISubscriber;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 事件总线实现，用于注册订阅者和发布事件
 * Event bus implementation, used to register subscribers and publish events
 * @author Liam Ye
 */
public class EventBus {

    public static final Logger logger = Logger.getLogger(EventBus.class.getName());

    private static final Map<Class<?>, List<ISubscriber>> subscriberMap = new HashMap<>();

    private static final Map<Class<?>, Boolean> subscriberStatusMap = new HashMap<>();

    private static final EventBus instance = new EventBus();

    private static final EventBusThreadFactory factory = new EventBusThreadFactory(1);

    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    public static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;

    private static final Executor executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            3L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            factory
            );

    private EventBus() {
        if(instance != null) {
            throw new RuntimeException("EventBus is a singleton");
        }
    }

    public static EventBus getInstance() {
        return instance;
    }

    public void register(Object subscriber) {
        Objects.requireNonNull(subscriber);
        if(subscriberStatusMap.get(subscriber.getClass()) != null) {
            logger.warning(String.format("Subscriber %s has already been registered",subscriber.getClass()));
            return;
        }
        // 找到已注册的单个事件的所有订阅者 事件类 -> 订阅者列表
        Map<Class<?>, List<ISubscriber>> registeredSubs = SubscriberFinder.find(subscriber);
        synchronized (this) {
            if(!registeredSubs.isEmpty()) {
                subscriberStatusMap.put(subscriber.getClass(), true);
            }
            // 将单个注册的订阅者列表合并到总的订阅者列表中
            registeredSubs.forEach((subscriberClass, subscribers) -> subscriberMap.computeIfAbsent(subscriberClass, k -> new ArrayList<>()).addAll(subscribers));
        }
    }

    public void unregister(Object subscriber) {
        Objects.requireNonNull(subscriber);
        if(subscriberMap != null && !subscriberMap.isEmpty()) {
            subscriberMap.forEach((subscriberClass, subscribers) -> {
                if(subscribers == null || subscribers.isEmpty()) {
                    logger.warning(String.format("Subscriber %s was not registered",subscriber.getClass()));
                    return;
                }
                subscribers.removeIf(sub -> sub.getSubscriber() == subscriber);
                subscriberStatusMap.remove(subscriber.getClass());
            });
        }else {
            logger.warning(String.format("Subscriber %s was not registered",subscriber.getClass()));
        }
    }

    public static void post(IEvent event) {
        Objects.requireNonNull(event);
        Class<? extends IEvent> clazz = event.getClass();
        List<ISubscriber> subscribers = subscriberMap.get(clazz);
        if (subscribers == null || subscribers.isEmpty()) {
            return;
        }
        for (ISubscriber subscriber : subscribers) {
            if(subscriber.getThreadType() == ThreadType.ASYNC) {
                executor.execute(() -> subscriber.invoke(event));
            }else {
                subscriber.invoke(event);
            }
        }
    }

}
