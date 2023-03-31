# EventBus
English | [简体中文](./README_CN.md)

EventBus is a lightweight and high-performance event bus, suitable for Java and Spring/SpringBoot framework. EventBus is an implementation of the publish/subscribe pattern, which can be used to replace the traditional Java listener pattern, implement component decoupling, and make your code more flexible and easy to maintain.

## EventBus Features

- Easy to use, only three lines of code, you can elegantly implement an event publishing and listening function. The project uses the singleton pattern and provides a shared EventBus instance globally.
- Compatibility, support pure Java applications and Spring/SpringBoot framework, in the Spring framework, no need to manually register the subscriber, EventBus will automatically scan the Bean and register.
- High performance, support asynchronous, in asynchronous mode, the logic in the subscriber will run in a separate thread, and be managed by the thread pool.
- Tiny, EventBus has no heavily dependencies, is tiny, the jar file is only about 15 kb.

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

#### 2.1 Use in Spring/SpringBoot framework

1. Define an event and implement the IEvent interface.

```java
public class TestEvent implements IEvent { /* ... */ }
```
2. Subscribe to the event: Create an event receiving method in the class where you need to subscribe to the event, and annotate this method with the `@Subscribe` annotation. Please note that the class must be managed by Spring, which means it must be a Spring Bean.

```java
@Subscribe
public void onTestEvent(TestEvent event){
        // do something
}
```

The `threadType` thread type field in the @Subscribe annotation can be specified, that is, `@Subscribe(threadType = ThreadType.CURRENT)`. Currently, two optional types are provided: `ThreadType.CURRENT`, `ThreadType.ASYNC`.

`ThreadType.CURRENT`: The default value, execute the subscriber method in the current thread, synchronous mode.

`ThreadType.ASYNC`: Execute the subscriber method in a separate thread, asynchronous mode.

3. Publishing an event: Call the `post` method of the EventBus from anywhere you want to publish an event.

```java
// post event
EventBus.post(new TestEvent());
```

#### 2.2 Use in pure Java application

1. Define an event and implement the IEvent interface.

```java
public class TestEvent implements IEvent { /* ... */ }
```

2. Subscribe to the event: Create an event receiving method in the class where you need to subscribe to the event, and annotate this method with the `@Subscribe` annotation.

```java
@Subscribe
public void onTestEvent(TestEvent event){
        // do something
}
```

3. Register and publish events: Call the register method of EventBus to register subscribers, passing in a reference to the class and call the post method to publish events.

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

When using the Spring/SpringBoot framework, EventBus will automatically scan the beans and register them as event subscribers, without the need for manual management of event subscribers.

There are also some [examples](./src/test/java/com/yeliheng/eventbus/EventBusBasicTest.java).

Enjoy it!
