package com.example.lily.baseframeapplication.dao;

import com.example.lily.baseframeapplication.app.Constant;
import com.example.lily.baseframeapplication.dao.retrofit.ErrorThrowable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by lily on 2017/11/9.
 */

public abstract class CObserver<T> implements Observer<T> {
    @Override public void onComplete() {

    }


    @Override public void onSubscribe(@NonNull Disposable d) {
        onPrepare();
    }


    @Override public void onNext(@NonNull T t) {
        onSuccess(t);

    }


    @Override public void onError(@NonNull Throwable e) {
        if (e == null) {
            onError(new ErrorThrowable(ReturnCode.LOCAL_UNKNOWN_ERROR,
                    Constant.DEBUG ? "throwable is null" : ""));
        }
        else if (e instanceof ErrorThrowable) {
            onError((ErrorThrowable) e);
        }
        else {
            onError(new ErrorThrowable(ReturnCode.LOCAL_ERROR_TYPE_ERROR,
                    e.getMessage()));
        }
    }


    public abstract void onPrepare();

    public abstract void onError(ErrorThrowable throwable);

    public abstract void onSuccess(T t);
}
