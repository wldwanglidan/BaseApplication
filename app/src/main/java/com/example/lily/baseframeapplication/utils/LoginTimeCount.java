package com.example.lily.baseframeapplication.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;
import com.example.lily.baseframeapplication.R;

/**
 * 倒计时类
 * User: Ljh
 * Date&Time: 2016-03-29 16:45
 */
public class LoginTimeCount extends CountDownTimer {
    private Button btnSendCode;
    private Context context;
    public LoginTimeCount(long millisInFuture, long countDownInterval, Button
            btnSendCode
    , Context context) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        this.btnSendCode=btnSendCode;
        this.context=context;
    }

    @Override
    public void onFinish() {//计时完毕时触发
        btnSendCode.setText(context.getResources().getString(R.string.resend_verification_code));
        btnSendCode.setClickable(true);
        btnSendCode.setEnabled(true);
        btnSendCode.setBackground(context.getResources().getDrawable(R
                .drawable.btn_send_code_bg));
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        btnSendCode.setClickable(false);
        btnSendCode.setText(String.format(context.getResources().getString(R.string
                .re_verification),millisUntilFinished / 1000));
        btnSendCode.setEnabled(false);
        btnSendCode.setBackground(null);
    }
}
