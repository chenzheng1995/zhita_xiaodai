package com.zhita.util;

//package com.sunline.alipay.service.socket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sunline.ark.support.exception.ProcessException;
//import com.sunline.nfs.ecm.util.DataLogicStatus;
//import com.sunline.ppy.dictionary.enums.MsRespCode;
//import com.sunline.ppy.dictionary.enums.PayfrontError;
//import com.sunline.ppy.dictionary.logregenums.LogRegexTools;
//import com.sunline.ppy.service.LogTools;

public class HttpClient {

	

	@Autowired
	// private KeyService keyService;
//	@Value("#{env['connectionTimeout']?:100000}")
	private int connectionTimeout=100000;

//	@Value("#{env['waitTimeout']?:100000}")
	private int waitTimeout=100000;

//	@Value("#{env['maxConnectionsPerHost']?:1000}")
	private int maxConnectionsPerHost=1000;

//	@Value("#{env['maxTotalConnections']?:20000}")
	private int maxTotalConnections=20000;

	private String reqXml;

	private String msgType;

	// @Value("#{env.ALIPAY_URL}")
	// private String ALIPAY_URL;

	private static final String charSet = "UTF-8";

	private HttpConnectionManager connectionManager;

	public void getConnection() {

		if (connectionManager == null) {
			// 创建一个线程安全的HTTP连接池
			connectionManager = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams params = new HttpConnectionManagerParams();
			// 连接建立超时
			params.setConnectionTimeout(connectionTimeout);
			// 数据等待超时
			params.setSoTimeout(waitTimeout);
			// 默认每个Host最多10个连接S
			params.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
			// 最大连接数（所有Host加起来）
			params.setMaxTotalConnections(maxTotalConnections);
			connectionManager.setParams(params);
		}

	}

	/**
	 * 调用支付前置
	 * 
	 * @param json
	 * @param uri
	 * @return
	 * @throws Exception 
	 */
	public String sendMsHttpPost(String json, String uri, String defCharset) throws Exception {
		getConnection();
		// 发送报文
		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
				connectionManager);
		PostMethod postMethod = new PostMethod(uri);
		String resJson = "";
		// postMethod.addRequestHeader("Content-Type", "application/json");
		postMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=" + defCharset);
		try {
			Map<String, Object> paramMap = this.json2Map(json);

			if (null != paramMap) {
				Set<String> keys = paramMap.keySet();
				for (String key : keys) {
					if (StringUtils.isNotBlank(key)) {
						Object value = paramMap.get(key);
						postMethod.setParameter(key,
								null == value ? "" : value.toString());
					}
				}
			}

			httpClient.executeMethod(postMethod);
			resJson = postMethod.getResponseBodyAsString();
//			logger.debug("响应报文:[{}]", resJson);
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		} catch (HttpException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			postMethod.releaseConnection();
		}

		return resJson;
	}
	/**
	 * 调用支付前置
	 * @param reqJson
	 * @param uri
	 * @param defCharset
	 * @param isCovert   是否转化
	 * @return
	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public String sendHttpPost(String reqJson, String uri, String defCharset, boolean isCovert) {
//		if (logger.isDebugEnabled())
//			logger.debug("发送地址:[{}]", uri);
//		LogTools.printLoggerByPatterns(logger, "发送报文", reqJson, LogRegexTools.getLogRegexes());
//		String respContent = "";
//		try {
//			HttpPost httpPost = new HttpPost(uri);
//			CloseableHttpClient client = HttpClients.createDefault();
//
//			// json方式
//			JSONObject json = JSON.parseObject(reqJson);
//			StringEntity entity = new StringEntity(json.toJSONString(), defCharset);
//			entity.setContentEncoding(defCharset);
//			entity.setContentType("application/json");
//			LogTools.printLoggerByPatterns(logger, "发送转换后的报文", entity.toString(), LogRegexTools.getLogRegexes());
////			if (logger.isDebugEnabled())
////				logger.debug("发送转换后的报文[{}]", entity.toString());
//			httpPost.setEntity(entity);
//
//			HttpResponse resp = client.execute(httpPost);
//			if (resp.getStatusLine().getStatusCode() == 200) {
//				HttpEntity he = resp.getEntity();
//				respContent = EntityUtils.toString(he, defCharset);
//			}
//			// 徽商银行授信脑残返回，{"@type":"java.util.HashMap","ret_code":"0","credit_limit":0.0D,"status":true,"score":0.0D,"ret_name":"\u653E\u884C\u7B56\u7565","credit_date":new Date(1507789731326)}
//			// 此处先做转化再返回
//			if (StringUtils.isNotBlank(respContent) && isCovert) {
//				Map map = JSON.parseObject(respContent);
//				JSONObject mapJson = new JSONObject(map);
//				respContent = mapJson.toJSONString();
//			}
//			LogTools.printLoggerByPatterns(logger, "响应报文", respContent, LogRegexTools.getLogRegexes());
////			logger.debug("响应报文[{}]", respContent);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90002.getCode(),
//					PayfrontError.E_90002.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90003.getCode(),
//					PayfrontError.E_90003.getDesc());
//		} catch (Exception e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} 
//
//		return respContent;
//	}
	/**
	 * json转Map
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> json2Map(String jsonStr) throws Exception {
//		if (logger.isDebugEnabled())
//			logger.debug("json转Map:[{}]", jsonStr);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = mapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return map;
	}
//
//	/**
//	 * 调用支付前置
//	 * 
//	 * @param json
//	 * @param uri
//	 * @return
//	 */
//	public String sendMsHttpGet(String json, String uri, String defCharset) {
//		LogTools.printLoggerByPatterns(logger, "发送报文", json, LogRegexTools.getLogRegexes());
////		if (logger.isDebugEnabled())
////			logger.debug("发送报文[{}]", json);
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		GetMethod getMethod = new GetMethod(uri);
//		String resJson = "";
//		// postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+defCharset);
//		org.apache.commons.httpclient.params.HttpMethodParams httpMethodParams = new org.apache.commons.httpclient.params.HttpMethodParams();
//		httpMethodParams.setContentCharset(defCharset);
//		getMethod.setParams(httpMethodParams);
//		try {
//			Map<String, Object> paramMap = this.json2Map(json);
//
//			if (null != paramMap) {
//				Set<String> keys = paramMap.keySet();
//				NameValuePair nameValuePair[] = new NameValuePair[paramMap
//						.entrySet().size()];
//				int i = 0;
//				for (String key : keys) {
//					if (StringUtils.isNotBlank(key)) {
//						Object value = paramMap.get(key);
//						nameValuePair[i] = new NameValuePair(key,
//								null == value ? "" : value.toString());
//						i++;
//					}
//				}
//				String queryString = EncodingUtil.formUrlEncode(nameValuePair,
//						defCharset);
//				getMethod.setQueryString(queryString);
//				LogTools.printLoggerByPatterns(logger, "get请求Parm", queryString, LogRegexTools.getLogRegexes());
////				if (logger.isDebugEnabled())
////					logger.debug("get请求Parm:[{}]", queryString);
//			}
//
//			httpClient.executeMethod(getMethod);
//			resJson = getMethod.getResponseBodyAsString();
//			LogTools.printLoggerByPatterns(logger, "响应报文", resJson, LogRegexTools.getLogRegexes());
////			if (logger.isDebugEnabled())
////				logger.debug("响应报文[{}]", resJson);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90002.getCode(),
//					PayfrontError.E_90002.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90003.getCode(),
//					PayfrontError.E_90003.getDesc());
//		} catch (Exception e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} finally {
//			getMethod.releaseConnection();
//		}
//
//		return resJson;
//	}
//
//	/**
//	 * HTTP发送请求方法，调用核心系统用的
//	 * 
//	 * @param uri
//	 * @param jsonReq
//	 * @return
//	 */
//	public String send(String uri, String jsonReq) {
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		PostMethod method = new PostMethod(uri);
//		method.addRequestHeader("Content-Type", "application/json;charset="+charSet);
//		try {	
//			if (logger.isDebugEnabled())
//				logger.debug("HTTP发送原始报文{}", jsonReq);
//			method.setRequestEntity(new StringRequestEntity(jsonReq, null, charSet));
//			httpClient.executeMethod(method);
//			String jsonRes = method.getResponseBodyAsString();
//			if (logger.isDebugEnabled())
//				logger.debug("Http响应原始编码{}",method.getResponseCharSet());
//				logger.debug("HTTP响应原始报文{}", jsonRes);
//			return jsonRes;
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E902.getCode(),
//					DataLogicStatus.E902.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E903.getCode(),
//					DataLogicStatus.E903.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E904.getCode(),
//					DataLogicStatus.E904.getDesc());
//		} catch (Exception e) {
//			logger.error("发送异常[{}]", e.getMessage());
//			throw new ProcessException(e.getMessage());
//		} finally {
//			method.releaseConnection();
//		}
//	}
//	
//	
//	/**
//	 * 广州征信平台用 有转码慎用
//	 * @param uri
//	 * @param jsonReq
//	 * @return
//	 */
//	
//	public String gzSend(String uri, String jsonReq) {
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		PostMethod method = new PostMethod(uri);
//		method.addRequestHeader("Content-Type", "application/json;charset="+charSet);
//		try {	
//			if (logger.isDebugEnabled())
//				logger.debug("HTTP发送原始报文{}", jsonReq);
//			method.setRequestEntity(new StringRequestEntity(jsonReq, null, charSet));
//			httpClient.executeMethod(method);
//			String jsonRes = method.getResponseBodyAsString();
//			String jsonToRes=new String (jsonRes.getBytes("ISO-8859-1"),"utf-8");
//			if (logger.isDebugEnabled())
//				logger.debug("Http响应原始编码{}",method.getResponseCharSet());
//				logger.debug("HTTP响应转码后原始报文{}", jsonToRes);
//			return jsonToRes;
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E902.getCode(),
//					DataLogicStatus.E902.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E903.getCode(),
//					DataLogicStatus.E903.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E904.getCode(),
//					DataLogicStatus.E904.getDesc());
//		} catch (Exception e) {
//			logger.error("发送异常[{}]", e.getMessage());
//			throw new ProcessException(e.getMessage());
//		} finally {
//			method.releaseConnection();
//		}
//	}
//	
//	/**
//	 * 调用外部系统
//	 * 
//	 * @param json
//	 * @param uri
//	 * @return
//	 */
//	public String sendMsHttpPost(Map<String, String> paramMap, String uri,
//			String defCharset) {
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		logger.debug("请求地址:{}", uri);
//		PostMethod postMethod = new PostMethod(uri);
//		String resJson = "";
//		postMethod.addRequestHeader("Content-Type",
//				"application/x-www-form-urlencoded;charset=" + defCharset);
//		try {
//			if (null != paramMap) {
//				Set<String> keys = paramMap.keySet();
//				for (String key : keys) {
//					if (StringUtils.isNotBlank(key)
//							&& StringUtils.isNotBlank(paramMap.get(key))) {
//						String value = paramMap.get(key);
//						postMethod.setParameter(key, value);
//					}
//				}
//			}
//
//			httpClient.executeMethod(postMethod);
//			resJson = postMethod.getResponseBodyAsString();
//			if (logger.isDebugEnabled())
//				logger.debug("接收到的HTTP响应原始报文:{}", resJson);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常{}", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E902.getCode(),
//					DataLogicStatus.E902.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常{]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E903.getCode(),
//					DataLogicStatus.E903.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常{}", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E904.getCode(),
//					DataLogicStatus.E904.getDesc());
//		} finally {
//			postMethod.releaseConnection();
//		}
//
//		return resJson;
//	}
//	/**
//	 * HTTP发送 xml原始报文请求方法
//	 * 
//	 * @param uri 地址
//	 * @param xml 报文
//	 * @param charset 编码
//	 * @return
//	 */
//	public String sendXml(String uri, String xml, String charset) {
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		PostMethod method = new PostMethod(uri);
//		method.addRequestHeader("Content-Type",
//				"application/xml;charset=" + charset);
//		try {
//			if (logger.isDebugEnabled())
//				logger.debug("HTTP发送原始报文{}", xml);
//			method.setRequestEntity(new StringRequestEntity(xml, null,
//					charset));
//			httpClient.executeMethod(method);
//			String respXml = method.getResponseBodyAsString();
//			if (logger.isDebugEnabled())
//				logger.debug("HTTP响应原始报文{}", respXml);
//			return respXml;
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E902.getCode(),
//					DataLogicStatus.E902.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E903.getCode(),
//					DataLogicStatus.E903.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e.getMessage());
//			throw new ProcessException(DataLogicStatus.E904.getCode(),
//					DataLogicStatus.E904.getDesc());
//		} catch (Exception e) {
//			logger.error("发送异常[{}]", e.getMessage());
//			throw new ProcessException(e.getMessage());
//		} finally {
//			method.releaseConnection();
//		}
//	}
//	
//	/**
//	 * 发送带header的HTTP请求
//	 * @param contentType
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public String sendHttpRequest(String contentType, Map<?,?> map,String charset, String request, String url){
//		String response = null;
//	    logger.info("发送报文[{}]", request);
//	    
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		PostMethod postMethod = new PostMethod(url);
//		if (map != null && map.get("headers") != null) {
//			Map<String,String> headers = (Map<String, String>)map.get("headers");
//			Set<String> keys = headers.keySet();
//			for (String key : keys) {
//				if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(headers.get(key))) {
//					String value = headers.get(key);
//					logger.info("发送报文请求headers的key:[{}],value:[{}]", key, value);
//					postMethod.addRequestHeader(key, value);
//				}
//			}
//		}
//				
//		postMethod.addRequestHeader("Content-Type",contentType);
//		try {
//			
//			postMethod.setRequestEntity(new StringRequestEntity(request, null,
//					charset));
//
//			httpClient.executeMethod(postMethod);
//			response = postMethod.getResponseBodyAsString();
//			logger.debug("响应报文[{}]", response);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90002.getCode(),
//					PayfrontError.E_90002.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90003.getCode(),
//					PayfrontError.E_90003.getDesc());
//		} catch (Exception e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} finally {
//			postMethod.releaseConnection();
//		}
//
//		return response;
//	}
//	
//	
//	public String sendHttpGet(String paramStr, String uri, String defCharset) {
//		if (logger.isDebugEnabled())
//			logger.debug("发送报文[{}]", paramStr);
//		
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		GetMethod getMethod = new GetMethod(uri);
//		
//		String resJson = "";
//		org.apache.commons.httpclient.params.HttpMethodParams httpMethodParams = new org.apache.commons.httpclient.params.HttpMethodParams();
//		httpMethodParams.setContentCharset(defCharset);
//		getMethod.setParams(httpMethodParams);
//		
//		try {
//			getMethod.setQueryString(URIUtil.encodeQuery(paramStr,defCharset));  
//			
//				if (logger.isDebugEnabled())
//					logger.debug("get请求Parm:[{}]", paramStr);
//
//			httpClient.executeMethod(getMethod);
//			resJson = getMethod.getResponseBodyAsString();
//			if (logger.isDebugEnabled())
//				logger.debug("响应报文[{}]", resJson);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90002.getCode(),
//					PayfrontError.E_90002.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90003.getCode(),
//					PayfrontError.E_90003.getDesc());
//		} catch (Exception e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} finally {
//			getMethod.releaseConnection();
//		}
//
//		return resJson;
//	}
//	
//	
//	public String sendHttpGet(String uri, String defCharset) {
//		if (logger.isDebugEnabled())
//			logger.debug("发送地址:[{}]", uri);
//		
//		getConnection();
//		// 发送报文
//		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(
//				connectionManager);
//		GetMethod getMethod = new GetMethod(uri);
//		
//		String resJson = "";
//		org.apache.commons.httpclient.params.HttpMethodParams httpMethodParams = new org.apache.commons.httpclient.params.HttpMethodParams();
//		httpMethodParams.setContentCharset(defCharset);
//		getMethod.setParams(httpMethodParams);
//		
//		try {
//			httpClient.executeMethod(getMethod);
//			resJson = getMethod.getResponseBodyAsString();
//			if (logger.isDebugEnabled())
//				logger.debug("响应报文:[{}]", resJson);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} catch (HttpException e) {
//			logger.error("交易http连接异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90002.getCode(),
//					PayfrontError.E_90002.getDesc());
//		} catch (IOException e) {
//			logger.error("交易http读取异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90003.getCode(),
//					PayfrontError.E_90003.getDesc());
//		} catch (Exception e) {
//			logger.error("交易报文编码异常[{}]", e);
//			throw new ProcessException(PayfrontError.E_90001.getCode(),
//					PayfrontError.E_90001.getDesc());
//		} finally {
//			getMethod.releaseConnection();
//		}
//
//		return resJson;
//	}
//	
	/**
	 * 发送json请求
	 * @param reqJson
	 * @param uri
	 * @param defCharset
	 * @return
	 * @throws Exception 
	 */
	public String sendJson(String reqJson, String uri, String defCharset) throws Exception {
		String respContent = "";
		try {
			HttpPost httpPost = new HttpPost(uri);
			CloseableHttpClient client = HttpClients.createDefault();
			httpPost.setHeader("Accept", "application/json;charset="+defCharset);
			// json方式
			JSONObject json = JSON.parseObject(reqJson);
			StringEntity entity = new StringEntity(json.toJSONString(), defCharset);
			entity.setContentEncoding(defCharset);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);

			HttpResponse resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he, defCharset);
			}

		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		} catch (HttpException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} catch (Exception e) {
			throw new Exception(e);
		} 

		return respContent;
	}
//	
//	/**
//	 * 压测挡板
//	 * 
//	 * @return
//	 */
//	public String testResutl() {
//		StringBuffer sbuf = new StringBuffer();
//		sbuf.append("{");
//		sbuf.append("\"code\": \"0\",");
//		sbuf.append("\"data\": {");
//		sbuf.append("\"errorCode\": \"0\",");
//		sbuf.append("\"errorMessage\": \"操作成功\"");
//		sbuf.append("},");
//		sbuf.append("\"message\": \"操作成功\"");
//		sbuf.append("}");
//		return sbuf.toString();
//	}

	// public void send() {
	// getConnection();
	// // 发送报文
	// org.apache.commons.httpclient.HttpClient httpClient = new
	// org.apache.commons.httpclient.HttpClient(connectionManager);
	// PostMethod method = new PostMethod(ALIPAY_URL);
	// method.addRequestHeader("Content-Type", "application/json");
	// try {
	// //数字签名
	// // reqXml = SignUtil.sign(DocumentUtil.getDocFromString(reqXml),
	// keyService.getMyPrivateKey(), msgType);
	// logger.debug("批量查询发送给支付宝报文{}",reqXml);
	// method.setRequestEntity(new StringRequestEntity(reqXml,null, charSet));
	// httpClient.executeMethod(method);
	// String resXml = method.getResponseBodyAsString();
	// logger.debug("批量查询支付宝响应报文{}",resXml);
	//
	// } catch (Exception e) {
	// logger.error("批量查询交易发送查询结果,异常内容[{}]",e.getMessage());
	// e.printStackTrace();
	// } finally {
	// method.releaseConnection();
	// }
	//
	// }

	public String getReqXml() {
		return reqXml;
	}

	public void setReqXml(String reqXml) {
		this.reqXml = reqXml;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
