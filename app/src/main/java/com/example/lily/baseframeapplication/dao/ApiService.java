package com.example.lily.baseframeapplication.dao;

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
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by lily on 2017/11/9.
 */

public interface ApiService {
    @GET Observable<ResponseBody> downLoadImage(@Url String url);

    //申请appId和appSerect  /tpurse/app/appIdApply
    @POST("tpurse/app/appIdApply") Observable<AppInfo> getAppInfo(
            @Body AppIdApplyRequest appIdApplyRequest);

    //获取访问令牌
    @POST("tpurse/app/tokenApply") Observable<AccessTokenInfo> getTokenInfo(
            @Body TokenApplyRequest tokenApplyRequest);

    //刷新登录验证票
    @POST("tpurse/user/refreshLogin") Observable<User> refreshLogin(
            @Body RefreshLoginRequest refreshLoginRequest);

    ////人脸识别生成签名
    //@POST("getSign") Observable<Sign> getSign(
    //        @Body GetSignRequest getSignRequest);

    //获取全局参数
    @POST("tpurse/app/getGlobalParams")
    Observable<GlobalParams> getGlobalParameters(@Body BaseHead baseHead);

    //使用手机短信登录
    @POST("tpurse/user/login") Observable<User> login(@Body LoginRequest loginRequest);

}
