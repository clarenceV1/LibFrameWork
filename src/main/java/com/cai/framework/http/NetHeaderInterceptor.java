package com.cai.framework.http;

import android.content.Context;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by clarence on 2018/3/21.
 */

public class NetHeaderInterceptor implements Interceptor {
    Context context;
    Map<String, String> headerMap;

    public NetHeaderInterceptor(Context context) {
        this.context = context;
    }

    public void setHeaderMap(Map<String, String> map) {
        this.headerMap = headerMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build());
    }
}
