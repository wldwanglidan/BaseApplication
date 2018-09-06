package com.example.lily.baseframeapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import butterknife.BindView;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.base.BaseActivity;
import com.example.lily.baseframeapplication.dao.CObserver;
import com.example.lily.baseframeapplication.dao.retrofit.ErrorThrowable;
import com.example.lily.baseframeapplication.manager.UserManager;
import com.example.lily.baseframeapplication.model.GlobalParams;
import com.example.lily.baseframeapplication.model.event.ListenUrl;
import com.example.lily.baseframeapplication.model.event.LoginEvent;
import com.example.lily.baseframeapplication.model.request.BaseHead;
import com.example.lily.baseframeapplication.utils.ToastUtils;
import com.example.lily.baseframeapplication.view.widget.BrowserLayout;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.lily.baseframeapplication.app.Constant.REQUEST_CODE_PAY;

/**
 * Created by lily on 2017/11/13.
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    public @BindView(R.id.bl) BrowserLayout mBrowserLayout;

    private CookieManager cookieManager;
    private String mWebUrl;
    private boolean mDisplayFinancialButton = false;
    private String mRefresh = "refresh";
    private GlobalParams mGlobalParams;

    public static Bundle buildBundle(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        return bundle;
    }

    @Override protected void getBundleExtras(Bundle extras) {
        mWebUrl = extras.getString("url");

    }


    @Override protected int getContentViewLayoutID() {

        return R.layout.activity_web;
    }


    @Override protected View getLoadingTargetView() {
        return null;
    }


    @Override protected void initView(Bundle savedInstanceState) {
        getData();

    }
    private void getData() {
        UserManager.getInstance().getGlobalParams().compose(bindToLifecycle()).
                subscribe(getNotProSubscribe(s -> mGlobalParams = s));
        if (!TextUtils.isEmpty(mWebUrl)) {
            runOnUiThread(() -> mBrowserLayout.loadUrl(mWebUrl));
        }
        else {
            ToastUtils.showLongToast("获取URL地址失败");
        }
        syncCookie(this, mWebUrl);
    }

    private void syncCookie(Context context, String url) {
        try {
            CookieSyncManager.createInstance(context);
            cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(url, "sessionContext=" +
                    new Gson().toJson(new BaseHead()).toString());
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            }
            else {
                CookieManager.getInstance().flush();
            }
            String newCookie = cookieManager.getCookie(url);
            if (newCookie != null) {
                Log.d("result", "newCookie" + newCookie);
            }
        } catch (Exception e) {
            Log.e("result", e.toString());
        }

    }


    @Override protected void initToolbar() {
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("");
            mToolbar.setNavigationOnClickListener(view -> {
                if (mBrowserLayout.getWebView().canGoBack()) {
                    mBrowserLayout.getWebView()
                                  .goBack(); // goBack()表示返回WebView的上一页面
                }
                else {
                    finish();
                }
            });
        }

    }

    //右上角弹出框
    //@Override public boolean onCreateOptionsMenu(Menu menu) {
    //    // Inflate the menu; this adds items to the action bar if it is present.
    //    if (mDisplayFinancialButton) {
    //        getMenuInflater().inflate(R.menu.menu_my_financial_management,
    //                menu);
    //    }
    //    return true;
    //}
    //
    //
    //@Override public boolean onOptionsItemSelected(MenuItem item) {
    //    int id = item.getItemId();
    //    if (id == R.id.action_more) {
    //        showConsumptionPop(ButterKnife.findById(this, R.id.action_more));
    //        return true;
    //    }
    //    return super.onOptionsItemSelected(item);
    //}


    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }


    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListenUrl(ListenUrl listenUrl) {
        Observable.timer(1, TimeUnit.SECONDS)
                  .compose(bindToLifecycle())
                  .subscribe(new CObserver<Long>() {
                      @Override public void onPrepare() {

                      }


                      @Override public void onError(ErrorThrowable throwable) {

                      }


                      @Override public void onSuccess(Long aLong) {
                          runOnUiThread(() -> setTitle(
                                  mBrowserLayout.getWebView().getTitle()));
                      }
                  });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent resultEvent) {
        if (mRefresh.equals("refresh")) {
            runOnUiThread(() -> mBrowserLayout.loadUrl(mWebUrl));
            cookieManager.setCookie(mWebUrl, "sessionContext=" +
                    new Gson().toJson(new BaseHead()).toString());
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            }
            else {
                CookieManager.getInstance().flush();
            }
        }
    }

    public void clearWebHistory() {

        if( mBrowserLayout.getWebView()!=null){
            mBrowserLayout.getWebView().clearHistory();
        }
    }

    public void hideWebBack() {
        mRefresh = "";
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) &&
                mBrowserLayout.getWebView().canGoBack()) {
            mBrowserLayout.getWebView().goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        //if (REQUEST_CODE_PAY == requestCode) {
        //    Base base = (Base) data.getSerializableExtra("rps");
        //    if (base == null) return;
        //    runOnUiThread(() -> mBrowserLayout.getWebView()
        //                                      .loadUrl(
        //                                              "javascript: payFinish( '" +
        //                                                      new Gson().toJson(
        //                                                              base)
        //                                                                .toString() +
        //                                                      "')"));
        //}

    }

    @Override public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }




}
