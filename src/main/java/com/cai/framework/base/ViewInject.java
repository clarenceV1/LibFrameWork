package com.cai.framework.base;

/**
 * Created by zhy on 16/4/22.
 */
public interface ViewInject<T>
{
    void inject(T t, Object source);
}
