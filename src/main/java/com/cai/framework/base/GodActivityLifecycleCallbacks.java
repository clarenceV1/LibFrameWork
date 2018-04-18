package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.cai.framework.manager.LogDock;
import com.facebook.stetho.common.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by clarence on 2018/1/11.
 */

public class GodActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    public Stack<Activity> store = new Stack<>();
    private static long resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        store.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;

        LogDock.getLog().debug("activityStack", "我在后台运行吗？" + isApplicationInForeground());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        LogDock.getLog().debug("activityStack", "我在前台运行吗？" + isApplicationVisible());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        store.remove(activity);
        printLiveActivity();
    }

    private void printLiveActivity() {
        LogDock.getLog().debug("activityStack", ">>>>>>>>>>>>>>>");
        Observable.fromIterable(store).subscribe(new Consumer<Activity>() {
            @Override
            public void accept(Activity activity) throws Exception {
                LogDock.getLog().debug("activityStack", "name: ", activityInfo(activity));
            }
        });
    }

    public String activityInfo(Activity activity) {
        return activity.getClass().getSimpleName();

    }

    public boolean isApplicationVisible() {
        return started > stopped;
    }

    public boolean isApplicationInForeground() {
        // 当所有 Activity 的状态中处于 resumed 的大于 paused 状态的，即可认为有Activity处于前台状态中
        return resumed > paused;
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return store.lastElement();
    }
}
