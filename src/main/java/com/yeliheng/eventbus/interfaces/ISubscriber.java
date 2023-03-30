package com.yeliheng.eventbus.interfaces;

import com.yeliheng.eventbus.enums.ThreadType;

/**
 * 订阅者接口
 * Subscriber interface
 * @author Liam Ye
 */
public interface ISubscriber {

    ThreadType getThreadType();

    public Object getSubscriber();

    void invoke(IEvent event);

}
