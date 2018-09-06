package com.example.lily.baseframeapplication.model;

import com.example.lily.baseframeapplication.model.response.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by lily on 2017/11/13.
 */

public class AccessTokenInfo extends BaseResponse implements Serializable {
    @SerializedName("accessToken")
    public String accessToken;

    @SerializedName("expireTime")
    public long expireTime;

    @SerializedName("sessionKey")
    public String sessionKey;

    @SerializedName("sessionSecret")
    public String sessionSecret;

    //第一次获取token的时间
    @SerializedName("getAccessTokenTime")
    public long getAccessTokenTime;


}
