package com.cai.framework.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.example.clarence.utillibrary.StringUtils;

import java.util.Locale;

/**
 * APP内应用语言
 * Created by davy on 2018/3/1.
 */

public class LanguageLocalUtil {

    public static void changeLanguage(Context mContext, String language) {
        if (!StringUtils.isEmpty(language)) {
            // 本地语言设置
            Locale myLocale = new Locale(language);
            Resources resources = mContext.getResources();
            Configuration configuration = resources.getConfiguration();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(myLocale);
            } else {
                configuration.locale = myLocale;
            }
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    public static String getSystemLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale.getLanguage();
    }

    public static boolean isChinese() {
        String language = getSystemLanguage();
        if ("zh".equals(language)) {
            return true;
        }
        return false;
    }
}
