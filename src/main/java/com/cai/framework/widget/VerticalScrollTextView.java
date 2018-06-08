package com.cai.framework.widget;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by zhangyangfeng on 2015/12/2.
 * auto vertical scroll textview
 */
public class VerticalScrollTextView extends RelativeLayout {

    private List<String> mStrList;
    private int mDuration = 500;
    private TextView mTextView;
    private int mIndex;
    private Handler mHandler;
    private static final int MSG_ANIMATE_LOOP = 100;
    private AnimationSet mAnimationShowSet, mAnimationDismissSet;

    public VerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new AnimateHandler(this);
        mTextView = new TextView(context, attrs);
        mTextView.setBackgroundDrawable(null);
        mTextView.setTextColor(mTextView.getTextColors());
    }

    private void doAnimateLoop() {
        if (mStrList.size() >= 2) {
            mTextView.startAnimation(mAnimationDismissSet);
        }
        mHandler.removeMessages(MSG_ANIMATE_LOOP);
        mHandler.sendEmptyMessageDelayed(MSG_ANIMATE_LOOP, 2000);
    }

    public void animationStart() {
        if (mHandler.hasMessages(MSG_ANIMATE_LOOP)) {
            mHandler.removeMessages(MSG_ANIMATE_LOOP);
        }
        mHandler.sendEmptyMessageDelayed(MSG_ANIMATE_LOOP, 2000);
    }

    public void setColor(String color){
        if (color != null && color.startsWith("#")) {
            mTextView.setTextColor(Color.parseColor(color));
        }
    }

    public void animationStop() {
        if (mHandler.hasMessages(MSG_ANIMATE_LOOP)) {
            mHandler.removeMessages(MSG_ANIMATE_LOOP);
        }
    }

    public void setStrList(List<String> strList) {
        this.mStrList = strList;
        initView();
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public CharSequence getText() {
        return mTextView.getText();
    }

    private void initView() {
        mIndex = 0;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        removeAllViews();
        this.addView(mTextView, params);
        if (mStrList.size() != 0) {
            mTextView.setText(mStrList.get(mIndex++));
            initAnimation();
        } else {
            mTextView.setText("找话题、找知识、找工具");
        }
    }

    private void initAnimation() {
        TranslateAnimation animationCome = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        AlphaAnimation animationShow = new AlphaAnimation(0.3f, 1f);

        mAnimationShowSet = new AnimationSet(true);
        mAnimationShowSet.addAnimation(animationCome);
        mAnimationShowSet.addAnimation(animationShow);
        mAnimationShowSet.setFillAfter(true);
        mAnimationShowSet.setDuration(mDuration);

        mAnimationDismissSet = new AnimationSet(true);
        mAnimationDismissSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -0.6f));
        mAnimationDismissSet.addAnimation(new AlphaAnimation(1f, 0f));
        mAnimationDismissSet.setFillAfter(true);
        mAnimationDismissSet.setDuration(mDuration - 300);

        mAnimationShowSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (mStrList.size() != 0) {
                    mTextView.setText(mStrList.get(mIndex++ % mStrList.size()));
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimationDismissSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTextView.startAnimation(mAnimationShowSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private static class AnimateHandler extends Handler {
        private final WeakReference<VerticalScrollTextView> textViewWeakReference;

        public AnimateHandler(VerticalScrollTextView verticalScrollTextView) {
            textViewWeakReference = new WeakReference<>(verticalScrollTextView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VerticalScrollTextView textView = this.textViewWeakReference.get();
            if (textView != null && msg.what == MSG_ANIMATE_LOOP) {
                textView.doAnimateLoop();
            }
        }
    }
}
