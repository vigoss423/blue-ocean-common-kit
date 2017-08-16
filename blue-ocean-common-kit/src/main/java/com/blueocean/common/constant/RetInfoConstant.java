package com.blueocean.common.constant;

public class RetInfoConstant{
	
	public static String acceptTime=String.valueOf(System.currentTimeMillis());
	/**
	 * 处理成功
	 */
	public static final String RETCODE_SUCCESS = "0";
	public static final String RETMSG_SUCCESS_MSG = "操作成功!";
	/**
	 * 通用错误
	 */
	public static final String RETCODE_COMMON_ERROR = "1001";
	public static final String RETMSG_COMMON_ERROR = "通用错误";
	
	/**
	 * 登录失败
	 */
	public static final String RETCODE_LOGIN_FAILED = "1002";
	public static final String RETMSG_LOGIN_FAILED = "登录失败!";
	
	/**
	 *  session失效
	 */
	public static final String RETCODE_SESSION_LOSE_EFFICACY = "1003";
	public static final String RETMSG_SESSION_LOSE_EFFICACY = "session失效!";
	
	/**
	 * 缺少参数Parameter missing
	 */
	public static final String RETCODE_PARAMETER_MISSING = "1004";
	public static final String RETMSG_PARAMETER_MISSING = "缺少参数!";
	
	/**
	 * 缺少药品信息
	 */
	public static final String RETCODE_DRUG_MSG_MISSING = "1005";
	public static final String RETMSG_DRUG_MSG_MISSING = "缺少药品信息!";
	
	/**
	 * 订单号不存在
	 */
	public static final String RETCODE_ORDER_NOT_EXIST = "1006";
	public static final String RETMSG_ORDER_NOT_EXIST = "订单号不存在!";
	
	/**
	 * 订单已关闭
	 */
	public static final String RETCODE_ORDER_CLOSED = "1007";
	public static final String RETMSG_ORDER_CLOSED = "订单已关闭!";
	
	/**
	 * 
	 */
	public static final String RETCODE_NEED_LOGIN = "-100";
	public static final String RETMSG_NEED_LOGIN = "用户需要登录";
	
	/**
	 * 
	 */
	public static final String RETCODE_NOT_LOGIN = "-101";
	public static final String RETMSG_NOT_LOGIN = "用户未登录";

	/**
	 * 未知错误返回
	 */
	public static final String RETCODE_UNKOWN_ERROR = "999999";

	public static final String RETCODE_NO_WWW_PROVISION = "100001";
	public static final String RETMSG_EXCEPTION_MSG = "未知异常";
	public static final String RETMSG_PARAMS_NOVALID = "入参错误";
}
