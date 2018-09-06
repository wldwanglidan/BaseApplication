package com.example.lily.baseframeapplication.dao.retrofit;

/**
 * Created by lily on 2017/11/9.
 */

public class ErrorThrowable extends Throwable{
    public int code;

    public String msg;
    public String errorCode;


    public ErrorThrowable(int code, String errorCode, String errorMsg) {
        this.code = code;
        this.errorCode=errorCode;
        this.msg = errorMsg;
    }

    public ErrorThrowable(int code, String errorMsg) {
        this.code = code;
        this.msg= errorMsg;
    }
    @Override public String toString() {
        return "code:" + code + ", msg:" +msg;
    }


}
