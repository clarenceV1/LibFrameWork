package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;

import com.cai.framework.store.StoreFactory;
import com.cai.framework.store.cache.MeetyouCacheLoader;

import java.util.Stack;

/**
 * Created by clarence on 2018/1/11.
 */

public class GodBaseApplication extends Application {
    private Stack<Activity> store;
    private static GodBaseApplication baseApplication;

    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        StoreFactory.init(this);
        MeetyouCacheLoader.init(getAppContext());
        store = new Stack<>();
        registerActivityLifecycleCallbacks(new GodActivityLifecycleCallbacks(store));
        GodBaseConfig.getInsatance().setDebug(true);
    }

    public static GodBaseApplication getAppContext() {
        return baseApplication;
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public Activity getCurActivity() {
        return store.lastElement();
    }
}
