package com.cai.framework.utils;

import android.content.Context;

import com.cai.framework.base.GodBaseApplication;


/**
 * @version V1.0.0 2015/12/25 chengaobin 初版
 * @description 通过反射获取资源Id
 */
public class CPResourceUtils {

    public static Context paramContext = GodBaseApplication.getAppContext();


    public static int getLayoutId(String resourceName) {

        return paramContext.getResources().getIdentifier(resourceName, "layout",paramContext.getPackageName());
    }


    public static int getStringId(String resourceName) {

        return paramContext.getResources().getIdentifier(resourceName,"string", paramContext.getPackageName());
    }

    public static int getDrawableId(String resourceName) {

        return paramContext.getResources().getIdentifier(resourceName,"drawable", paramContext.getPackageName());
    }

    public static int getStyleId(String resourceName) {

        return paramContext.getResources().getIdentifier(resourceName,"style", paramContext.getPackageName());
    }

    public static int getId(String resourceName) {

        return paramContext.getResources().getIdentifier(resourceName,"id", paramContext.getPackageName());
    }

    public static int getColorId(String resourceName) {

        return paramContext.getResources().getIdentifier(resourceName,"color", paramContext.getPackageName());
    }

    public static int getArrayId(Context paramContext, String resourceName) {

        return paramContext.getResources().getIdentifier(resourceName,"array", paramContext.getPackageName());
    }
}


