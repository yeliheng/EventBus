package com.yeliheng.eventbus.events;

import com.yeliheng.eventbus.interfaces.IEvent;

public class StringTestEvent implements IEvent
{
    private String str;

    public StringTestEvent(String str)
    {
        this.str = str;
    }

    public String getStr()
    {
        return str;
    }
}
