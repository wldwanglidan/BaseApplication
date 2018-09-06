package com.example.lily.baseframeapplication.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.app.AppContext;
import com.example.lily.baseframeapplication.app.AppStatusConstant;
import com.example.lily.baseframeapplication.app.AppStatusManager;
import com.example.lily.baseframeapplication.app.Constant;
import com.example.lily.baseframeapplication.dao.CObserver;
import com.example.lily.baseframeapplication.dao.retrofit.ErrorThrowable;
import com.example.lily.baseframeapplication.manager.DataManager;
import com.example.lily.baseframeapplication.manager.UserManager;
import com.example.lily.baseframeapplication.model.GlobalParams;
import com.example.lily.baseframeapplication.utils.ActivityUtils;
import com.example.lily.baseframeapplication.utils.AppUtils;
import com.example.lily.baseframeapplication.utils.ToastUtils;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.tbruyelle.rxpermissions2.RxPermissions;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by lily on 2017/11/15.
 */
/**
 * 注意，我们这里让StartPageActivity继承Activity不要继承AppCompatActivity，因为AppCompatActivity会默认去加载主题，造成卡顿
 */
public class StartPageActivity extends Activity {
    @BindView(R.id.iv_entry) ImageView mIVEntry;



    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        ButterKnife.bind(this);
        //设置StatusBarHeight
        AppContext.STATUS_HEIGHT= AppUtils.getStatusBarHeight(this);
        AppStatusManager.getInstance()
                        .setAppStatus(
                                AppStatusConstant.STATUS_NORMAL);
        //进入应用初始化设置成未登录状态
        detectPermission();
    }

    private void detectPermission() {
        new RxPermissions(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(granted -> {
            if (granted) {
                runOnUiThread(() -> {
                    //初始化应用变量
                    AppContext.initAppParams(StartPageActivity.this);
                    DataManager.getGlobalParameters().subscribe(new CObserver<GlobalParams>() {
                        @Override public void onPrepare() {

                        }

                        @Override
                        public void onError(ErrorThrowable throwable) {
                            ToastUtils.showLongToast(
                                    throwable.getMessage());
                            Log.d("result","StartPageActivity"+throwable
                                    .getMessage());
                            finish();

                        }


                        @Override
                        public void onSuccess(GlobalParams globalParams) {
                            UserManager.getInstance()
                                       .setGlobalParams(
                                               globalParams);
                            startPage();
                        }
                    });

                });
            }
            else {
                // 未获取权限
                finish();
            }
        });
    }

    private void startPage() {
        float nowVersionCode = AppUtils.getAppVersionCode(
                StartPageActivity.this);
        float spVersionCode = RxSharedPreferences.create(
                getDefaultSharedPreferences(this))
                                                 .getFloat(
                                                         Constant.KEY_VERSION_CODE)
                                                 .get();
        if (nowVersionCode > spVersionCode) {
            //spVersionCode默认没有值，首次启动会保存VersionCode
            //首次启动
            ActivityUtils.launchActivity(StartPageActivity.this,
                    WelcomeGuideActivity.class);

        }
        else {
            //非首次启动
            ActivityUtils.launchActivity(StartPageActivity.this,
                    MainActivity.class);

        }
        finish();

    }


    @Override public void onBackPressed() {
        //super.onBackPressed(); 不要调用父类的方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
