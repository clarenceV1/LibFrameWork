package com.cai.framework.base;


import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import java.util.List;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class GodBasePresenterActivity<M extends ViewDataBinding> extends DataBindingActivity<M> implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    public List<GodBasePresenter> observerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLifecycleObserver();
    }

    private void addLifecycleObserver() {
        observerList = getPresenters();
        if (observerList != null) {
            for (GodBasePresenter lifecycleObserver : observerList) {
                lifecycleObserver.init(mRegistry, this);
                lifecycleObserver.setContext(this);
                getLifecycle().addObserver(lifecycleObserver);
            }
        }
    }

    public abstract List<GodBasePresenter> getPresenters();

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (observerList != null) {
            for (GodBasePresenter godBasePresenter : observerList) {
                godBasePresenter.onDetached();
            }
        }
    }
}
