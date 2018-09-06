package com.example.lily.baseframeapplication.dao;

/**
 * <返回码定义>
 *
 * @author jianhao025@gmail.com
 * @data: 2016/07/27 08:59
 * @version: V1.0
 */
public class ReturnCode {
    //这里不是自己定义的，全部按照服务器的来

    /** 未知错误 **/
    public static final int LOCAL_UNKNOWN_ERROR = 0x10100;
    /** 无网络返回码 */
    public static final int LOCAL_NO_NETWORK = 0x10101;
    /** 获取Token失败 */
    public static final int LOCAL_GET_TOKEN_ERROR = 0x10102;
    /** 请求接口失败 */
    public static final int LOCAL_SERVER_ERROR = 0x10103;
    /** 链接网络失败，或者系统异常 */
    public static final int LOCAL_UNKNOWN_HOST_ERROR = 0x10104;
    /** 接口解析数据异常*/
    public static final int LOCAL_GSON_ERROR = 0x10105;
    /** 解密解密失败 */
    public static final int LOCAL_ENCRYPT_ERROR = 0x10106;
    /** 刷新验证票失败 **/
    public static final int LOCAL_REFRESH_LOGIN_ERROR = 0x10107;
    /**  */
    public static final int LOCAL_ERROR_TYPE_ERROR = 0x10108;


    /** 成功 */
    public static final int CODE_SUCCESS = 0;
    /** 失败 */
    public static final int CODE_ERROR = -1;

    /** sign值错误 */
    public static final int CODE_SIGN_ERROR = -101;
    /** time未传 */
    public static final int CODE_TIME_NULL = -102;
    /** time过期 */
    public static final int CODE_TIMEOUT = -103;
    /** sign 为空*/
    public static final int CODE_SIGN_NULL = -104;
    /** 无效的授权,需要重新登录(调用刷新验证票接口) */
    public static final int CODE_INVALIDATED_LOGIN = -105;
    /** token失效 */
    public static final int CODE_INVALIDATED_TOKEN =107;
    /** appId无效 */
    public static final int CODE_INVALIDATED_APPID=109;
    /** 数据为空 */
    public static final int CODE_EMPTY = 1004;

}
