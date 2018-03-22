package com.cai.framework.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.cai.framework.log.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clarence on 2018/3/21.
 */
public class BaseLifecycleObserver implements LifecycleObserver {
    public Lifecycle lifecycle;
    public Map<String, Object> data = new HashMap<>();
    public static final String CLASS_NAME = "className";
    public boolean isDebug;

    private String getClassName() {
        if (data != null && data.get(CLASS_NAME) != null) {
            return data.get(CLASS_NAME).toString();
        }
        return "请设置类名称";
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void ON_CREATE() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver", getClassName(), ":ON_CREATE");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void ON_START() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver", getClassName(), ":ON_START");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void ON_RESUME() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver", getClassName(), ":ON_RESUME");
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void ON_PAUSE() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver", getClassName(), ":ON_PAUSE");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void ON_STOP() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver", getClassName(), ":ON_STOP");
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void ON_DESTROY() {
        if (isDebug) {
            LogUtils.getInsatance().debug("LifecycleObserver", getClassName(), ":ON_DESTROY");
        }
    }
}