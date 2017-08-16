package com.blueocean.common.vo;

import java.io.Serializable;

public final class RetInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String errorMsg;
	
	private String acceptTime;
	
	private String detail_msg;
	
	private Long total;
	
	private Object data;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getDetail_msg() {
		return detail_msg;
	}

	public void setDetail_msg(String detail_msg) {
		this.detail_msg = detail_msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "RetInfo [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", acceptTime=" + acceptTime
				+ ", detail_msg=" + detail_msg + ", total=" + total + ", data=" + data + "]";
	}

	

}
