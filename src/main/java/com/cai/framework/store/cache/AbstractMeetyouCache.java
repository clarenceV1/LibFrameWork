package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public abstract class AbstractMeetyouCache extends MeetyouMemoryCache {
    protected String mName;
    protected String mPath;
    private Interceptor mInterceptor;

    public AbstractMeetyouCache() {
    }

    public abstract String getName();

    public abstract String getPath();

    public abstract boolean write();

    public abstract boolean read();

    protected abstract void setContext(Context var1);

    public abstract boolean delete();

    protected abstract void setName(String var1);

    protected abstract void setPath(String var1);

    protected byte[] onInterceptorWrite(byte[] src) {
        return this.mInterceptor != null ? this.mInterceptor.write(src) : src;
    }

    protected byte[] onInterceptorRead(byte[] src) {
        return this.mInterceptor != null ? this.mInterceptor.read(src) : src;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.mInterceptor = interceptor;
    }

    protected void write(File cache) throws Exception {
        BufferedSink sink = null;
        try {
            CacheMapping mapping = new CacheMapping();
            mapping.setObjs(this.mCache);
            sink = Okio.buffer(Okio.sink(cache));
            byte[] des = this.onInterceptorWrite(JSON.toJSONString(mapping).getBytes());
            sink.write(des);
            sink.flush();
        } finally {
            if (sink != null) {
                sink.close();
            }
        }
    }

    protected void read(File cache) throws Exception {
        BufferedSource source = null;
        try {
            source = Okio.buffer(Okio.source(cache));
            if (cache.exists()) {
                this.mCache.clear();
                this.mCache = null;
                byte[] des = this.onInterceptorRead(source.readByteArray());
                CacheMapping cacheMapping =JSON.parseObject(new String(des), CacheMapping.class);
                this.mCache = cacheMapping.getObjs();
                return;
            }
            this.mCache = new ConcurrentHashMap();
        } finally {
            if (this.mCache == null) {
                this.mCache = new ConcurrentHashMap();
            }

            if (source != null) {
                source.close();
            }
        }
    }
}

