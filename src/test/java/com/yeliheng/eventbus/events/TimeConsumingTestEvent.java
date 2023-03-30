package com.yeliheng.eventbus.events;

import com.yeliheng.eventbus.interfaces.IEvent;

/**
 * @author Liam Ye
 */
public class TimeConsumingTestEvent implements IEvent {

        private final int value;

        public TimeConsumingTestEvent(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

}
