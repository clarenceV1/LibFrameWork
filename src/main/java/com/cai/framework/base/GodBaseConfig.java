package com.cai.framework.base;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 底层配置文件
 * Created by clarence on 2018/3/21.
 */
@Singleton
public class GodBaseConfig {
    private Map<String, Object> switchMap = null;

    private static final String IS_DEBUG = "debug";
    private static final String BASE_URL = "base_url";

    @Inject
    public GodBaseConfig() {
        switchMap = new HashMap<>();
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

    public String getBaseUrl() {
        if (switchMap.get(BASE_URL) != null) {
            return (String) switchMap.get(BASE_URL);
        }
        return null;
    }

    public void setBaseUrl(String baseUrl) {
        this.switchMap.put(BASE_URL, baseUrl);
    }
}
