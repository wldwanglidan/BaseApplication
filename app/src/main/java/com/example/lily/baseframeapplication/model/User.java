package com.example.lily.baseframeapplication.model;

import com.example.lily.baseframeapplication.model.response.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by lily on 2017/11/13.
 * <用户实体类>
 */
public class User extends BaseResponse implements Serializable {
    @SerializedName("headUrl") public String headUrl;
    @SerializedName("mobile") public String mobile;
    @SerializedName("nickname") public String nickname;
    @SerializedName("userId") public String userId;
    @SerializedName("authenTicket") public String authenTicket;


}
