package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cai.framework.manager.LogDock;
import com.cai.framework.manager.NetDock;
import com.example.clarence.datastorelibrary.store.StoreFactory;

import java.util.Stack;

/**
 * Created by clarence on 2018/1/11.
 */

public class GodBaseApplication extends Application {
    private Stack<Activity> store;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onCreate() {
        super.onCreate();
        initConfig();
        NetDock.initNet(this);
        LogDock.initLog(this);
        initStore();
        store = new Stack<>();
        registerActivityLifecycleCallbacks(new GodActivityLifecycleCallbacks(store));
    }

    private void initStore() {
        StoreFactory.init(this);
    }


    private void initConfig() {
        GodBaseConfig.getInsatance().setDebug(true);
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