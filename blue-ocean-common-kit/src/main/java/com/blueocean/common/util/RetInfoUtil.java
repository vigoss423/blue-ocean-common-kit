package com.blueocean.common.util;

import com.blueocean.common.constant.RetInfoConstant;
import com.blueocean.common.vo.RetInfo;

public class RetInfoUtil {

	public static RetInfo initRetInfo4Err() {
		return initRetInfo(RetInfoConstant.RETCODE_UNKOWN_ERROR, RetInfoConstant.RETMSG_EXCEPTION_MSG);
	}

	public static RetInfo initRetInfo4Succ() {
		return initRetInfo(RetInfoConstant.RETCODE_SUCCESS, RetInfoConstant.RETMSG_SUCCESS_MSG);
	}

	public static RetInfo initRetInfo4NeedLogin() {
		return initRetInfo(RetInfoConstant.RETCODE_NEED_LOGIN, RetInfoConstant.RETMSG_NOT_LOGIN);
	}
	public static RetInfo initRetInfo4ErrTime() {
		return initRetInfo(RetInfoConstant.RETCODE_UNKOWN_ERROR, RetInfoConstant.RETMSG_EXCEPTION_MSG,RetInfoConstant.acceptTime);
	}
	public static RetInfo initRetSessionErrTime() {
		return initRetInfo(RetInfoConstant.RETCODE_SESSION_LOSE_EFFICACY, RetInfoConstant.RETMSG_SESSION_LOSE_EFFICACY,RetInfoConstant.acceptTime);
	}
	public static RetInfo initRetInfo4SuccTime() {
		return initRetInfo(RetInfoConstant.RETCODE_SUCCESS, RetInfoConstant.RETMSG_SUCCESS_MSG,RetInfoConstant.acceptTime);
	}

	public static RetInfo initRetInfo4NeedLoginTime() {
		return initRetInfo(RetInfoConstant.RETCODE_NEED_LOGIN, RetInfoConstant.RETMSG_NOT_LOGIN,RetInfoConstant.acceptTime);
	}
	/**
	 * 
	 * @author wangtlc
	 * @date 2015-6-10 下午8:22:56
	 * 
	 * @param retCode
	 *            返回编码，具体定义请见：RetInfoConstant
	 * @param retMsg
	 *            返回信息，具体定义请见：RetInfoConstant
	 * @param result
	 *            结果数据,比如结果列表等
	 * @return
	 */
	public static RetInfo initRetInfo(String errorCode, String errorMsg, Object result) {
		RetInfo retInfo = new RetInfo();
		retInfo.setErrorCode(errorCode);
		retInfo.setErrorMsg(errorMsg);
		retInfo.setData(result);
		return retInfo;
	}

	public static RetInfo initRetInfo(String errorCode, String errorMsg) {
		RetInfo retInfo = new RetInfo();
		retInfo.setErrorCode(errorCode);
		retInfo.setErrorMsg(errorMsg);
		return retInfo;
	}
	public static RetInfo initRetInfo(String errorCode, String errorMsg,String acceptTime) {
		RetInfo retInfo = new RetInfo();
		retInfo.setErrorCode(errorCode);
		retInfo.setErrorMsg(errorMsg);
		retInfo.setAcceptTime(acceptTime);
		return retInfo;
	}
}
