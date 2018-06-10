package com.cai.framework.widget.wheel;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cai.framework.R;
/**
 * 单个wheel
 */
public class OneWheelDialog extends BaseBottomDialog implements OnClickListener {
    /**
     * 确定按钮控件
     **/
    private TextView mButtonOK;
    /**
     * 取消按钮控件
     **/
    private TextView mButtonCancel;
    /**
     * 确定按钮点击事件
     **/
    private WheelCallBackListener mOkClickListener;
    /**
     * 取消按钮点击事件
     **/
    private WheelCallBackListener mCancelClickListener;
    /**
     * 滑动选择事件
     **/
    private WheelCallBackListener mSelectChangeFinishListener;
    /**
     * 配置类
     **/
    OneWheelModel oneWheelModel;
    /**
     * 对话框标题控件
     **/
    private TextView mDialogTitle;

    public OneWheelDialog(Activity context , OneWheelModel oneWheelModel) {
        super(context, oneWheelModel);
    }

    public OneWheelDialog(Activity context, int style, OneWheelModel oneWheelModel) {
        super(style, context, oneWheelModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_layout_wheel_1;
    }


    public View getRootView() {
        return findViewById(R.id.rootView);
    }

    @Override
    public void initDatas(Object... params) {
        oneWheelModel = (OneWheelModel) params[0];
    }

    @Override
    public void initUI(Object... params) {
        mButtonOK = (TextView) findViewById(R.id.dialog_btnCancel);
        mButtonOK.setOnClickListener(this);
        mButtonCancel = (TextView) findViewById(R.id.dialog_btnOk);
        mButtonCancel.setOnClickListener(this);

        mDialogTitle = (TextView) findViewById(R.id.dialog_title);
        mDialogTitle.setVisibility(View.VISIBLE);
        mDialogTitle.setText(oneWheelModel.getTitle());
        if (oneWheelModel.getTitleSize() > 0) {
            mDialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, oneWheelModel.getTitleSize());
        }
        initWheelUI();
    }

    private WheelView wheelLeft;

    private void initWheelUI() {
        wheelLeft = (WheelView) findViewById(R.id.pop_wv);
        wheelLeft.setTextSelectorColor(mContext.getResources().getColor(R.color.black_a));
        wheelLeft.setAdapter(oneWheelModel.getContent());
        wheelLeft.setCyclic(oneWheelModel.isCircle());
        wheelLeft.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                oneWheelModel.setCurrentPosition(newValue);
            }
        });
        wheelLeft.setOnScrollListener(new WheelView.OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (mSelectChangeFinishListener != null) {
                    mSelectChangeFinishListener.onClick(wheel.getCurrentItem());
                }
            }
        });
        wheelLeft.setCurrentItem(oneWheelModel.getCurrentPosition());
        ((TextView) findViewById(R.id.dialog_title)).setText(oneWheelModel
                .getTitle());
    }

    public WheelView getWheel() {
        return wheelLeft;
    }

    /**
     * 确定按钮点击事件
     *
     * @param onClickListener
     */
    public void setOnOKButtonListener(WheelCallBackListener onClickListener) {
        mOkClickListener = onClickListener;
    }

    /**
     * 确定按钮点击事件
     *
     * @param text            确定按钮文案
     * @param onClickListener
     */
    public void setOnOKButtonListener(String text, WheelCallBackListener onClickListener) {
        mButtonOK.setText(text);
        mOkClickListener = onClickListener;
    }

    /**
     * 取消按钮点击事件
     *
     * @param onClickListener
     */
    public void setOnCancelButtonListener(WheelCallBackListener onClickListener) {
        mCancelClickListener = onClickListener;
    }

    /**
     * 取消按钮点击事件
     *
     * @param text            取消按钮文案
     * @param onClickListener
     */
    public void setOnCancelButtonListener(String text, WheelCallBackListener onClickListener) {
        mButtonCancel.setText(text);
        mCancelClickListener = onClickListener;
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
            mOkClickListener.onClick(oneWheelModel.getCurrentPosition());
        }
    }

    private void onButtonCancel() {
        dismiss();
        if (mCancelClickListener != null) {
            mCancelClickListener.onClick();
        }
    }

    public void setTitle(String title) {        //仅用于刷新title
        mDialogTitle.setText(title);
        mDialogTitle.invalidate();
    }
}
