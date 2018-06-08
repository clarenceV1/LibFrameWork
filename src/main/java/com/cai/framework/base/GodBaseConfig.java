package com.cai.framework.base;

import android.text.TextUtils;

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
    private static final String IS_UNIT_TEST = "unitTest";
    private static final String BASE_URL = "base_url";
    private static String baseUrl;//请求地址的域名，只能设置一次 天气预报域名==》"http://www.sojson.com"

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

    public static String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(this.baseUrl)) {
            this.baseUrl = baseUrl;
        }
    }
}
