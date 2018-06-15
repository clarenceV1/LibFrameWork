package com.cai.framework.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 短信验证码倒计时
 */
public class SMSCountDownTimer extends CountDownTimer {
    TextView textView;//显示倒计时的控件

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public SMSCountDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //防止计时过程中重复点击
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s后重新获取");
    }

    @Override
    public void onFinish() {
//重新给Button设置文字
        textView.setText("重新获取");
        //设置可点击
        textView.setClickable(true);
    }
}
