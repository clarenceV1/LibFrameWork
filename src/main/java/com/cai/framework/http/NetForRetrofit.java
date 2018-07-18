package com.cai.framework.http;

import android.content.Context;
import android.util.Log;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by clarence on 2018/3/22.
 */

public class NetForRetrofit implements INet {
    private Retrofit retrofit;
    private Context context;
    private String baseUrl;
    private OkHttpClient.Builder okHttpBuilder;
    Map<String, String> headerMap;

    private NetForRetrofit(Builder builder) {
        context = builder.context;
        baseUrl = builder.baseUrl;
        init();
    }

    private void init() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("logInterceptor", message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(context.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //100Mb

        okHttpBuilder = new OkHttpClient.Builder()
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .cache(cache)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(SocketFactory.createSSLSocketFactory());

        NetHeaderInterceptor headerInterceptor = new NetHeaderInterceptor(context);
        if (headerMap != null && headerMap.size() > 0) {
            headerInterceptor.setHeaderMap(headerMap);
        }
        okHttpBuilder.addInterceptor(headerInterceptor);

        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Retrofit request() {
        return retrofit;
    }

    public static final class Builder {
        private Context context;
        private String baseUrl;
        Map<String, String> headerMap;

        public Builder() {
        }

        public Builder context(Map<String, String> map) {
            headerMap = map;
            return this;
        }

        public Builder context(Context val) {
            context = val;
            return this;
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }

        public NetForRetrofit build() {
            return new NetForRetrofit(this);
        }
    }
}
