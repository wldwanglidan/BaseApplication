package com.example.lily.baseframeapplication.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.R2;
import com.example.lily.baseframeapplication.app.App;
import com.example.lily.baseframeapplication.app.AppContext;
import com.example.lily.baseframeapplication.base.BaseActivity;
import com.example.lily.baseframeapplication.base.BaseFragment;
import com.example.lily.baseframeapplication.manager.UserManager;
import com.example.lily.baseframeapplication.model.GlobalParams;
import com.example.lily.baseframeapplication.ui.financial.FinancialFragment;
import com.example.lily.baseframeapplication.ui.home.HomeFragment;
import com.example.lily.baseframeapplication.ui.me.MeFragment;
import com.example.lily.baseframeapplication.utils.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.jude.swipbackhelper.SwipeBackHelper;
import java.io.File;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private static long DOUBLE_CLICK_TIME = 0L;

    @BindView(R2.id.btn_home) Button mBtnHome;
    @BindView(R2.id.btn_financial) Button mBtnFinancial;
    @BindView(R2.id.btn_me) Button mBtnMe;

    private HomeFragment mHomeFragment;
    public FinancialFragment mFinancialFragment;
    private MeFragment mMeFragment;

    private Button[] mTabs;
    private int index;
    private int currentTabIndex;
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    private BaseFragment[] mFragments = new BaseFragment[3];
    public GlobalParams mGlobalParams;


    @Override protected void getBundleExtras(Bundle extras) {

    }


    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override protected View getLoadingTargetView() {
        //这里是什么意思
        return ButterKnife.findById(this, android.R.id.content);
    }


    @Override protected void initView(Bundle savedInstanceState) {

        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this)
                       .setDisallowInterceptTouchEvent(true);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0,
                null);
        UserManager.getInstance().getGlobalParams().compose(bindToLifecycle()).
                subscribe(getNotProSubscribe(s -> mGlobalParams = s));
        mTabs = new Button[3];
        mTabs[0] = mBtnHome;
        mTabs[1] = mBtnFinancial;
        mTabs[2] = mBtnMe;
        // select first tab
        mTabs[0].setSelected(true);
        mHomeFragment = HomeFragment.newInstance();
        mFinancialFragment = FinancialFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
        if (savedInstanceState == null) {
            mFragments[FIRST] = mHomeFragment;
            mFragments[SECOND] = mFinancialFragment;
            mFragments[THIRD] = mMeFragment;
            loadMultipleRootFragment(R.id.fragment_container, FIRST,
                    mFragments[FIRST], mFragments[SECOND], mFragments[THIRD]);
        }
        else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(FinancialFragment.class);
            mFragments[THIRD] = findFragment(MeFragment.class);
        }
        new Handler().postDelayed(
                () -> runOnUiThread(() -> resetFragmentView(mFragments[FIRST])),
                1);


    }

    public void resetFragmentView(BaseFragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View contentView = findViewById(android.R.id.content);
            if (contentView != null) {
                ViewGroup rootView;
                rootView = (ViewGroup) ((ViewGroup) contentView).getChildAt(0);
                if (rootView.getPaddingTop() != 0) {
                    rootView.setPadding(0, 0, 0, 0);
                }
            }
            if (fragment.getView() != null) {
                fragment.getView()
                        .setLayoutParams(new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT));
                fragment.getView()
                        .setPadding(0, AppContext.STATUS_HEIGHT, 0, 0);
            }
        }
    }

    /**
     * on tab clicked
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                index = FIRST;
                break;

            case R.id.btn_financial:
                index = SECOND;
                break;
            case R.id.btn_me:
                index = THIRD;
                break;
        }


        if (currentTabIndex != index) {

            resetFragmentView(mFragments[index]);
            showHideFragment(mFragments[index], mFragments[currentTabIndex]);
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }


    @Override protected void initToolbar() {

    }


    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }


    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override protected void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);

    }

    @Override protected void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override protected void onDestroy() {
        ToastUtils.cancelToast();
        super.onDestroy();
    }


    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

                if (index != 0) {
                    mBtnHome.performClick();
                }
                else {
                    if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) >
                            2000) {
                        showToast(getString(R.string.double_click_exit));
                        DOUBLE_CLICK_TIME = System.currentTimeMillis();
                    }
                    else {
                        ((App) getApplication()).exitApp();
                    }

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}
