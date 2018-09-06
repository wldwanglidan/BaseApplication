package com.example.lily.baseframeapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.lily.baseframeapplication.dao.CObserver;
import com.example.lily.baseframeapplication.dao.retrofit.ErrorThrowable;
import com.example.lily.baseframeapplication.utils.ProgressDialogUtils;
import com.example.lily.baseframeapplication.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import javax.annotation.Nonnull;

/**
 * Created by lily on 2017/11/10.
 */

public abstract class BaseDialogFragment extends DialogFragment
        implements LifecycleProvider<FragmentEvent> {
    protected Context mContext = null;
    //异步操作对话框
    public ProgressDialogUtils mProgressDialogUtils;

    //Unbinder对象，用来解绑ButterKnife
    private Unbinder mUnbinder;

    private final BehaviorSubject<FragmentEvent> lifecycleSubject
            = BehaviorSubject.create();


    public BaseDialogFragment() {
    }


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
        mProgressDialogUtils = new ProgressDialogUtils(mContext);
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), container, false);
        }
        else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        initView(view);
    }


    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewID();

    /**
     * override this method to do operation in the fragment
     * 设置监听器
     */
    protected abstract void initView(View view);


    @Nonnull @Override public Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }


    @Nonnull @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(
            @Nonnull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject,event);
    }


    @Nonnull @Override public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }


    @Override public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
        mContext = activity;
    }


    @Override public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }


    @Override public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }


    @Override public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }


    @Override public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }


    @Override public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (mUnbinder != null) {
            //取消绑定ButterKnife
            mUnbinder.unbind();
        }
    }


    @Override @CallSuper public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
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


    public <T> CObserver<T> getNotProSubscribe(OnSubscribeSuccess<T> onSubscribeSuccess) {
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
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        mProgressDialogUtils.hideProgress();
        super.onDestroy();
    }


    public interface OnSubscribeSuccess<T> {
        void onSuccess(T t);
    }




}
