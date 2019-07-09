package com.zhita.controller.face;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.service.manage.userattestation.UserAttestationService;

@Controller
@RequestMapping(value="/face")
public class FaceController {
	
    @Autowired
    UserAttestationService userAttestationService;
	
    @RequestMapping("/getface")
    @ResponseBody
    @Transactional
	public Map<String, Object> getface(int userId){
    	String result ="";
    	String biz_token ="";
    	JSONObject jsonObject =null;
		 Map<String, Object> map = new HashMap<String, Object>();
	        if (StringUtils.isEmpty(userId)) {
	            map.put("msg", "userId不能为空");
	            return map;
	        } else {
	        	Map<String, Object> userAttestation = userAttestationService.selectUserAttestationService(userId);
	        	String sign = (String) userAttestation.get("sign");
	        	String sign_version = "hmac_sha1";
	        	String return_url = "http://xcx.rong51dai.com/idcard/renzhengchenggong/index1.html?userId="+userId;
	        	String notify_url = "http://39.98.83.65:8080/zhita_xiaodai_app/faceParam/notify";
	        	int comparison_type =1;
	        	String idcard_name = (String) userAttestation.get("trueName");
	        	String idcard_number = (String) userAttestation.get("idcard_number");
	        	Float idcard_threshold = (float) 0.8;
	        	String liveness_type = "video_number";
	        	Get_biz_token get_biz_token = new Get_biz_token();
	        	result = get_biz_token.getBizToken(sign, sign_version, return_url, notify_url, comparison_type, idcard_name, idcard_number, idcard_threshold, liveness_type);
	        	jsonObject = JSONObject.parseObject(result);
	        	  biz_token =jsonObject.get("biz_token").toString();
			      String biz_token_url = "https://openapi.faceid.com/lite/v1/do/"+jsonObject.get("biz_token").toString();
			      userAttestationService.updateFaceBizToken(biz_token, userId);
			      map.put("Code", "200");
			      map.put("biz_token", biz_token_url);
	        	
	        }
			return map;

	}
}
