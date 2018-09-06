package com.example.lily.baseframeapplication.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 2017/11/14.
 */

public class RefreshLoginRequest extends BaseRequest{
    @SerializedName("ticket") public String ticket;
    @SerializedName("timestamp") public String timestamp;


    public  RefreshLoginRequest(String  ticket, String timestamp) {
        this.ticket = ticket;
        this.timestamp=timestamp;
    }
}
