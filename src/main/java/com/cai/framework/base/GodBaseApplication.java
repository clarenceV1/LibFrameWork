package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.cai.framework.dagger.component.DaggerFrameWorkComponent;
import com.example.clarence.utillibrary.log.Log1Build;
import com.example.clarence.utillibrary.log.LogFactory;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by clarence on 2018/1/11.
 */

public class GodBaseApplication extends Application {
    private static GodBaseApplication application;
    @Inject
    public GodBaseConfig config;

    private RefWatcher refWatcher;
    GodActivityLifecycleCallbacks callbacks;

    public void onCreate() {
        super.onCreate();
        application = this;
        DaggerFrameWorkComponent.create().inject(this);
        config.setDebug(true);
        config.setUnitTest(true);
        LogFactory.getInsatance().init(new Log1Build(this).setDebug(config.isDebug()));

        registerLifecycle();

        initStetho();

        initLeakCanary();

        initRxBus();

    }

    private void initRxBus() {
//        RxBus.setMainScheduler(AndroidSchedulers.mainThread());
    }

    private void initLeakCanary() {
        if (!config.isUnitTest()) {
            refWatcher = setupLeakCanary();
        }
    }

    private void initStetho() {
        if (!config.isUnitTest()) {
            Stetho.initializeWithDefaults(this);
        }
    }

    /**
     * 注册生命周期监听
     */
    private void registerLifecycle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            callbacks = new GodActivityLifecycleCallbacks();
            registerActivityLifecycleCallbacks(callbacks);
        }
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }


    public static RefWatcher getRefWatcher(Context context) {
        GodBaseApplication application = (GodBaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }


    public static GodBaseApplication getAppContext() {
        return application;
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        if (callbacks != null) {
            return callbacks.getCurrentActivity();
        }
        return null;
    }

    /**
     * 应用是否在前台
     *
     * @return
     */
    public boolean isApplicationVisible() {
        if (callbacks != null) {
            return callbacks.isApplicationVisible();
        }
        return true;
    }

    /**
     * 应用是否在后台
     *
     * @return
     */
    public boolean isApplicationInForeground() {
        if (callbacks != null) {
            return callbacks.isApplicationInForeground();
        }
        return false;
    }
}
