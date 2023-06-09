package com.yeliheng.eventbus;

import com.yeliheng.eventbus.annotations.Subscribe;
import com.yeliheng.eventbus.enums.ThreadType;
import com.yeliheng.eventbus.events.IntTestEvent;
import com.yeliheng.eventbus.events.StringTestEvent;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 事件总线基础测试用例
 * EventBus basic test case
 * @author Liam Ye
 */
public class EventBusBasicTest {

    public static final Logger logger = LoggerFactory.getLogger(EventBusBasicTest.class);

    public EventBus eventBus = EventBus.getInstance();

    @Test
    public void testRegisterAndPost() {
        eventBus.register(this);
        EventBus.post(new IntTestEvent(666));
        eventBus.unregister(this);
    }

    @Test
    public void testRegisterTwice() {
        eventBus.register(this);
        eventBus.register(this);
        EventBus.post(new IntTestEvent(666));
    }

    @Test
    public void testPostManyTimes() {
        eventBus.register(this);
        int count = 1000000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            EventBus.post(new StringTestEvent("Hello, World!"));
        }
        long time = System.currentTimeMillis() - start;
        logger.info("Posted " + count + " events in " + time + "ms");
    }

    @Subscribe
    public void onIntTestEvent(IntTestEvent event) {
        logger.info("onIntTestEvent: " + event.getValue());
    }

    @Subscribe(threadType = ThreadType.ASYNC)
    public void onIntTestEventAsync(IntTestEvent event) {
        logger.info("I am second: " + event.getValue());
    }


}
