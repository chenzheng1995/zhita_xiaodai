package com.zhita.controller.face;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.service.manage.userattestation.UserAttestationService;
import com.zhita.util.PostAndGet;

@Controller
@RequestMapping(value="/faceresult")
public class GetFaceResultController {
    @Autowired
    UserAttestationService UserAttestationService;
	
    @RequestMapping("/getfaceresult")
    @ResponseBody
    @Transactional
    public Map<String, Object> getfaceresult(int userId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("Ncode","2000");
    	Map<String, Object> userAttestation = UserAttestationService.getuserAttestation(userId); 
    	JSONObject jsonObject =null;
    	JSONObject jsonObject1 =null;
    	JSONObject jsonObject2 =null;
    	JSONObject jsonObject3 =null;
    	String sign = (String) userAttestation.get("sign");
      	sign = URLEncoder.encode(sign, "UTF-8");
    	String biz_token = (String) userAttestation.get("faceBizToken");
    	PostAndGet postAndGet = new PostAndGet();
    	String result = postAndGet.sendGet("https://openapi.faceid.com/lite/v1/get_result?sign="+sign+"&sign_version=hmac_sha1&biz_token="+biz_token+"&verbose=1");
    	
    	System.out.println(result);
    	   jsonObject = JSONObject.parseObject(result);
    	   try {
    		   int result_code =Integer.parseInt(jsonObject.get("result_code").toString());
    		   if(result_code==1000) {
    			   
    			   jsonObject1 =  (JSONObject) jsonObject.get("verification");
    			   jsonObject3 = (JSONObject) jsonObject1.get("idcard");
    			   jsonObject2 = (JSONObject) jsonObject3.get("thresholds");
    			   BigDecimal confidence = (BigDecimal) jsonObject3.get("confidence");
    			   BigDecimal e6 = (BigDecimal) jsonObject2.get("1e-6");
    			   int i = confidence.compareTo(e6);
    			   if(i==0||i==1) {
                       map.put("msg", "成功");
                       map.put("Code", "200");

    			   }else {
                       map.put("msg", "失败");
                       map.put("Code", "405");
				}
    		   }
		} catch (Exception e) {
			return map;
		}
    	   
		return map;
    	
    }
}
