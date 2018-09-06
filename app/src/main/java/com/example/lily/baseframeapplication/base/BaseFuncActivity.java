package com.example.lily.baseframeapplication.base;

import com.example.lily.baseframeapplication.dao.CObserver;
import com.example.lily.baseframeapplication.dao.retrofit.ErrorThrowable;
import com.example.lily.baseframeapplication.utils.ProgressDialogUtils;
import com.example.lily.baseframeapplication.utils.ToastUtils;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by lily on 2017/11/9.
 */

public class BaseFuncActivity extends SupportActivity {
    //异步操作对话框
    public ProgressDialogUtils mProgressDialogUtils;


    public BaseFuncActivity() {
        mProgressDialogUtils = new ProgressDialogUtils(this);
    }



    public <T> CObserver<T> getSubscribe(OnSubscribeSuccess<T> onSubscribeSuccess) {
        return getSubscribe(0, onSubscribeSuccess);
    }


    public <T> CObserver<T> getSubscribe(int loadingRes, OnSubscribeSuccess<T> onSubscribeSuccess) {
        return new CObserver<T>() {
            @Override public void onPrepare() {
                if (loadingRes != -1) {
                    if (loadingRes == 0) {
                        mProgressDialogUtils.showProgress();
                    }
                    else {
                        mProgressDialogUtils.showProgress(loadingRes);
                    }
                }
            }


            @Override public void onError(ErrorThrowable throwable) {
                ToastUtils.showLongToast(throwable.msg);
                if (loadingRes != -1) {
                    mProgressDialogUtils.hideProgress();
                }
            }


            @Override public void onSuccess(T t) {
                if (loadingRes != -1) {
                    mProgressDialogUtils.hideProgress();
                }
                if (onSubscribeSuccess != null) onSubscribeSuccess.onSuccess(t);
            }
        };
    }


    /**
     * 不显示异步操作对话框的Subscribe
     * @param onSubscribeSuccess
     * @param <T>
     * @return
     */
    public <T> CObserver<T> getNotProSubscribe(OnSubscribeSuccess<T> onSubscribeSuccess) {
        return new CObserver<T>() {
            @Override public void onPrepare() {

            }


            @Override public void onError(ErrorThrowable throwable) {
                mProgressDialogUtils.hideProgress();
                ToastUtils.showLongToast(throwable.msg);
            }


            @Override public void onSuccess(T t) {
                mProgressDialogUtils.hideProgress();
                if (onSubscribeSuccess != null) onSubscribeSuccess.onSuccess(t);
            }
        };
    }


    public interface OnSubscribeSuccess<T> {
        void onSuccess(T t);
    }


    @Override protected void onDestroy() {
        mProgressDialogUtils.hideProgress();
        super.onDestroy();
    }
}
