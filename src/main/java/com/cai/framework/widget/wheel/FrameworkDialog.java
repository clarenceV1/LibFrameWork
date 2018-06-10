//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cai.framework.widget.wheel;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class FrameworkDialog extends Dialog {
    private static final String TAG = "FrameworkDialog";
    public View layoutView;
    private Context mContext;

    public FrameworkDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public FrameworkDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public void setContentView(int layoutResID) {
        this.layoutView = LayoutInflater.from(mContext).inflate(layoutResID, null);
        super.setContentView(this.layoutView);
    }

    public void show() {
        super.show();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /*if(this.mContext instanceof Activity) {
            LogUtils.d("LinganDialog", "BehaviorActivityWatcher LinganDialog onWindowFocusChanged hasFocus:" + hasFocus + "==>name:" + ((Activity)this.mContext).getClass().getSimpleName(), new Object[0]);
            BehaviorActivityWatcher watcher = (BehaviorActivityWatcher) WatcherManager.getInstance().getWatcher("behavior-stack");
            if(watcher != null) {
                watcher.addDialogWindowFocusChanged((Activity)this.mContext, hasFocus);
            }
        }*/

    }

    public void setContentView(View view) {
        throw new RuntimeException("do not use this method");
    }

    public void setContentView(View view, LayoutParams params) {
        throw new RuntimeException("do not use this method");
    }

    protected FrameworkDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
