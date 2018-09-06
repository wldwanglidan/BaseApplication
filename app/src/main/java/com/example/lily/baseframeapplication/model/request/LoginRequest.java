package com.example.lily.baseframeapplication.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 2017/11/13.
 */

public class LoginRequest extends BaseRequest{
    @SerializedName("mobile") public String mobile;
    @SerializedName("verifyCode") public String verifyCode;

    public LoginRequest(String mobile, String verifyCode) {
        this.mobile = mobile;
        this.verifyCode = verifyCode;
    }

}
