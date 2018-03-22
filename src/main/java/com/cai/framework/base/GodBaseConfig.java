package com.cai.framework.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 底层配置文件
 * Created by clarence on 2018/3/21.
 */

public class GodBaseConfig {
    private Map<String, Object> switchMap = null;

    private static final String IS_DEBUG = "debug";

    private static class SingletonHolder {
        private static final GodBaseConfig instance = new GodBaseConfig();
    }

    private GodBaseConfig() {
        switchMap = new HashMap<>();
    }

    public static final GodBaseConfig getInsatance() {
        return SingletonHolder.instance;
    }

    public void setSwitchMap(String key, Object value) {
        this.switchMap.put(key, value);
    }

    public boolean isDebug() {
        if (switchMap.get(IS_DEBUG) != null) {
            return (boolean) switchMap.get(IS_DEBUG);
        }
        return false;
    }

    public void setDebug(boolean isDebug) {
        this.switchMap.put(IS_DEBUG, isDebug);
    }
}
