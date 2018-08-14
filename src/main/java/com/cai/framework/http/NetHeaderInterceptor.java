package com.cai.framework.http;

import android.content.Context;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by clarence on 2018/3/21.
 */

public class NetHeaderInterceptor implements Interceptor {
    Context context;
    Headers headers;

    public NetHeaderInterceptor(Context context, Headers headers) {
        this.context = context;
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (headers != null) {
            try {
                Request request = chain.request().newBuilder()
                        .headers(headers)
                        .build();
                return chain.proceed(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return chain.proceed(chain.request());
    }
}
