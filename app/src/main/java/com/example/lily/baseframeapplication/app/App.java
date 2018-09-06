package com.example.lily.baseframeapplication.app;

import android.support.multidex.MultiDexApplication;
import com.example.lily.baseframeapplication.base.BaseActivityManager;
import com.example.lily.baseframeapplication.dao.NetworkConfig;
import com.example.lily.baseframeapplication.dao.ReturnCode;
import com.example.lily.baseframeapplication.dao.ReturnCodeConfig;
import com.example.lily.baseframeapplication.dao.cache.DataProvider;
import com.example.lily.baseframeapplication.utils.ACache;

/**
 * Created by lily on 2017/11/8.
 */

public class App extends MultiDexApplication {

    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    private ACache mACache;

    public void onCreate() {
        super.onCreate();
        App.mInstance = this;
        mACache = ACache.get(this);
        AppContext.getInstance().init(this);
        DataProvider.init(this);
        NetworkConfig.setBaseUrl(Constant.HOST_URL);
        NetworkConfig.setCacheFile(
                DirContext.getInstance().getDir(DirContext.DirEnum.CACHE),
                Constant.NETWORK_URL_CACHE_SIZE);
        ReturnCodeConfig.getInstance()
                        .initReturnCode(ReturnCode.CODE_SUCCESS,
                                ReturnCode.CODE_EMPTY);
    }


    @Override public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }


    public void exitApp() {
        BaseActivityManager.getInstance().clear();
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static ACache getACache() {
        return getInstance().mACache;
    }






}
