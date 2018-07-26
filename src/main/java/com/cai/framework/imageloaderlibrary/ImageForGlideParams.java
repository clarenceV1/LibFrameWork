package com.cai.framework.imageloaderlibrary;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by clarence on 2018/4/9.
 */

public class ImageForGlideParams implements ILoadImageParams {
    private ImageView imageView;
    private String url;
    private BitmapTransformation transformation;
    private int placeholder;//占位图
    private int error;//失败图
    private int local;//本地资源ID；

    private ImageForGlideParams(Builder builder) {
        url = builder.url;
        transformation = builder.transformation;
        this.placeholder = builder.placeholder;
        this.error = builder.error;
        this.local = builder.local;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getError() {
        return error;
    }

    public int getLocal() {
        return local;
    }

    @Override
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getUrl() {
        return url;
    }

    public static final class Builder {
        private String url;
        private BitmapTransformation transformation;
        private int placeholder;//占位图
        private int error;//失败图
        private int local;//本地资源ID；

        public Builder() {
        }

        public Builder local(int val) {
            local = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder transformation(BitmapTransformation bitmapTransformation) {
            transformation = bitmapTransformation;
            return this;
        }

        public Builder placeholder(int placePic) {
            placeholder = placePic;
            return this;
        }

        public Builder error(int errorPic) {
            error = errorPic;
            return this;
        }

        public ImageForGlideParams build() {
            return new ImageForGlideParams(this);
        }
    }
}
