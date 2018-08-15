package com.cai.framework.imageload;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cai.framework.R;

/**
 * Created by clarence on 2018/4/9.
 */

public class ImageForGlide implements ILoadImage {


    public ImageForGlide(ImageForGlideBuild imageForGlideBuild) {

    }

    public static class ImageForGlideBuild {

        public ImageForGlideBuild() {

        }

        public ImageForGlide build() {
            return new ImageForGlide(this);
        }
    }

    @Override
    public void loadImage(Context context, ILoadImageParams builder) {
        if (context != null) {
            RequestManager requestManager = Glide.with(context);
            assemble(requestManager, builder);
        }
    }

    @Override
    public void loadImage(FragmentActivity activity, ILoadImageParams builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity != null && !activity.isDestroyed()) {
                RequestManager requestManager = Glide.with(activity);
                assemble(requestManager, builder);
            }
        } else {
            if (activity != null) {
                RequestManager requestManager = Glide.with(activity);
                assemble(requestManager, builder);
            }
        }
    }

    @Override
    public void loadImage(Fragment fragment, ILoadImageParams builder) {
        if (fragment != null && fragment.getActivity() != null) {
            RequestManager requestManager = Glide.with(fragment);
            assemble(requestManager, builder);
        }
    }

    private void assemble(RequestManager requestManager, ILoadImageParams builder) {
        if (builder instanceof ImageForGlideParams) {
            ImageForGlideParams glideParams = (ImageForGlideParams) builder;
            DrawableRequestBuilder drawableRequestBuilder;
            if (glideParams.getLocal() != 0) {
                drawableRequestBuilder = requestManager.load(glideParams.getLocal());
            } else {
                drawableRequestBuilder = requestManager.load(glideParams.getUrl());
            }
            if (glideParams.getTransformation() != null) {
                drawableRequestBuilder.centerCrop().transform(glideParams.getTransformation());
            }
            if (glideParams.getError() != 0) {
                drawableRequestBuilder.error(glideParams.getError());
            } else {
                drawableRequestBuilder.error(R.drawable.default_image);
            }
            if (glideParams.getPlaceholder() != 0) {
                drawableRequestBuilder.placeholder(glideParams.getPlaceholder());
            } else {
                drawableRequestBuilder.placeholder(R.drawable.default_image);
            }
            drawableRequestBuilder.into(glideParams.getImageView());
        }
    }
}
