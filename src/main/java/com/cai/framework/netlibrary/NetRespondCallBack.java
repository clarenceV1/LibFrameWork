package com.cai.framework.netlibrary;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

public abstract class NetRespondCallBack<T> implements INetCallBack<T>, FlowableSubscriber<T> {
    Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        subscription.request(1);
    }

    @Override
    public void onNext(T next) {
        respondResult(subscription, next);
        subscription.cancel();
    }

    @Override
    public void onError(Throwable t) {
        respondError(t);
        subscription.cancel();
    }

    @Override
    public void onComplete() {

    }
}
