package com.example.lily.baseframeapplication.view.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.base.BaseActivity;
import com.example.lily.baseframeapplication.base.js.InjectedChromeClient;
import com.example.lily.baseframeapplication.model.event.ListenUrl;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by lily on 2017/11/13.
 */

public class BrowserLayout extends LinearLayout {
    private Context mContext = null;
    private WebView mWebView = null;
    private int mBarHeight = 5;
    private ProgressBar mProgressBar = null;

    private String mLoadUrl;

    private boolean loadError;


    public BrowserLayout(Context context) {
        super(context);
        init(context);
    }


    public BrowserLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);
        // setBackgroundColor(R.color.colorAccent);
        mProgressBar = (ProgressBar) LayoutInflater.from(context)
                                                   .inflate(
                                                           R.layout.progress_horizontal,
                                                           null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                        mBarHeight, getResources().getDisplayMetrics()));

        mWebView = new WebView(context);
        mWebView.getSettings().setJavaScriptEnabled(true);//支持js
        //mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        this.mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mWebView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
        }
        mWebView.getSettings().setBlockNetworkImage(false);
        this.mWebView.requestFocusFromTouch();//支持获取手势焦点，输入用户名、密码或其他
        this.mWebView.getSettings().setDomStorageEnabled(true);
        this.mWebView.getSettings()
                     .setJavaScriptCanOpenWindowsAutomatically(
                             true);//支持通过JS打开新窗口
        this.mWebView.getSettings()
                     .setLoadsImagesAutomatically(true);  //支持自动加载图片
        this.mWebView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式

        this.mWebView.getSettings()
                     .setLayoutAlgorithm(
                             WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        this.mWebView.getSettings().supportMultipleWindows();  //多窗口
        this.mWebView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        this.mWebView.getSettings()
                     .setUseWideViewPort(true);//将图片调整到适合webview的大小
        this.mWebView.getSettings()
                     .setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提。
        this.mWebView.getSettings()
                     .setBuiltInZoomControls(
                             true);//设置内置的缩放控件。若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        this.mWebView.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件
        this.mWebView.getSettings().setAllowFileAccess(true);//设置可以访问文件
        //this.mWebView.getSettings().setUserAgentString(App
        //        .getUserAgent());
        this.mWebView.getSettings().setAppCacheEnabled(true);
        this.mWebView.getSettings().setDatabaseEnabled(true);
        this.mWebView.getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        this.mWebView.getSettings()
                     .setRenderPriority(WebSettings.RenderPriority.HIGH);
        LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        addView(mWebView, lps);

        mWebView.setWebViewClient(new WebViewClient(){
            /**
             * 防止加载网页时调起系统浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadUrl=url;
                if (loadError){
                    //当网页加载成功的时候判断是否加载成功
                    //view.loadUrl("file:///android_asset/404.html");
                }
                EventBus.getDefault().post(new ListenUrl(url));
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                loadError=true;
            }
            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                //handler.proceed();//接受信任所有网站的证书
                if (sslError.getPrimaryError() ==
                        android.net.http.SslError.SSL_INVALID) {// 校验过程遇到了bug
                    sslErrorHandler.proceed();
                }
                else {
                    sslErrorHandler.cancel();
                }
            }



        });
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }


    public WebView getWebView() {
        return mWebView != null ? mWebView : null;
    }


    public ValueCallback<Uri> uploadMessage;
    public ValueCallback<Uri[]> uploadMessageAboveL;
    public final static int FILE_CHOOSER_RESULT_CODE = 10000;

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        ((BaseActivity) getContext()).startActivityForResult(
                Intent.createChooser(i, "Image " + "Chooser"),
                FILE_CHOOSER_RESULT_CODE);
    }

    private class ChromeClient extends InjectedChromeClient {
        private Context mContext;


        public ChromeClient(String injectedName, Class injectedCls, Context context) {
            super(injectedName, injectedCls);
            this.mContext = context;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            // to do your work
            // ...
            AlertDialog.Builder b2 = new AlertDialog.Builder(mContext).setTitle(
                    "提示")
                                                                      .setMessage(
                                                                              message)
                                                                      .setPositiveButton(
                                                                              "确定",
                                                                              (dialog, which) -> {
                                                                                  // TODO Auto-generated method stub
                                                                                  result.confirm();
                                                                              });

            b2.setCancelable(false);
            b2.create();
            b2.show();
            return super.onJsAlert(view, url, message, result);
        }

        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // to do your work
            // ...
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            }
            else {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
        }


        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }


        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }


        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            // to do your work
            // ...
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        /**
         * 当WebView加载之后，返回 HTML 页面的标题 Title
         */
        @Override public void onReceivedTitle(WebView view, String title) {
            //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
            if (!TextUtils.isEmpty(title) &&
                    title.toLowerCase().contains("error")) {
                loadError = true;
            }
        }


    }
}
