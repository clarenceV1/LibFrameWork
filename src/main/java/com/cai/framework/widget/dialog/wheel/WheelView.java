package com.cai.framework.widget.dialog.wheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.cai.framework.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Numeric wheel view. 不能自定义item原始的
 *
 * @author Yuri Kanivets
 *         主要修改成符合6.0需求的view
 * @重构 庄育锋
 */
public class WheelView extends View {
    private final String TAG = "WheelView";
    private final boolean isDebug = false;
    /**
     * Scrolling duration
     */
    private final int SCROLLING_DURATION = 200;
    /**
     * Minimum delta for scrolling
     */
    private final int MIN_DELTA_FOR_SCROLLING = 1;
    /**
     * Current value & label text color
     */
    private int mValueTextColor = 0xFF323232;//选中的颜色
    /**
     * Items text color
     */
    private int mTextItemValue = 0XFF888888;//非选中的文字颜色
    /**
     * Top and bottom shadows colors
     */
    private final int[] SHADOWS_COLORS = new int[]{0xFF111111, 0x00AAAAAA,
            0x00AAAAAA};
    /**
     * Additional width for items layout
     */
    /**
     * Label offset
     */
    private int LABEL_OFFSET = 8;
    private int FIST_LABEL_OFFSET = 0;
    /**
     * Default count of visible items
     */
    private final int DEF_VISIBLE_ITEMS = 5;
    // Messages
    private final int MESSAGE_SCROLL = 0;
    private final int MESSAGE_JUSTIFY = 1;
    // Cyclic
    private boolean isCyclic = true;
    /**
     * Additional items height (is added to standard text item height)
     * item之间的间隔
     */
    private int ADDITIONAL_ITEM_HEIGHT = 20;
    /**
     * Text size
     */
    private int mTextSize = 24;
    private int mFistTextSize = 16;
    /**
     * Top and bottom items offset (to hide that)
     */
    // Wheel Values
    private String[] adapter = null;
    private int currentItem = -1;//选中的位置
    // Widths
    private int itemsWidth = 0;
    private int labelWidth = 0;
    private int fistLableWidth = 0;
    // Count of visible items
    private int visibleItems = DEF_VISIBLE_ITEMS;
    // Item height
    private int viewWidth;//此控件的宽度
    // Text paints
    private TextPaint labelPaint;
    private TextPaint fistLabelPaint;
    //
    // Label & background
    private String fistLabel;
    private String label;
    private Drawable centerDrawable;
    // Shadows drawables
    private GradientDrawable topShadow;
    private GradientDrawable bottomShadow;
    // Scrolling
    private boolean isScrollingPerformed;//是否是滚动状态
    private float scrollingOffset;
    // Scrolling animation
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int lastScrollY;
    /**
     * 滚轮滑动时可以滑动到的最小/最大的Y坐标
     */
    private int mMinFlingY, mMaxFlingY;
    private String unit = ""; // 单位
    //
    private List<ItemValue> itemValues = new LinkedList<>();//存放绘制item的画笔
    private int dTextSize = 6;//字体递减数值 单位sp
    private float scrollPercent = 0f;//滚动的百分比
    //
    private int gravity;//
    private float itemScrollOffset;//滚动一个item需要滚动的距离
    private float itemMaxHeight;//item绘制的最大高度不包括边距
    private float itemMaxWidth;//item绘制的最大宽度
    //测试位置辅助画线
    private Paint testLinePain;
    private Paint centerLinePain;
    //
    private Rect clipRect;

    // Listeners
    private List<OnWheelChangedListener> changingListeners = new LinkedList<>();
    private OnWheelScrollListener scrollListener;
    // gesture listener
    private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            if (isScrollingPerformed) {
                scroller.forceFinished(true);
                clearMessages();
                return true;
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            startScrolling();
            doScroll((int) -distanceY);

            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            computeFlingLimitY();
            scroller.fling(0, (int) scrollingOffset, 0, (int) -velocityY / 2, 0, 0, mMinFlingY, mMaxFlingY);
            setNextMessage(MESSAGE_SCROLL);
            return true;
        }
    };
    // animation handler
    private Handler animationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            scroller.computeScrollOffset();
            int currY = scroller.getCurrY();
            int delta = lastScrollY - currY;
            lastScrollY = currY;
            if (delta != 0) {
                doScroll(delta);
            }

            if (Math.abs(currY - scroller.getFinalY()) < MIN_DELTA_FOR_SCROLLING) {
                scroller.forceFinished(true);
            }
            if (!scroller.isFinished()) {
                animationHandler.sendEmptyMessage(msg.what);
            } else if (msg.what == MESSAGE_SCROLL) {
                justify();
            } else {
                finishScrolling();
            }
        }
    };

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.WheelView,
                0, 0);
        try {
            mValueTextColor = a.getInteger(R.styleable.WheelView_textValueColor, mValueTextColor);
            mTextItemValue = a.getInteger(R.styleable.WheelView_textItemColor, mTextItemValue);
        } finally {
            a.recycle();
        }
        initData(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor
     */
    public WheelView(Context context) {
        this(context, null, 0);
    }

    /**
     * Initializes class data
     *
     * @param context the context
     */
    private void initData(Context context) {
        ADDITIONAL_ITEM_HEIGHT = (int) dp2px(ADDITIONAL_ITEM_HEIGHT);
        mTextSize = (int) sp2px(mTextSize);
        mFistTextSize = (int) sp2px(mFistTextSize);
        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(false);
        scroller = new Scroller(context);
        dTextSize = (int) sp2px(dTextSize);
        //
        LABEL_OFFSET = (int) dp2px(LABEL_OFFSET);

    }

    public void setAdapter(int[] adapter) {
        String[] s = new String[adapter.length];
        for (int i = 0; i < adapter.length; i++) {
            s[i] = String.valueOf(adapter[i]);
        }
        setAdapter(s);

    }

    /**
     * Gets wheel adapter
     *
     * @return the adapter
     */
    public String[] getAdapter() {
        return adapter;
    }

    /**
     * Sets wheel adapter
     *
     * @param adapter the new wheel adapter
     */
    public void setAdapter(String[] adapter) {
        this.adapter = adapter;
        clipRect = null;
        invalidateLayouts();
        invalidate();
    }

    /**
     * Set the the specified scrolling interpolator
     *
     * @param interpolator the interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        scroller.forceFinished(true);
        scroller = new Scroller(getContext(), interpolator);
    }

    /**
     * Gets count of visible items
     *
     * @return the count of visible items
     */
    public int getVisibleItems() {
        return visibleItems;
    }

    /**
     * Sets count of visible items
     *
     * @param count the new count
     */
    public void setVisibleItems(int count) {
        visibleItems = count;
        visibleItems = visibleItems % 2 == 0 ? visibleItems - 1 : visibleItems;//保证取到的值是奇数
        invalidate();
    }


    /**
     * Notifies changing listeners
     *
     * @param oldValue the old wheel value
     * @param newValue the new wheel value
     */
    private void notifyChangingListeners(int oldValue, int newValue) {
        for (OnWheelChangedListener listener : changingListeners) {
            listener.onChanged(this, oldValue, newValue);
        }
    }

    /**
     * Gets current value
     *
     * @return the current value
     */
    public int getCurrentItem() {
        return currentItem;
    }

    /**
     * Sets the current item w/o animation. Does nothing when index is wrong.
     *
     * @param index the item index
     */
    public void setCurrentItem(int index) {
        setCurrentItem(index, false);
    }

    /**
     * Sets the current item. Does nothing when index is wrong.
     *
     * @param index    the item index
     * @param animated the animation flag
     */
    private void setCurrentItem(int index, boolean animated) {
        if (adapter == null || adapter.length == 0) {
            return; // throw?
        }
        if (index < 0 || index >= adapter.length) {
            if (isCyclic) {
                while (index < 0) {
                    index += adapter.length;
                }
                index %= adapter.length;
            } else {
                return; // throw?
            }
        }
        if (index != currentItem) {
            if (animated) {
                scroll(index - currentItem, SCROLLING_DURATION);
            } else {
                invalidateLayouts();
                int old = currentItem;
                currentItem = index;
                notifyChangingListeners(old, currentItem);
                invalidate();
            }
        }
    }

    /**
     * Tests if wheel is cyclic. That means before the 1st item there is shown
     * the last one
     *
     * @return true if wheel is cyclic
     */
    public boolean isCyclic() {
        return isCyclic;
    }

    /**
     * Set wheel cyclic flag
     *
     * @param isCyclic the flag to set
     */
    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        invalidate();
        invalidateLayouts();
    }

    /**
     * Invalidates layouts
     */
    private void invalidateLayouts() {
        itemValues.clear();
        scrollPercent = 0;
        scrollingOffset = 0;
        calculateMaxContentWidth();
    }

    /**
     * Initializes resources
     */
    private void initResourcesIfNecessary() {

        if (labelPaint == null) {
            labelPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                    | Paint.DITHER_FLAG);
            labelPaint.setTextSize(mTextSize);
            labelPaint.setColor(mValueTextColor);
        }
        if (fistLabelPaint == null) {
            fistLabelPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            fistLabelPaint.setTextSize(mFistTextSize);
            fistLabelPaint.setColor(mValueTextColor);
        }

        if (centerDrawable == null) {
            centerDrawable = getContext().getResources().getDrawable(
                    R.drawable.wheel_val);
        }
        if (topShadow == null) {
            topShadow = new GradientDrawable(Orientation.TOP_BOTTOM,
                    SHADOWS_COLORS);
        }
        if (bottomShadow == null) {
            bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP,
                    SHADOWS_COLORS);
        }
        //
        if (isDebug) {
            //
            if (testLinePain == null) {
                testLinePain = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                testLinePain.setColor(mValueTextColor);
            }

            if (centerLinePain == null) {
                centerLinePain = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                centerLinePain.setColor(0xFF303F9F);
            }
        }

    }


    /**
     * Returns text item by index
     *
     * @param index the item index
     * @return the item or null
     */
    private String getTextItem(int index) {
        if (adapter == null || adapter.length == 0) {
            return null;
        }
        int count = adapter.length;
        if ((index < 0 || index >= count) && !isCyclic) {
            return null;
        } else {
            while (index < 0) {
                index = count + index;
            }
        }
        index %= count;
        return adapter[index] + getUnit();
    }

    /**
     * Returns the max item length that can be present
     *
     * @return the max length
     */
    private int getMaxTextLength() {
        if (adapter == null)
            return 0;
       /* String maxText = null;
        int addItems = visibleItems / 2;
        try {
            for (int i = Math.max(currentItem - addItems, 0); i < Math.min(
                    currentItem + visibleItems, adapter.length); i++) {
                String text = adapter[i] + getUnit();
                if (text != null
                        && (maxText == null || maxText.length() < text.length())) {
                    maxText = text;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            maxText = adapter[0] + getUnit();
        }
        L.e("maxText="+maxText);*/
        String maxText = getMaxLenghtText();
        return maxText != null ? maxText.length() : 0;
    }

    private void calculateMaxContentWidth() {
        initResourcesIfNecessary();
        String maxText = getMaxLenghtText();
        if (!TextUtils.isEmpty(maxText)) {
            itemMaxWidth = (float) Math.ceil(Layout.getDesiredWidth(maxText, labelPaint));
            itemsWidth = (int) itemMaxWidth;
        } else {
            itemMaxWidth = 0;
            itemsWidth = 0;
        }
    }

    /**
     * Calculates control width and creates text layouts
     *
     * @param widthSize the input layout width
     * @param mode      the layout mode
     * @return the calculated control width
     */
    private int calculateLayoutWidth(int widthSize, int mode) {
        calculateMaxContentWidth();
        labelWidth = 0;
        fistLableWidth = 0;
        if (label != null && label.length() > 0) {
            labelWidth = (int) Math.ceil(Layout.getDesiredWidth(label,
                    labelPaint));
        }
        //
        if (fistLabel != null && fistLabel.length() > 0) {
            fistLableWidth = (int) Math.ceil(Layout.getDesiredWidth(fistLabel,
                    fistLabelPaint));
        }
        //
        boolean recalculate = false;
        if (mode == MeasureSpec.EXACTLY) {
            viewWidth = widthSize;
            recalculate = true;
        } else {
            viewWidth = itemsWidth + labelWidth + fistLableWidth;
            if (labelWidth > 0) {
                viewWidth += LABEL_OFFSET;
            }
            if (fistLableWidth > 0) {
                viewWidth += FIST_LABEL_OFFSET;
            }
            // Check against our minimum width
            viewWidth = Math.max(viewWidth, getSuggestedMinimumWidth());
            if (mode == MeasureSpec.AT_MOST && widthSize < viewWidth) {
                viewWidth = widthSize;
                recalculate = true;
            }
        }
        if (recalculate) {
            // recalculate width
            int pureWidth = viewWidth - LABEL_OFFSET - FIST_LABEL_OFFSET;
            if (pureWidth <= 0) {
                itemsWidth = labelWidth = fistLableWidth = 0;
            }
            if (labelWidth > 0) {
                double newWidthItems = (double) itemsWidth * pureWidth
                        / (itemsWidth + labelWidth + fistLableWidth);
                itemsWidth = (int) newWidthItems;
                labelWidth = pureWidth - itemsWidth;
            } else {
                itemsWidth = pureWidth + LABEL_OFFSET + FIST_LABEL_OFFSET; // no label
            }
        }
        if (itemsWidth > 0) {
            setDefauteCurrentItem();
            createItemsPain();
            // createLabel(labelWidth);
            // createFistLabel(fistLableWidth);
        }
        //  }
        return viewWidth;
    }

    //设置默认选中项
    private void setDefauteCurrentItem() {
        if (adapter == null) {
            return;
        }
        if (currentItem < 0) {
            int count = adapter.length;
            if (count >= visibleItems) {
                setCurrentItem(visibleItems / 2);
            } else if (count > 0) {
                setCurrentItem(0);
            }
        }
    }

    //创建item内容画笔
    private void createItemsPain() {
        if (adapter == null) {
            return;
        }
        //创建item view
        itemValues.clear();
        int topPosition = getTopPosition();
        for (int i = -1; i < visibleItems + 1; i++) {//从顶部顺序增加 提前绘制上下边界外的内容，解决滑动会有闪烁的情况
            int itemPosition = topPosition + i;
            float textSize = calculateTextSize(itemPosition);
            String text = getText(itemPosition);//获取文案
            TextPaint itemTextPain = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            itemTextPain.setTextSize(textSize);
            itemTextPain.setColor(mTextItemValue);
            //
            ItemValue itemValue = new ItemValue();
            itemValue.itemTextPain = itemTextPain;
            itemValue.text = text;
            itemValue.height = getTextHeight(itemTextPain);
            itemValues.add(itemValue);
            if (i == 0) {
                itemScrollOffset = itemValue.height + ADDITIONAL_ITEM_HEIGHT;
            }
            //获取绘制最大的高度
            if (itemValue.height > itemMaxHeight) {
                itemMaxHeight = itemValue.height;
            }
        }
    }


    //计算文字大小 position 文字item
    private synchronized float calculateTextSize(int position) {
        int diff = currentItem - position;//计算与中间的item间隔多少个
        float calculateSize = mTextSize - Math.abs(diff) * dTextSize;//停止滚动时候，应该展示的字体大小 最大字体-每个间隔之间的字体大小
        if (position < currentItem) {//中间item 之上的item
            calculateSize = calculateSize + dTextSize * scrollPercent;
        } else {//字体变小
            if (position == currentItem && scrollPercent <= 0) {
                calculateSize = calculateSize + dTextSize * scrollPercent;
            } else
                calculateSize = calculateSize - dTextSize * scrollPercent;
        }
        //
        float abs = Math.abs(scrollPercent);
        if (abs == 0 || abs >= 1) {//滚动百分比是0或者大于1的时候矫正字体大小
            calculateSize = mTextSize - Math.abs(diff) * dTextSize;
        }

        //不是循环时候
        if (!isCyclic && ((currentItem == 0 && scrollPercent >= 0) || (currentItem == adapter.length - 1 && scrollPercent <= 0))) {
            calculateSize = mTextSize - Math.abs(diff) * dTextSize;
        }

        return calculateSize;

    }

    //滚轮可见第一个内容所在的位置
    private int getTopPosition() {
        return currentItem - (visibleItems / 2);
    }

    //获取对应位置的内容
    private String getText(int position) {
        int count = adapter.length;
        if (position >= count && !isCyclic) {//滚轮不循环，并且超过了
            return "";
        }
        //
        if (position >= count && isCyclic) {//循环滚轮时候
            position = position - count;//计算超出数据长度，从头开始的位置
        }
        String itemValue = getTextItem(position);
        return itemValue == null ? "" : itemValue;//此方法不能满足这种特定的需求，所以是否循环自己处理
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = calculateLayoutWidth(widthSize, widthMode);
        //高度以实际内容显示，不会适配使用者设置的高度
        int height = 0;//
        int size = itemValues.size();
        for (int i = 1; i < size - 1; i++) {
            height += itemValues.get(i).height + ADDITIONAL_ITEM_HEIGHT;//item之间的间距
        }
        setMeasuredDimension(width, height);
    }

    private void createClipRect() {
        //int left, int top, int right, int bottom
        clipRect = new Rect();
        float clipHeight = itemMaxHeight + ADDITIONAL_ITEM_HEIGHT;
        float top = (clipHeight + getHeight()) / 2 - clipHeight;
        clipRect.set((int) getStartX(itemMaxWidth), (int) top, (int) (getStartX(itemMaxWidth) + itemMaxWidth), (int) (top + clipHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (itemValues.isEmpty()) {
            if (itemsWidth == 0) {
                calculateLayoutWidth(getWidth(), MeasureSpec.EXACTLY);
            } else {
                setDefauteCurrentItem();
                createItemsPain();
            }
        }
        //创建
        if (clipRect == null) {
            createClipRect();
        }

        if (itemsWidth > 0) {
            drawItems(canvas);
            drawLabel(canvas);
            drawFistLabel(canvas);
            //
            if (isDebug) {
                canvas.save();
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, centerLinePain);
                canvas.restore();
            }
        }
    }


    /**
     * 新的方法
     *
     * @param canvas
     */
    private void drawItems(Canvas canvas) {
        //
        int size = itemValues.size();
        if (size == 0) {
            return;
        }
        int topPosition = getTopPosition();
        //
        float height = 0;
        float testHeight = 0;
        for (int i = 0; i < size; i++) {
            int itemPosition = topPosition + i - 1;
            canvas.save();
            canvas.clipRect(clipRect, Region.Op.DIFFERENCE);
            ItemValue itemValue = itemValues.get(i);
            TextPaint itemTextPain = itemValue.itemTextPain;
            itemTextPain.setColor(mTextItemValue);
            itemTextPain.setTextSize(calculateTextSize(itemPosition));
            float itemHeight = getTextHeight(itemTextPain);

            if (i == 0) {
                height = ADDITIONAL_ITEM_HEIGHT / 2 * -1;
            } else if (i == 1) {
                height = itemHeight + ADDITIONAL_ITEM_HEIGHT / 2;
            } else {
                height += itemHeight + ADDITIONAL_ITEM_HEIGHT;
            }
            float x = getStartX(Layout.getDesiredWidth(itemValue.text, itemTextPain));
            float y = height + scrollingOffset;
            canvas.drawText(itemValue.text, x, y, itemTextPain);
            canvas.restore();
            //
            canvas.save();
            canvas.clipRect(clipRect);
            itemTextPain.setColor(mValueTextColor);
            canvas.drawText(itemValue.text, x, y, itemTextPain);
            canvas.restore();
            //
            if (isDebug) {
                canvas.save();
                if (i == 0) {
                    testHeight = ADDITIONAL_ITEM_HEIGHT * -1;
                } else if (i == 1) {
                    testHeight = itemValue.height + ADDITIONAL_ITEM_HEIGHT;
                } else {
                    testHeight += itemValue.height + ADDITIONAL_ITEM_HEIGHT;
                }
                canvas.drawLine(0, testHeight, getWidth(), testHeight, testLinePain);
                if (i == 1) {
                    canvas.drawLine(0, itemValue.height + ADDITIONAL_ITEM_HEIGHT / 2, getWidth(), itemValue.height + ADDITIONAL_ITEM_HEIGHT / 2, testLinePain);
                    canvas.restore();
                }
            }

        }
    }


    //绘制内容后面的单位
    private void drawLabel(Canvas canvas) {
        if (TextUtils.isEmpty(label)) {//空就不绘制了
            return;
        }
        if (labelPaint != null) {
            canvas.save();
            float height = getTextHeight(labelPaint);
            canvas.drawText(label, getStartX(itemMaxWidth) + itemMaxWidth + LABEL_OFFSET, (getHeight() + height) / 2, labelPaint);
            canvas.restore();
        }
    }

    private void drawFistLabel(Canvas canvas) {
        // draw label
        if (TextUtils.isEmpty(fistLabel)) {//空就不绘制了
            return;
        }
        if (fistLabelPaint != null) {
            canvas.save();
            float height = getTextHeight(fistLabelPaint);
            float y = (getHeight() + height) / 2;
            float x = getStartX(itemMaxWidth) - fistLableWidth - FIST_LABEL_OFFSET;
            if (fistLabel.equals(".")) {
                fistLabelPaint.setTextSize(mTextSize);
                y = (getHeight() + getTextHeight(fistLabelPaint)) / 2;
                fistLabelPaint.setTextSize(mFistTextSize);
            }
            canvas.drawText(fistLabel, x, y, fistLabelPaint);
            canvas.restore();
        }
    }

    //计算高度
    private float getTextHeight(TextPaint textPaint) {
        if (textPaint == null)
            return 0;
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float height = (float) Math.ceil(fm.descent - fm.ascent - fm.bottom);
        return height;

    }

    //获取内容绘制X轴的起始位置
    private float getStartX(float itemWith) {
        float x = (getWidth() - itemWith) / 2;//居中
        return x;
    }

    //获取最长的text文案
    private String getMaxLenghtText() {
        if (adapter == null || adapter.length == 0)
            return "";
        String maxLengthText = "";
        for (int i = 0; i < adapter.length; i++) {
            String text = adapter[i];
            if (maxLengthText.length() < text.length()) {
                maxLengthText = text;
            }
        }
        return maxLengthText;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        handleTouch(event);

        String[] adapter = getAdapter();
        if (adapter == null) {
            return true;
        }
        if (!gestureDetector.onTouchEvent(event) && event.getAction() == MotionEvent.ACTION_UP) {
            justify();
        }

        return true;
    }


    private int centerPosition;
    private int clickPosition;
    long downTime;

    // 支持点击
    private void handleTouch(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                try {
                    downTime = System.currentTimeMillis();
                    if (!isScrollingPerformed) {//不是滚动状态下可以点击
                        float y = event.getY();//点击的坐标
                        clickPosition = -1;//点击的item位置
                        float height = 0;
                        for (int i = 0; i < visibleItems; i++) {
                            ItemValue itemValue = itemValues.get(i + 1);
                            height += itemValue.height + ADDITIONAL_ITEM_HEIGHT;
                            if (height - y > 0) {
                                clickPosition = i;
                                break;
                            }
                        }
                        //
                        centerPosition = visibleItems / 2;
                       /* //防止用户的使用意向是滚动
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 150);*/

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    clickPosition = -1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - downTime <= 150 && clickPosition != -1) {
                    if (!isScrollingPerformed) {
                        if (clickPosition != centerPosition) {//点击的不是中间那个item
                            setCurrentItem(currentItem - (centerPosition - clickPosition));
                            notifyClickComplete();
                        }
                    }
                }
                break;
        }

    }

    private void notifyClickComplete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // To change body of implemented methods use File | Settings |
                // File Templates.
                if (scrollListener != null) {
                    scrollListener.onScrollingFinished(WheelView.this);
                }
            }
        }, 50);
    }

    /**
     * Scrolls the wheel
     * 计算偏移量
     *
     * @param delta the scrolling value
     */
    private void doScroll(int delta) {
        scrollingOffset += delta;
        float percent = (float) ((scrollingOffset * 100 / getItemHeight()) * 0.01);//计算出百分值
        scrollPercent = percent >= 1 ? 1 : percent;
        // 限制滚动范围  基本不可能达到极值
        if (scrollingOffset >= 0) {
            scrollingOffset = Math.min(scrollingOffset, getHeight());
        } else {
            scrollingOffset = Math.max(scrollingOffset, -getHeight());
        }
        //
        int count = (int) (scrollingOffset / getItemHeight());//计算是否滚动到下一个位置，当count!=0 表示滚到下一个位置
        int pos = currentItem - count;//
        if (isCyclic && adapter.length > 0) {
            // fix position by rotating
            while (pos < 0) {
                pos += adapter.length;
            }
            pos %= adapter.length;
            //
        } else if (isScrollingPerformed) {
            if (pos < 0) {
                pos = 0;
            } else if (pos >= adapter.length) {
                pos = adapter.length - 1;
            }
        } else {
            // fix position
            pos = Math.max(pos, 0);
            pos = Math.min(pos, adapter.length - 1);
        }


        if (pos != currentItem) {
            scrollPercent = 0;//
            setCurrentItem(pos, false);
        } else {
            invalidate();
        }
    }

    private float getItemHeight() {
        if (scrollingOffset > 0)
            return itemScrollOffset;
        else
            return itemScrollOffset - dTextSize;

    }

    /**
     * Set next message to queue. Clears queue before.
     *
     * @param message the message to set
     */
    private void setNextMessage(int message) {
        clearMessages();
        animationHandler.sendEmptyMessage(message);
    }

    /**
     * Clears messages from queue
     */
    private void clearMessages() {
        animationHandler.removeMessages(MESSAGE_SCROLL);
        animationHandler.removeMessages(MESSAGE_JUSTIFY);
    }

    /**
     * Justifies wheel 矫正让选中项居中
     */
    private void justify() {
        if (adapter == null) {
            return;
        }
        lastScrollY = 0;
        int offset = (int) scrollingOffset;
        int itemHeight = (int) itemScrollOffset;
        boolean needToIncrease = offset > 0 ? currentItem < adapter.length : currentItem > 0;
        if ((isCyclic || needToIncrease) && Math.abs((float) offset) > (float) itemHeight / 2) {
            if (offset < 0)
                offset += itemHeight + MIN_DELTA_FOR_SCROLLING;
            else
                offset -= itemHeight + MIN_DELTA_FOR_SCROLLING;
        }
        // 限制滚动长度
        offset = Math.min(offset, getHeight());
        if (offset >= 0) {
            offset = Math.min(offset, getHeight());
        } else {
            offset = Math.max(offset, -getHeight());
        }
        if (Math.abs(offset) > MIN_DELTA_FOR_SCROLLING) {
            scroller.startScroll(0, 0, 0, offset, SCROLLING_DURATION);
            setNextMessage(MESSAGE_JUSTIFY);
        } else {
            finishScrolling();
        }
    }

    /**
     * Starts scrolling
     */
    private void startScrolling() {
        if (!isScrollingPerformed) {
            isScrollingPerformed = true;
            if (scrollListener != null) {
                scrollListener.onScrollingStarted(this);
            }
        }
    }

    /**
     * Finishes scrolling
     */
    private void finishScrolling() {
        if (isScrollingPerformed) {
            isScrollingPerformed = false;
            if (scrollListener != null) {
                scrollListener.onScrollingFinished(this);
            }
        }
        invalidateLayouts();
        invalidate();
    }

    /**
     * Scroll the wheel
     *
     * @param
     * @param time scrolling duration
     */
    private void scroll(int itemsToScroll, int time) {
        scroller.forceFinished(true);

        lastScrollY = (int) scrollingOffset;
        int offset = (int) (itemsToScroll * itemScrollOffset);

        scroller.startScroll(0, lastScrollY, 0, offset - lastScrollY, time);
        setNextMessage(MESSAGE_SCROLL);

        startScrolling();
    }

    public void setOnScrollListener(OnWheelScrollListener listener) {
        scrollListener = listener;
    }

    public interface OnWheelChangedListener {
        /**
         * Callback method to be invoked when current item changed
         *
         * @param wheel    the wheel view whose state has changed
         * @param oldValue the old value of current item
         * @param newValue the new value of current item
         */
        void onChanged(WheelView wheel, int oldValue, int newValue);
    }

    public interface OnWheelScrollListener {
        /**
         * Callback method to be invoked when scrolling started.
         *
         * @param wheel the wheel view whose state has changed.
         */
        void onScrollingStarted(WheelView wheel);

        /**
         * Callback method to be invoked when scrolling ended.
         *
         * @param wheel the wheel view whose state has changed.
         */
        void onScrollingFinished(WheelView wheel);
    }

    /**
     * Gets label
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label
     *
     * @param newLabel the label to set
     */
    public void setLabel(String newLabel) {
        if (label == null || !label.equals(newLabel)) {
            label = newLabel;
            invalidate();
        }
    }

    /**
     * 设置内容前面的label文案
     *
     * @param newFistLabel
     */
    public void setFistLabel(String newFistLabel) {
        if (fistLabel == null || !fistLabel.equals(newFistLabel)) {
            fistLabel = newFistLabel;
            reset();
            invalidate();
        }
    }

    /**
     * Adds wheel changing listener
     *
     * @param listener the listener
     */
    public void addChangingListener(OnWheelChangedListener listener) {
        changingListeners.add(listener);
    }


    /**
     * 设置未选中的颜色
     *
     * @param color
     */
    public void setTextDefaultColor(int color) {
        mTextItemValue = color;
        invalidate();
    }

    /**
     * 设置选中的颜色
     *
     * @param color
     */
    public void setTextSelectorColor(int color) {
        mValueTextColor = color;
        invalidate();
    }

    /**
     * 设置内容后面文字颜色
     *
     * @param color
     */
    public void setTextLabelColor(int color) {
        if (labelPaint != null) {
            labelPaint.setColor(color);
            invalidate();
        }
    }

    /**
     * 设置内容前面label的文字颜色
     *
     * @param color
     */
    public void setTextFistLabelColor(int color) {
        if (fistLabelPaint != null) {
            fistLabelPaint.setColor(color);
            invalidate();
        }
    }

    /**
     * 设置显示的字体大小
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        mTextSize = (int) sp2px(textSize);
        reset();
        invalidate();
    }

    /**
     * 设置内容前面的label字体大小
     *
     * @param textSize
     */
    public void setFistLabelTextSize(int textSize) {
        mFistTextSize = (int) sp2px(textSize);
        invalidate();
    }

    /**
     * 绘制前就需要完成此设置
     * 设置item之间
     */
    public void setItemDivHeight(int divHeight) {
        ADDITIONAL_ITEM_HEIGHT = (int) dp2px(divHeight);
        reset();
        invalidate();
    }

    /**
     * 每个item之间的字体大小差
     *
     * @param downSzie
     */
    public void setTextDownSize(int downSzie) {
        dTextSize = (int) sp2px(downSzie);
        reset();
        invalidate();
    }

    /**
     * 设置
     *
     * @param space
     */
    public void setLabelSpace(int space) {
        LABEL_OFFSET = (int) dp2px(space);
        invalidate();
    }

    /**
     * 设置内容前面的label与内容的间隙
     *
     * @param space
     */
    public void setFistLabelSpace(int space) {
        FIST_LABEL_OFFSET = (int) dp2px(space);
        reset();
        invalidate();
    }

    //重置为初始化状态
    private void reset() {
        if (itemValues != null) {
            itemValues.clear();
        }
        clipRect = null;
        itemValues.clear();
        itemsWidth = 0;
    }

    private float sp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }

    private float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        invalidate();
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    private class ItemValue {
        TextPaint itemTextPain;
        String text;
        float height;
    }

    private void computeFlingLimitY() {
        if (adapter != null) {
            if (isCyclic) {
                mMinFlingY =Integer.MIN_VALUE;
                mMaxFlingY = Integer.MAX_VALUE;
            } else {


                mMinFlingY = (int) - (currentItem * itemScrollOffset);
                mMaxFlingY= (int)((adapter.length - 1 - currentItem) * itemScrollOffset);

            }
        }
    }


}
