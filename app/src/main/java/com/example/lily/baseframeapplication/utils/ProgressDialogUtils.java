package com.example.lily.baseframeapplication.utils;

import android.app.ProgressDialog;
import android.content.Context;
import com.example.lily.baseframeapplication.app.AppContext;

/**
 * Created by Administrator on 2016/11/21.
 */

public class ProgressDialogUtils {
    private ProgressDialog mProgressDialog;

    private Context mContext;


    public ProgressDialogUtils(Context c) {
        this.mContext = c;
        //mProgressDialog = new ProgressDialog(c);
    }


    public void showProgress(boolean flag, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(flag);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
        }

        mProgressDialog.show();
    }


    public void showProgress(String message) {
        showProgress(true, message);
    }
    public void showProgress(int textResId) {
        showProgress(true, AppContext.getString(textResId));
    }

    public void showProgress() {
        showProgress(true);
    }


    public void showProgress(boolean flag) {
        showProgress(flag, "");
    }


    public void hideProgress() {
        if (mProgressDialog == null) return;
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
