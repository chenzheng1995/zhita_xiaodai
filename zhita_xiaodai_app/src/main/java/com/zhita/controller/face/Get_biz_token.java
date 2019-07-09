package com.zhita.controller.face;

import com.zhita.util.PostAndGet;

public class Get_biz_token {
	public String getBizToken(String sign,String sign_version,String return_url,String notify_url,int comparison_type,String idcard_name,String idcard_number,float idcard_threshold,String liveness_type){
		postDemo postDemo = new postDemo();
//		String param = "{'sign':'"+sign+"','sign_version':'hmac_sha1','return_url':'https://www.taobao.com','notify_url':'https://www.taobao.com','biz_no':'1','comparison_type':'1','idcard_name':'陈峥','idcard_number':'330225199507155112','liveness_type':'video_number'}";
		String param = "{'sign':'"+sign+"','sign_version':'"+sign_version+"','return_url':'"+return_url+"','notify_url':'"+notify_url+"','comparison_type':'"+comparison_type+"','idcard_name':'"+idcard_name+"','idcard_number':'"+idcard_number+"','liveness_type':'"+liveness_type+"'}";
		String result = postDemo.post("https://openapi.faceid.com/lite/v1/get_biz_token",param);
		return result;
		
	}
		 
	 }
