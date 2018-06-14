package com.cai.framework.widget.dialog.wheel;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cai.framework.R;
import com.cai.framework.widget.dialog.BaseBottomDialog;

/**
 * 两个wheelview
 */
public class TwoWheelDialog extends BaseBottomDialog implements View.OnClickListener {
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
    TwoWheelModel twoWheelModel;
    /**
     * 确定按钮点击事件
     **/
    private WheelCallBackListener mOkClickListener;
    /**
     * 取消按钮点击事件
     **/
    private WheelCallBackListener mCancelClickListener;
    /**
     * 左边滚轮
     **/
    private WheelView wheelLeft;
    /**
     * 右边滚轮
     **/
    private WheelView wheelRight;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_layout_wheel_2;
    }

    @Override
    public void initUI(Object... params) {
        init();
    }

    /**
     * 滑动选择事件
     **/
    private WheelCallBackListener mSelectChangeFinishListener;

    @Override
    public void initDatas(Object... params) {
        twoWheelModel = (TwoWheelModel) params[0];
    }

    @Override
    public View getRootView() {
        return findViewById(R.id.rootView);
    }

    public TwoWheelDialog(Context context, TwoWheelModel twoWheelModel) {
        super(context, twoWheelModel);
    }

    public TwoWheelDialog(Context context, int style, TwoWheelModel twoWheelModel) {
        super(style, context, twoWheelModel);
    }

    private void init() {
        mButtonOK = (TextView) findViewById(R.id.dialog_btnOk);
        mButtonOK.setOnClickListener(this);
        mButtonCancel = (TextView) findViewById(R.id.dialog_btnCancel);
        mButtonCancel.setOnClickListener(this);
        mDialogTitle = (TextView) findViewById(R.id.dialog_title);
        mDialogTitle.setVisibility(View.VISIBLE);
        mDialogTitle.setText(twoWheelModel.getTitle());
        initWheelUI();
    }

    private void initWheelUI() {
        wheelLeft = (WheelView) findViewById(R.id.pop_wv_left);
        wheelLeft.setTextSelectorColor(mContext.getResources().getColor(R.color.black_a));
        wheelLeft.setAdapter(twoWheelModel.getLeftContent());
        wheelLeft.setCurrentItem(twoWheelModel.getLeftCurrentPosition());
        wheelLeft.setCyclic(twoWheelModel.isLeftCircle());
        wheelLeft.setOnScrollListener(new WheelView.OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mSelectChangeFinishListener != null) {
                    mSelectChangeFinishListener.onClick(wheel.getCurrentItem(), wheelRight.getCurrentItem());
                }
            }
        });
        wheelLeft.addChangingListener(new WheelView.OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                twoWheelModel.setLeftCurrentPosition(newValue);
            }
        });

        wheelRight = (WheelView) findViewById(R.id.pop_wv_right);
        wheelRight.setTextSelectorColor(mContext.getResources().getColor(R.color.black_a));
        wheelRight.setAdapter(twoWheelModel.getRightContent());
        wheelRight.setCurrentItem(twoWheelModel.getRightCurrentPosition());
        wheelRight.setCyclic(twoWheelModel.isRightCircle());
        wheelRight.setOnScrollListener(new WheelView.OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mSelectChangeFinishListener != null) {
                    mSelectChangeFinishListener.onClick(wheelLeft.getCurrentItem(), wheel.getCurrentItem());
                }
            }
        });
        wheelRight.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                twoWheelModel.setRightCurrentPosition(newValue);
            }
        });
    }

    /**
     * 设置左右滚轮的默认选中位置
     *
     * @param left
     * @param right
     */
    public void setWheelLeftRightValue(int left, int right) {
        if (null != wheelLeft)
            wheelLeft.setCurrentItem(left);
        if (null != wheelRight)
            wheelRight.setCurrentItem(right);
    }

    /**
     * 设置确定按钮监听事件
     *
     * @param text
     * @param wheelCallBackListener
     */
    public void setOnOKButtonListener(String text,
                                      WheelCallBackListener wheelCallBackListener) {
        mButtonOK.setText(text);
        mOkClickListener = wheelCallBackListener;
    }

    /**
     * 设置确定按钮监听事件
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
     * @param wheelCallBackListener
     */
    public void setOnCancelButtonListener(String text, WheelCallBackListener wheelCallBackListener) {
        mButtonCancel.setText(text);
        mCancelClickListener = wheelCallBackListener;
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
        dismiss();
        if (mOkClickListener != null) {
            mOkClickListener.onClick(twoWheelModel.getLeftCurrentPosition(), twoWheelModel.getRightCurrentPosition());
        }
    }

    private void onButtonCancel() {
        dismiss();
        if (mCancelClickListener != null) {
            mCancelClickListener.onClick();
        }
    }
}
