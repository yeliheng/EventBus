package com.yeliheng.eventbus.spring;

import com.yeliheng.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * Spring下自动注册所有带有@Subscribe注解的Bean
 * Auto register all beans with @Subscribe annotation in Spring
 * @author Liam Ye
 */
public class EventBusAutoRegister implements BeanPostProcessor {

    public static final Logger logger = LoggerFactory.getLogger(EventBusAutoRegister.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object realBean = AopProxyUtils.getSingletonTarget(bean);
        if(realBean == null) {
            realBean = bean;
        }
        EventBus.getInstance().register(realBean);
        return bean;
    }
}
