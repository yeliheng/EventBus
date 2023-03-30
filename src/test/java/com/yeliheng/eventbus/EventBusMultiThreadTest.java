package com.yeliheng.eventbus;

import com.yeliheng.eventbus.annotations.Subscribe;
import com.yeliheng.eventbus.enums.ThreadType;
import com.yeliheng.eventbus.events.DiscardTestEvent;
import com.yeliheng.eventbus.events.StringTestEvent;
import com.yeliheng.eventbus.events.TimeConsumingTestEvent;
import org.junit.Test;
import java.util.logging.Logger;

/**
 * 事件总线多线程接收测试
 * @author Liam Ye
 */
public class EventBusMultiThreadTest {
    public static final Logger logger = Logger.getLogger(EventBusMultiThreadTest.class.getName());

    public EventBus eventBus = EventBus.getInstance();

    @Test
    public void testRegisterAndPost() {
        eventBus.register(this);
        EventBus.post(new StringTestEvent("Hello, World!"));
        eventBus.unregister(this);
    }

    @Test
    public void testTimeConsumingOperation() throws InterruptedException {
        eventBus.register(this);
        EventBus.post(new TimeConsumingTestEvent(666));
        for (int i = 0; i < 3; i ++) {
            logger.info("[Sync]-" + i);
            Thread.sleep(1000);
        }
        eventBus.unregister(this);
        logger.info("[Sync] Operation completed.");
        Thread.sleep(3000);
        logger.info("[Sync] Thread terminated");
    }

    @Test
    public void testPostManyEventsWithAsync() {
        eventBus.register(this);
        int count = 1000000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            EventBus.post(new DiscardTestEvent());
        }
        long time = System.currentTimeMillis() - start;
        logger.info("Posted " + count + " events in " + time + "ms");
        eventBus.unregister(this);
    }

    // 异步 - 1
    @Subscribe(threadType = ThreadType.ASYNC)
    public void onStringTestEventAsync(StringTestEvent event) {
         logger.info("[ASYNC-1] " + event.getString());
    }

    // 异步 - 2
    @Subscribe(threadType = ThreadType.ASYNC)
    public void onStringTestEventAsync2(StringTestEvent event) {
        logger.info("[ASYNC-2] " + event.getString());
    }

    // 同步 - 1
    @Subscribe
    public void onStringTestEvent(StringTestEvent event) {
        logger.info("[CURRENT-1] " + event.getString());
    }

    // 同步 - 2
    @Subscribe
    public void onStringTestEvent2(StringTestEvent event) {
        logger.info("[CURRENT-2] " + event.getString());
    }

    @Subscribe(threadType = ThreadType.ASYNC)
    public void onDiscardTestEvent(DiscardTestEvent event) {
        // Just do nothing.
    }

    // 耗时操作
    @Subscribe(threadType = ThreadType.ASYNC)
    public void onTimeConsumingTestEvent(TimeConsumingTestEvent event) throws InterruptedException {
        logger.info("[Async] Starting time-consuming operation...");
        Thread.sleep(5000);
        logger.info("[Async] Operation completed.");

    }

    // 同步对照操作
    @Subscribe
    public void onTimeConsumingTestEvent2(TimeConsumingTestEvent event) {
        logger.info("[Sync] Return " + event.getValue());
    }

}
