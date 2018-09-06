package com.example.lily.baseframeapplication.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 2017/11/13.
 */

public class AppIdApplyRequest extends BaseRequest{
    @SerializedName("deviceId") public String deviceId;

    public AppIdApplyRequest(String deviceId) {
        this.deviceId = deviceId;
    }


}
