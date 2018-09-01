package com.cai.framework.base;


import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.pm.ActivityInfo;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cai.framework.R;
import com.example.clarence.utillibrary.DeviceUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑层
 * Created by clarence on 2018/1/11.
 */
public abstract class GodBasePresenterActivity<M extends ViewDataBinding> extends DataBindingActivity<M> implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private List<GodBasePresenter> observerList = new ArrayList<>();
    private SystemBarTintManager tintManager;
    public ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStateBar();
        rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setBackgroundResource(com.cai.framework.R.color.white_a);
        }
        initDagger();
        initPresenter();
        initView();
    }

    private void initStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (!DeviceUtils.isFullScreen(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
                tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setTintColor(getResources().getColor(R.color.black_a));
                setStatusBar(tintManager);
                try {
                    View rootView = findViewById(android.R.id.content);
                    if (rootView != null) {
                        rootView.setBackgroundResource(R.color.ys_24_24_24);
                        ViewGroup viewGroup = (ViewGroup) rootView;
                        if (viewGroup.getChildCount() > 0) {
                            viewGroup.getChildAt(0).setFitsSystemWindows(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void initPresenter() {
        addPresenters(observerList);
        if (observerList != null && observerList.size() > 0) {
            for (GodBasePresenter lifecycleObserver : observerList) {
                lifecycleObserver.init(mRegistry, this);
                lifecycleObserver.setContext(this);
                getLifecycle().addObserver(lifecycleObserver);
            }
        }
    }

    public abstract void setStatusBar(SystemBarTintManager tintManager);

    public abstract void initDagger();

    public abstract void addPresenters(List<GodBasePresenter> observerList);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPresenter();
    }

    private void destroyPresenter() {
        if (observerList != null && observerList.size() > 0) {
            for (GodBasePresenter godBasePresenter : observerList) {
                godBasePresenter.onDetached();
            }
        }
    }
}
