package com.cai.framework.dagger.module;

import android.content.Context;

import com.cai.framework.base.GodBaseApplication;
import com.example.clarence.datastorelibrary.store.share_preference.ISharePreference;
import com.example.clarence.datastorelibrary.store.share_preference.StoreForSharePreference;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ImageForGlide;
import com.example.clarence.netlibrary.INet;
import com.example.clarence.netlibrary.NetForRetrofit;

import dagger.Module;
import dagger.Provides;

/**
 * Created by clarence on 2018/3/26.
 */
@Module
public class FrameWorkModule {

    @Provides
    Context provideContext() {
        return GodBaseApplication.getAppContext();
    }

    @Provides
    public ILoadImage provideImageLoader() {
        return new ImageForGlide.ImageForGlideBuild().build();
    }

    @Provides
    public INet provideRequest(Context context) {
        return new NetForRetrofit.Builder().context(context).baseUrl("http://www.sojson.com").build();
    }

    @Provides
    public ISharePreference provideSharePreference(Context context) {
        return new StoreForSharePreference.Builder().context(context).spName("spSave").build();
    }
}
