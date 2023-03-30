# EventBus
简体中文 | [English](./README.md)

EventBus 是一个轻量级的高性能事件总线，适用于 Java 以及 Spring 框架。EventBus 是一个发布/订阅模式的实现，它可以用来替代传统的 Java 监听器模式，实现组件间的解耦，让你的代码更加灵活，易于维护。
## EventBus 特性
- 易于使用，只需要三行代码即可优雅地实现一个事件发布和监听功能。项目使用单例模式，可全局共享一个 EventBus 实例。
- 高性能，支持异步，在异步模式下，订阅者中的逻辑将会在独立的线程中运行，并通过线程池进行管理。
- 轻量化，EventBus 无任何外部依赖，体积小，jar 文件仅约 10 kb。
## 环境要求
- JDK 1.8+
## 使用方法
### 一、引入Maven依赖
```xml
<dependency>
    <groupId>com.yeliheng.eventbus</groupId>
    <artifactId>eventbus</artifactId>
    <version>1.0.0</version>
</dependency>
```
### 二、开始使用

1. 定义事件，实现 IEvent 接口
```java
public class TestEvent implements IEvent { /* ... */ }
```
2. 订阅该事件：创建一个事件接收方法，并将 **@Subscribe** 注解标注在该方法上。

```java
@Subscribe
public void onTestEvent(TestEvent event){
        // do something
}
```
@Subscribe注解中可指定`threadType`线程类型字段，即`@Subscribe(threadType = ThreadType.CURRENT)`。目前提供两种可选类型：`ThreadType.CURRENT`，`ThreadType.ASYNC`。

`ThreadType.CURRENT`: 默认值，在当前线程中执行订阅者方法，同步方式。

`ThreadType.ASYNC`: 在独立的线程中执行订阅者方法，异步方式。

3. 注册并发布事件

```java
public static void main(String[] args){
    // 注册
    EventBus.getInstance().register(this);
    // 发布事件
    EventBus.post(new TestEvent());
    // 取消注册，释放内存
    EventBus.getInstance().unregister(this);    
}
```

本项目采用单例事件总线模式，可在项目中的任何位置调用`EventBus.getInstance()`来获取总线实例，也可以创建一个全局实例以便直接调用。事件注册可按需注册，也可在同一位置进行统一管理。

如有事件生命周期需求，请在生命周期开始时调用`register()`方法，并在生命周期结束时调用`unregister()`。

完整示例参见：[EventBusBasicTest](./src/test/java/com/yeliheng/eventbus/EventBusBasicTest.java)

祝使用愉快！