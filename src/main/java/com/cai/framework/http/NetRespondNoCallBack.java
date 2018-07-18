package com.cai.framework.http;

import android.util.Log;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

public class NetRespondNoCallBack<T> implements FlowableSubscriber<T> {
    Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        subscription.request(1);
    }

    @Override
    public void onNext(T next) {
        Log.d("NetRespondNoCallBack","onNext==>cancel()");
        subscription.cancel();
    }

    @Override
    public void onError(Throwable t) {
        Log.d("NetRespondNoCallBack","onError==>cancel()");
        subscription.cancel();
    }

    @Override
    public void onComplete() {

    }
}
