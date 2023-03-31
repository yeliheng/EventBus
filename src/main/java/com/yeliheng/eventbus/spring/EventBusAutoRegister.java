package com.yeliheng.eventbus.spring;

import com.yeliheng.eventbus.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * Spring下自动注册所有带有@Subscribe注解的Bean
 * Auto register all beans with @Subscribe annotation in Spring
 * @author Liam Ye
 */
public class EventBusAutoRegister implements BeanPostProcessor {

    private final EventBus eventBus = EventBus.getInstance();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        eventBus.register(bean);
        return bean;
    }
}
