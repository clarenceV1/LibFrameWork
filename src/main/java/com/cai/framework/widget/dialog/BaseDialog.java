package com.cai.framework.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class BaseDialog extends Dialog {
    public View layoutView;
    private Context mContext;

    public BaseDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setContentView(int layoutResID) {
        this.layoutView = LayoutInflater.from(mContext).inflate(layoutResID, null);
        super.setContentView(this.layoutView);
    }

    public void show() {
        super.show();
    }

    public void setContentView(View view) {
        throw new RuntimeException("do not use this method");
    }

    public void setContentView(View view, LayoutParams params) {
        throw new RuntimeException("do not use this method");
    }

}
