package com.example.lily.baseframeapplication.model;

import com.example.lily.baseframeapplication.model.response.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by lily on 2017/11/13.
 * AppInfo信息
 */

public class AppInfo extends BaseResponse implements Serializable {
    @SerializedName("appId")
    public String appId;

    @SerializedName("appSecret")
    public String appSecret;

}
