package com.example.lily.baseframeapplication.dao.cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.example.lily.baseframeapplication.app.DirContext;
import com.example.lily.baseframeapplication.utils.AppUtils;
import com.example.lily.baseframeapplication.utils.Md5;
import java.io.IOException;

/**
 * Created by lily on 2017/11/8.
 *  desc  : APP应用缓存相关配置
 */
public class DataProvider {

    private static final String FILTER_FIELD = "srv";

    private Disker disker;

    private static DataProvider provider;
    private static Context context;

    public static void init(Context context) {
        DataProvider.context = context;
    }

    public synchronized static DataProvider getInstance() {
        if (provider == null) {
            provider = new DataProvider();
        }
        return provider;
    }


    private DataProvider() {
        disker = new Disker(context, DirContext.getInstance().getDir(DirContext.DirEnum.ROOT_dir).getName());
    }

    public void putStringToDisker(String content, String url) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(url)) {
            return;
        }
        int index = url.indexOf(FILTER_FIELD);
        int startIndex = index == -1 ? 0 : index;
        String key = Md5.digest32(url.substring(startIndex));
        Log.i("Data", "put key : " + key + "startIndex : " + startIndex);

        disker.putStringToDisker(content, key);
    }


    public String getCacheFromDisker(String url) {
        try {
            int index = url.indexOf(FILTER_FIELD);
            int startIndex = index == -1 ? 0 : index;
            String key = Md5.digest32(url.substring(startIndex));
            return disker.getStringFromDisk(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearAllCache() {
        disker.deleteCache();
    }

    public String getDiskSize() {
        return AppUtils.bytes2Convert(disker.getSize());
    }
}
