package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.cai.lib.logger.AndroidLogAdapter;
import com.cai.lib.logger.FormatStrategy;
import com.cai.lib.logger.Logger;
import com.cai.lib.logger.PrettyFormatStrategy;
import com.example.clarence.utillibrary.ToastUtils;
import com.example.clarence.utillibrary.log.Log1Build;
import com.example.clarence.utillibrary.log.LogFactory;
import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class GodBaseApplication extends Application {
    private static GodBaseApplication application;

    GodBaseConfig config;

    GodActivityLifecycleCallbacks callbacks;
    private RefWatcher refWatcher;

    public void onCreate() {
        super.onCreate();
        application = this;
        initConfig();

        LogFactory.getInsatance().init(new Log1Build(this).setDebug(config.isDebug()));

        initWebProtocol();

        registerLifecycle();


        initRxBus();

        initToast();

        initLog();

//        initStetho();
//
//        initBlockCanary(config.isDebug());
//
//        initStrictMode(config.isDebug());
//
//        initLeakCanary();
    }

    private void initConfig() {
        config = GodBaseConfig.getInstance();
        config.setDebug(isDebug());
        config.setBaseUrl(getBaseUrl());
    }

    public abstract void initWebProtocol();

    public abstract String getBaseUrl();

    public abstract boolean isDebug();

    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("MyLog")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        AndroidLogAdapter androidLogAdapter = new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        };
        Logger.addLogAdapter(androidLogAdapter);
    }

    private void initToast() {
        ToastUtils.initToast(application);
    }

    private void initRxBus() {
//        RxBus.setMainScheduler(AndroidSchedulers.mainThread());
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

    private void initBlockCanary(boolean isTest) {
        if (isTest) {
            BlockCanary.install(GodBaseApplication.getAppContext(), new GodBlockCanaryContext()).start();
        }
    }

    private void initStrictMode(boolean isTest) {
        if (isTest && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    private void initStetho() {
        if (!config.isUnitTest()) {
            Stetho.initializeWithDefaults(this);
        }
    }

    private void initLeakCanary() {
        if (!config.isUnitTest()) {
            refWatcher = setupLeakCanary();
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
}
