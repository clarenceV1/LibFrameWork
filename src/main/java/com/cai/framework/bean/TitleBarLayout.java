package com.cai.framework.bean;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cai.framework.R;

public class TitleBarLayout extends RelativeLayout {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvRight;

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this);
        ivBack = getViewById(R.id.ivBack);
        tvTitle = getViewById(R.id.tvTitle);
        tvRight = getViewById(R.id.tvRight);
    }

    /**
     * 获取布局中的View
     *
     * @param viewId view的Id
     * @param <T>    View的类型
     * @return view
     */
    public <T extends View> T getViewById(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }

    public void setTitleText(String titleText) {
        if (titleText != null && tvTitle != null) {
            tvTitle.setText(titleText);
        }
    }

    public void setRightText(String rightText) {
        if (tvRight != null && tvRight != null) {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightText);
        }
    }

    public void setBackImgIcon(int ResID) {
        ivBack.setImageResource(ResID);
        ivBack.setVisibility(View.VISIBLE);
    }

    public void setClickListener(final OnTitleBarClickListener clickListener) {
        if (clickListener == null) {
            return;
        }
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onBackClick(v);
                }
            }
        });
        tvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onTitleClick(v, tvTitle.getText().toString());
                }
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onRightClick(v, tvRight.getText().toString());
                }
            }
        });
    }
}
