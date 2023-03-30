# EventBus
English | [简体中文](./README_CN.md)

EventBus is a lightweight and high-performance event bus, suitable for Java and Spring framework. EventBus is an implementation of the publish/subscribe pattern, which can be used to replace the traditional Java listener pattern, implement component decoupling, and make your code more flexible and easy to maintain.

## EventBus Features

- Easy to use, only three lines of code, you can elegantly implement an event publishing and listening function. The project uses the singleton pattern and provides a shared EventBus instance globally.
- High performance, support asynchronous, in asynchronous mode, the logic in the subscriber will run in a separate thread, and be managed by the thread pool.
- Tiny, EventBus has no external dependencies, is tiny, the jar file is only about 10 kb.

## Environment Requirements

- JDK 1.8+

## Usage

### 1. Import Maven dependency

```xml
<dependency>
    <groupId>com.yeliheng.eventbus</groupId>
    <artifactId>eventbus</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Start to use

1. Define an event and implement the IEvent interface.

```java
public class TestEvent implements IEvent { /* ... */ }
```

2. Subscribe to the event: create an event receiving method and annotate the method with **@Subscribe**.

```java
@Subscribe
public void onTestEvent(TestEvent event){
        // do something
}
```

The `threadType` thread type field in the @Subscribe annotation can be specified, that is, `@Subscribe(threadType = ThreadType.CURRENT)`. Currently, two optional types are provided: `ThreadType.CURRENT`, `ThreadType.ASYNC`.

`ThreadType.CURRENT`: The default value, execute the subscriber method in the current thread, synchronous mode.

`ThreadType.ASYNC`: Execute the subscriber method in a separate thread, asynchronous mode.

3. Register and publish events

```java
public static void main(String[] args){
    // register
    EventBus.getInstance().register(this);
    // post event
    EventBus.post(new TestEvent());
    // unregister, release memory
    EventBus.getInstance().unregister(this);    
}
```

This project uses the singleton event bus pattern, and you can call `EventBus.getInstance()` anywhere in the project to get the bus instance, or you can create a global instance for easy access. Event registration can be registered on demand or can be managed in the same location.

If you have the lifecycle requirements, please call the `EventBus.getInstance().unregister(this)` method to unregister the event when the object is destroyed.

There are also some [examples](./src/test/java/com/yeliheng/eventbus/EventBusBasicTest.java).

Enjoy it!
