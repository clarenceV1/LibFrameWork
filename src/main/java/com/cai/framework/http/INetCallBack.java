package com.cai.framework.http;

import org.reactivestreams.Subscription;

public interface INetCallBack<T> {

    void respondResult(Subscription subscription, T t);

    void respondError(Throwable t);

}
