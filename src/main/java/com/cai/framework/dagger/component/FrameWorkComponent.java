package com.cai.framework.dagger.component;

import android.content.Context;

import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.dataStore.ISharePreference;
import com.cai.framework.http.INet;
import com.cai.framework.imageload.ILoadImage;

import retrofit2.Retrofit;

/**
 * Created by clarence on 2018/3/26.
 */

public interface FrameWorkComponent {

    void inject(GodBaseApplication application);

    Context provideContext();

    ILoadImage provideLoadImage();

    INet provideRequestNet();

    ISharePreference provideSharePreference();

    Retrofit provideRequestRetrofit();
}
