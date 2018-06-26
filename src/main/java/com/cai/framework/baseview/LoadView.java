package com.cai.framework.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cai.framework.R;
import com.example.clarence.utillibrary.ViewUtils;

public class LoadView extends RelativeLayout {
    ProgressBar progressBar;
    TextView tvTips;

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.load_view, this);
        progressBar = ViewUtils.getViewById(this, R.id.progressBar);
        tvTips = ViewUtils.getViewById(this, R.id.tvTips);
    }

    public void setTips(String tips) {
        tvTips.setText(tips);
    }
}
