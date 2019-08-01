package com.zhita.controller.operator.demo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.service.manage.applycondition.IntApplyconditionService;
import com.zhita.service.manage.manconsettings.IntManconsettingsServcie;
import com.zhita.service.manage.operator.OperatorService;
import com.zhita.service.manage.source.IntSourceService;
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
	
	@Autowired 
	IntSourceService intSourceService;
	
	@Autowired 
	IntManconsettingsServcie intManconsettingsServcie;
	
	@Autowired 
	IntApplyconditionService intApplyconditionService;
	
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
   	 int num = operatorService.getuserId(userId);
   	 if(num==0) {
   	   	 int number = operatorService.setredIdAndPhone(reqId,userId,phone);
         if (number == 1) {                  	
             map.put("msg", "数据插入成功");
             map.put("Code", "201");
         } else {
             map.put("msg", "数据插入失败");
             map.put("Code", "405");
         }
   	 }else{
   		operatorService.updatereqId(userId,reqId);
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
    
    
    //判断用户是否年龄或者地域不允许借钱
  @RequestMapping("/conditions")
  @ResponseBody
  @Transactional
  public Map<String, Object> conditions(int userId,int companyId) throws ParseException, Exception{
	  Map<String, Object> map = new HashMap<>();
	  map.put("code", "200");
	  map.put("msg", "符合条件");
	  Map<String, Object> map1 =  userAttestationService.getuserAttestation(userId);
	  String address = (String) map1.get("address");//身份证上的住址
	  String birth_year = (String) map1.get("birth_year");//出生年份
	  String birth_month = (String) map1.get("birth_month");//出生月份
	  String birth_day = (String) map1.get("birth_day");//出生日
	  String birthday = birth_year+"-"+birth_month+"-"+birth_day;
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  int age = getAge(sdf.parse(birthday));
	  Map<String, Object> map2 =  intApplyconditionService.getApplycondition(companyId);
	  int minimumage =(int) map2.get("minimumage");
	  int maximumage =(int) map2.get("maximumage");
	  String refuseApplyProvince = (String) map2.get("refuseApplyProvince");
	  if(age<minimumage||age>maximumage) {
		  map.put("code", "405");
		  map.put("msg", "年龄不符合条件");
		  return map;
	  }
	  
	  if(address.indexOf(refuseApplyProvince)!=-1) {
		  map.put("code", "406");
		  map.put("msg", "地域不符合条件");
		  return map;
	  }
	  
	return map;
    
    
  }
  
//分控状态
@RequestMapping("/getshareOfState")
@ResponseBody
@Transactional
public Map<String, Object> getshareOfState(int userId){
	Map<String, Object> map = new HashMap<>();
	String shareOfState = intUserService.getshareOfState(userId);
	map.put("shareOfState", shareOfState);
	return map;
	
}
  
    
//   融360 分控分数
    @RequestMapping("/getScore")
    @ResponseBody
    @Transactional
    public Map<String, Object> getScore(int userId,String sourceName){
    	String shareOfState =null;
    	Map<String, Object> map = new HashMap<>();
//    	shareOfState ="6";
//    	intUserService.updateshareOfState(userId, shareOfState);
		Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
		String name = (String) userAttestation.get("trueName");
		String idNumber = (String) userAttestation.get("idcard_number");
    
        Map<String, Object> operator = operatorService.getOperator(userId);
        String phone = (String) operator.get("phone");
        String reqId = (String) operator.get("reqId");
        String search_id = (String) operator.get("search_id");
    	RuleDemo ruleDemo = new RuleDemo();
    	ruleDemo.getRule(userId, phone, name, idNumber, reqId);
    	
    	ScoreDemo scoreDemo = new ScoreDemo();
    	String result = scoreDemo.getScore(search_id, phone, name, idNumber, reqId);
    	  JSONObject jsonObject =null;
    	  jsonObject = JSONObject.parseObject(result);
          String tianji_api_tianjiscore_pdscorev5_response =jsonObject.get("tianji_api_tianjiscore_pdscorev5_response").toString();
          jsonObject = JSONObject.parseObject(tianji_api_tianjiscore_pdscorev5_response);
          int score = Integer.parseInt(jsonObject.get("score").toString());  
           int manageControlId = intSourceService.getmanageControlId(sourceName);//风控id
           Map<String, Object> map1 =  intManconsettingsServcie.getManconsettings(manageControlId);  
           String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
           String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
           String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
           int roatnptFractionalSegmentSmall =Integer.parseInt(roatnptFractionalSegment.substring(0,roatnptFractionalSegment.indexOf("-")));
           int roatnptFractionalSegmentBig =Integer.parseInt(roatnptFractionalSegment.substring(0,roatnptFractionalSegment.indexOf("-")));
           if(score<roatnptFractionalSegmentSmall) {
        	   shareOfState ="0";
        	   map.put("code", 200);
        	   map.put("msg", "分数不够");
           }
           if(score>roatnptFractionalSegmentSmall&&score<roatnptFractionalSegmentBig) {
        	   shareOfState ="1";
        	   map.put("code", 200);
        	   map.put("msg", "需要人工审核");
           }
           if(score>roatnptFractionalSegmentBig) {
        	   shareOfState ="2";
        	   map.put("code", 200);
        	   map.put("msg", "分数够了");
           }
           intUserService.updateScore(score,userId,shareOfState);
          map.put("score", score);
          
    	
    	
		return map;
    	
    }
    
    
    public static  int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance(); 
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay); 
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
        int age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一

} } return age; }

}
