package com.yeliheng.eventbus.events;

import com.yeliheng.eventbus.interfaces.IEvent;

/**
 * 整型事件测试
 * Integer event test
 * @author Liam Ye
 */
public class IntTestEvent implements IEvent
{
    private final int value;

    public IntTestEvent(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

}
