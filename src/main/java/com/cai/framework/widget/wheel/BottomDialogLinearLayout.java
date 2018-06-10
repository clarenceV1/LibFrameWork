//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cai.framework.widget.wheel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class BottomDialogLinearLayout extends LinearLayout {
    private Context mContext;
    private Scroller mScroller;

    public BottomDialogLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public BottomDialogLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public BottomDialogLinearLayout(Context context) {
        super(context);
        this.init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mScroller = new Scroller(this.mContext);
    }

    public void computeScroll() {
        if(this.mScroller.computeScrollOffset()) {
            this.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            this.postInvalidate();
        }

        super.computeScroll();
    }

    public Scroller getScroller() {
        return this.mScroller;
    }
}
