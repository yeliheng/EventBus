package com.yeliheng.eventbus.interfaces;

import com.yeliheng.eventbus.enums.ThreadType;

public interface ISubscriber {

    ThreadType getThreadType();

    public Object getSubscriber();

    void invoke(IEvent event);

}
