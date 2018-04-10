package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cai.framework.dagger.component.DaggerFrameWorkComponent;
import com.cai.framework.manager.LogDock;
import com.example.clarence.utillibrary.log.Log1Build;
import com.example.clarence.utillibrary.log.LogFactory;

import java.util.Stack;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/1/11.
 */

public class GodBaseApplication extends Application {
    private Stack<Activity> store;
    private static GodBaseApplication application;
    @Inject
    public GodBaseConfig config;

    public void onCreate() {
        super.onCreate();
        application = this;
        DaggerFrameWorkComponent.create().inject(this);
        config.setDebug(true);
        LogFactory.getInsatance().init(new Log1Build(this).setDebug(config.isDebug()));
        store = new Stack<>();
        registerActivityLifecycleCallbacks(new GodActivityLifecycleCallbacks(store));
    }

    public static GodBaseApplication getAppContext() {
        return application;
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
