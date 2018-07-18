package com.cai.framework.dataStore;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by clarence on 2018/4/9.
 */

public class StoreForSharePreference implements ISharePreference {
    Context context;
    String spName;
    private static final String DEFAULT_NAME = "default";
    SharedPreferences sharedPreferences;

    private StoreForSharePreference(Builder builder) {
        context = builder.context;
        spName = builder.spName;
        sharedPreferences = loadSP();
    }

    public SharedPreferences loadSP() {
        if (TextUtils.isEmpty(spName)) {
            spName = DEFAULT_NAME;
        }
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    @Override
    public int read(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public float read(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public long read(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public boolean read(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public String read(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public boolean write(String key, int vaule) {
        return sharedPreferences.edit().putInt(key, vaule).commit();
    }

    @Override
    public boolean write(String key, float vaule) {
        return sharedPreferences.edit().putFloat(key, vaule).commit();
    }

    @Override
    public boolean write(String key, long vaule) {
        return sharedPreferences.edit().putLong(key, vaule).commit();
    }

    @Override
    public boolean write(String key, boolean vaule) {
        return sharedPreferences.edit().putBoolean(key, vaule).commit();
    }

    @Override
    public boolean write(String key, String vaule) {
        return sharedPreferences.edit().putString(key, vaule).commit();
    }

    public static final class Builder {
        private Context context;
        private String spName;

        public Builder() {
        }

        public Builder context(Context val) {
            context = val;
            return this;
        }

        public Builder spName(String val) {
            spName = val;
            return this;
        }

        public StoreForSharePreference build() {
            return new StoreForSharePreference(this);
        }
    }
}
