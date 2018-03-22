package com.cai.framework.store.base;

/**
 * Created by clarence on 2018/1/23.
 */

public class StoreImp implements IStore {


    @Override
    public int read(String key, int defaultValue) {
        return 0;
    }

    @Override
    public float read(String key, float defaultValue) {
        return 0;
    }

    @Override
    public long read(String key, long defaultValue) {
        return 0;
    }

    @Override
    public boolean read(String key, boolean defaultValue) {
        return false;
    }

    @Override
    public String read(String key, String defaultValue) {
        return null;
    }

    @Override
    public boolean write(String key, int vaule) {
        return false;
    }

    @Override
    public boolean write(String key, float vaule) {
        return false;
    }

    @Override
    public boolean write(String key, long vaule) {
        return false;
    }

    @Override
    public boolean write(String key, boolean vaule) {
        return false;
    }

    @Override
    public boolean write(String key, String vaule) {
        return false;
    }
}
