package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

public class CacheData {
    private long modify_time;
    private Object data;

    public CacheData() {
    }

    public long getModify_time() {
        return this.modify_time;
    }

    public void setModify_time(long modify_time) {
        this.modify_time = modify_time;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        return this.data.equals(o);
    }
}
