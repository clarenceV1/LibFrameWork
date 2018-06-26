package com.cai.framework.bean;

import android.view.View;

/**
 * Created by davy on 2018/3/26.
 */

public interface OnTitleBarClickListener {
    void onBackClick(View v);

    void onTitleClick(View v, String title);

    void onRightClick(View v, String text);
}
