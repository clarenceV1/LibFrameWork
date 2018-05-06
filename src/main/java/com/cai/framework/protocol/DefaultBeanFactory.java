package com.cai.framework.protocol;

import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {
    private Map<Class<?>, Object> defaultBeanMap = new HashMap<>();

    public <T> DefaultBeanFactory put(Class<T> tClass, Object obj) {
        defaultBeanMap.put(tClass, obj);
        return this;
    }

    @Override
    public <T> Object getBean(Class<T> clazz) {
        return defaultBeanMap.get(clazz);
    }
}
