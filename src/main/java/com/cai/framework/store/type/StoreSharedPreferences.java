package com.cai.framework.store.type;

import android.content.Context;
import android.content.SharedPreferences;

import com.cai.framework.store.base.IStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clarence on 2018/1/23.
 */

public class StoreSharedPreferences implements IStore {
    private String defaultFileName = "StoreSharedPreferences";
    private SharedPreferences.Editor editor;
    private Map<String, SharedPreferences> spMap;
    private Context context;
    private SharedPreferences sp;

    public StoreSharedPreferences(Context context) {
        this.context = context;
        spMap = new HashMap<>();
        sp = context.getSharedPreferences(defaultFileName, 0);
        editor = sp.edit();
        spMap.put(defaultFileName, sp);
    }

    public StoreSharedPreferences getSharedPreferences() {
        return getSharedPreferences(defaultFileName);
    }

    public StoreSharedPreferences getSharedPreferences(String name) {
        if (spMap.get(name) == null) {
            synchronized (this) {
                if (spMap.get(name) == null) {
                    SharedPreferences sp = context.getSharedPreferences(name, 0);
                    spMap.put(name, sp);
                }
            }
        }
        sp = spMap.get(name);
        editor = sp.edit();
        return this;
    }

    @Override
    public int read(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    @Override
    public float read(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    @Override
    public long read(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    @Override
    public boolean read(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    @Override
    public String read(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    @Override
    public boolean write(String key, int vaule) {
        editor.putInt(key, vaule);
        return editor.commit();
    }

    @Override
    public boolean write(String key, float vaule) {
        editor.putFloat(key, vaule);
        return editor.commit();
    }

    @Override
    public boolean write(String key, long vaule) {
        editor.putLong(key, vaule);
        return editor.commit();
    }

    @Override
    public boolean write(String key, boolean vaule) {
        editor.putBoolean(key, vaule);
        return editor.commit();
    }

    @Override
    public boolean write(String key, String vaule) {
        editor.putString(key, vaule);
        return editor.commit();
    }
}
