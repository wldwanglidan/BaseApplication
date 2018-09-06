package com.example.lily.baseframeapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by lily on 2017/11/8.
 *  desc  : 设备相关工具类
 */
public class DeviceUtils {

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 获取设备AndroidID
     *
     * @param context 上下文
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * 获取设备型号
     * <p>如MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        }
        else {
            model = "";
        }
        return model;
    }
}