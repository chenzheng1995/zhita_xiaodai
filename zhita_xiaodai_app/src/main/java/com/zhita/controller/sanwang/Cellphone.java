package com.zhita.controller.sanwang;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class Cellphone {
//	public static void main(String[] args) {
//	    String host = "http://cellphone.haoservice.com";
//	    String path = "/efficient/cellphone";
//	    String method = "GET";
//	    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
//	    Map<String, String> headers = new HashMap<String, String>();
//	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//	    headers.put("Authorization", "APPCODE " + appcode);
//	    Map<String, String> querys = new HashMap<String, String>();
//	    querys.put("idCard", "140121198504101513");
//	    querys.put("mobile", "13331033200");
//	    querys.put("realName", "张强");
//
//
//	    try {
//	    	/**
//	    	* 重要提示如下:
//	    	* HttpUtils请从
//	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//	    	* 下载
//	    	*
//	    	* 相应的依赖请参照
//	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//	    	*/
//	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//	    	System.out.println(response.toString());
//	    	//获取response的body
//	    	System.out.println(EntityUtils.toString(response.getEntity()));
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	    }
//	}
	
	
	
	public String getCellphone(String idcard_number,String trueName,String phone) {
	    String host = "http://cellphone.haoservice.com";
	    String path = "/efficient/cellphone";
	    String method = "GET";
	    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("idCard", idcard_number);
	    querys.put("mobile",phone);
	    querys.put("realName",trueName);


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	return EntityUtils.toString(response.getEntity());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return appcode;
	}
}
