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
    private static final String IS_UNIT_TEST = "unitTest";
    private static final String BASE_URL = "base_url";//请求地址的域名，只能设置一次 天气预报域名==》"http://www.sojson.com"

    public static class LazyHolder {
        public final static GodBaseConfig INSTANCE = new GodBaseConfig();
    }

    private GodBaseConfig() {
        switchMap = new HashMap<>();
    }

    public static GodBaseConfig getInstance() {
        return LazyHolder.INSTANCE;
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

    public boolean isUnitTest() {
        if (switchMap.get(IS_UNIT_TEST) != null) {
            return (boolean) switchMap.get(IS_UNIT_TEST);
        }
        return false;
    }

    public void setUnitTest(boolean isDebug) {
        this.switchMap.put(IS_UNIT_TEST, isDebug);
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
