package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

import android.content.Context;

import com.cai.framework.utils.StringUtils;

import java.util.HashMap;


public class MeetyouCacheLoader {
    private static Context mContext;
    private HashMap<String, MeetyouFileCache> mFileCaches;
    private Interceptor mInterceptor;

    private final static class Holder {
        private final static MeetyouCacheLoader mMeetyouCache = new MeetyouCacheLoader();

        private Holder() {
        }
    }

    private MeetyouCacheLoader() {
        this.mFileCaches = new HashMap();
    }

    public static MeetyouCacheLoader getInstance() {
        return MeetyouCacheLoader.Holder.mMeetyouCache;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public HashMap<String, MeetyouFileCache> getFileCaches() {
        return this.mFileCaches;
    }

    public AbstractMeetyouCache findCache(String path, String name, boolean crypt, int mode) {
        return getFileCache(path, name, crypt);
    }

    public AbstractMeetyouCache findCache(String path, String name, boolean crypt) {
        return this.findCache(path, name, crypt, 0);
    }

    public AbstractMeetyouCache findCache(String name, boolean crypt) {
        return this.findCache(null, name, crypt, 0);
    }

    public AbstractMeetyouCache findCache(String name) {
        return this.findCache(null, name, false, 0);
    }

    public AbstractMeetyouCache findCache(String path, String name) {
        return this.findCache(path, name, false, 0);
    }

    private Interceptor getInterceptor() {
        if (this.mInterceptor == null) {
            this.mInterceptor = new Interceptor() {
                public byte[] write(byte[] src) {
                    try {
                        return AESUtils.encrypt(src);
                    } catch (Exception var3) {
                        var3.printStackTrace();
                        return src;
                    }
                }

                public byte[] read(byte[] src) {
                    try {
                        return AESUtils.decrypt(src);
                    } catch (Exception var3) {
                        var3.printStackTrace();
                        return src;
                    }
                }
            };
        }
        return this.mInterceptor;
    }

    private MeetyouFileCache getFileCache(String path, String name, boolean crypt) {
        String key = StringUtils.buildString(new Object[]{path, name});
        MeetyouFileCache meetyouFileCache = this.mFileCaches.get(key);
        if (meetyouFileCache == null) {
            meetyouFileCache = new MeetyouFileCache(mContext, path, name);
            if (crypt) {
                meetyouFileCache.setInterceptor(this.getInterceptor());
            }
            this.mFileCaches.put(key, meetyouFileCache);
            meetyouFileCache.read();
        }
        return meetyouFileCache;
    }
}

