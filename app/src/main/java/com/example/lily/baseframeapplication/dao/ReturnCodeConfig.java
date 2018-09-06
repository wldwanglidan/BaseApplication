package com.example.lily.baseframeapplication.dao;

/**
 * Created by lily on 2017/11/8.
 *  desc  : 返回码配置
 */
public class ReturnCodeConfig {

	/** 空数据返回码 */
	public static int CODE_EMPTY = -0x1000;
	public static int CODE_SUCCESS = -0x1200;
	public static int CODE_LOCAL_EMPTY = -0x1000;
	/** 无网络返回码 */
	public static final int CODE_NO_NETWORK = -0x1001;
	public static final int GESTURE_CANCEL = 0x0001;
	
	private static ReturnCodeConfig instance;
	
	public int successCode;
	
	private ReturnCodeConfig() {
		successCode =0;
	}
	
	public static ReturnCodeConfig getInstance() {
		if(instance == null) {
			instance = new ReturnCodeConfig();
		}
		return instance;
	}
	
	/**
	 * 设置成功的返回码, 用来判断
	 * @param successCode
	 */
	public void initReturnCode(int successCode, int emptyCode) {
		this.successCode = successCode;
		ReturnCodeConfig.CODE_SUCCESS = successCode;
		ReturnCodeConfig.CODE_EMPTY = emptyCode;
	}
	
	public int getEmptyCode() {
		return CODE_LOCAL_EMPTY;
	}
	
	/**
	 * 是否数据位空
	 * @param code
	 * @return
	 */
	public boolean isEmptyCode(int code) {
		return code == CODE_EMPTY || code == CODE_LOCAL_EMPTY;
	}
	
	/**
	 * 判断网络是否成功
	 * @param code
	 * @return
	 */
	public boolean isSuccess(int code) {
		return code == successCode;
	}
	
	/**
	 * 判断是否无网络
	 * @param code
	 * @return
	 */
	public boolean isNoNetwork(int code) {
		return code == CODE_NO_NETWORK;
	}
	
}
