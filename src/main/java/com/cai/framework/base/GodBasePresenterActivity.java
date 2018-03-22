package com.cai.framework.base;


import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import java.lang.reflect.ParameterizedType;

/**
 * Created by clarence on 2018/1/11.
 */

public abstract class GodBasePresenterActivity<P extends GodBasePresenter, M extends ViewDataBinding> extends DataBindingActivity<M> implements LifecycleRegistryOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    public void initPresenter() {
        try {
            if (this instanceof GodBaseView && this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                    ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
                Class presenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
                mPresenter = getPresenter(presenterClass);
                if (mPresenter != null) {
                    mPresenter.init(mRegistry,this);
                    getLifecycle().addObserver(mPresenter);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract P getPresenter(Class mPresenterClass);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }
}
