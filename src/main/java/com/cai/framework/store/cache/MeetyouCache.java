package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

import java.util.Map;
import java.util.Set;

public interface MeetyouCache {
    int getInt(String var1, int var2);

    long getLong(String var1, long var2);

    String getString(String var1, String var2);

    boolean getBoolean(String var1, boolean var2);

    float getFloat(String var1, float var2);

    double getDouble(String var1, double var2);

    int size();

    void put(String var1, Object var2);

    <T> T get(String var1, Class<T> var2);

    Object remove(String var1);

    void clear();

    boolean containsKey(String var1);

    boolean containsValue(Object var1);

    Set<Map.Entry<String, CacheData>> entrySet();

    boolean isEmpty();

    Set<String> keySet();

    void putAll(MeetyouMemoryCache var1);
}

