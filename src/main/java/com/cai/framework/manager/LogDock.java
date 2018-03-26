package com.cai.framework.manager;

import com.cai.framework.base.GodBaseConfig;
import com.example.clarence.utillibrary.log.LogFactory;

/**
 * Created by clarence on 2018/3/23.
 */

public class LogDock {
    /**
     * 初始化一次就够了
     */
    public static void initLog() {
        boolean isDebug = GodBaseConfig.getInsatance().isDebug();
        LogFactory.getInsatance().init(1, isDebug);
    }

    public static void error(String tag, String... msg) {
        LogFactory.getInsatance().error(tag, msg);
    }

    public static void warn(String tag, String... msg) {
        LogFactory.getInsatance().warn(tag, msg);
    }

    public static void info(String tag, String... msg) {
        LogFactory.getInsatance().info(tag, msg);
    }

    public static void debug(String tag, String... msg) {
        LogFactory.getInsatance().debug(tag, msg);
    }

    public static void verbose(String tag, String... msg) {
        LogFactory.getInsatance().verbose(tag, msg);
    }

    public static void showLogPosition(String tag, String... msg) {
        LogFactory.getInsatance().showLogPosition(tag, msg);
    }
}
