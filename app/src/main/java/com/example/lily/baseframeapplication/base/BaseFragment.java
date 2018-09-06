package com.example.lily.baseframeapplication.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.lily.baseframeapplication.view.widget.loading.VaryViewHelperController;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;
import javax.annotation.Nonnull;

/**
 * Created by lily on 2017/11/10.
 */

public abstract class BaseFragment extends BaseFuncFragment
        implements LifecycleProvider<FragmentEvent> {

    protected Context mContext = null;
    //Unbinder对象，用来解绑ButterKnife
    private Unbinder mUnbinder;

    private VaryViewHelperController mVaryViewHelperController = null;

    private final BehaviorSubject<FragmentEvent> lifecycleSubject
            = BehaviorSubject.create();


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), null);
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
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(
                    getLoadingTargetView());
        }
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

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * show toast
     */
    protected void showToast(String msg) {
        if (null != msg && !TextUtils.isEmpty(msg)) {
            Snackbar.make(getActivity().getWindow().getDecorView(),
                    msg, Snackbar.LENGTH_SHORT).show();
        }
    }


    /**
     * toggle show loading
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException(
                    "You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        }
        else {
            mVaryViewHelperController.restore();
        }
    }


    /**
     * toggle show empty
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException(
                    "You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        }
        else {
            mVaryViewHelperController.restore();
        }
    }


    /**
     * toggle show error
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException(
                    "You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        }
        else {
            mVaryViewHelperController.restore();
        }
    }


    /**
     * toggle show network error
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException(
                    "You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        }
        else {
            mVaryViewHelperController.restore();
        }
    }


    @Override @NonNull @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Nonnull @Override @CheckResult
    public <T> LifecycleTransformer<T> bindUntilEvent(
            @NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override @NonNull @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }


    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
        mContext = activity;
    }


    @Override public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }


    @Override public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }


    @Override public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }


    @Override public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }


    @Override public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (mUnbinder != null) {
            //取消绑定ButterKnife
            mUnbinder.unbind();
        }

    }
    @Override public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }


    @Override public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }
}
