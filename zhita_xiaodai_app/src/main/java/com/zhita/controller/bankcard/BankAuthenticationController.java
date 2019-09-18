package com.zhita.controller.bankcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("bankauthentication")
public class BankAuthenticationController {
	
	public static void main(String[] args) {
	    String host = "https://bankver.market.alicloudapi.com";
	    String path = "/creditop/BankCardQuery/BankCardVerification";
	    String method = "GET";
	    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("accountNo", "6214835901884138");
	    querys.put("bankPreMobile", "13487139655");
	    querys.put("idCardCode", "420621199905157170");
	    querys.put("name", "东新雨");


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
	    	System.out.println("CC:"+response.toString());
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	
	/**
	 * 身份证识别
	 */
	@ResponseBody
	@RequestMapping("Photorecognition")
	public void Photorecognition(){
		String host = "https://bankocr.market.alicloudapi.com";
	    String path = "/cardbank";
	    String method = "POST";
	    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    Map<String, String> bodys = new HashMap<String, String>();
	    bodys.put("img", "http://img3.fegine.com/image/bank.jpg");
	    //或者base64
	    //bodys.put("img", "data:image/jpeg;base64,/9j/4A......");



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
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	//System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
