package com.cai.framework.base;


import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class GodBasePresenterActivity<M extends ViewDataBinding> extends DataBindingActivity<M> implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private List<GodBasePresenter> observerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
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
