package com.cai.framework.imageload;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by clarence on 2018/4/9.
 */
public interface ILoadImage {
    void loadImage(Context context, ILoadImageParams builder);

    void loadImage(FragmentActivity activity, ILoadImageParams builder);

    void loadImage(Fragment fragment, ILoadImageParams builder);
}
