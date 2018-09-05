package com.cai.framework.imageZoom;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cai.framework.R;
import com.cai.framework.imageZoom.widget.PhotoViewAttacher;
import com.example.clarence.utillibrary.DeviceUtils;

public class ImageDetailFragment extends Fragment {
    private String mImageUrl;

    private ImageView mImageView;

    private ProgressBar progressBar;

    private PhotoViewAttacher mAttacher;
    private SimpleTarget simpleTarget = null;

    public static ImageDetailFragment newInstance(String imageUrl) {
        ImageDetailFragment f = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);
        //单击退出页面
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
        progressBar = v.findViewById(R.id.loading);
        loadImage();
        return v;
    }

    private void loadImage() {
        simpleTarget = new SimpleTarget<Bitmap>(SimpleTarget.SIZE_ORIGINAL, SimpleTarget.SIZE_ORIGINAL) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> glideAnimation) {
                progressBar.setVisibility(View.GONE);
                mImageView.setImageBitmap(loadedImage);
                mAttacher.update();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        };
        Glide.with(this).load(mImageUrl).asBitmap().into(simpleTarget);
    }

}
