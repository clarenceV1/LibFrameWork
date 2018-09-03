package com.cai.framework.base;


import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class GodBasePresenterFragment<M extends ViewDataBinding> extends DataBindingFragment<M> implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private List<GodBasePresenter> observerList = new ArrayList<>();

    protected Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        initDagger();
        super.onCreate(savedInstanceState);
        initPresenter();

    }

    private void initPresenter() {
        addPresenters(observerList);
        if (observerList != null && observerList.size() > 0) {
            for (GodBasePresenter lifecycleObserver : observerList) {
                lifecycleObserver.init(mRegistry, this);
                lifecycleObserver.setContext(getContext());
                getLifecycle().addObserver(lifecycleObserver);
            }
        }
    }


    public abstract void addPresenters(List<GodBasePresenter> observerList);

    public abstract void initDagger();

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
