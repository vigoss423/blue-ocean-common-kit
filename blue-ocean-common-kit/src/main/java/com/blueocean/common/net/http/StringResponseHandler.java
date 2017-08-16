package com.blueocean.common.net.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串响应报文处理器
 * 
 * @author evelyn
 *
 */
public class StringResponseHandler implements ResponseHandler<String> {
	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(StringResponseHandler.class);

	/**
	 * 编码
	 */
	private static final String encoding = "utf-8";

	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		final StatusLine statusLine = response.getStatusLine();
		final HttpEntity entity = response.getEntity();
		logger.info("status_code={}", statusLine.getStatusCode());
		logger.info("status_reason={}", statusLine.getReasonPhrase());
		String responseStr = EntityUtils.toString(entity, encoding);
		logger.info("ret_entity={}", responseStr);
		EntityUtils.consume(entity);
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		return entity == null ? null : responseStr;
	}

}
