package com.zhita.controller.idcard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.face.postDemo;
import com.zhita.service.manage.userattestation.UserAttestationService;
import com.zhita.util.Base64ToInputStream;
import com.zhita.util.OssUtil;
import com.zhita.util.PostAndGet;

@Controller
@RequestMapping(value="/result")
public class GetResultController {
    @Autowired
    UserAttestationService UserAttestationService;
	
    @RequestMapping("/getresult")
    @ResponseBody
    @Transactional
    public Map<String, Object> getresult(int userId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Map<String, Object> userAttestation = UserAttestationService.getuserAttestation(userId); 
    	JSONObject jsonObject =null;
    	JSONObject jsonObject1 =null;
    	String sign = (String) userAttestation.get("sign");
      	sign = URLEncoder.encode(sign, "UTF-8");
    	String biz_token = (String) userAttestation.get("idCardBizToken");
//    	String param = "{'sign':'"+sign+"','sign_version':'hmac_sha1','userId':'"+userId+"','biz_token':'"+biz_token+"','need_image':1}";
    	PostAndGet postAndGet = new PostAndGet();
    	String result = postAndGet.sendGet("https://openapi.faceid.com/lite_ocr/v1/get_result?sign="+sign+"&sign_version=hmac_sha1&biz_token="+biz_token+"&need_image=1");
    	   jsonObject = JSONObject.parseObject(result);
    	   try {
    		   int result_code =Integer.parseInt(jsonObject.get("result_code").toString());
               String idcard_number =  jsonObject.get("idcard_number").toString();
               jsonObject1 = JSONObject.parseObject(idcard_number);
               idcard_number = jsonObject1.getString("result");
               String image_frontside = jsonObject.get("image_frontside").toString();
               String image_backside = jsonObject.get("image_backside").toString();
               String name = jsonObject.get("name").toString();
               jsonObject1 = JSONObject.parseObject(name);
               name = jsonObject1.getString("result");
               String gender = jsonObject.get("gender").toString();
               jsonObject1 = JSONObject.parseObject(gender);
               gender = jsonObject1.getString("result");
               String nationality = jsonObject.get("nationality").toString();
               jsonObject1 = JSONObject.parseObject(nationality);
               nationality = jsonObject1.getString("result");
               String birth_year = jsonObject.get("birth_year").toString();
               jsonObject1 = JSONObject.parseObject(birth_year);
               birth_year = jsonObject1.getString("result");
               String birth_month = jsonObject.get("birth_month").toString();
               jsonObject1 = JSONObject.parseObject(birth_month);
               birth_month = jsonObject1.getString("result");
               String birth_day = jsonObject.get("birth_day").toString();
               jsonObject1 = JSONObject.parseObject(birth_day);
               birth_day = jsonObject1.getString("result");
               String address = jsonObject.get("address").toString();
               jsonObject1 = JSONObject.parseObject(address);
               address = jsonObject1.getString("result");
               String issued_by = jsonObject.get("issued_by").toString();
               jsonObject1 = JSONObject.parseObject(issued_by);
               issued_by = jsonObject1.getString("result");
               String valid_date_start = jsonObject.get("valid_date_start").toString();
               jsonObject1 = JSONObject.parseObject(valid_date_start);
               valid_date_start = jsonObject1.getString("result");
               String valid_date_end = jsonObject.get("valid_date_end").toString();
               jsonObject1 = JSONObject.parseObject(valid_date_end);
               valid_date_end = jsonObject1.getString("result");
               
               if(result_code==1001) {
            	    
            	    Base64ToInputStream base64ToInputStream = new Base64ToInputStream();
            	    InputStream stream = base64ToInputStream.BaseToInputStream(image_frontside);
            	    InputStream stream1 = base64ToInputStream.BaseToInputStream(image_backside);
    				String path = "xiaodai_idcard/"+idcard_number+"zhengmian"+".jpg";
    				String path1 = "xiaodai_idcard/"+idcard_number+"beimian"+".jpg";
    				OssUtil ossUtil = new OssUtil();
    				String frontsidePath = ossUtil.uploadFile(stream, path);
    				String backsidePath = ossUtil.uploadFile(stream1, path1);
                    int number = UserAttestationService.updateUserAttestation(name, gender, nationality, birth_year, birth_month, birth_day, address,issued_by,valid_date_start,valid_date_end,frontsidePath,backsidePath,userId,idcard_number);
                    if (number == 1) {                  	
                        map.put("msg", "数据插入成功");
                        map.put("Code", "200");
                        map.put("name", name);
                        map.put("idcard_number", idcard_number);
                    } else {
                        map.put("msg", "数据插入失败");
                        map.put("Code", "405");
                    }
   			
               }
		} catch (Exception e) {
			return map;
		}
          
		return map;
    	
    }
}
