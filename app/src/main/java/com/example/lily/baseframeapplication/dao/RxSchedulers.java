package com.example.lily.baseframeapplication.dao;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.lily.baseframeapplication.dao.ResponseHandle.errorResumeFunc;

/**
 * Created by lily on 2017/11/14.
 */

public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> io_main() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                                         .unsubscribeOn(
                                                 Schedulers.computation())
                                         .observeOn(
                                                 AndroidSchedulers.mainThread())
                                         .onErrorResumeNext(errorResumeFunc());
    }

}
