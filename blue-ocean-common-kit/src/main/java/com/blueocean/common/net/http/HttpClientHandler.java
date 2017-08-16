/**
   * @author evelyn 创建 2015年1月22日 
   * @version 1.0.0
   * Copyright(c) 北京思特奇信息技术股份有限公司 电子商务部(miso_ecd)
   */
package com.blueocean.common.net.http;

import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blueocean.common.util.HttpClientUtil;



/**
 * apache http client封装处理类,支持IOC注入
 * @author evelyn
 *
 */
public class HttpClientHandler {
	
	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory
			.getLogger(HttpClientHandler.class);
	
	/**
	 * http失败重试处理器
	 */
	private HttpRequestRetryHandler httpRequestRetryHandler;
	/**
	 * httpclient对象
	 */
	private CloseableHttpClient httpClient;
	/**
	 * 代理
	 */
	private HttpHost proxy;
	
	/**
	 * 是否https认证   true:是   false:否
	 */
	private boolean ssl;
	
	/**
	 * 超时时间  单位:秒
	 */
	private  int timeOut;

	/**
	 * http连接池个数
	 */
	private  int poolSize;
	
	
	/**
	 * 编码格式
	 */
	private String encoding;
	
	
	/**
	 * 请求数据类型
	 */
	private String contentType;

	/**
	 * @param httpRequestRetryHandler the httpRequestRetryHandler to set
	 */
	public void setHttpRequestRetryHandler(
			HttpRequestRetryHandler httpRequestRetryHandler) {
		this.httpRequestRetryHandler = httpRequestRetryHandler;
	}
	
	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(HttpHost proxy) {
		this.proxy = proxy;
	}
	
	/**
	 * @param ssl the ssl to set
	 */
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	
    /**
	 * @param timeOut the timeOut to set
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @param poolSize the poolSize to set
	 */
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	
	

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
     * 初始化时，创建http client 连接 
     */
    public HttpClientHandler() {
    	// 根据指定配置获得相应的http连接
		this.httpClient = HttpClientUtil.getHttpClient(poolSize,timeOut,proxy,ssl,httpRequestRetryHandler);
	}
    
    
    /**
     *	httpclient post请求
     * @param  url<String>   http请求url地址
     * @param  data<String>  post请求的数据串
     * @return 响应的报文串
    */
    public String doHttpPost(String url,String data){
    	logger.debug("url={} data={}",url,data);
    	logger.debug("contentType={} encoding={}",this.contentType,this.encoding);
    	logger.debug("timeOut={} poolSize={}",this.timeOut,this.poolSize);
    	logger.debug("ssl={}",this.ssl);
    	logger.debug("retry nums={}",((HttpRequestRetryHandlerImpl)httpRequestRetryHandler).getRetryNums());
    	return HttpClientUtil.doPost(httpClient, url, data,this.contentType,this.encoding);
    }
    
    
    
    /**
     *	httpclient post请求
     * @param  url<String>   http请求url地址
     * @param  paramMap<Map>  post请求 模拟form表单数据
     * @return 响应的报文串
    */
    public String doHttpPost(String url,Map<String,String> paramMap){
    	return HttpClientUtil.doPost(httpClient, url, paramMap,contentType,encoding);
    }
    
    
    /**
     *	httpclient get请求
     * @param  url<String>   http请求url地址
     * @return 响应的报文串
    */
    public String doHttpGet(String url){
    	return HttpClientUtil.doGet(httpClient, url);
    }
	
	

}
