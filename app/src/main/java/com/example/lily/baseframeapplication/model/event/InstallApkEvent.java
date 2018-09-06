package com.example.lily.baseframeapplication.model.event;

import com.example.lily.baseframeapplication.model.BaseInfo;

/**
 * Created by lily on 2017/11/10.
 */

public class InstallApkEvent extends BaseInfo {

    public long apkPackageID;

    public InstallApkEvent(long apkPackageID) {
        this.apkPackageID = apkPackageID;
    }


}
