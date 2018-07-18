package com.cai.framework.dataStore;

/**
 * Created by clarence on 2018/4/9.
 */

public interface ISharePreference {
    int read(String key, int defaultValue);

    float read(String key, float defaultValue);

    long read(String key, long defaultValue);

    boolean read(String key, boolean defaultValue);

    String read(String key, String defaultValue);

    boolean write(String key, int vaule);

    boolean write(String key, float vaule);

    boolean write(String key, long vaule);

    boolean write(String key, boolean vaule);

    boolean write(String key, String vaule);

}
