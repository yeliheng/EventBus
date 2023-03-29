package com.yeliheng.eventbus;

import com.yeliheng.eventbus.annotations.Subscribe;
import com.yeliheng.eventbus.enums.ThreadType;
import com.yeliheng.eventbus.events.IntTestEvent;
import com.yeliheng.eventbus.events.StringTestEvent;
import org.junit.Test;

import java.util.logging.Logger;

public class EventBusBasicTest {

    public static final Logger logger = Logger.getLogger(EventBusBasicTest.class.getName());

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
        int count = 10000000;
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
