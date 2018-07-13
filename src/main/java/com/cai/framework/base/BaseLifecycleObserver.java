package com.cai.framework.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.cai.framework.manager.LogDock;
import com.example.clarence.utillibrary.log.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clarence on 2018/3/21.
 */
public class BaseLifecycleObserver implements LifecycleObserver {
    public Lifecycle lifecycle;
    public Map<String, Object> data = new HashMap<>();
    public static final String CLASS_NAME = "className";

    public BaseLifecycleObserver() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void ON_CREATE() {
        if (data.get(CLASS_NAME) != null) {
            LogDock.getLog().debug("LifecycleObserver", data.get(CLASS_NAME).toString(), ":ON_CREATE");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void ON_START() {
        if (data.get(CLASS_NAME) != null) {
            LogDock.getLog().debug("LifecycleObserver", data.get(CLASS_NAME).toString(), ":ON_START");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void ON_RESUME() {
        if (data.get(CLASS_NAME) != null) {
            LogDock.getLog().debug("LifecycleObserver", data.get(CLASS_NAME).toString(), ":ON_RESUME");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void ON_PAUSE() {
        if (data.get(CLASS_NAME) != null) {
            LogDock.getLog().debug("LifecycleObserver", data.get(CLASS_NAME).toString(), ":ON_PAUSE");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void ON_STOP() {
        if (data.get(CLASS_NAME) != null) {
            LogDock.getLog().debug("LifecycleObserver", data.get(CLASS_NAME).toString(), ":ON_STOP");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void ON_DESTROY() {
        if (data.get(CLASS_NAME) != null) {
            LogDock.getLog().debug("LifecycleObserver", data.get(CLASS_NAME).toString(), ":ON_DESTROY");
        }
    }
}