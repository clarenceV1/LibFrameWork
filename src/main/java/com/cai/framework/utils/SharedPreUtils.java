package com.cai.framework.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cai.framework.base.GodBaseApplication;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.encrypt.AESCrypt;

import java.security.GeneralSecurityException;


/**
 * gakes 2014.6.5
 * SharedPre封装工具类
 */
public class SharedPreUtils {
    private static SharedPreferences preferences;

    
    private static final String SHARE_MENU = "clientSp";

    public static final String AES_KEY = "@3jm$>/~.Kdd91V&amp;V)k9jn,UAH";

    private static GodBaseApplication baseApp = GodBaseApplication.getAppContext();

    /**
     * @return
     */
    public synchronized static void getSharedPreferencesInstance() {
        if (preferences == null) {
            try {
                preferences = baseApp.getSharedPreferences(SHARE_MENU, baseApp.MODE_PRIVATE);
            } catch (Exception e) {

            }
        }
    }

    public static final byte STRING = 0;
    public static final byte INT = 1;
    public static final byte BOOLEAN = 2;
    public static final byte LONG = 3;

    /**
     * @param key   键
     * @param value 值
     * @param type  传入值的类型
     * @type类型 SharedPreUtils.STRING， SharedPreUtils.INT，
     * SharedPreUtils.BOOLEAN， SharedPreUtils.LONG
     */
    public static void putValue(String key, Object value, byte type) {
        SharedPreUtils.getSharedPreferencesInstance();
        Editor edit = preferences.edit();
        switch (type) {
            case STRING:
                edit.putString(key, (String) value);
                break;
            case INT:
                edit.putInt(key, (Integer) value);
                break;
            case BOOLEAN:
                edit.putBoolean(key, (Boolean) value);
                break;
            case LONG:
                edit.putLong(key, (Long) value);
                break;
        }
        edit.commit();
    }

    /**
     * 实做数据存储
     *
     * @param key   键
     * @param value 值
     */

    private static void putValue(String key, Object value) {
        SharedPreUtils.getSharedPreferencesInstance();
        Editor editor = preferences.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else {
            try {
                editor.putString(key, AESCrypt.encrypt(AES_KEY, String.valueOf(value)));
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }

        editor.commit();
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void putString(String key, String value) {
        putValue(key, value);
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void putInt(String key, int value) {
        putValue(key, value);
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void putLong(String key, long value) {
        putValue(key, value);
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void putFloat(String key, float value) {
        putValue(key, value);
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        putValue(key, value);
    }

    /**
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static String getString(String key, String defValue) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getString(key, defValue);
    }

    /**
     * @param key 键
     * @return
     */
    public static String getString(String key) {
        String keyValue = null;
        try {
            SharedPreUtils.getSharedPreferencesInstance();
            keyValue = AESCrypt.decrypt(AES_KEY, preferences.getString(key, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.isEmpty(keyValue) ? "" : keyValue;
    }

    /**
     * @param key 键
     * @return
     */
    public static String getUserConfigString(String key) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getString(key, "");
    }

    /**
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static int getInt(String key, int defValue) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getInt(key, defValue);
    }

    /**
     * @param key 键
     * @return
     */
    public static int getInt(String key) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getInt(key, 0);
    }

    /**
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static long getLong(String key, long defValue) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getLong(key, defValue);
    }

    /**
     * @param key 键
     * @return
     */
    public static long getLong(String key) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getLong(key, 0);
    }

    /**
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static float getFloat(String key, float defValue) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getFloat(key, defValue);
    }

    /**
     * @param key 键
     * @return
     */
    public static float getFloat(String key) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getFloat(key, (float) 0.0);
    }

    /**
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getBoolean(key, defValue);
    }

    /**
     * @param key 键
     * @return
     */
    public static boolean getBoolean(String key) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.getBoolean(key, false);
    }

    /**
     * @param key 键
     * @return
     */
    public static boolean contains(String key) {
        SharedPreUtils.getSharedPreferencesInstance();
        return preferences.contains(key);
    }

    /**
     * @param key  键
     * @param type 传入值的类型
     * @type类型 SharedPreUtils.STRING， SharedPreUtils.INT，
     * SharedPreUtils.BOOLEAN， SharedPreUtils.LONG
     */
    public static Object getValue(String key, byte type) {
        SharedPreUtils.getSharedPreferencesInstance();
        switch (type) {
            case STRING:
                return preferences.getString(key, "");
            case INT:
                return preferences.getInt(key, -1);
            case BOOLEAN:
                return preferences.getBoolean(key, true);
            case LONG:
                return preferences.getLong(key, 0);
        }
        return null;
    }

    /**
     * @param key
     * @param type
     * @param defValue
     * @return
     */
    public static Object getValue(String key, byte type, Object defValue) {
        SharedPreUtils.getSharedPreferencesInstance();
        switch (type) {
            case STRING:
                return preferences.getString(key, (String) defValue);
            case INT:
                return preferences.getInt(key, (Integer) defValue);
            case BOOLEAN:
                return preferences.getBoolean(key, (Boolean) defValue);
            case LONG:
                return preferences.getLong(key, (Long) defValue);
        }
        return null;
    }

    /**
     * 清除所有的数据
     */
    public static void clear() {
        SharedPreferences sp = baseApp.getSharedPreferences(SHARE_MENU, baseApp.MODE_PRIVATE); //读取文件,如果没有则会创建
        Editor editor = sp.edit();
        editor.clear().commit();

    }


    /**
     * 清除某一条数据
     */
    public static void remove(String key) {
        SharedPreferences sp = baseApp.getSharedPreferences(SHARE_MENU, baseApp.MODE_PRIVATE); //读取文件,如果没有则会创建
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }


}
