package com.cai.framework.utils.stausbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.clarence.utillibrary.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by davy on 2018/3/13.
 */

public class StatusBarController {

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";


    private static class Holder {
        static StatusBarController instance = new StatusBarController();
    }

    public static StatusBarController getInstance() {
        return Holder.instance;
    }


    public boolean setStatusTextColor(Activity activity, boolean darkmode) {
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                return false;
            }
            if (isMiui(activity.getApplicationContext())) {
                return setMiuiStatusBarDarkMode(activity, darkmode);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (darkmode)
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    else
                        activity.getWindow().getDecorView().setSystemUiVisibility(0);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 是否是miui
     *
     * @return
     */
    private boolean isMiui(Context context) {
//        try {
//            if (!mFrameworkSpDbUtil.containKey(context, "is_miui")) {
//                Properties prop = new Properties();
//                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
//                boolean isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
//                        || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
//                        || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
//                mFrameworkSpDbUtil.saveBoolean("is_miui", isMIUI);
//
//                String version = prop.getProperty(KEY_MIUI_VERSION_NAME, null);
//                if (!StringUtils.isNotEmpty(version)) {
//                    mFrameworkSpDbUtil.saveString("miui_version", version);
//                }
//                return isMIUI;
//            }
//            return mFrameworkSpDbUtil.getBoolean("is_miui", false);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        return false;
    }


    //miui
    private boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            String version = "";
            if (StringUtils.isEmpty(version) || !version.equalsIgnoreCase("V9")) {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            } else {
                //V9特别处理
                if (darkmode) {
                    Window window = activity.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    activity.getWindow().getDecorView().setSystemUiVisibility(0);
                }

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置状态栏颜色值
     *
     * @param activity
     * @param color
     */
    public void setStausBarColor(Activity activity, int color) {
        StatusBarUtil.setColor(activity, color, 1);
    }


    private boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }
}
