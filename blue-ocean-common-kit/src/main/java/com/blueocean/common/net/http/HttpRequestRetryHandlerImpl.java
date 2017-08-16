/**
   *
   * @author evelyn 创建 2015年1月14日 
   * @author evelyn 修改 2015年1月14日 
   * @version 1.0.0
   * Copyright(c) 北京思特奇信息技术股份有限公司 电子商务部(miso_ecd)
   */
package com.blueocean.common.net.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求重试
 * 
 * @author evelyn
 *
 */
public class HttpRequestRetryHandlerImpl implements HttpRequestRetryHandler {

	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(HttpRequestRetryHandlerImpl.class);

	public HttpRequestRetryHandlerImpl() {
	}

	public HttpRequestRetryHandlerImpl(String retryCount) {
		this.retryNums = retryCount;
	}

	/**
	 * 重试字数
	 */
	private String retryNums = "0";

	/**
	 * @param retryNums
	 *            the retryNums to set
	 */
	public void setRetryNums(String retryNums) {
		this.retryNums = retryNums;
	}

	/**
	 * @return the retryNums
	 */
	public String getRetryNums() {
		return retryNums;
	}

	public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
		// TODO Auto-generated method stub
		logger.info("retryNums={}", retryNums);
		if (executionCount >= Integer.parseInt(retryNums)) {
			// Do not retry if over max retry count
			return false;
		}
		if (exception instanceof InterruptedIOException) {
			// Timeout
			return false;
		}
		if (exception instanceof UnknownHostException) {
			// Unknown host
			return false;
		}
		if (exception instanceof ConnectTimeoutException) {
			// Connection refused
			return false;
		}
		if (exception instanceof SSLException) {
			// SSL handshake exception
			return false;
		}
		//
		HttpClientContext clientContext = HttpClientContext.adapt(context);
		HttpRequest request = clientContext.getRequest();
		boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
		if (idempotent) {
			// Retry if the request is considered idempotent
			return true;
		}
		return false;
	}

}
