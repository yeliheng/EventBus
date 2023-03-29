package com.yeliheng.eventbus;

import com.yeliheng.eventbus.annotations.Subscribe;
import com.yeliheng.eventbus.enums.ThreadType;
import com.yeliheng.eventbus.interfaces.IEvent;
import com.yeliheng.eventbus.interfaces.ISubscriber;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 扫描被@Subscriber标注的方法，查找所有的订阅者
 */
public class SubscriberFinder {

    public static Map<Class<?>, List<ISubscriber>> find(Object subscriber) {
        Map<Class<?>, List<ISubscriber>> subscriberMap = new HashMap<>();
        Class<?> clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if(annotation != null) {
                checkSubscriberMethod(method);
                Class<?>[] parameterTypes = method.getParameterTypes();
                subscriberMap.computeIfAbsent(parameterTypes[0], k -> new ArrayList<>()).add(new Subscription(subscriber, method, annotation.threadType()));
            }
        }
        return subscriberMap;
    }

    private static void checkSubscriberMethod(Method method) {

        if(method.getParameterCount() != 1) {
            throw new IllegalArgumentException(String.format("[%s] Subscriber method: %s must have exactly 1 parameter", method.getDeclaringClass(), method.getName()));
        }
        if(method.getReturnType() != void.class) {
            throw new IllegalArgumentException(String.format("[%s] Subscriber method: %s must have a void return type", method.getDeclaringClass(), method.getName()));
        }
        if(!IEvent.class.isAssignableFrom(method.getParameterTypes()[0])) {
            throw new IllegalArgumentException(String.format("[%s] Subscriber method: %s must have a parameter type of IEvent", method.getDeclaringClass(), method.getName()));
        }

    }

}
