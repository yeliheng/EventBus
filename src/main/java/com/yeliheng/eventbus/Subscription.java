package com.yeliheng.eventbus;

import com.yeliheng.eventbus.enums.ThreadType;
import com.yeliheng.eventbus.interfaces.IEvent;
import com.yeliheng.eventbus.interfaces.ISubscriber;

import java.lang.reflect.Method;

/**
 * Subscription 实体
 * @author Liam Ye
 */
public class Subscription implements ISubscriber {

    private final Object subscriber;

    private final Method method;

    private final ThreadType threadType;

    public Subscription(Object subscriber, Method method, ThreadType threadType) {
        this.subscriber = subscriber;
        this.method = method;
        this.threadType = threadType;
    }

    @Override
    public ThreadType getThreadType() {
        return threadType;
    }

    @Override
    public void invoke(IEvent event) {
        try {
            method.invoke(subscriber, event);
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected exception - " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Override
    public Object getSubscriber() {
        return subscriber;
    }

    public Method getMethod() {
        return method;
    }
}
