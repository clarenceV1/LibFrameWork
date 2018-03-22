package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

import java.util.concurrent.ConcurrentHashMap;

class CacheMapping {
    private ConcurrentHashMap<String, CacheData> objs;

    CacheMapping() {
    }

    public ConcurrentHashMap<String, CacheData> getObjs() {
        return this.objs;
    }

    public void setObjs(ConcurrentHashMap<String, CacheData> objs) {
        this.objs = objs;
    }
}
