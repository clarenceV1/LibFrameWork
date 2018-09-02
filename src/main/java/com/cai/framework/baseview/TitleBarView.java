package com.cai.framework.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cai.framework.R;

public class TitleBarView extends RelativeLayout {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvRight;

    public TitleBarView(Context context) {
        super(context);
        init();
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this);
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvRight = findViewById(R.id.tvRight);
    }

    public void setTitleText(String titleText) {
        if (titleText != null && tvTitle != null) {
            tvTitle.setText(titleText);
            tvTitle.setVisibility(VISIBLE);
        }
    }

    public void hideBackBtn() {
        ivBack.setVisibility(GONE);
    }

    public void hideTitle() {
        tvTitle.setVisibility(GONE);
    }

    public void hideAll() {
        setVisibility(GONE);
    }

    public void hideRightTextView() {
        tvRight.setVisibility(GONE);
    }

    public void setRightText(String rightText) {
        if (tvRight != null && tvRight != null) {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightText);
        }
    }

    public void setRightClickListener(OnClickListener clickListener) {
        if (clickListener != null) {
            tvRight.setOnClickListener(clickListener);
        }
    }

    public void setLeftClickListener(OnClickListener clickListener) {
        if (clickListener != null) {
            ivBack.setOnClickListener(clickListener);
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

    public TextView getRightTextView() {
        return tvRight;
    }
}
