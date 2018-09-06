package com.example.lily.baseframeapplication.app;

import android.content.Context;
import com.example.lily.baseframeapplication.utils.FileUtils;
import java.io.File;

/**
 * Created by lily on 2017/11/8.
 * 应用文件系统上下文， 负责应用中文件目录的初始化等工作.
 */

public class DirContext {
    private static DirContext sInstance = null;

    private String mCacheDir;

    public enum DirEnum {

        ROOT_dir("TempusWallet"), IMAGE("ImgCache"), CACHE("cache"), VOLLEY
                ("volley"), UPLOAD_IMAGE_TEMP("ImgUploadTemp"), DOWNLOAD
                ("download"), CRASH_LOGS("crashLogs"), LUBANCACHE
                ("LubanCache"),IMAGES_FROM_WEBVIEW("ImagesFromWebView");

        private String value;

        DirEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private DirContext() {
        initDirContext();
    }

    public static DirContext getInstance() {
        if (sInstance == null) {
            sInstance = new DirContext();
        }
        return sInstance;
    }

    private void initDirContext() {

    }

    public void initCacheDir(Context context) {
        this.mCacheDir = FileUtils.getDiskCacheDir(context, "").getAbsolutePath();
    }

    public File getRootDir() {
        File file = new File(
                android.os.Environment.getExternalStorageDirectory(),
                DirEnum.ROOT_dir.getValue());

        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    public File getDir(DirEnum dirEnum) {
        File file = new File(getRootDir(), dirEnum.getValue());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

}
