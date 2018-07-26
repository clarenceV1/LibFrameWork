package com.cai.framework.dagger.component;

import android.content.Context;

import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.imageloaderlibrary.ILoadImage;
import com.cai.framework.netlibrary.INet;
import com.cai.framework.share_preference.ISharePreference;

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
