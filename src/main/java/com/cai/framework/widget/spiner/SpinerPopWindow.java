package com.cai.framework.widget.spiner;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cai.framework.R;

public class SpinerPopWindow extends PopupWindow {

    private Context mContext;
    private ListView mListView;
    private BaseAdapter mAdapter;
    private boolean isYellowBg = false;

    public SpinerPopWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public void setYellowBg(boolean yellowBg) {
        isYellowBg = yellowBg;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        if (mListView != null) {
            mListView.setOnItemClickListener(onItemClickListener);
        }
    }

    public void setAdatper(BaseAdapter adapter) {
        mAdapter = adapter;
        if (mListView != null) {
            if (isYellowBg) {
                mListView.setBackgroundResource(R.drawable.jy_optionbg);
            }
            mListView.setAdapter(mAdapter);
        }
    }


    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_pop_window, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        /**
         * 设置背景只有设置了这个才可以点击外边和BACK消失
         */
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        /**
         *设置可以触摸
         */
        setTouchable(true);
        /**
         * 设置点击外边可以消失
         */
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                /**
                 * 判断是不是点击了外部
                 */
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    return true;
                }
                //不是点击外部
                return false;
            }
        });
        mListView = (ListView) view.findViewById(R.id.listview);
    }
}