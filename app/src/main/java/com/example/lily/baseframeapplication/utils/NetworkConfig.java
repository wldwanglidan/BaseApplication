package com.example.lily.baseframeapplication.utils;

import java.io.File;
import okhttp3.Cache;

/**
 * <网络模块 配置文件>
 *
 * @author chenml@cncn.com
 * @data: 2016/2/17 11:30
 * @version: V1.0
 */
public class NetworkConfig {

    //默认的缓存文件
    public static final String DEFAULT_CAHCE_FILE = "NetworkCahce";
    //默认缓存目录大小
    public static final int DEFAULT_CAHCE_SIZE = 1024 * 1024 * 100; //100M

    private static String BASE_URL;

    private static Cache CACHE;

    public static void setBaseUrl(String url) {
        NetworkConfig.BASE_URL = url;
    }

    //设置网络请求缓存文件
    public static void setCacheFile(File directory, long maxSize) {
        CACHE = new Cache(directory, maxSize);
    }

    public static Cache getCache() {
        if(CACHE == null) {
            //设置默认缓存文件
            File file = new File(android.os.Environment.getExternalStorageDirectory(), NetworkConfig.DEFAULT_CAHCE_FILE);
            CACHE = new Cache(file, NetworkConfig.DEFAULT_CAHCE_SIZE);
        }
        return CACHE;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

}
