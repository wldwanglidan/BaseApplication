package com.example.lily.baseframeapplication.model;

import com.example.lily.baseframeapplication.model.response.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lily on 2017/11/14.
 */

public class ListData<T> extends BaseResponse implements Serializable {
    @SerializedName("list") public List<T> list;

    @SerializedName("hasMore") public String hasMore;


    public boolean isHasMore() {
        return hasMore.equals("1");
    }
}
