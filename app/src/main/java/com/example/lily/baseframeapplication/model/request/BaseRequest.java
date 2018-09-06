package com.example.lily.baseframeapplication.model.request;

import android.text.TextUtils;
import com.example.lily.baseframeapplication.app.AppContext;
import com.example.lily.baseframeapplication.manager.TokenManager;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 2017/11/13.
 */

public class BaseRequest {
    @SerializedName("channel") public String channel = "P";
    @SerializedName("serviceCode") public String serviceCode;
    @SerializedName("localDateTime") public String localDateTime;
    @SerializedName("userId") public String userId;
    @SerializedName("externalReferenceNo") public String externalReferenceNo;
    @SerializedName("userReferenceNo") public String userReferenceNo;
    @SerializedName("stepCode") public String stepCode = "1";
    @SerializedName("accessSource") public String accessSource = "3";
    @SerializedName("accessSourceType") public String accessSourceType;
    @SerializedName("version") public String version = AppContext.APP_VERSION_NAME;
    @SerializedName("accessToken") public String accessToken
            = (TokenManager.getInstance().getSimpleTokenInfo() != null &&
            !TextUtils.isEmpty(TokenManager.getInstance()
                                           .getSimpleTokenInfo().accessToken))
              ? TokenManager.getInstance().getSimpleTokenInfo().accessToken
              : "";


}
