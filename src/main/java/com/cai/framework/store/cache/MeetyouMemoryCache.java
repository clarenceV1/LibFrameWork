package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MeetyouMemoryCache implements MeetyouCache {
    protected ConcurrentHashMap<String, CacheData> mCache = new ConcurrentHashMap();

    public MeetyouMemoryCache() {
    }

    private Map<String, CacheData> getCache() {
        return Collections.unmodifiableMap(this.mCache);
    }

    public void put(String key, Object val) {
        if (key != null && val != null) {
            long time = System.currentTimeMillis();
            CacheData cacheObject = this.mCache.get(key);
            if (cacheObject == null) {
                cacheObject = new CacheData();
            }

            cacheObject.setData(val);
            cacheObject.setModify_time(time);
            this.mCache.put(key, cacheObject);
            this.processInfoCache("MeetyouCache.last_put_time");
        } else {
            throw new NullPointerException("key or val is null.");
        }
    }

    protected void processInfoCache(String key) {
        CacheData infoCacheObject = this.mCache.get(key);
        if (infoCacheObject == null) {
            infoCacheObject = new CacheData();
        }

        infoCacheObject.setModify_time(System.currentTimeMillis());
        this.mCache.put(key, infoCacheObject);
    }

    public <T> T get(String key, Class<T> clazz) {
        if (key == null) {
            throw new NullPointerException("key is null.");
        } else {
            CacheData cacheData = this.mCache.get(key);
            if (cacheData == null) {
                return null;
            } else {
                Object obj = cacheData.getData();
                if (clazz == String.class) {
                    return (T) obj;
                } else if (clazz == Double.class) {
                    return (T) obj;
                } else if (clazz == Integer.class) {
                    return (T) obj;
                } else if (clazz == Long.class) {
                    return (T) obj;
                } else if (clazz == Boolean.class) {
                    return (T) obj;
                } else if (clazz == Float.class) {
                    return (T) obj;
                } else {
                    CacheData dataCacheObject;
                    if (obj.getClass() == String.class) {
                        obj = JSON.parseObject((String) obj, clazz);
                        dataCacheObject = new CacheData();
                        dataCacheObject.setData(obj);
                        dataCacheObject.setModify_time(System.currentTimeMillis());
                        this.mCache.put(key, dataCacheObject);
                    } else if (obj.getClass() == JSONObject.class) {
                        obj = JSON.toJavaObject((JSONObject) obj, clazz);
                        dataCacheObject = new CacheData();
                        dataCacheObject.setData(obj);
                        dataCacheObject.setModify_time(System.currentTimeMillis());
                        this.mCache.put(key, dataCacheObject);
                    } else if (obj.getClass() == JSONArray.class) {
                        obj = JSON.toJavaObject((JSONArray) obj, clazz);
                        dataCacheObject = new CacheData();
                        dataCacheObject.setData(obj);
                        dataCacheObject.setModify_time(System.currentTimeMillis());
                        this.mCache.put(key, dataCacheObject);
                    }

                    return (T) obj;
                }
            }
        }
    }

    public void clear() {
        this.mCache.clear();
    }

    public boolean containsKey(String key) {
        return this.getCache().containsKey(key);
    }

    public boolean containsValue(Object value) {
        CacheData dataCacheObject = new CacheData();
        dataCacheObject.setModify_time(System.currentTimeMillis());
        dataCacheObject.setData(value);
        return this.getCache().containsValue(dataCacheObject);
    }

    public Set<Map.Entry<String, CacheData>> entrySet() {
        return this.getCache().entrySet();
    }

    public boolean isEmpty() {
        return this.getCache().isEmpty();
    }

    public Set<String> keySet() {
        return this.getCache().keySet();
    }

    public void putAll(MeetyouMemoryCache cache) {
        this.mCache.putAll(cache.getCache());
    }

    public int size() {
        return this.mCache.size();
    }

    public Object remove(String key) {
        if (key == null) {
            throw new NullPointerException("key is null.");
        } else {
            return ((CacheData) this.mCache.remove(key)).getData();
        }
    }

    public int getInt(String key, int def) {
        Object value = this.get(key, Integer.class);
        return value != null ? ((Integer) value).intValue() : def;
    }

    public long getLong(String key, long def) {
        Object value = this.get(key, Long.class);
        return value != null ? ((Long) value).longValue() : def;
    }

    public String getString(String key, String def) {
        Object value = this.get(key, String.class);
        return value != null ? (String) value : def;
    }

    public boolean getBoolean(String key, boolean def) {
        Object value = this.get(key, Boolean.class);
        return value != null ? ((Boolean) value).booleanValue() : def;
    }

    public float getFloat(String key, float def) {
        Object value = this.get(key, Float.class);
        return value != null ? ((Float) value).floatValue() : def;
    }

    public double getDouble(String key, double def) {
        Object value = this.get(key, Double.class);
        return value != null ? ((Double) value).doubleValue() : def;
    }
}

