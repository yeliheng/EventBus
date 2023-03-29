package com.yeliheng.eventbus.events;

import com.yeliheng.eventbus.interfaces.IEvent;

public class IntTestEvent implements IEvent
{
    private int value;

    public IntTestEvent(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

}
