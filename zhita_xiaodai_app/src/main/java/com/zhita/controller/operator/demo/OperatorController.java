package com.zhita.controller.operator.demo;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zhita.service.manage.operator.OperatorService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;



@Controller
@RequestMapping(value="/Operator")
public class OperatorController {
	@Autowired 
	OperatorService operatorService;
	
	@Autowired 
	UserAttestationService userAttestationService;
	
	@Autowired 
	IntUserService intUserService;
	
    @RequestMapping("/getOperator")
    @ResponseBody
    @Transactional
    public String getOperator(int userId,String phone){
    	AuthTokenDemo authTokenDemo = new AuthTokenDemo();
    	authTokenDemo.toNotify();
		Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
		String name = (String) userAttestation.get("trueName");
		String idNumber = (String) userAttestation.get("idcard_number");
		String reqId =phone+System.currentTimeMillis();		

    	
   	 Map<String, Object> map = new HashMap<String, Object>();
   	 int number = operatorService.setredIdAndPhone(reqId,userId,phone);
        if (number == 1) {                  	
            map.put("msg", "数据插入成功");
            map.put("Code", "201");
        } else {
            map.put("msg", "数据插入失败");
            map.put("Code", "405");
        }
        
        H5ReportDemo h5ReportDemo = new H5ReportDemo();
        return h5ReportDemo.getH5Report(userId, phone, name, idNumber, reqId);
    }
    	
    @RequestMapping("/updateOperatorJson")
    @ResponseBody
    @Transactional
    public Map<String, String> updateOperatorJson(int userId){
    	Map<String, String> map = new HashMap<>();
		Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
		String name = (String) userAttestation.get("trueName");
		String idNumber = (String) userAttestation.get("idcard_number");
    
        Map<String, Object> operator = operatorService.getOperator(userId);
        String search_id = (String) operator.get("search_id");
        String phone = (String) operator.get("phone");
        String reqId = (String) operator.get("reqId");

        
        
    	H5ReportQueryDemo h5ReportQueryDemo = new H5ReportQueryDemo();
    	String url = h5ReportQueryDemo.getH5ReportQuery(userId,phone,name,idNumber,reqId,search_id);
      	int  number = operatorService.updateOperatorJson(url,userId);
         if (number == 1) {                  	
             map.put("msg", "认证成功");
             map.put("Code", "200");
         } else {
             map.put("msg", "认证成功失败");
             map.put("Code", "401");
         }


		return map;
    	
    }
    
    
//    分控分数
    @RequestMapping("/getScore")
    @ResponseBody
    @Transactional
    public Map<String, Object> getScore(int userId){
    	Map<String, Object> map = new HashMap<>();
		Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
		String name = (String) userAttestation.get("trueName");
		String idNumber = (String) userAttestation.get("idcard_number");
    
        Map<String, Object> operator = operatorService.getOperator(userId);
        String phone = (String) operator.get("phone");
        String reqId = (String) operator.get("reqId");
    	RuleDemo ruleDemo = new RuleDemo();
    	ruleDemo.getRule(userId, phone, name, idNumber, reqId);
    	
    	ScoreDemo scoreDemo = new ScoreDemo();
    	String result = scoreDemo.getScore(userId, phone, name, idNumber, reqId);
    	  JSONObject jsonObject =null;
    	  jsonObject = JSONObject.parseObject(result);
          String tianji_api_tianjiscore_pdscorev5_response =jsonObject.get("tianji_api_tianjiscore_pdscorev5_response").toString();
          jsonObject = JSONObject.parseObject(tianji_api_tianjiscore_pdscorev5_response);
          int score = Integer.parseInt(jsonObject.get("score").toString());  
          intUserService.updateScore(score,userId);
          
          
          int a = 300;
          if(score>a||score==a) {
        	  map.put("code", 200);
        	  map.put("msg", "分数够了");
          }else {
        	  map.put("code", 400);
        	  map.put("msg", "分数不够");
		}
          map.put("score", score);
          
    	
    	
		return map;
    	
    }
    
    
    

}
