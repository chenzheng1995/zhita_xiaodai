package com.zhita.controller.operator.demo;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.istack.internal.logging.Logger;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xml.internal.utils.IntVector;
import com.zhita.controller.xinyan.action.OperatorAction;
import com.zhita.dao.manage.ThreeElementsMapper;
import com.zhita.dao.manage.ZhimiRiskMapper;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.service.manage.applycondition.IntApplyconditionService;
import com.zhita.service.manage.blacklistuser.IntBlacklistuserService;
import com.zhita.service.manage.manconsettings.IntManconsettingsServcie;
import com.zhita.service.manage.operator.OperatorService;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;
import com.zhita.service.manage.whitelistuser.IntWhitelistuserService;
import com.zhita.util.PhoneDeal;
import com.zhita.util.TuoMinUtil;



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
    
    @Autowired
    IntOrderService intOrderService;
    
    @Autowired
    ZhimiRiskMapper zhimiRiskMapper;
    
    @Autowired
    IntWhitelistuserService intWhitelistuserService;
    
    @Autowired
    ThreeElementsMapper threeElementsMapper;
	
    @RequestMapping("/getOperator")
    @ResponseBody
    @Transactional
    public Map<String, Object> getOperator(int userId,String phone){
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
        	 map.put("Ncode","2000");
             map.put("msg", "数据插入成功");
             map.put("Code", "201");
         } else {
        	 map.put("Ncode","405");
             map.put("msg", "数据插入失败");
             map.put("Code", "405");
         }
   	 }else{
   		operatorService.updatereqId(userId,reqId);
   	 }
    
        H5ReportDemo h5ReportDemo = new H5ReportDemo();
        String redirectUrl =h5ReportDemo.getH5Report(userId, phone, name, idNumber, reqId);//运营商链接
        map.put("redirectUrl",redirectUrl);
        map.put("Ncode","2000");
        return map;
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
        	map.put("Ncode","2000");
                map.put("msg", "认证成功");
                map.put("Code", "200");
		}else {
	    	if(error.equals("30000")) {
	    		if(url.indexOf("205")!=-1) {
	        		attestationStatus ="2";
	        		operatorService.updateAttestationStatus(attestationStatus,userId);
	            	map.put("Ncode","300");
	    			  map.put("msg", "数据抓取中，请5分钟后再调一下该接口");
	    	          map.put("Code", "300");
	    		}
	    	}else {
	        	map.put("Ncode","401");
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
    public Map<String, Object> isBlacklist(String phone,String idCard,int companyId,int userId){
    	Map<String, Object> map = new HashMap<>();
    	map.put("Ncode","2000");
        map.put("msg", "不是黑名单 ");
        map.put("code", "200");
        if(idCard==null||idCard.isEmpty()) {
        	int num1 = intBlacklistuserService.getid(phone,companyId);//判断手机号是否是黑名单
        	if(num1==1) {
        		map.put("Ncode","407");
                map.put("msg", "手机号黑名单 ");
                map.put("code", "407");
                map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
                intUserService.updateifBlacklist(userId);
                return map;              
        	}    
        	
        }else {
        	int num1 = intBlacklistuserService.getid(phone,companyId);//判断手机号是否是黑名单
        	int num2 = intBlacklistuserService.getid1(idCard,companyId);//判断身份证是否是黑名单
        	if(num1==1) {
        		map.put("Ncode","407");
                map.put("msg", "手机号黑名单 ");
                map.put("code", "407");
                map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
                intUserService.updateifBlacklist(userId);
                return map;
        	}    	
        	if(num2==1) {
        		map.put("Ncode","408");
                map.put("msg", "身份证黑名单 ");
                map.put("code", "408");
                map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
                intUserService.updateifBlacklist(userId);
                return map;
        	}
		}

		return map;
    	
    }
    
    
    //判断用户是不是重复用户
    @RequestMapping("/isRepeat")
    @ResponseBody
    @Transactional
    public Map<String, Object> isRepeat(String idCard,int userId,int companyId,String phone){
    	Map<String, Object> map = new HashMap<>();
    	map.put("Ncode","2000");
    	PhoneDeal pDeal = new PhoneDeal();
        map.put("msg", "不是重复用户");
        map.put("code", "200");
        boolean a =false;
        int id1 = 0;
    	List<Integer> list = userAttestationService.getuserId(idCard);
    	for (int id : list) {
			String attestationStatus = operatorService.getattestationStatus(id);
			if(attestationStatus==null) {
				attestationStatus="0";
			}
			if(userId!=id&&(attestationStatus.equals("1")||attestationStatus.equals("2"))) {
				a = true;
				id1 = id;
				String phone1 = intUserService.getphone(id);				
				String newphone = pDeal.decryption(phone1);
				TuoMinUtil tUtil = new TuoMinUtil();
				String newphone1 = tUtil.mobileEncrypt(newphone);
		    	map.put("Ncode","401");
	            map.put("msg", "该用户是重复用户");
	            map.put("code", "401");
	            map.put("prompt", "您的身份证已被使用,使用者手机号码为"+newphone1+",如有疑问请联系客服。");
	            intUserService.updateifBlacklist(userId);
	            Map<String, Object> map1 = userAttestationService.getuserAttestation(userId);
	            String name = (String) map1.get("trueName");
	            String date = System.currentTimeMillis()+"";
	            
	            int num1 = intBlacklistuserService.getid(phone,companyId);//判断手机号是否是黑名单
	            if(num1==0) {
		            String blackType = "2";
		            intBlacklistuserService.setBlacklistuser(idCard,userId,companyId,newphone,name,date,blackType);
	            }
	            break;
			}
		}
    	
    	if(a==true) {
        	for (int id : list) {
    			String attestationStatus = operatorService.getattestationStatus(id);
    			if(attestationStatus==null) {
    				attestationStatus="0";
    			}
    			if(id!=id1) {
    				intUserService.updateifBlacklist(id);
    	            Map<String, Object> map1 = userAttestationService.getuserAttestation(id);
    	            String name = (String) map1.get("trueName");
    	            phone = intUserService.getphone(id1);
    	            String newphone = pDeal.decryption(phone);
    	            String date = System.currentTimeMillis()+"";
    	            int num1 = intBlacklistuserService.getid(phone,companyId);//判断手机号是否是黑名单
    	            if(num1==0) {
    	            String blackType = "2";
    	            intBlacklistuserService.setBlacklistuser(idCard,id,companyId,newphone,name,date,blackType);	
    			}
    			}
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
	  map.put("Ncode","2000");
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
		  map.put("Ncode","405");
		  map.put("code", "405");
		  map.put("msg", "年龄不符合条件");
		  return map;
	  }
	  
	  String[] aString = refuseApplyProvince.split("/");
      for (int i = 0; i < aString.length; i++) {
    	  if(address.indexOf(aString[i])!=-1) {
    		  map.put("Ncode","406");
    		  map.put("code", "406");
    		  map.put("msg", "地域不符合条件");
    		  return map;
    	  }
      }

	  
	return map;
    
    
  }
  
  
  
//分控状态
@RequestMapping("/getshareOfState")
@ResponseBody
@Transactional
public Map<String, Object> getshareOfState(int userId){
	String shareOfState = intUserService.getshareOfState(userId);
	if("0".equals(shareOfState)||("1".equals(shareOfState))||("3".equals(shareOfState))) {
		int riskControlPoints = intUserService.getRiskControlPoints(userId);
		int sourceId = intUserService.getsourceId(userId);
		String sourceName = intSourceService.getsourceName(sourceId);
		int manageControlId = intSourceService.getmanageControlId(sourceName);//风控id
		 Map<String, Object> map1 =  intManconsettingsServcie.getManconsettings(manageControlId);
		  String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
          String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
          String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
          int roatnptFractionalSegmentSmall =Integer.parseInt(roatnptFractionalSegment.substring(0,roatnptFractionalSegment.indexOf("-")));
          int roatnptFractionalSegmentBig =Integer.parseInt(roatnptFractionalSegment.substring(roatnptFractionalSegment.indexOf("-")+1,roatnptFractionalSegment.length()));
          
          if(riskControlPoints<roatnptFractionalSegmentSmall) {
           String orderNumber = intOrderService.getorderNumber(userId);
           if (orderNumber==null||!"3".equals(orderNumber)) {
        	   shareOfState ="0";
        	   intUserService.updateshareOfState(userId, shareOfState);
		}
          }
          if(riskControlPoints>roatnptFractionalSegmentSmall&&riskControlPoints<roatnptFractionalSegmentBig) {
       	   shareOfState ="1";
       	intUserService.updateshareOfState(userId, shareOfState);
          }
          if(riskControlPoints>roatnptFractionalSegmentBig) {
       	   shareOfState ="2";
       	intUserService.updateshareOfState(userId, shareOfState);
          }
          
	}
	Map<String, Object> map = new HashMap<>();
	map.put("Ncode","2000");
	String timStamp = System.currentTimeMillis()+"";//当前时间戳
	String applynumber = "SQ"+userId+timStamp;//申请编号
	intUserService.setuser(userId,timStamp,applynumber);
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


//做三要素认证
@RequestMapping("/threeElements")
@ResponseBody
@Transactional
public Map<String, Object> getthreeElements(int userId,String phone,int companyId) throws UnsupportedEncodingException{
	Map<String, Object> map1 = new HashMap<>();
    String code1 = threeElementsMapper.getcode(userId);
    if("0".equals(code1)) {
		map1.put("Ncode","2000");
	 map1.put("code","200");
	 map1.put("msg","认证一致");
	 return map1;
    }
    
    String ifBlacklist = intUserService.getifBlacklist2(userId);
    if("1".equals(ifBlacklist)) {
		map1.put("Ncode","402");
	 map1.put("code","402");
	 map1.put("msg","黑名单用户");
	 return map1;
    }
    
	Map<String, Object> map = userAttestationService.getuserAttestation(userId);
	String trueName = (String) map.get("trueName");
	String idcard_number = (String) map.get("idcard_number");
	OperatorAction operatorAction = new OperatorAction();
	 Map<String, Object> map2 = operatorAction.certification(idcard_number, trueName, phone);
	 String result =(String) map2.get("result");
	 String trans_id = (String) map2.get("trans_id");
	JSONObject jsonObject =null;
	 jsonObject = JSONObject.parseObject(result);
	 jsonObject = jsonObject.getJSONObject("data");
	 String code = jsonObject.getString("code");
	 if("0".equals(code)) {
		 int certification_number =0;
		 int num =  threeElementsMapper.getnum(userId,phone);
		 if(num==0) {
			 threeElementsMapper.setThreeElements(userId,code,trans_id,certification_number,phone);
		 }
		 if(num>0) {
			 threeElementsMapper.updateThreeElements(userId,code,trans_id,certification_number,phone);
		}		 
			map1.put("Ncode","2000");
		 map1.put("code","200");
		 map1.put("msg","认证一致");
		
		 

	 }
	 if("1".equals(code)) {
		 int num =  threeElementsMapper.getnum(userId,phone);
		 if(num==0) {
			 int certification_number = 1;
			 threeElementsMapper.setThreeElements(userId,code,trans_id,certification_number,phone);
		 }
		 if(num>0) {
		 int certification_number = threeElementsMapper.getCertificationnumber(userId);
		 certification_number = certification_number+1;
		 threeElementsMapper.updateThreeElements(userId,code,trans_id,certification_number,phone);
		 if(certification_number>2) {
			 intUserService.updateifBlacklist(userId);
			 String date = System.currentTimeMillis()+"";
	            String blackType = "5";
	            intBlacklistuserService.setBlacklistuser(idcard_number,userId,companyId,phone,trueName,date,blackType);
		 }
		 }
			map1.put("Ncode","405");
		 map1.put("code","405");
		 map1.put("msg","认证不一致");	
		 map1.put("prompt","请使用本人手机号认证");
	 }
	 if("2".equals(code)) {
		 int certification_number =0;
		 int num =  threeElementsMapper.getnum(userId,phone);
		 if(num==0) {
			 threeElementsMapper.setThreeElements(userId,code,trans_id,certification_number,phone);
		 }
		 if(num>0) {
			 certification_number = threeElementsMapper.getCertificationnumber(userId);
			 threeElementsMapper.updateThreeElements(userId,code,trans_id,certification_number,phone);
		}	
		 map1.put("Ncode","407");
		 map1.put("code","407");
		 map1.put("msg","认证信息不存在");		
		 map1.put("prompt","认证信息不存在，请重新认证");
	 }
	 if("9".equals(code)) {
		 int certification_number =0;
		 int num =  threeElementsMapper.getnum(userId,phone);
		 if(num==0) {
			 threeElementsMapper.setThreeElements(userId,code,trans_id,certification_number,phone);
		 }
		 if(num>0) {
			 certification_number = threeElementsMapper.getCertificationnumber(userId);
			 threeElementsMapper.updateThreeElements(userId,code,trans_id,certification_number,phone);
		}	
		 map1.put("Ncode","409");
		 map1.put("code","409");
		 map1.put("msg","其他异常");	
		 map1.put("prompt","未知错误，请联系客服");
	 }
	
	
	return map1;
	
}
    
    
//  分控分数
   @RequestMapping("/getScore")
   @ResponseBody
   @Transactional
   public Map<String, Object> getScore(int userId){



   	String shareOfState =null;
   	int score =0;
   	Map<String, Object> map = new HashMap<>();
   	map.put("Ncode","2000");
//   	shareOfState ="6";
//   	intUserService.updateshareOfState(userId, shareOfState);
	 Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
	String name = (String) userAttestation.get("trueName");
	String idNumber = (String) userAttestation.get("idcard_number");
   	int sourceId = intUserService.getsourceId(userId);
   	String sourceName = intSourceService.getsourceName(sourceId);
   	String phone1 = operatorService.getphone(userId);
   	PhoneDeal pDeal = new PhoneDeal();
   	String newphone = pDeal.decryption(phone1);
   	Map<String, Object> map3 =  userAttestationService.getuserAttestation(userId);
   	String idcard_number =(String) map3.get("idcard_number");
   	String address = (String) map3.get("address");
   	int number = intWhitelistuserService.getWhitelistuser(newphone,idcard_number);
   	if(number!=0) {
    	   shareOfState ="2";
    	   intUserService.updateScore1(userId,shareOfState);
    	   map.put("code", 200);
    	   
   		return map;
   	}

    int manageControlId = intSourceService.getmanageControlId(sourceName);//风控id
    Map<String, Object> map1 =  intManconsettingsServcie.getManconsettings(manageControlId);
           String rmModleName = (String) map1.get("rmModleName");
     if ("风控甲".equals(rmModleName)) {

    
        Map<String, Object> operator = operatorService.getOperator(userId);
        String phone = (String) operator.get("phone");
        String reqId = (String) operator.get("reqId");
        String search_id = (String) operator.get("search_id");

    	
    	ScoreDemo scoreDemo = new ScoreDemo();
    	String result = scoreDemo.getScore(search_id, phone, name, idNumber, reqId);  
    	System.out.println("result-----------------------------"+result);
    	  JSONObject jsonObject =null;
    	  jsonObject = JSONObject.parseObject(result);
          String tianji_api_tianjiscore_pdscorev5_response =jsonObject.get("tianji_api_tianjiscore_pdscorev5_response").toString();
          jsonObject = JSONObject.parseObject(tianji_api_tianjiscore_pdscorev5_response);
         
          System.out.println("search_id"+search_id+"phone"+phone+"name"+name+"idNumber"+idNumber+"reqId"+reqId+"tianji_api_tianjiscore_pdscorev5_response"+tianji_api_tianjiscore_pdscorev5_response);
          score = Integer.parseInt(jsonObject.get("score").toString());
	}
     if("风控乙".equals(rmModleName)) {
         System.out.println("rmModleName------------------------------------------"+1111111);
    	 String fileContent = operatorService.getoperatorJson(userId);
    	 Map<String, Object> map2 = userAttestationService.getuserAttestation(userId);
    	 String linkmanOneName = (String) map2.get("linkmanOneName");
    	 String linkmanOnePhone = (String) map2.get("linkmanOnePhone");
    	 String linkmanTwoName = (String) map2.get("linkmanTwoName");
    	 String linkmanTwoPhone = (String) map2.get("linkmanTwoPhone");
    	 ZhimiRiskDemo zDemo = new ZhimiRiskDemo();
    	 String responseStr = zDemo.getzhimi(fileContent, linkmanOneName, linkmanOnePhone, linkmanTwoName, linkmanTwoPhone,address,newphone,idcard_number,name);
   	  JSONObject jsonObject =null;
   	  jsonObject = JSONObject.parseObject(responseStr);
   	     score =new Double(jsonObject.getDouble("score")).intValue();
   	    String request_id = jsonObject.getString("request_id");
   	    String history_apply = jsonObject.getString("history_apply");
   	 jsonObject = JSONObject.parseObject(history_apply);
   	 int mobile_1h_cnt = jsonObject.getInteger("mobile_1h_cnt");
   	 int mobile_3h_cnt = jsonObject.getInteger("mobile_3h_cnt");
   	int mobile_12h_cnt = jsonObject.getInteger("mobile_12h_cnt");
   	int mobile_1d_cnt = jsonObject.getInteger("mobile_1d_cnt");
   	int mobile_3d_cnt = jsonObject.getInteger("mobile_3d_cnt");
   	int mobile_7d_cnt = jsonObject.getInteger("mobile_7d_cnt");
   	int mobile_14d_cnt = jsonObject.getInteger("mobile_14d_cnt");
   	int mobile_30d_cnt = jsonObject.getInteger("mobile_30d_cnt");
   	int mobile_60d_cnt = jsonObject.getInteger("mobile_60d_cnt");
   	int idcard_1h_cnt = jsonObject.getInteger("idcard_1h_cnt");
   	int idcard_3h_cnt = jsonObject.getInteger("idcard_3h_cnt");
   	int idcard_12h_cnt = jsonObject.getInteger("idcard_12h_cnt");
   	int idcard_1d_cnt = jsonObject.getInteger("idcard_1d_cnt");
   	int idcard_3d_cnt = jsonObject.getInteger("idcard_3d_cnt");
   	int idcard_7d_cnt = jsonObject.getInteger("idcard_7d_cnt");
   	int idcard_14d_cnt = jsonObject.getInteger("idcard_14d_cnt");
   	int idcard_30d_cnt = jsonObject.getInteger("idcard_30d_cnt");
   	int idcard_60d_cnt = jsonObject.getInteger("idcard_60d_cnt");
   	zhimiRiskMapper.setzhimiRisk(userId,request_id,mobile_1h_cnt,mobile_3h_cnt,mobile_12h_cnt,mobile_1d_cnt,mobile_3d_cnt,mobile_7d_cnt,
   			mobile_14d_cnt,mobile_30d_cnt,mobile_60d_cnt,idcard_1h_cnt,idcard_3h_cnt,idcard_12h_cnt,idcard_1d_cnt,idcard_3d_cnt,idcard_7d_cnt,
   			idcard_14d_cnt,idcard_30d_cnt,idcard_60d_cnt);
     }

          String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
          String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
          String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
          int roatnptFractionalSegmentSmall =Integer.parseInt(roatnptFractionalSegment.substring(0,roatnptFractionalSegment.indexOf("-")));
          int roatnptFractionalSegmentBig =Integer.parseInt(roatnptFractionalSegment.substring(roatnptFractionalSegment.indexOf("-")+1,roatnptFractionalSegment.length()));
          System.out.println("roatnptFractionalSegmentSmall--------------------------------------------------"+roatnptFractionalSegmentSmall);
          System.out.println("roatnptFractionalSegmentBig------------------------------------------"+roatnptFractionalSegmentBig);
          System.out.println("score------------------------------------------"+score);
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
