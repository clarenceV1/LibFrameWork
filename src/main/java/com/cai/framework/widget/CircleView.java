package com.cai.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CircleView extends View {
    int color = Color.RED;
    Context context;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setColor(String colorRBG) {
        color = Color.parseColor(colorRBG);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //得到屏幕宽高
        int width = getWidth();
        int height = getHeight();
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.RED);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
