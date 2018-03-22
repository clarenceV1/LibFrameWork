package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

public interface Interceptor {
    byte[] write(byte[] var1);

    byte[] read(byte[] var1);
}
