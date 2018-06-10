package com.cai.framework.widget.wheel;

import android.content.Context;
import android.content.DialogInterface;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.TextView;
import com.cai.framework.R;
/**
 * 3个wheelview
 */
public class ThreeWheelDialog extends BaseBottomDialog implements View.OnClickListener {
    /**
     * 确定按钮控件
     **/
    private TextView mButtonOK;
    /**
     * 取消按钮控件
     **/
    private TextView mButtonCancel;
    /**
     * 对话框标题控件
     **/
    private TextView mDialogTitle;
    /**
     * 配置类
     **/
    private ThreeWheelModel threeWheelModel;
    /**
     * 确定按钮点击事件
     **/
    private WheelCallBackListener mOkClickListener;
    /**
     * 取消按钮点击事件
     **/
    private WheelCallBackListener mCancelClickListener;
    /**
     * 滚轮滑动事件
     **/
    private WheelDialogChangedListener mWheelDialogChangedListener;
    /**
     * 左边滚轮
     **/
    private WheelView wheelLeft;
    /**
     * 右边滚轮
     **/
    private WheelView wheelRight;
    /**
     * 中间滚轮
     **/
    private WheelView wheelCenter;

    /**
     * 确定按钮点击事件
     **/
    private WheelCallBackValueListener mOkClickValueListener;
    /**
     * 取消按钮点击事件
     **/
    private WheelCallBackValueListener mCancelClickValueListener;
    /**
     * 滑动选择事件
     **/
    private WheelCallBackListener mSelectChangeFinishListener;

    private boolean mOKClick;

    public boolean isOKClick() {
        return mOKClick;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_layout_wheel_3;
    }

    @Override
    public void initUI(Object... params) {
        initTop();
        initWheel();
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mOKClick = true;
            }
        });
    }

    /**
     * 设置布局平分的比例
     * 此方法可不调用,默认1：1：1,
     */
    public void setDivideRate(float leftRate, float centerRate, float rightRate) {
        float allRate = leftRate + centerRate + rightRate;
        leftRate = leftRate / allRate;
        centerRate = centerRate / allRate;
        rightRate = rightRate / allRate;

        PercentRelativeLayout.LayoutParams prlp1 = (PercentRelativeLayout.LayoutParams) wheelLeft.getLayoutParams();
        prlp1.getPercentLayoutInfo().widthPercent = leftRate;
        wheelLeft.setLayoutParams(prlp1);

        PercentRelativeLayout.LayoutParams prlp2 = (PercentRelativeLayout.LayoutParams) wheelCenter.getLayoutParams();
        prlp2.getPercentLayoutInfo().widthPercent = centerRate;
        wheelCenter.setLayoutParams(prlp2);

        PercentRelativeLayout.LayoutParams prlp3 = (PercentRelativeLayout.LayoutParams) wheelRight.getLayoutParams();
        prlp3.getPercentLayoutInfo().widthPercent = rightRate;
        wheelRight.setLayoutParams(prlp3);

        wheelLeft.requestLayout();
        wheelCenter.requestLayout();
        wheelRight.requestLayout();
    }


    @Override
    public void initDatas(Object... params) {
        threeWheelModel = (ThreeWheelModel) params[0];
    }

    @Override
    public View getRootView() {
        return findViewById(R.id.rootView);
    }

    public ThreeWheelDialog(Context context, ThreeWheelModel threeWheelModel) {
        super(context, threeWheelModel);
    }

    public ThreeWheelDialog(Context context, int style, ThreeWheelModel threeWheelModel) {
        super(style, context, threeWheelModel);
    }


    private void initTop() {
        mButtonOK = (TextView) findViewById(R.id.dialog_btnOk);
        mButtonOK.setOnClickListener(this);
        mButtonCancel = (TextView) findViewById(R.id.dialog_btnCancel);
        mButtonCancel.setOnClickListener(this);
        mDialogTitle = (TextView) findViewById(R.id.dialog_title);
        mDialogTitle.setVisibility(View.VISIBLE);
        mDialogTitle.setText(threeWheelModel.getTitle());
    }

    private void initWheel() {

        //左边滚轮
        wheelLeft = (WheelView) findViewById(R.id.pop_wv_left);
        wheelLeft.setTextSelectorColor(mContext.getResources().getColor(R.color.black_a));
        wheelLeft.setAdapter(threeWheelModel.getLeftContent());
        wheelLeft.setCurrentItem(threeWheelModel.getLeftCurrentPosition());
        wheelLeft.setCyclic(threeWheelModel.isLeftCircle());
        wheelLeft.setOnScrollListener(new WheelView.OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mSelectChangeFinishListener != null) {
                    mSelectChangeFinishListener.onClick(wheel.getCurrentItem(), threeWheelModel.getCenterCurrentPosition(), threeWheelModel.getRightCurrentPosition());
                }
            }
        });
        wheelLeft.addChangingListener(new WheelView.OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                threeWheelModel.setLeftCurrentPosition(newValue);
                WheelSelectedChangeListener wheelSelectedChangeListener = threeWheelModel.getWheelSelectedChangeListener();
                if (wheelSelectedChangeListener != null) {
                  /* String leftNewValue = threeWheelModel.getLeftValue()[newValue];
                     wheelSelectedChangeListener.onLeftChanged(leftNewValue);*/
                    String leftNewValue = threeWheelModel.getLeftValue()[newValue];
                    String centerNewValue = threeWheelModel.getCenterValue()[threeWheelModel.getCenterCurrentPosition()];
                    String rightNewValue = threeWheelModel.getRightValue()[threeWheelModel.getRightCurrentPosition()];
                    wheelSelectedChangeListener.onChanged(leftNewValue, centerNewValue, rightNewValue);

                    wheelCenter.setAdapter(threeWheelModel.getCenterContent());
                    wheelCenter.setCurrentItem(threeWheelModel.getCenterCurrentPosition());
                    wheelRight.setAdapter(threeWheelModel.getRightContent());
                    wheelRight.setCurrentItem(threeWheelModel.getRightCurrentPosition());
                }

                if (mWheelDialogChangedListener != null) {
                    mWheelDialogChangedListener.onChanged(newValue,
                            threeWheelModel.getCenterCurrentPosition(), threeWheelModel.getRightCurrentPosition());
                }
            }
        });

        //中间滚轮
        wheelCenter = (WheelView) findViewById(R.id.pop_wv_center);
        wheelCenter.setTextSelectorColor(mContext.getResources().getColor(R.color.black_a));
        wheelCenter.setAdapter(threeWheelModel.getCenterContent());
        wheelCenter.setCurrentItem(threeWheelModel.getCenterCurrentPosition());
        wheelCenter.setCyclic(threeWheelModel.isCenterCircle());
        wheelCenter.setOnScrollListener(new WheelView.OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mSelectChangeFinishListener != null) {
                    mSelectChangeFinishListener.onClick(threeWheelModel.getLeftCurrentPosition(), wheel.getCurrentItem(), threeWheelModel.getRightCurrentPosition());
                }
            }
        });
        wheelCenter.addChangingListener(new WheelView.OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                threeWheelModel.setCenterCurrentPosition(newValue);
                WheelSelectedChangeListener wheelSelectedChangeListener = threeWheelModel.getWheelSelectedChangeListener();
                if (wheelSelectedChangeListener != null) {
                /*    String leftNewValue = threeWheelModel.getLeftValue()[threeWheelModel.getLeftCurrentPosition()];
                    String centerNewValue = threeWheelModel.getCenterValue()[newValue];
                    wheelSelectedChangeListener.onCenterChanged(leftNewValue, centerNewValue);*/
                    String leftNewValue = threeWheelModel.getLeftValue()[threeWheelModel.getLeftCurrentPosition()];
                    String centerNewValue = threeWheelModel.getCenterValue()[newValue];
                    String rightNewValue = threeWheelModel.getRightValue()[threeWheelModel.getRightCurrentPosition()];
                    wheelSelectedChangeListener.onChanged(leftNewValue, centerNewValue, rightNewValue);
                    wheelRight.setAdapter(threeWheelModel.getRightContent());
                    wheelRight.setCurrentItem(threeWheelModel.getRightCurrentPosition());
                }

                if (mWheelDialogChangedListener != null) {
                    mWheelDialogChangedListener.onChanged(threeWheelModel.getLeftCurrentPosition(),
                            newValue, threeWheelModel.getRightCurrentPosition());
                }
            }
        });

        //右边滚轮
        wheelRight = (WheelView) findViewById(R.id.pop_wv_right);
        wheelRight.setTextSelectorColor(mContext.getResources().getColor(R.color.black_a));
        wheelRight.setAdapter(threeWheelModel.getRightContent());
        wheelRight.setCurrentItem(threeWheelModel.getRightCurrentPosition());
        wheelRight.setCyclic(threeWheelModel.isRightCircle());
        wheelRight.setOnScrollListener(new WheelView.OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mSelectChangeFinishListener != null) {
                    mSelectChangeFinishListener.onClick(threeWheelModel.getLeftCurrentPosition(), wheel.getCurrentItem(), threeWheelModel.getRightCurrentPosition());
                }
            }
        });
        wheelRight.addChangingListener(new WheelView.OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                threeWheelModel.setRightCurrentPosition(newValue);
                WheelSelectedChangeListener wheelSelectedChangeListener = threeWheelModel.getWheelSelectedChangeListener();
                if (wheelSelectedChangeListener != null) {
                 /*   String leftNewValue = threeWheelModel.getLeftValue()[threeWheelModel.getLeftCurrentPosition()];
                    String centerNewValue = threeWheelModel.getCenterValue()[threeWheelModel.getCenterCurrentPosition()];
                    String rightNewValue = threeWheelModel.getRightValue()[newValue];
                    wheelSelectedChangeListener.onRightChanged(leftNewValue, centerNewValue, rightNewValue);*/
                    String leftNewValue = threeWheelModel.getLeftValue()[threeWheelModel.getLeftCurrentPosition()];
                    String centerNewValue = threeWheelModel.getCenterValue()[threeWheelModel.getCenterCurrentPosition()];
                    String rightNewValue = threeWheelModel.getRightValue()[newValue];
                    wheelSelectedChangeListener.onChanged(leftNewValue, centerNewValue, rightNewValue);
                }
                if (mWheelDialogChangedListener != null) {
                    mWheelDialogChangedListener.onChanged(threeWheelModel.getLeftCurrentPosition(),
                            threeWheelModel.getCenterCurrentPosition(), newValue);
                }
            }
        });
    }

    /**
     * 设置滚轮左中右的默认选中值
     *
     * @param left
     * @param center
     * @param right
     */
    public void setWheelLeftCenterRightValue(int left, int center, int right) {
        if (null != wheelLeft)
            wheelLeft.setCurrentItem(left);
        if (null != wheelRight)
            wheelRight.setCurrentItem(right);
        if (null != wheelCenter)
            wheelCenter.setCurrentItem(center);
    }

    /**
     * 设置确认按钮监听事件
     *
     * @param text                  确认按钮的文案
     * @param wheelCallBackListener
     */
    public void setOnOKButtonListener(String text,
                                      WheelCallBackListener wheelCallBackListener) {
        mButtonOK.setText(text);
        mOkClickListener = wheelCallBackListener;
    }

    /**
     * 设置确认按钮监听事件
     *
     * @param wheelCallBackListener
     */
    public void setOnOKButtonListener(WheelCallBackListener wheelCallBackListener) {
        mOkClickListener = wheelCallBackListener;
    }

    /**
     * 设置取消按钮监听事件
     *
     * @param wheelCallBackListener
     */
    public void setOnCancelButtonListener(WheelCallBackListener wheelCallBackListener) {
        mCancelClickListener = wheelCallBackListener;
    }

    /**
     * 设置取消按钮监听事件
     *
     * @param text                  取消按钮的文案
     * @param wheelCallBackListener
     */
    public void setOnCancelButtonListener(String text, WheelCallBackListener wheelCallBackListener) {
        mButtonCancel.setText(text);
        mCancelClickListener = wheelCallBackListener;
    }

    /**
     * 设置滚轮滑动监听事件
     *
     * @param wheelDialogChangedListener
     */
    public void setOnWheelDialogChangedListener(WheelDialogChangedListener wheelDialogChangedListener) {
        mWheelDialogChangedListener = wheelDialogChangedListener;
    }

    /**
     * 设置确认按钮监听事件
     *
     * @param text                       确认按钮的文案
     * @param wheelCallBackValueListener
     */
    public void setOnOKButtonListener(String text,
                                      WheelCallBackValueListener wheelCallBackValueListener) {
        mButtonOK.setText(text);
        mOkClickValueListener = wheelCallBackValueListener;
    }

    /**
     * 设置确认按钮监听事件
     *
     * @param wheelCallBackValueListener
     */
    public void setOnOKButtonListener(WheelCallBackValueListener wheelCallBackValueListener) {
        mOkClickValueListener = wheelCallBackValueListener;
    }

    /**
     * 设置取消按钮监听事件
     *
     * @param wheelCallBackValueListener
     */
    public void setOnCancelButtonListener(WheelCallBackValueListener wheelCallBackValueListener) {
        mCancelClickValueListener = wheelCallBackValueListener;
    }

    /**
     * 设置取消按钮监听事件
     *
     * @param text                       取消按钮的文案
     * @param wheelCallBackValueListener
     */
    public void setOnCancelButtonListener(String text, WheelCallBackValueListener wheelCallBackValueListener) {
        mButtonCancel.setText(text);
        mCancelClickValueListener = wheelCallBackValueListener;
    }

    /**
     * 滑动选择事件
     */
    public void setOnSelectChangeFinishListener(WheelCallBackListener onClickListener) {
        mSelectChangeFinishListener = onClickListener;
    }

    public void onClick(View v) {
        if (R.id.dialog_btnOk == v.getId()) {
            onButtonOK();
        } else if (R.id.dialog_btnCancel == v.getId()) {
            onButtonCancel();
        }
    }

    private void onButtonOK() {
        mOKClick = true;
        dismiss();
        if (mOkClickListener != null) {
            mOkClickListener.onClick(threeWheelModel.getLeftCurrentPosition(), threeWheelModel.getCenterCurrentPosition(), threeWheelModel.getRightCurrentPosition());
        }
        if (mOkClickValueListener != null) {
            String leftValue = threeWheelModel.getLeftWheelModel().getValues()[threeWheelModel.getLeftCurrentPosition()];
            String centerValue = threeWheelModel.getCenterWheelModel().getValues()[threeWheelModel.getCenterCurrentPosition()];
            String rightValue = threeWheelModel.getRightWheelModel().getValues()[threeWheelModel.getRightCurrentPosition()];
            mOkClickValueListener.onClick(leftValue, centerValue, rightValue);
        }
    }

    private void onButtonCancel() {
        mOKClick = false;
        dismiss();
        if (mCancelClickListener != null) {
            mCancelClickListener.onClick();
        }
        if (mCancelClickValueListener != null) {
            mCancelClickValueListener.onClick();
        }
    }
}