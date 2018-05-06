package com.cai.framework.protocol;

/**
 * Created by hxd on 16/3/24.
 */
public interface BeanFactory {
    <T> Object getBean(Class<T> clazz);
}
