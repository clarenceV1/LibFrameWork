package com.cai.framework.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.cai.framework.BuildConfig;
import com.cai.lib.logger.Logger;
import com.github.moduth.blockcanary.BlockCanaryContext;

public class GodBlockCanaryContext extends BlockCanaryContext {

    private static final String TAG = "AppBlockCanaryContext";

    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            Context context = provideContext();
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, "provideQualifier exception", e);
        }
        return qualifier;
    }

    @Override
    public String provideUid() {
        return "87224330";
    }

    @Override
    public String provideNetworkType() {
        return "wifi";
    }

    @Override
    public int provideBlockThreshold() {
        return 5000;
    }

    @Override
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }


    @Override
    public boolean stopWhenDebugging() {
        return true;
    }
}
