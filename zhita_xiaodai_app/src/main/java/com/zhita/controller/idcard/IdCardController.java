package com.zhita.controller.idcard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.face.HmacSha1Sign;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;

@Controller
@RequestMapping(value="/idcard")
public class IdCardController {
	
    @Autowired
    UserAttestationService UserAttestationService;
    
    @Autowired
    IntUserService intUserService;
	
    @RequestMapping("/getidcard")
    @ResponseBody
    @Transactional
	public Map<String, Object> getface(int userId) throws Exception{
    	String sign = "";
    	String error = "";
    	String result ="";
    	JSONObject jsonObject =null;
    	String biz_token ="";
		 Map<String, Object> map = new HashMap<String, Object>();
	        if (StringUtils.isEmpty(userId)) {
	            map.put("msg", "userId不能为空");
	            return map;
	        } else {	        	
	        	sign =UserAttestationService.selectSign(userId);	        	
	        	if(sign==null) {
	        	HmacSha1Sign hmacSha1Sign = new HmacSha1Sign();
	        	String apiKey = "R45DVIJY9y9Jqztdnp4xlQ8qJZ9SbVdh";
	        	String secretKey = "4thZ2ai7nd1SGaHUZTSudmTsxmtIS2By";
	        	long expired = 86400000;   	
//	        	long expired = 0; 
	         	sign = hmacSha1Sign.genSign(apiKey, secretKey, expired);
	         	System.out.println(sign);
	         	int number = UserAttestationService.insertSign(sign,userId);
	         	  if (number == 1) {
	         		 map.put("Ncode","2000");
                      map.put("msg", "数据插入成功");
                      map.put("Code", "201");
                  } else {
             		 map.put("Ncode","405");
                      map.put("msg", "数据插入失败");
                      map.put("Code", "405");
                  }
	        		
	        	}
	        }
	    	String sign_version = "hmac_sha1";
	    	String capture_image = "0";
	    	String return_url = "http://xcx.rong51dai.com/idcard/renzhengchenggong/index.html?userId="+userId;
	    	String notify_url = "http://fk.rong51dai.com/zhita_xiaodai_app/idcardParam/notify";
	    	String idcard_threshold = "0.8";
	    	String limit_completeness = "2";
	    	String limit_quality = "0.753";
	    	String limit_logic ="1";
	        Idcard_biz_token idcard_biz_token = new Idcard_biz_token();
	        result = idcard_biz_token.getBizToken(sign, sign_version, capture_image, return_url, notify_url, idcard_threshold, limit_completeness, limit_quality, limit_logic);
	        jsonObject = JSONObject.parseObject(result);
	        try {
	          biz_token = jsonObject.get("biz_token").toString();
	          String biz_token_url = "https://openapi.faceid.com/lite_ocr/v1/do/"+jsonObject.get("biz_token").toString();
		      UserAttestationService.updateBizToken(biz_token, userId);
		      map.put("Code", "200");
		      map.put("Ncode","2000");
		      map.put("biz_token", biz_token_url);	      
			} catch (Exception e) {
			    error = jsonObject.get("error").toString();
		        if(error.equals("AUTHORIZATION_ERROR:EXPIRED_SIGN")) {
		        	HmacSha1Sign hmacSha1Sign = new HmacSha1Sign();
		        	String apiKey = "R45DVIJY9y9Jqztdnp4xlQ8qJZ9SbVdh";
		        	String secretKey = "4thZ2ai7nd1SGaHUZTSudmTsxmtIS2By";
		        	long expired = 86400000;   	
//		        	long expired = 0; 
		         	sign = hmacSha1Sign.genSign(apiKey, secretKey, expired);
		        	UserAttestationService.updateSign(sign,userId);
			        result = idcard_biz_token.getBizToken(sign, sign_version, capture_image, return_url, notify_url, idcard_threshold, limit_completeness, limit_quality, limit_logic);
			        jsonObject = JSONObject.parseObject(result);
			         biz_token = jsonObject.get("biz_token").toString();
			         String  biz_token_url = "https://openapi.faceid.com/lite_ocr/v1/do/"+jsonObject.get("biz_token").toString();
				    UserAttestationService.updateBizToken(biz_token, userId);
				    map.put("Ncode","2000");
				    map.put("Code", "200");
				    map.put("biz_token", biz_token_url);	  
		        }
			}
	        
			return map;

	}
    
    
    @RequestMapping("/setAddress")
    @ResponseBody
    @Transactional
	public Map<String, Object> setAddress(String homeAddressLongitude,String homeAddressLatitude,String detailAddress,int userId){
    	 Map<String, Object> map = new HashMap<String, Object>();

    	 int number = UserAttestationService.setAddress(homeAddressLongitude,homeAddressLatitude,detailAddress,userId);
         if (number == 1) {      
        	 map.put("Ncode","2000");
             map.put("msg", "数据插入成功");
             map.put("Code", "200");
         } else {
        	 map.put("Ncode","405");
             map.put("msg", "数据插入失败");
             map.put("Code", "405");
         }
	
		return map;
    	
    }
        
    //紧急联系人页面
    @RequestMapping("/setlinkman")
    @ResponseBody
    @Transactional
	public Map<String, Object> setlinkman(String linkmanOneRelation,String linkmanOneName,String linkmanOnePhone,String linkmanTwoRelation,String linkmanTwoName,String linkmanTwoPhone,int userId){
    	 Map<String, Object> map = new HashMap<String, Object>();
    	 map.put("Ncode","2000");
    	 int number = UserAttestationService.setlinkman(linkmanOneRelation,linkmanOneName,linkmanOnePhone,linkmanTwoRelation,linkmanTwoName,linkmanTwoPhone,userId);
         if (number == 1) {                  	
             String attestationStatus = "1";
             String authenticationSteps ="3";
             UserAttestationService.updateAttestationStatus(attestationStatus,userId,authenticationSteps);
             String userAuthenStatus ="1";
             intUserService.updateUserAuthenStatus(userId, userAuthenStatus);
        	 map.put("Ncode","2000");
             map.put("msg", "认证成功");
             map.put("Code", "200");
         } else {
        	 map.put("Ncode","405");
             map.put("msg", "认证失败");
             map.put("Code", "405");
         }
	
		return map;
    	
    }
}
