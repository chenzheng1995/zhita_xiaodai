package com.zhita.controller.face;

import com.zhita.util.PostAndGet;

public class Get_biz_token {
	public String getBizToken(){
		String sign = "8O8UylpHTB3s4T61GV5/2yI36M9hPVI0NURWSUpZOXk5SnF6dGRucDR4bFE4cUpaOVNiVmRoJmI9MTU2MjE1NTIwMCZjPTE1NjIxNTUyMDAmZD05ODcyNzA1NzU=";
		postDemo postDemo = new postDemo();
		String param = "{'sign':'"+sign+"','sign_version':'hmac_sha1','return_url':'https://www.taobao.com','notify_url':'https://www.taobao.com','biz_no':'1','comparison_type':'1','idcard_name':'陈峥','idcard_number':'330225199507155112','liveness_type':'video_number'}";
		String result = postDemo.post("https://openapi.faceid.com/lite/v1/get_biz_token",param);
		return result;
		
	}
	public static void main(String[] args) {
		Get_biz_token get_biz_token = new Get_biz_token();
		System.out.println(get_biz_token.getBizToken());
	}
		 
	 }
