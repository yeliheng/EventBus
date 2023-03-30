package com.yeliheng.eventbus.events;

import com.yeliheng.eventbus.interfaces.IEvent;

/**
 * 耗时操作事件测试
 * Time-consuming operation event test
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
