package com.cai.framework.baseview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cai.framework.R;
import com.example.clarence.utillibrary.ViewUtils;

public class TitleBarView extends RelativeLayout {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvRight;

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this);
        ivBack = ViewUtils.getViewById(this, R.id.ivBack);
        tvTitle = ViewUtils.getViewById(this, R.id.tvTitle);
        tvRight = ViewUtils.getViewById(this, R.id.tvRight);
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
