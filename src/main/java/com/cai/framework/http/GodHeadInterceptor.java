package com.cai.framework.http;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by clarence on 2018/3/21.
 */

public class GodHeadInterceptor implements Interceptor {
    Context context;

    public GodHeadInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build());
    }
}
