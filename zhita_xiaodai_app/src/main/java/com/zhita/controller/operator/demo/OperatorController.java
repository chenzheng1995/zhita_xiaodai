package com.zhita.controller.operator.demo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;

import com.zhita.model.manage.ManageControlSettings;
import com.zhita.service.manage.applycondition.IntApplyconditionService;
import com.zhita.service.manage.blacklistuser.IntBlacklistuserService;
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
	
    @Autowired
    IntBlacklistuserService intBlacklistuserService;
	
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
		String attestationStatus =null;
		String name = (String) userAttestation.get("trueName");
		String idNumber = (String) userAttestation.get("idcard_number");
    
        Map<String, Object> operator = operatorService.getOperator(userId);
        String search_id = (String) operator.get("search_id");
        String phone = (String) operator.get("phone");
        String reqId = (String) operator.get("reqId");

        
        
//        Map<String, Object> map2 = operatorService.getOperator(userId);
//        String url = (String) map2.get("operatorJson");
//        JSONObject sampleObject = JSON.parseObject(url);
//    	String error = sampleObject.getString("error");
        
    	H5ReportQueryDemo h5ReportQueryDemo = new H5ReportQueryDemo();
    	String url = h5ReportQueryDemo.getH5ReportQuery(userId,phone,name,idNumber,reqId,search_id);
    	JSONObject sampleObject = JSON.parseObject(url);
    	String error = sampleObject.getString("error");
		int  number = operatorService.updateOperatorJson(url,userId);
		if(number==1) {
			map.put("msg", "数据更新成功");
		}else {
			map.put("msg", "数据更新失败");
		}
    	if (error.equals("200")) {     
    		attestationStatus ="1";
    		operatorService.updateAttestationStatus(attestationStatus,userId);
                map.put("msg", "认证成功");
                map.put("Code", "200");
		}else {
	    	if(error.equals("30000")) {
	    		if(url.indexOf("205")!=-1) {
	        		attestationStatus ="2";
	        		operatorService.updateAttestationStatus(attestationStatus,userId);
	    			  map.put("msg", "数据抓取中，请5分钟后再调一下该接口");
	    	          map.put("Code", "300");
	    		}
	    	}else {
	            map.put("msg", "认证失败");
	            map.put("Code", "401");
			}

        }
//		  map.put("msg", "数据抓取中，请5分钟后再调一下该接口");
//        map.put("Code", "300");	
    	
		return map;
    	
    }
    
    //判断用户是不是黑名单
    @RequestMapping("/isBlacklist")
    @ResponseBody
    @Transactional
    public Map<String, Object> isBlacklist(String phone,String idCard,int companyId){
    	Map<String, Object> map = new HashMap<>();
        map.put("msg", "不是黑名单 ");
        map.put("code", "200");
    	int num1 = intBlacklistuserService.getid(phone,companyId);//判断手机号是否是黑名单
    	int num2 = intBlacklistuserService.getid1(idCard,companyId);//判断身份证是否是黑名单
    	if(num1==1) {
            map.put("msg", "手机号黑名单 ");
            map.put("code", "407");
            return map;
    	}    	
    	if(num2==1) {
            map.put("msg", "身份证黑名单 ");
            map.put("code", "408");
            return map;
    	}
		return map;
    	
    }
    
    
    //判断用户是不是重复用户
    @RequestMapping("/isRepeat")
    @ResponseBody
    @Transactional
    public Map<String, Object> isRepeat(String idCard,int userId,int companyId,String phone){
    	Map<String, Object> map = new HashMap<>();
        map.put("msg", "不是重复用户");
        map.put("code", "200");
    	List<Integer> list = userAttestationService.getuserId(idCard);
    	for (int id : list) {
			String attestationStatus = operatorService.getattestationStatus(id);
			if(attestationStatus==null) {
				attestationStatus="0";
			}
			if(userId!=id&&attestationStatus.equals("1")) {
	            map.put("msg", "该用户是重复用户");
	            map.put("code", "401");
	            intUserService.updateifBlacklist(userId);
	            Map<String, Object> map1 = userAttestationService.getuserAttestation(userId);
	            String name = (String) map1.get("trueName");
	            String date = System.currentTimeMillis()+"";
	            String blackType = "2";
	            intBlacklistuserService.setBlacklistuser(idCard,userId,companyId,phone,name,date,blackType);
				return map;
			}
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
	String timStamp = System.currentTimeMillis()+"";//当前时间戳
	String applynumber = "SQ"+userId+timStamp;//申请编号
	intUserService.setuser(userId,timStamp,applynumber);
	String shareOfState = intUserService.getshareOfState(userId);
	map.put("shareOfState", shareOfState);
	return map;
	
}


  
    
////   分控分数
//    @RequestMapping("/getScore")
//    @ResponseBody
//    @Transactional
//    public Map<String, Object> getScore(int userId,String sourceName){
//    	String shareOfState =null;
//    	int score =0;
//    	int roatnptFractionalSegmentSmall =0;
//    	int roatnptFractionalSegmentBig =0;
//    	Map<String, Object> map = new HashMap<>();
////    	shareOfState ="6";
////    	intUserService.updateshareOfState(userId, shareOfState);
//
//		Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
//		String name = (String) userAttestation.get("trueName");
//		String idNumber = (String) userAttestation.get("idcard_number");
//    
//        Map<String, Object> operator = operatorService.getOperator(userId);
//        String phone = (String) operator.get("phone");
//        String reqId = (String) operator.get("reqId");
//        String search_id = (String) operator.get("search_id");
//        int manageControlId = intSourceService.getmanageControlId(sourceName);//风控id
//        Map<String, Object> map1 =  intManconsettingsServcie.getManconsettings(manageControlId);  
//        String rmModleName =(String) map1.get("rmModleName");
//        if(rmModleName.equals("风控甲")) {
//        	RuleDemo ruleDemo = new RuleDemo();
//        	ruleDemo.getRule(userId, phone, name, idNumber, reqId);
//        	
//        	ScoreDemo scoreDemo = new ScoreDemo();
//        	String result = scoreDemo.getScore(search_id, phone, name, idNumber, reqId);
//        	  JSONObject jsonObject =null;
//        	  jsonObject = JSONObject.parseObject(result);
//              String tianji_api_tianjiscore_pdscorev5_response =jsonObject.get("tianji_api_tianjiscore_pdscorev5_response").toString();
//              jsonObject = JSONObject.parseObject(tianji_api_tianjiscore_pdscorev5_response);
//               score = Integer.parseInt(jsonObject.get("score").toString());   
//               String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
//               String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
//               String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
//               roatnptFractionalSegmentSmall =Integer.parseInt(roatnptFractionalSegment.substring(0,roatnptFractionalSegment.indexOf("-")));
//               roatnptFractionalSegmentBig =Integer.parseInt(roatnptFractionalSegment.substring(0,roatnptFractionalSegment.indexOf("-")));
//        }
//        
//        if(rmModleName.equals("风控乙")) {
//        	Map<String, Object> map2 = userAttestationService.getuserAttestation(userId);
//        	String linkmanOneName = (String) map2.get("linkmanOneName");
//        	String linkmanOnePhone = (String) map2.get("linkmanOnePhone");
//        	String linkmanTwoName = (String) map2.get("linkmanTwoName");
//        	String linkmanTwoPhone = (String) map2.get("linkmanTwoPhone");
//        	ZhimiRiskDemo zhimiRiskDemo = new ZhimiRiskDemo();
//        	zhimiRiskDemo.getzhimi(fileContent, linkmanOneName, linkmanOnePhone, linkmanTwoName, linkmanTwoPhone);
//        }
//
//           if(score<roatnptFractionalSegmentSmall) {
//        	   shareOfState ="0";
//        	   map.put("code", 200);
//        	   map.put("msg", "分数不够");
//           }
//           if(score>roatnptFractionalSegmentSmall&&score<roatnptFractionalSegmentBig) {
//        	   shareOfState ="1";
//        	   map.put("code", 200);
//        	   map.put("msg", "需要人工审核");
//           }
//           if(score>roatnptFractionalSegmentBig) {
//        	   shareOfState ="2";
//        	   map.put("code", 200);
//        	   map.put("msg", "分数够了");
//           }
//           intUserService.updateScore(score,userId,shareOfState);
//          map.put("score", score);
//          
//    	
//    	
//		return map;
//    	
//    }
    
    
//  分控分数
   @RequestMapping("/getScore")
   @ResponseBody
   @Transactional
   public Map<String, Object> getScore(int userId,String sourceName){
   	String shareOfState =null;
   	Map<String, Object> map = new HashMap<>();
//   	shareOfState ="6";
//   	intUserService.updateshareOfState(userId, shareOfState);
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
