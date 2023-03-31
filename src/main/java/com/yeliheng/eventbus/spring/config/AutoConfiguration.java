package com.yeliheng.eventbus.spring.config;

import com.yeliheng.eventbus.EventBus;
import com.yeliheng.eventbus.spring.EventBusAutoRegister;
import com.yeliheng.eventbus.spring.context.EventBusContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 自动配置Bean
 * Auto configuration bean
 * @author Liam Ye
 */

@Configuration
public class AutoConfiguration {

    @Bean(name = "eventBusContext")
    public EventBusContext eventBusContext() {
        return new EventBusContext();
    }

    @Bean(name = "eventBusAutoRegister")
    public EventBusAutoRegister eventBusAutoRegister() {
        return new EventBusAutoRegister();
    }
}
