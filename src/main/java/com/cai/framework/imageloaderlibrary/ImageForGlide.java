package com.cai.framework.imageloaderlibrary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

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
        RequestManager requestManager = Glide.with(context);
        assemble(requestManager, builder);
    }

    @Override
    public void loadImage(FragmentActivity activity, ILoadImageParams builder) {
        RequestManager requestManager = Glide.with(activity);
        assemble(requestManager, builder);
    }

    @Override
    public void loadImage(Fragment fragment, ILoadImageParams builder) {
        RequestManager requestManager = Glide.with(fragment);
        assemble(requestManager, builder);
    }

    private void assemble(RequestManager requestManager, ILoadImageParams builder) {
        if (builder instanceof ImageForGlideParams) {
            ImageForGlideParams glideParams = (ImageForGlideParams) builder;
            DrawableRequestBuilder drawableRequestBuilder = requestManager.load(glideParams.getUrl());
            if (glideParams.getTransformation() != null) {
                drawableRequestBuilder.centerCrop().transform(glideParams.getTransformation());
            }
            if (glideParams.getError() != 0) {
                drawableRequestBuilder.error(glideParams.getError());
            }
            if (glideParams.getPlaceholder() != 0) {
                drawableRequestBuilder.placeholder(glideParams.getPlaceholder());
            }
            if (glideParams.getLocal() != 0) {
                drawableRequestBuilder.load(glideParams.getLocal());
            }
            drawableRequestBuilder.into(glideParams.getImageView());
        }
    }
}
