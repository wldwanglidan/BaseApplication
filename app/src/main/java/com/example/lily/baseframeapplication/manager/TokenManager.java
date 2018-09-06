package com.example.lily.baseframeapplication.manager;

import android.text.TextUtils;
import android.util.Log;
import com.example.lily.baseframeapplication.app.App;
import com.example.lily.baseframeapplication.app.AppContext;
import com.example.lily.baseframeapplication.app.Constant;
import com.example.lily.baseframeapplication.dao.ReturnCodeConfig;
import com.example.lily.baseframeapplication.dao.exception.NoNetworkException;
import com.example.lily.baseframeapplication.dao.retrofit.ErrorThrowable;
import com.example.lily.baseframeapplication.model.AccessTokenInfo;
import com.example.lily.baseframeapplication.model.AppInfo;
import com.example.lily.baseframeapplication.model.request.TokenApplyRequest;
import com.example.lily.baseframeapplication.utils.ACache;
import com.example.lily.baseframeapplication.utils.DES;
import com.example.lily.baseframeapplication.utils.Md5Tool;
import com.example.lily.baseframeapplication.utils.StringUtils;
import com.google.gson.Gson;
import io.reactivex.Observable;

/**
 * Created by lily on 2017/11/13.
 * Token管理类
 */

public class TokenManager {
    private static final String TAG = "TokenManager";
    private static TokenManager mInstance;

    private AppInfo mAppInfo;
    private AccessTokenInfo mAccessTokenInfo;

    private boolean refreshLoginFlag;
    private ACache mACache;


    public static TokenManager getInstance() {
        if (mInstance == null) {
            synchronized (TokenManager.class) {
                if (mInstance == null) {
                    mInstance = new TokenManager();
                }
            }
        }
        return mInstance;
    }


    private TokenManager() {
        mACache = App.getACache();
    }


    //验证设置是否已经获取Token且Token有效
    private boolean isTokenVerification() {
        if (mAccessTokenInfo != null && !isTimeout(mAccessTokenInfo)) {
            initAppInfo();
            return true;
        }
        else {
            String appStr = mACache.getAsString(Constant.KEY_ACCESS_TOKEN);
            if (!TextUtils.isEmpty(appStr)) {
                try {
                    String decryptToken = DES.decryptDES(Constant.LOCAL_KEY_DES,
                            appStr);
                    AccessTokenInfo accessTokenInfo = new Gson().fromJson(
                            decryptToken, AccessTokenInfo.class);
                    if (!isTimeout(accessTokenInfo)) {
                        this.mAccessTokenInfo = accessTokenInfo;
                        return true;
                    }
                    else {
                        mACache.put(Constant.KEY_ACCESS_TOKEN, "");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "initApi(" + e + ")");
                }
            }
        }
        mAccessTokenInfo = null;
        return false;
    }


    //Token是否过期
    private boolean isTimeout(AccessTokenInfo accessTokenInfo) {
        return System.currentTimeMillis() / 1000 >
                accessTokenInfo.getAccessTokenTime;
    }


    //初始化AppInfo
    private void initAppInfo() {
        if (mAppInfo == null) {
            String str = mACache.getAsString(Constant.KEY_APP);
            try {
                str = DES.decryptDES(Constant.LOCAL_KEY_DES, str);
                mAppInfo = new Gson().fromJson(str, AppInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public AccessTokenInfo getSimpleTokenInfo() {
        return mAccessTokenInfo;
    }


    public Observable<AccessTokenInfo> getTokenInfo() {
        if (!isTokenVerification()) {
            if (UserManager.getInstance().isLogin()) setRefreshLogin();
        }
        return Observable.just(UserManager.getInstance().isLogin())
                         .flatMap(o1 -> {
                             if (refreshLoginFlag) {
                                 refreshLoginFlag = false;
                                 return TokenManager.getInstance()
                                                    .getTokenInfoObservable()
                                                    .flatMap(
                                                            tokenInfo -> UserManager
                                                                    .getRefreshLoginParams())
                                                    .flatMap(
                                                            o -> getTokenInfoObservable());
                             }
                             return getTokenInfoObservable();
                         });
    }


    private Observable<AccessTokenInfo> getTokenInfoObservable() {
        if (!AppContext.isNetworkAvailable()) {
            return Observable.error(new NoNetworkException());
        }
        if (isTokenVerification()) {
            return Observable.just(mAccessTokenInfo);
        }
        else {
            String str = mACache.getAsString(Constant.KEY_APP);
            if (TextUtils.isEmpty(str)) {
                mACache.put(Constant.KEY_UUID, AppContext.DEVICE_ID);
                return DataManager.getAppInfo().flatMap(appInfoResponse -> {
                    if (appInfoResponse.isSuccess()) {
                        Log.d("result",
                                "----------appInfoResponse.isSuccess()");
                        mAppInfo = appInfoResponse;
                        try {
                            mACache.put(Constant.KEY_APP,
                                    DES.encryptDES(Constant.LOCAL_KEY_DES,
                                            new Gson().toJson(
                                                    appInfoResponse)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return requestToken(mAppInfo.appId);
                    }
                    else {
                        Log.d("result", "not----------appInfoResponse" +
                                ".isSuccess()" +
                                ReturnCodeConfig.getInstance().successCode);
                        return Observable.error(
                                new ErrorThrowable(appInfoResponse.retStatus,
                                        appInfoResponse.errorMsg));
                    }
                });
            }
            else {
                initAppInfo();
                return requestToken(mAppInfo.appId);
            }
        }
    }


    private Observable<AccessTokenInfo> requestToken(String appId) {
        long timestamp = System.currentTimeMillis() / 1000;
        String uuid = mACache.getAsString(Constant.KEY_UUID);
        if (StringUtils.isEmpty(uuid)) {
            uuid = AppContext.DEVICE_ID;
            mACache.put(Constant.KEY_UUID, uuid);
        }
        AppContext.DEVICE_ID = uuid;
        String signature = Md5Tool.MD5(uuid + mAppInfo.appSecret + timestamp);
        return DataManager.getTokenInfo(
                new TokenApplyRequest(uuid, appId, signature, timestamp + ""))
                          .flatMap(tokenInfoResponse -> {
                              if (tokenInfoResponse.isSuccess()) {
                                  Log.d("result", "tokenInfoResponse is not  " +
                                          "null");
                                  tokenInfoResponse.getAccessTokenTime =
                                          System.currentTimeMillis() / 1000 +
                                                  tokenInfoResponse.expireTime;
                                  mAccessTokenInfo = tokenInfoResponse;
                                  try {
                                      mACache.put(Constant.KEY_ACCESS_TOKEN,
                                              DES.encryptDES(
                                                      Constant.LOCAL_KEY_DES,
                                                      new Gson().toJson(
                                                              tokenInfoResponse)));
                                  } catch (Exception e) {
                                      e.printStackTrace();
                                  }
                                  return Observable.just(mAccessTokenInfo);
                              }
                              else {
                                  return Observable.error(new ErrorThrowable(
                                          tokenInfoResponse.retStatus,
                                          tokenInfoResponse.errorMsg));
                              }
                          });
    }


    public AccessTokenInfo resetToken() {
        String tokenTemp = new Gson().toJson(this.mAccessTokenInfo);
        AccessTokenInfo temp = new Gson().fromJson(tokenTemp,
                AccessTokenInfo.class);
        this.mAccessTokenInfo = null;
        mACache.put(Constant.KEY_ACCESS_TOKEN, "");
        return temp;
    }


    public AppInfo getAppInfo() {
        return mAppInfo;
    }


    public void setRefreshLogin() {
        refreshLoginFlag = true;
    }
}
