package com.cai.framework.manager;

import android.content.Context;

import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.base.GodBaseConfig;
import com.example.clarence.utillibrary.log.ILog;
import com.example.clarence.utillibrary.log.Log1Build;
import com.example.clarence.utillibrary.log.LogBaseBuild;
import com.example.clarence.utillibrary.log.LogFactory;

/**
 * Created by clarence on 2018/3/23.
 */

public class LogDock {
    /**
     * 初始化一次就够了
     */
    public static void initLog() {
        Context context = GodBaseApplication.getAppContext();
        boolean isDebug = GodBaseConfig.getInsatance().isDebug();
        LogFactory.getInsatance().init(new Log1Build(context).setDebug(isDebug));
    }

    public static ILog getLog() {
        return LogFactory.getInsatance().getiLog();
    }

}
