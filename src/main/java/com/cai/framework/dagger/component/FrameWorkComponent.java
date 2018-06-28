package com.cai.framework.dagger.component;

import android.content.Context;

import com.cai.framework.base.GodBaseApplication;
import com.example.clarence.datastorelibrary.store.share_preference.ISharePreference;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.netlibrary.INet;

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
