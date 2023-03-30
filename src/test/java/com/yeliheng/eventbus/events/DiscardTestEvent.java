package com.yeliheng.eventbus.events;

import com.yeliheng.eventbus.interfaces.IEvent;

/**
 * 丢弃事件，用于性能测试
 * Discard event, used for performance testing
 * @author Liam Ye
 */
public class DiscardTestEvent implements IEvent {
    // Just do nothing.
}
