package com.example.lily.baseframeapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.lily.baseframeapplication.dao.CObserver;
import com.example.lily.baseframeapplication.dao.retrofit.ErrorThrowable;
import com.example.lily.baseframeapplication.utils.ProgressDialogUtils;
import com.example.lily.baseframeapplication.utils.ToastUtils;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by lily on 2017/11/10.
 */

public class BaseFuncFragment extends SupportFragment {
    //异步操作对话框
    public ProgressDialogUtils mProgressDialogUtils;


    public BaseFuncFragment() {
        //mProgressDialogUtils = new ProgressDialogUtils(getContext());
    }


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialogUtils = new ProgressDialogUtils(getContext());
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

    public <T> CObserver<T> getNotProSubscribe(BaseFuncActivity.OnSubscribeSuccess<T>
                                                       onSubscribeSuccess) {
        return new CObserver<T>() {
            @Override public void onPrepare() {

            }


            @Override public void onError(ErrorThrowable throwable) {
                ToastUtils.showLongToast(throwable.msg);

            }


            @Override public void onSuccess(T t) {
                if (onSubscribeSuccess != null) onSubscribeSuccess.onSuccess(t);
            }
        };
    }

    @Override public void onDestroy() {
        mProgressDialogUtils.hideProgress();
        super.onDestroy();
    }


    public interface OnSubscribeSuccess<T> {
        void onSuccess(T t);
    }


}
