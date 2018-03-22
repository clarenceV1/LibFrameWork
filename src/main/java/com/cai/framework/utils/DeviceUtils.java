package com.cai.framework.utils;

import android.content.Context;

/**
 * Created by clarence on 2018/1/25.
 */

public class DeviceUtils {
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }
}
