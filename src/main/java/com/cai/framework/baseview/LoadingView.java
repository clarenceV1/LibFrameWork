package com.cai.framework.baseview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cai.framework.R;
import com.cai.framework.widget.CircleProgressBar;
import com.example.clarence.utillibrary.NetWorkUtil;

/**
 * Created by davy on 2018/3/9.
 */

public class LoadingView extends LinearLayout {
    public static final int STATUS_HIDDEN = 0;//不显示
    public static final int STATUS_LOADING = 1;//开始加载
    public static final int STATUS_NODATA = 2;//无数据
    public static final int STATUS_NONETWORK = 3;//无网络

    private CircleProgressBar loading_pro_bar;
    private TextView tipTV;
    private TextView erro_tipTV;
    //
    private int mStatus;

    public LoadingView(Context context) {
        this(context, null);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        erro_tipTV = findViewById(R.id.loading_error_tipTV);
        tipTV = findViewById(R.id.loading_tip_tv);
        loading_pro_bar = findViewById(R.id.loading_pro_bar);
        setClickable(true);
    }

    public void setStatus(int status) {
        if (status != STATUS_HIDDEN && !NetWorkUtil.isNetConnected(getContext())) {
            status = STATUS_NONETWORK;
        }
        mStatus = status;
        switch (status) {
            case STATUS_LOADING:
                tipTV.setText(R.string.loading_value);
                showChange(true);
                setVisibility(VISIBLE);
                break;
            case STATUS_NODATA:
                erro_tipTV.setText(R.string.loading_no_data);
                showChange(false);
                setVisibility(VISIBLE);
                break;
            case STATUS_NONETWORK:
                erro_tipTV.setText(R.string.loading_net_error);
                showChange(false);
                setVisibility(VISIBLE);
                break;
            case STATUS_HIDDEN:
            default:
                setVisibility(GONE);
                break;
        }
    }

    private void showChange(boolean isLoading) {
        if (isLoading) {
            tipTV.setVisibility(VISIBLE);
            erro_tipTV.setVisibility(GONE);
            loading_pro_bar.setVisibility(VISIBLE);
        } else {
            erro_tipTV.setVisibility(VISIBLE);
            loading_pro_bar.setVisibility(GONE);
            tipTV.setText(R.string.loading_click_tip);
        }
    }

    public int getStatus() {
        return mStatus;
    }

    public void setClickListener(final LoadViewClickListener onClickListener) {
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = getStatus();
                if (status == LoadingView.STATUS_NODATA || status == LoadingView.STATUS_NONETWORK) {
                    if (onClickListener != null) {
                        onClickListener.onLoadViewClick(status);
                    }
                }
            }
        });
    }

    public interface LoadViewClickListener {
        void onLoadViewClick(int status);
    }
}
