package com.cai.framework.store;

import android.content.Context;

import com.cai.framework.store.base.IStore;
import com.cai.framework.store.base.StoreType;
import com.cai.framework.store.type.StoreFile;
import com.cai.framework.store.type.StoreHttp;
import com.cai.framework.store.type.StoreProvider;
import com.cai.framework.store.type.StoreSQLite;
import com.cai.framework.store.type.StoreSdCard;
import com.cai.framework.store.type.StoreSharedPreferences;

import java.util.HashMap;

/**
 * Created by clarence on 2018/1/23.
 */

public class StoreFactory {

    public static Context context;
    private static boolean initFinish = false;
    private static HashMap<StoreType, IStore> storeMap = new HashMap<>();

    private static final class Holder {
        private static final StoreFactory instance = new StoreFactory();
    }

    /**
     * 必须先初始化
     *
     * @param context
     */
    public static void init(Context context) {
        Holder.instance.context = context;
        Holder.instance.initFinish = true;
    }

    public static IStore getInstance(StoreType type) {
        return getInstance(type, null);
    }

    public static IStore getInstance(StoreType type, String path) {
        if (!initFinish) {
            try {
                throw new Exception("请先调用StoreFactory.init()初始化！！！");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return generateStore(type,path);
        }
    }

    private static IStore generateStore(StoreType type,String path) {
        IStore store = storeMap.get(type);
        if (store != null) {
            return store;
        }
        switch (type) {
            case SHARED_PREFERENCE:
                store = new StoreSharedPreferences(context);
                break;
            case File:
                store = new StoreFile();
                break;
            case HTTP:
                store = new StoreHttp();
                break;
            case SDCARD:
                store = new StoreSdCard();
                break;
            case SQLITE:
                store = new StoreSQLite();
                break;
            case CONTENT_PROVIDER:
                store = new StoreProvider();
                break;
        }
        return store;
    }
}
