package com.zhita.controller.idcard;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.face.Get_biz_token;
import com.zhita.controller.face.postDemo;

public class Idcard_biz_token {
	public String getBizToken(){
		String sign = "oJH2/2tbw0V2YBHxFssnlKQUP7RhPVI0NURWSUpZOXk5SnF6dGRucDR4bFE4cUpaOVNiVmRoJmI9MTU2MjIzMzU3MyZjPTE1NjIyMzM1NzMmZD0yOTAxOTI1OTQ=";
		postDemo postDemo = new postDemo();
		String param = "{'sign':'"+sign+"','sign_version':'hmac_sha1','capture_image':'0','return_url':'https://www.taobao.com','notify_url':'http://39.98.83.65:8080/zhita_xiaodai_app/idcardParam/notify','biz_no':'1','idcard_threshold':'0.8','limit_completeness':'2','limit_quality':'0.753','limit_logic':'1'}";
		String result = postDemo.post("https://openapi.faceid.com/lite_ocr/v1/get_biz_token",param);
//		JSONObject jsonObject = JSONObject.parseObject(result);
//        String biz_token = "https://openapi.faceid.com/lite_ocr/v1/do/"+jsonObject.get("biz_token").toString();
//        System.out.println("11111111111111111111111"+biz_token);
		return result;
		
	}
	public static void main(String[] args) {
		Idcard_biz_token idcard_biz_token = new Idcard_biz_token();
		System.out.println(idcard_biz_token.getBizToken());
	}
}
