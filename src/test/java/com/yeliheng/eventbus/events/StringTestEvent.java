package com.yeliheng.eventbus.events;

import com.yeliheng.eventbus.interfaces.IEvent;

/**
 * 字符串事件测试
 * @author Liam Ye
 */
public class StringTestEvent implements IEvent
{
    private final String str;

    public StringTestEvent(String str)
    {
        this.str = str;
    }

    public String getString()
    {
        return str;
    }
}
