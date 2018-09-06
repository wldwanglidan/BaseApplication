package com.example.lily.baseframeapplication.manager;

import com.example.lily.baseframeapplication.app.AppContext;
import com.example.lily.baseframeapplication.dao.ResponseHandle;
import com.example.lily.baseframeapplication.dao.RetrofitDao;
import com.example.lily.baseframeapplication.dao.RxSchedulers;
import com.example.lily.baseframeapplication.model.AccessTokenInfo;
import com.example.lily.baseframeapplication.model.AppInfo;
import com.example.lily.baseframeapplication.model.GlobalParams;
import com.example.lily.baseframeapplication.model.User;
import com.example.lily.baseframeapplication.model.request.AppIdApplyRequest;
import com.example.lily.baseframeapplication.model.request.BaseHead;
import com.example.lily.baseframeapplication.model.request.LoginRequest;
import com.example.lily.baseframeapplication.model.request.RefreshLoginRequest;
import com.example.lily.baseframeapplication.model.request.TokenApplyRequest;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by lily on 2017/11/13.
 * 数据管理类
 */

public class DataManager {
    private static DataManager ourInstance;


    // 单例写法---双重检查锁模式
    public static DataManager getInstance() {
        if (ourInstance == null) {
            synchronized (DataManager.class) {
                if (ourInstance == null) ourInstance = new DataManager();
            }
        }
        return ourInstance;
    }

    //2.1 申请appId和appSerect
    public static Observable<AppInfo> getAppInfo() {
        return RetrofitDao.getInstance()
                          .getApiService()
                          .getAppInfo(
                                  new AppIdApplyRequest(AppContext.DEVICE_ID));
    }


    //得到Token
    public static Observable<AccessTokenInfo> getTokenInfo(TokenApplyRequest tokenApplyRequest) {
        return RetrofitDao.getInstance()
                          .getApiService()
                          .getTokenInfo(tokenApplyRequest);
    }

    //2.2 获取访问令牌
    public static Observable<User> refreshLogin(List<String> params) {
        return RetrofitDao.getInstance()
                          .getApiService()
                          .refreshLogin(new RefreshLoginRequest(params.get(0),
                                  params.get(1)))
                          .flatMap(ResponseHandle.newEntityData());
    }

    //获取全局参数
    public static Observable<GlobalParams> getGlobalParameters() {
        return TokenManager.getInstance()
                           .getTokenInfo()
                           .flatMap(accessTokenInfo -> RetrofitDao.getInstance()
                                                                  .getApiService()
                                                                  .getGlobalParameters(
                                                                          new BaseHead()))
                           .flatMap(ResponseHandle.newEntityData())
                           .retry(ResponseHandle.canRetry())
                           .compose(RxSchedulers.io_main());
    }


    //使用手机短信登录
    public static Observable<User> login(String mobile, String smsCode) {
        return TokenManager.getInstance()
                           .getTokenInfo()
                           .flatMap(accessTokenInfo -> RetrofitDao.getInstance()
                                                                  .getApiService()
                                                                  .login(new LoginRequest(
                                                                          mobile,
                                                                          smsCode)))
                           .flatMap(ResponseHandle.newEntityData())
                           .retry(ResponseHandle.canRetry())
                           .compose(RxSchedulers.io_main());
    }



}
