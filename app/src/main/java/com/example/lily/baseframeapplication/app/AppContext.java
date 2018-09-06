package com.example.lily.baseframeapplication.app;

import android.content.Context;
import android.text.TextUtils;
import com.example.lily.baseframeapplication.utils.AppUtils;
import com.example.lily.baseframeapplication.utils.DeviceUtils;
import com.example.lily.baseframeapplication.utils.Md5;
import com.example.lily.baseframeapplication.utils.NetworkUtils;

/**
 * Created by lily on 2017/11/8.
 * 应用初始化信息配置，如读取用户保存在SP中的数据，配置应用版本号，版本名等
 */

public class AppContext {
    private static Context context;
    private static volatile AppContext appContext;

    /** 应用版本号 */
    public static int APP_VERSION_CODE;
    /** 应用版本名 */
    public static String APP_VERSION_NAME;
    /** 本地时间 */
    public static String LOCAL_DATE_TIME;
    public static String MODEL;
    public static String OS_VERSION;
    public static String DEVICE_ID;
    //public static GlobalParams sGlobalParams;
    public static int STATUS_HEIGHT;

    private AppContext() {}


    public static Context getContext() {
        return context;
    }

    public synchronized static AppContext getInstance() {
        if (appContext == null) {
            synchronized (AppContext.class) {
                if (appContext == null) {
                    appContext = new AppContext();
                }
            }
        }
        return appContext;
    }

    public synchronized void init(Context context) {
        AppContext.context = context;
        initAppParams(context);
        initUserParams();
        DirContext.getInstance().initCacheDir(context);

    }

    /**
     * 填充用户信息
     */
    public void initUserParams() {

        //UserManager.getInstance().fillUser();
    }


    /**
     * 初始化应用变量
     */
    public static void initAppParams(Context context) {
        APP_VERSION_CODE = AppUtils.getAppVersionCode(context);
        APP_VERSION_NAME = AppUtils.getAppVersionName(context);
        MODEL = android.os.Build.MODEL;
        MODEL = MODEL.replace(" ", "");
        LOCAL_DATE_TIME = String.valueOf(System.currentTimeMillis() / 1000);
        OS_VERSION = android.os.Build.VERSION.RELEASE;
        try {
            String uuid = App.getACache()
                             .getAsString(
                                     com.example.lily.baseframeapplication.app.Constant .KEY_UUID);
            DEVICE_ID = TextUtils.isEmpty(uuid) ? Md5.digest32(
                    DeviceUtils.getAndroidID(context)) : uuid;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getString(int resId) {
        return AppContext.getContext().getString(resId);
    }


    /** 是否有无网络 */
    public static boolean isNetworkAvailable() {
        return NetworkUtils.isConnected(context);    }




}
