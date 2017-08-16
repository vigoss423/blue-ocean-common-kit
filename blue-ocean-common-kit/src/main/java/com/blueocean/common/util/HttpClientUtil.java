package com.blueocean.common.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blueocean.common.net.http.HttpRequestRetryHandlerImpl;
import com.blueocean.common.net.http.StringResponseHandler;



/**
 * httpClient客户端调用工具类
 * 封装jdk自带的httpclient
 * 封装apache httpclient v4.3.5
 * 
 * @author evelyn
 * 
 */

public final class HttpClientUtil {

	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory
			.getLogger(HttpClientUtil.class);

	/**
	 * 编码
	 */
	private static final String encoding = "utf-8";

	/**
	 * 
	 */
	private static final String USER_AGENT_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";

	/**
	 * 超时时间30秒
	 */
	private static final int TIMEOUT = 120;
	
	
	/**
	 * 连接数50
	 */
	private static final int POOL_SIZE = 50;

	/**
	 * get请求
	 */
	private static final String GET_METHOD = "get";

	/**
	 * post请求
	 */
	private static final String POST_METHOD = "post";

	/**
	 * 重构 原http get调用
	 * 
	 * @param url
	 *            <String> 请求url地址
	 * @param datas
	 *            <String> post数据串
	 * @return 响应报文
	 * @throws
	 */
	public static String retrieve(String url) throws IOException {
		logger.info("url:{}", url);
		URLConnection uc = getHttpConnection(url);
		return readResponse(uc);
	}

	/**
	 * 重构 原http post调用
	 * 
	 * @param url
	 *            <String> 请求url地址
	 * @param datas
	 *            <String> post数据串
	 * @return 响应报文
	 * @throws
	 */
	public static String retrieve(String url, String datas) throws IOException {
		logger.info("url:{}", url);
		URLConnection uc = getHttpConnection(url);
		writeRequestStr(uc, datas);
		return readResponse(uc);

	}

	/**
	 * 读请求响应
	 * 
	 * @param uc
	 *            <URLConnection > 创建的连接
	 * @return 响应的报文串 <String>
	 */
	public static String readResponse(URLConnection uc) {

		if (null == uc) {
			return "";
		}
		String ret="";
		
		try {
			ret=readResponse(uc.getInputStream());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("read IOException", e);
		}
		return ret;
	}
	
	
	/**
	 * 读请求响应
	 * 
	 * @param is
	 *            <InputStream > 输入流
	 * @return 响应的报文串 <String>
	 */
	public static String readResponse(InputStream is) {

		if (null ==is) {
			return "";
		}
		String ret="";
		byte[] byteDatas={};
		try {
			byteDatas = new byte[is.available()];
			is.read(byteDatas);
			ret=new String(byteDatas,encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("readResponse IOException", e);
		}
		
		return ret;

	}

	/**
	 * 写请求报文
	 * 
	 * @param uc
	 *            <URLConnection > 创建的连接
	 * @return data<String> 请求的报文串
	 */
	public static void writeRequestByte(URLConnection uc, String data) {

		BufferedOutputStream out = null;
		try {

			out = new BufferedOutputStream(uc.getOutputStream());

			byte[] postData = {};
			postData = data.getBytes(encoding);

			final int len = 1024; // 1KB
			int dataLen = postData.length;
			int off = 0;
			while (off < dataLen) {
				if (len >= dataLen) {
					out.write(postData, off, dataLen);
				} else {
					out.write(postData, off, len);
				}

				// 刷新缓冲区
				out.flush();

				off += len;

				dataLen -= len;
			}

			// 关闭流

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("write IOException", e);
		} finally {
			// 关闭流
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("close IOException", e);
			}
		}
	}

	/**
	 * 写请求报文
	 * 
	 * @param uc
	 *            <URLConnection > 创建的连接
	 * @return data<String> 请求的报文串
	 */
	public static void writeRequestStr(URLConnection uc, String data) {
		OutputStream out = null;
		try {
			out = uc.getOutputStream();
			out.write(data.getBytes(encoding));
			out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("write IOException", e);
		} finally {
			// 关闭流
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("close IOException", e);
			}
		}
	}

	/**
	 * 获取http协议的连接
	 * 
	 * @param urlStr
	 *            <String> 请求url串
	 * @return URLConnection 返回的HttpsURLConnection
	 */
	public static URLConnection getHttpConnection(String urlStr) {
		if (StringUtils.isEmpty(urlStr))
			return null;
		URL url = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			setURLConnectionProperty(urlConnection);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("getConn MalformedURLException", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("getConn IOException", e);
		}
		return urlConnection;
	}

	/**
	 * 获取https协议的连接
	 * 
	 * @param urlStr
	 *            <String> 请求url串
	 * @return URLConnection 返回的HttpsURLConnection
	 */
	public static URLConnection getHttpsConnection(String urlStr) {
		if (StringUtils.isEmpty(urlStr))
			return null;
		URL url = null;
		SSLContext sslContext = null;
		HttpsURLConnection urlConnection = null;
		try {
			sslContext = SSLContext.getDefault();
			SSLSocketFactory sf = sslContext.getSocketFactory();
			url = new URL(urlStr);
			urlConnection = (HttpsURLConnection) url.openConnection();
			setURLConnectionProperty(urlConnection);
			urlConnection.setSSLSocketFactory(sf);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("getHttpsConnection MalformedURLException", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("getHttpsConnection IOException", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("getHttpsConnection NoSuchAlgorithmException", e);
		}
		return urlConnection;
	}

	/**
	 * 设置URLConnection属性
	 * 
	 * @param urlConnection
	 *            <URLConnection> 连接对象
	 */
	public static void setURLConnectionProperty(URLConnection urlConnection) {
		// 设置连接超时时间
		urlConnection.setConnectTimeout(TIMEOUT * 1000);
		// 读取超时,避免僵死
		urlConnection.setReadTimeout(TIMEOUT * 1000);

		urlConnection.setRequestProperty("Accept-Charset", encoding);

		// User-Agent
		urlConnection.setRequestProperty("User-Agent",
				HttpClientUtil.USER_AGENT_VALUE);

		// 不使用缓存
		urlConnection.setUseCaches(false);

		// 允许输入输出
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
	}

	/**
	 * http client调用 get请求
	 * 
	 * @param urlStr
	 *            <String> 请求url串
	 * @return 响应的报文串
	 */
	public static String doHttpGet(String urlStr) {
		URLConnection conn = getHttpConnection(urlStr);
		try {
			((HttpURLConnection) conn).setRequestMethod(GET_METHOD);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("doHttpGet ProtocolException", e);
		}
		return readResponse(conn);

	}

	/**
	 * https client调用 get请求
	 * 
	 * @param urlStr
	 *            <String> 请求url串
	 * @return 响应的报文串
	 */
	public static String doHttpsGet(String urlStr) {
		URLConnection conn = getHttpsConnection(urlStr);
		try {
			((HttpsURLConnection) conn).setRequestMethod(GET_METHOD);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("doHttpsGet ProtocolException", e);
		}
		return readResponse(conn);

	}

	/**
	 * http client调用 post请求
	 * 
	 * @param urlStr
	 *            <String> 请求url串
	 * @param data
	 *            <String> post的数据串
	 * @return 响应的报文串
	 */
	public static String doHttpPost(String urlStr, String data) {
		URLConnection conn = getHttpConnection(urlStr);
		try {
			((HttpURLConnection) conn).setRequestMethod(POST_METHOD);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("doHttpPost ProtocolException", e);
		}

		writeRequestStr(conn, data);
		return readResponse(conn);

	}

	/**
	 * https client调用 post请求
	 * 
	 * @param urlStr
	 *            <String> 请求url串
	 * @param data
	 *            <String> post的数据串
	 * @return 响应的报文串
	 */
	public static String doHttpsPost(String urlStr, String data) {
		URLConnection conn = getHttpsConnection(urlStr);
		try {
			((HttpURLConnection) conn).setRequestMethod(POST_METHOD);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("doHttpsPost ProtocolException", e);
		}
		writeRequestStr(conn, data);
		return readResponse(conn);

	}

	/**
	 * 重构 原调rest接口 get请求
	 * 
	 * @param urlStr
	 * @return
	 */
	public static String doRestUrl(String urlStr) {
		if (StringUtils.isEmpty(urlStr))
			return "";
		// 替换null为""
		urlStr = urlStr.replaceAll("\"\"null\"\"|\"null\"|null", "\"\"");
		URLConnection uc = getHttpConnection(urlStr);
		String result = readResponse(uc);
		return result;
	}

	/**
	 * apache http client with responseHandler get方式
	 * 
	 * @param url
	 *            <String> get请求串
	 * @return 响应报文串
	 */
	public static String GetWithResponseHandler(String url) {
		HttpClient httpclient = getHttpClient();  
		String responseBody = "";
		try {
			HttpGet httpget = new HttpGet(url);

			logger.info("executing request " + httpget.getURI());

			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			responseBody = httpclient.execute(httpget, responseHandler);
			logger.info("responseBody={}", responseBody);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("httpclient ClientProtocolException", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("httpclient IOException", e);

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		logger.info("responseBody={}", responseBody);
		return responseBody;
	}
	
	/**
	 * apache http client with responseHandler get方式
	 * 
	 * @param url
	 *            <String> get请求串
	 * @return 响应报文串
	 */
	public static String GetWithHttpEntity(String url) {
		 HttpClient httpclient = getHttpClient();  
		 InputStream instream =null;
	        try {
	            HttpGet httpget = new HttpGet(url);
	            // Execute HTTP request
	            logger.info("executing request " + httpget.getURI());
	            HttpResponse response=null;
				try {
					response = httpclient.execute(httpget);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					logger.error("httpclient ClientProtocolException", e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("httpclient IOException", e);
				}

	            logger.info("statusline:{}",response.getStatusLine());

	            // Get hold of the response entity
	            HttpEntity entity = response.getEntity();

	            // If the response does not enclose an entity, there is no need
	            // to bother about connection release
	            if (entity != null) {
	            	
	               try {
	            	   instream= entity.getContent();
	               
	                    instream.read();
	                    // do something useful with the response
	                } catch (IOException ex) {
	                    // In case of an IOException the connection will be released
	                    // back to the connection manager automatically
	                	logger.error("httpclient IOException", ex);
	                } catch (RuntimeException ex) {
	                    // In case of an unexpected exception you may want to abort
	                    // the HTTP request in order to shut down the underlying
	                    // connection immediately.
	                    httpget.abort();
	                    logger.error("httpclient RuntimeException", ex);
	                } finally {
	                    // Closing the input stream will trigger connection release
	                    try { instream.close(); } catch (Exception ignore) {}
	                }
	            }

	        } finally {
	            // When HttpClient instance is no longer needed,
	            // shut down the connection manager to ensure
	            // immediate deallocation of all system resources
	            httpclient.getConnectionManager().shutdown();
	        }
	        return readResponse(instream);
	}
	
	
    /**
     * httpclient  simple get方法
     * @param  url<String> 请求地址
     * @return <String> 返回的响应报文
     * @throws
    */
    public static String httpGet(String url) {  
        CloseableHttpClient httpclient =getHttpClient();   
        return doGet(httpclient,url);
    }  
     
    /**
     *	httpclient  simple post方法
     * @param  url<String> 请求地址
     * @param  paramMap<Map<String,String>> post数据集合
     * @return <String> 返回的响应报文
     * @throws
    */
    public static String httpPost(String url,Map<String,String> paramMap) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient =getHttpClient();  
        return doPost(httpclient,url,paramMap);
    } 
    
    
    
    /**
     * httpsclient  simple get方法
     * @param  url<String> 请求地址
     * @return <String> 返回的响应报文
     * @throws
    */
    public static String httpsGet(String url) {  
        CloseableHttpClient httpclient = getHttpsClient();
        return doGet(httpclient,url);
    }  
     
    /**
     *	httpsclient  simple post方法
     * @param  url<String> 请求地址
     * @param  paramMap<Map<String,String>> post数据集合
     * @return <String> 返回的响应报文
     * @throws
    */
    public static String httpsPost(String url,Map<String,String> paramMap) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = getHttpsClient(); 
        return doPost(httpclient,url,paramMap);
    }
    
    
    /**
     *	httpsclient  post方法
     * @param  url<String> 请求地址
     * @param  data<String>> post数据串
     * @param  contentType<String>> 请求数据内容类型
     * @param  charset<String>> 编码类型
     * @return <String> 返回的响应报文
     * @throws
    */
    public static String httpPost(String url,String data,final String contentType, final String charset) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = getHttpClient();  
        return doPost(httpclient,url,data,contentType,charset);
    } 
    
    
    
    /**
     *	httpclient  post方法
     * @param  url<String> 请求地址
     * @param  data<String>> post数据串
     * @return <String> 返回的响应报文
     * @throws
    */
    public static String httpPost(String url,String data) {  
        return httpPost(url,data,null,null);
    } 
    
    
    /**
     *	httpclient   post模拟提交form
     * @param  httpclient<CloseableHttpClient>  创建连接的类型
     * @param  url<String> 请求地址
     * @param  paramMap<Map<String,String>> post数据集合
     * @return par<String> 返回的响应报文
     * @throws
    */
    public static String doPost(CloseableHttpClient httpclient,String url,Map<String,String> paramMap) {  
        return doPost(httpclient,url,paramMap,null,null);
    } 
    
    /**
     *	httpclient   post模拟提交form
     * @param  httpclient<CloseableHttpClient>  创建连接的类型
     * @param  url<String> 请求地址
     * @param  paramMap<Map<String,String>> post数据集合
     * @param  contentType<String>> 请求数据内容类型
     * @param  charset<String>> 编码类型
     * @return par<String> 返回的响应报文
     * @throws
    */
    public static String doPost(CloseableHttpClient httpclient,String url,Map<String,String> paramMap,final String contentType, final String charset) {  
        // 响应的报文串    
        String response="";
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);
        logger.info("executing request " + httppost.getURI());  
         
        // 创建参数队列    
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
        for(Iterator<String> it=paramMap.keySet().iterator();it.hasNext();){
        	String key=it.next();
        	String value=String.valueOf(paramMap.get(key));
        	formParams.add(new BasicNameValuePair(key, value));
        }
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = StringUtils.isEmpty(charset)?new UrlEncodedFormEntity(formParams, encoding):new UrlEncodedFormEntity(formParams, charset);  
            if(!StringUtils.isEmpty(contentType)){
            	uefEntity.setContentType(contentType);
            }
            httppost.setEntity(uefEntity);  
            logger.info("executing request " + httppost.getURI());  
            response = httpclient.execute(httppost,new StringResponseHandler());
            
        } catch (ClientProtocolException e) {  
        	logger.error("httpPost ClientProtocolException:",e);
        } catch (UnsupportedEncodingException e1) {  
        	logger.error("httpPost UnsupportedEncodingException:",e1);
        } catch (IOException e) {  
        	logger.error("httpPost IOException:",e);
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
               // httppost.releaseConnection();
            } catch (IOException e) {
            	logger.error("httpPost IOException:",e);
            }  
        }  
        return response;
    } 
    
    
    /**
     *	httpclient   post请求发送数据
     * @param  httpclient<CloseableHttpClient>  创建连接的类型
     * @param  url<String> 请求地址
     * @param  data<String>> post数据串
     * @param  contentType<String>> 请求数据内容类型
     * @param  charset<String>> 编码类型
     * @return <String> 返回的响应报文
     * @throws
    */
    public static String doPost(CloseableHttpClient httpclient,String url,String data,final String contentType, final String charset) {  
        // 响应的报文串    
        String response="";
        // 创建http post    
        HttpPost httppost = new HttpPost(url);
        logger.info("executing request " + httppost.getURI());  
        
        try {  
        // 创建参数    
        StringEntity stringEntity=new StringEntity(data);
        if(StringUtils.isEmpty(charset)){
        	stringEntity.setContentEncoding(encoding);
        }else{
        	stringEntity.setContentEncoding(charset);
        }
        
        if(!StringUtils.isEmpty(contentType)){
        	stringEntity.setContentType(contentType);
        }
            httppost.setEntity(stringEntity);
            logger.info("executing request " + httppost.getURI());
            response = httpclient.execute(httppost,new StringResponseHandler());
          
        } catch (ClientProtocolException e) {  
        	logger.error("httpPost ClientProtocolException:",e);
        } catch (UnsupportedEncodingException e1) {  
        	logger.error("httpPost UnsupportedEncodingException:",e1);
        } catch (IOException e) {  
        	logger.error("httpPost IOException:",e);
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {
            	logger.error("httpPost IOException:",e);
            }  
        }  
        return response;
    } 
    
    
    
    /**
     *	httpclient   post请求发送数据
     * @param  httpclient<CloseableHttpClient>  创建连接的类型
     * @param  url<String> 请求地址
     * @param  data<String>> post数据串
     * @throws
    */
    public static String doPost(CloseableHttpClient httpclient,String url,String data) {  
    	return doPost(httpclient,url,data,null,null);
    } 
    
    /**
     *	httpclient   get
     * @param  httpclient<CloseableHttpClient>  创建连接的类型
     * @param  url<String> 请求地址
     * @return par<String> 返回的响应报文
     * @throws
    */
    public static String doGet(CloseableHttpClient httpclient,String url) {  
    	// 响应的报文串   
    	 String response="";
         try {  
             // 创建httpget.    
             HttpGet httpget = new HttpGet(url);
             logger.info("executing request " + httpget.getURI());  
             // 执行get请求.    
             response = httpclient.execute(httpget,new StringResponseHandler());
         }catch (ClientProtocolException e) {  
             logger.error("httpGet ClientProtocolException:",e);
         } catch (ParseException e) {  
             logger.error("httpGet ParseException:",e);
         } catch (IOException e) {  
             logger.error("httpGet IOException:",e);
         } finally {  
             // 关闭连接,释放资源    
             try {  
                 httpclient.close();  
             } catch (IOException e) {  
                 logger.error("httpGet IOException:",e); 
             }  
         }  
         return response;
    } 
    
    
    /**
     * 获得默认配置
     * @return <RequestConfig> 请求参数配置
    */
    public static RequestConfig getRequestConfig(){
    	return getRequestConfig(0);
    }
    
    
    /**
     *	获得指定配置
     * @param  timeout<int> 超时时间
     * @return RequestConfig 请求参数配置
    */
    public static RequestConfig getRequestConfig(int timeout){
    	return getRequestConfig(timeout,null);
    }
    
    /**
     *	获得指定配置
     * @param  timeout<int> 超时时间
     * @param  proxy<HttpHost> 代理服务器
     * @return RequestConfig 请求参数配置
    */
    public static RequestConfig getRequestConfig(int timeout,HttpHost proxy){
    	int time_out=timeout==0?TIMEOUT:timeout;
    	return RequestConfig.custom().setSocketTimeout(time_out * 1000)
				.setConnectTimeout(time_out * 1000).setConnectionRequestTimeout(time_out * 1000).setProxy(proxy).build();
    }
    
  
    /**
     * 获得http client连接
     * @param  poolSize<int>  连接池个数
     * @param  timeout<int>  超时时间
     * @param  proxy<HttpHost>  代理服务器
     * @param  ssl<boolean>  true:https认证   false:http非认证
     * @param  retryHandler<HttpRequestRetryHandler>  重试处理类
     * @return CloseableHttpClient  httpclient连接
    */
    public static CloseableHttpClient getHttpClient(int poolSize,int timeout,HttpHost proxy,boolean ssl,HttpRequestRetryHandler retryHandler){
    	int pool_size=poolSize==0?POOL_SIZE:poolSize;
    	int time_out=timeout==0?TIMEOUT:timeout;
    	if(retryHandler==null)retryHandler=new HttpRequestRetryHandlerImpl();
    	// https认证
    	if(ssl){
    		try {
       		 
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    //信任所有
                    public boolean isTrusted(X509Certificate[] chain,
     
                            String authType) throws CertificateException {
     
                        return true;
     
                    }
     
                }).build();
     
                SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslContext);
     
                return HttpClientBuilder.create().setRetryHandler(retryHandler).setMaxConnTotal(pool_size).setMaxConnPerRoute(pool_size)
    			.setDefaultRequestConfig(getRequestConfig(time_out,proxy)).setSSLSocketFactory(sslSf).build();
     
            } catch (KeyManagementException e) {
     
                logger.error("createSSLClientDefault KeyManagementException", e);
     
            } catch (NoSuchAlgorithmException e) {
     
                logger.error("createSSLClientDefault NoSuchAlgorithmException", e);
     
            } catch (KeyStoreException e) {
     
                logger.error("createSSLClientDefault KeyStoreException", e);
     
            }
    		
    	}
    	// http非认证
    	return HttpClientBuilder.create().setRetryHandler(retryHandler).setMaxConnTotal(pool_size).setMaxConnPerRoute(pool_size)
    			.setDefaultRequestConfig(getRequestConfig(time_out,proxy)).build();
    }
    
    /**
     * 获得http client连接
     * @param  poolSize<int>  连接池个数
     * @param  timeout<int>  超时时间
     * @param  proxy<HttpHost>  代理服务器
     * @param  ssl<boolean>  true:https认证   false:http非认证
     * @return CloseableHttpClient  httpclient连接
    */
    public static CloseableHttpClient getHttpClient(int poolSize,int timeout,HttpHost proxy,boolean ssl){
    	
    	return getHttpClient(poolSize,timeout,proxy,ssl,null);
    }
    
    /**
     *	获得http client连接
     * @return CloseableHttpClient  httpclient连接
    */
    public static CloseableHttpClient getHttpClient(){
    	return getHttpClient(0,0,null,false);
    }
    
    /**
     * 获得http client连接
     * @param  poolSize<int>  连接池个数
     * @param  timeout<int>  超时时间
     * @return CloseableHttpClient  httpclient连接
    */
    public static CloseableHttpClient getHttpClient(int poolSize,int timeout){
    	return getHttpClient(poolSize,timeout,null,false);
    }
   
    /**
     * 获得http client连接
     * @param  poolSize<int>  连接池个数
     * @param  timeout<int>  超时时间
     * @return CloseableHttpClient  httpclient连接
    */
    public static CloseableHttpClient getHttpClient(int poolSize,int timeout,HttpHost proxy){
    	return getHttpClient(poolSize,timeout,proxy,false);
    }
    
    /**
     *	获得https client连接
     * @return CloseableHttpClient  httpclient连接
    */
    public static CloseableHttpClient getHttpsClient(){
    	return getHttpClient(0,0,null,true);
    }
    
    /**
     *	获得https client连接
     * @param  poolSize<int>  连接池数量
     * @param  timeout<int>  超时时间
     * @return CloseableHttpClient httpclient连接
    */
    public static CloseableHttpClient getHttpsClient(int poolSize,int timeout){
    	 return getHttpClient(poolSize,timeout,null,true);
    }
    
    /**
     *	获得https client连接
     * @param  poolSize<int>  连接池数量
     * @param  timeout<int>  超时时间
     * @param  proxy<HttpHost>  代理服务器
     * @return CloseableHttpClient httpclient连接
    */
    public static CloseableHttpClient getHttpsClient(int poolSize,int timeout,HttpHost proxy){
    	 return getHttpClient(poolSize,timeout,proxy,true);
    }
    
   
    	


	public static void main(String[] args) {
		logger.info("testinfo");
		logger.debug("testdebug");
		logger.warn("testwarn");
		logger.error("testerror");
	}
}
