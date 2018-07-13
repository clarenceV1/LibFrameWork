package com.cai.framework.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GodActivityManger implements Application.ActivityLifecycleCallbacks {

    private Application mApplication;
    private ArrayList<Activity> activitys;
    ArrayList<Activity> cacheActivitys;

    private static class Holder {
        static GodActivityManger instance = new GodActivityManger();
    }


    public static GodActivityManger getInstance() {
        return Holder.instance;
    }

    private GodActivityManger() {
        activitys = new ArrayList<>();
        cacheActivitys = new ArrayList<>();
    }

    public void init(Application application) {
        mApplication = application;
        //注册生命周期监听
        mApplication.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d("GodActivityManger", activity.getClass().getSimpleName());
        activitys.add(activity);
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
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activitys.remove(activity);
    }

    public void exitApp() {
        for (Activity activity : activitys) {
            activity.finish();
        }
        System.exit(0);
    }
}
