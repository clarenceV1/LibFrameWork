package com.cai.framework.base;


import android.arch.lifecycle.Lifecycle;

import io.reactivex.disposables.CompositeDisposable;

public abstract class GodBasePresenter<V> extends BaseLifecycleObserver {
    protected V mView;
    protected CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    /**
     * 初始化必须调用
     *
     * @param lifecycle
     */
    public void init(Lifecycle lifecycle, V v) {
        this.lifecycle = lifecycle;
        this.mView = v;
        this.isDebug = GodBaseConfig.getInsatance().isDebug();
        this.onAttached();
    }

    public abstract void onAttached();

    public void onDetached() {
        mCompositeSubscription.dispose();
    }
}
