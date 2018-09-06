package com.example.lily.baseframeapplication.app;

/**
 * Created by lily on 2017/11/8.
 * 常量类
 */
public class Constant {
    public static final boolean DEBUG = true;

    public static final String APPLICATION_ID = "com.example.lily.baseframeapplication";

    private static final boolean USE_REAL_SERVER = false;

    private static final String SERVER_REAL
            = "https://tpurse.tempus.cn/tpurse/";//生产地址
    public static final String SERVER_TEST = "http://172.18.126.54:7088/tpurse/";//测试地址
    public static final String HOST_URL = USE_REAL_SERVER
                                          ? SERVER_REAL
                                          : SERVER_TEST;
    /**
     * 接口版本
     */
    public static final String KEY_DES = "wdvbjil/";
    public static final String LOCAL_KEY_DES = ")(*&^%$&";

    //请求网络的缓存文件大小
    public static final int NETWORK_URL_CACHE_SIZE = 1024 * 1024 * 100; //100M
    //图片缓存大小
    public static final int IMAGE_CACHE_SIZE = 1024 * 1024 * 100; //100M
    // 获取微信用户信息
    // RxBusEventName
    public static final String T_APP_PACKAGE_NAME = "com.example.lily.baseframeapplication";//T钱包包名
    public static final int REQUEST_CODE_T_PAY = 0x210;//T钱包支付
    /**
     * 检查更新
     */
    /**
     * 下载apkId
     **/
    public static final String KEY_APK_PACKAGE_ID = "key_apk_package_id";

    //下载状态
    public static final String KEY_DOWNLOAD_STATE = "key_download_state";
    //SPConstant
    /**
     * APP的请求码
     */
    public static final String KEY_APP = "key_app";
    /**
     * APP访问令牌
     */
    public static final String KEY_ACCESS_TOKEN = "key_access_token";

    /**
     * uuid
     */
    public static final String KEY_UUID = "key_uuid";

    //用户信息
    public static final String KEY_USER = "user";
    public static final String KEY_USER_INFO = "user_info";
    public static final String KEY_GESTURE_PASSWORD = "gesture_password";
    public static final String KEY_VERSION_CODE = "version_code";
    public static final String KEY_OPEN_SWEEP_CODE = "open_sweep_code";
    //全局参数
    public static final String KEY_GLOBALPARAMS = "globalParams";
    public static final String KEY_GESTURE_PASSWORD_VERIFY =
            "gesture_password_verify";
    public static final String KEY_WIFI_UPDATE = "key_wifi_update";
    public static final String KEY_MESSAGE = "key_message";

    public static final int REQUEST_SMS_VERIFICATION_LOGIN = 0x14;
    public static final int REQUEST_CODE_REAL_NAME = 0x15;
    public static final int REUEST_CODDE = 0x10;
    public static final int REQUEST_CODE_PAY = 0x10;
    public static final int REQUEST_CODE_LOGIN = 0x11;
    public static final int REQUEST_CODE_IMAGE = 0x10;
    public static final int REQUEST_PAY_WORD_CODE = 1111;
    public static final int REQUEST_GESTURE_CODE = 1112;
    public static final int REQUEST_SET_PAY_WORD = 1113;
    public static final int REQUEST_SELECT_AUTHENTICATION_LOGIN = 0x15;
    public static final int REUEST_FINISH_CODDE = 0x16;
    public static final int REUEST_RECHARGE_CODDE = 0x17;
    public static final int REUEST_SET_PAY_PASSWORD = 0x18;
    public static final int REUEST_SET_GESTURE_PASSWORD = 0x19;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
}
