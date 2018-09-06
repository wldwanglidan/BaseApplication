package com.example.lily.baseframeapplication.model;

/**
 * Created by lily on 2017/11/10.
 * 基础类
 */

public abstract class BaseInfo {
    //实体类返回提示语
    private String successHintMsg;

    public void setSussceHintMsg(String msg) {
        this.successHintMsg = msg;
    }

    public String getSuccessHintMsg() {
        return successHintMsg;
    }

}
