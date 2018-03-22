package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * Created by clarence on 2018/1/11.
 */

public class GodActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks{
    public Stack<Activity> store;

    public GodActivityLifecycleCallbacks(Stack<Activity> store) {
        this.store = store;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        store.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        store.remove(activity);
    }
}
