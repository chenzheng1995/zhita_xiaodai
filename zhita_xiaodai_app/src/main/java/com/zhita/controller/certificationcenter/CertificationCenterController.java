package com.zhita.controller.certificationcenter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.ast.SQLPartitionValue.Operator;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.zhita.dao.manage.BankcardMapper;
import com.zhita.dao.manage.BorrowMoneyMessageMapper;
import com.zhita.dao.manage.RetrialWindControlMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.Bankcard;
import com.zhita.service.manage.autheninfor.IntAutheninforService;
import com.zhita.service.manage.configuration.ConfigurationService;
import com.zhita.service.manage.operator.OperatorService;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;
import com.zhita.service.manage.whitelistuser.IntWhitelistuserService;
import com.zhita.util.PhoneDeal;
import com.zhita.util.PostAndGet;
import com.zhita.util.PostUtil;

import sun.text.normalizer.ICUBinary.Authenticate;

@Controller
@RequestMapping(value="/CertificationCenter")
public class CertificationCenterController {
	
	@Autowired
	IntAutheninforService intAutheninforService;
	
	@Autowired
	UserAttestationService userAttestationService;
	
	@Autowired
	OperatorService operatorService;
	
	@Autowired
	IntUserService intUserService;
	
	@Autowired
	BankcardMapper bankcardMapper;
	
	@Autowired
	IntWhitelistuserService intWhitelistuserService;
	
	@Autowired
	IntOrderService intOrderService;
	
	@Autowired
	RetrialWindControlMapper retrialWindControlMapper;
	
	@Autowired
	BorrowMoneyMessageMapper borrowMoneyMessageMapper;
	
	@Autowired
	ConfigurationService configurationService;
	
	//插入分控模型数据
	@RequestMapping("/setconfiguration")
	@ResponseBody
	@Transactional
    public Map<String, Object> setconfiguration(String jsonString,int userId,String phone){
		PostAndGet pGet = new PostAndGet();
		System.out.println("数据！"+jsonString+"AAA"+phone);
		pGet.sendGet2("http://39.98.83.65:8080/zhita_heitong_Fengkong/Anti/AddUserPhone?jsonString="+jsonString+"&phone="+phone);
    	Map<String, Object> map = new HashMap<>();
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String phoneMarket = jsonObject.getString("phoneMarket");
		String phoneModel = jsonObject.getString("phoneModel");
		String phoneRes = jsonObject.getString("phoneRes");
		String phoneStand = jsonObject.getString("phoneStand"); 
		JSONObject jsonObject1 = JSONObject.parseObject(phoneStand);
		String lac = jsonObject1.getString("lac");
		String loc = jsonObject1.getString("loc");
		String uuid = jsonObject.getString("uuid");
		String wifiIP = jsonObject.getString("wifiIP");
		String wifiMac = jsonObject.getString("wifiMac");
		String wifiName = jsonObject.getString("wifiName");
		String wrapName = jsonObject.getString("wrapName");
		int number = configurationService.getId(userId);
		if(number==0) {
			int num = configurationService.setconfiguration(userId,phoneMarket,phoneModel,phoneRes,lac,loc,uuid,wifiIP,wifiMac,wifiName,wrapName);
			if (num==1) {
				map.put("Ncode","2000");
				map.put("code","200");
				map.put("msg","数据插入成功");
			}else {
				map.put("Ncode","405");
				map.put("code","405");
				map.put("msg","数据插入失败");
			}
		}else {
			int num = configurationService.updateconfiguration(userId,phoneMarket,phoneModel,phoneRes,lac,loc,uuid,wifiIP,wifiMac,wifiName,wrapName);
			if (num==1) {
				map.put("Ncode","2000");
				map.put("code","200");
				map.put("msg","数据更新成功");
			}else {
				map.put("Ncode","405");
				map.put("code","405");
				map.put("msg","数据更新失败");
			}
		}

		
		return map;

    }
	
	
	//获取分控模型数据
	@RequestMapping("/getconfiguration")
	@ResponseBody
	@Transactional
    public Map<String, Object> getconfiguration(int userId){
    	Map<String, Object> map = new HashMap<>();
		map = configurationService.getconfiguration(userId);
		return map;

    }
	
	
	
	
	
	
//	认证中心的数据
	   @RequestMapping("/getCertificationCenter")
	    @ResponseBody
	    @Transactional
		public Map<String, Object> getCertificationCenter(int userId,int companyId){
//			Map<String, Object> map1 = new HashMap<String, Object>();
//		   ArrayList<Object> list = new ArrayList<Object>();
//		   ArrayList<AuthenticationInformation> CertificationCenter = IntAutheninforService.getCertificationCenter(companyId);
		   Map<String, Object> map = userAttestationService.getuserAttestation(userId);
		   Map<String, Object> map3  = new HashMap<String, Object>();
		   map3.put("Ncode","2000");
		   String userAttestation =null;
		   String Operator =null;
		   String bankcard =null;
		   if(map==null) {
			   userAttestation ="0";
		   }else {
			   userAttestation = (String) map.get("attestationStatus");
		   }
		   		  		   
		   Map<String, Object> map2 = operatorService.getOperator(userId);
		   if(map2==null) {
			  Operator ="0";
		   }else {
			   Operator = (String) map2.get("attestationStatus");
		   }
		   
		   Map<String, Object> map4 = bankcardMapper.getbankcard(userId);
		   if(map4==null) {
			   bankcard ="0";
		   }else {
			   bankcard = (String) map4.get("attestationStatus");
		   }
		   
		   String zhima ="0";
		   
		   ArrayList<String> list = intAutheninforService.getifAuthentication(companyId);
		   String userAttestationAutheninfor = list.get(0);
		   String operatorAutheninfor = list.get(2);
		   String bankcardAutheninfor = list.get(1);
		   String zhimaAutheninfor = list.get(3);
		   
           map3.put("userAttestation", userAttestation);
           map3.put("Operator", Operator);
           map3.put("bankcard", bankcard);
           map3.put("zhima", zhima);
           map3.put("userAttestationAutheninfor", userAttestationAutheninfor);
           map3.put("operatorAutheninfor", operatorAutheninfor);
           map3.put("bankcardAutheninfor", bankcardAutheninfor);
           map3.put("zhimaAutheninfor", zhimaAutheninfor);
           
		  if("1".equals(userAttestation)&&"1".equals(bankcard)) {
			   String attestationStatus = "1";		   
			   String phone = intUserService.getphone(userId);
			   PhoneDeal phoneDeal = new PhoneDeal();
			   String newPhone = phoneDeal.decryption(phone);
			   Map<String, Object> map5 = userAttestationService.getuserAttestation(userId);
			   String idcard_number = (String) map5.get("idcard_number");
			   String name = (String) map5.get("trueName");
				int num = intWhitelistuserService.getWhitelistuser1(newPhone, idcard_number, name);
				if (num > 0) {
					operatorService.updateAttestationStatus(attestationStatus, userId);
					String shareOfState = "2";
					intUserService.updateshareOfState(userId, shareOfState);
					int num1 = operatorService.getuserId(userId);
					if (num1 == 0) {
						String authentime = System.currentTimeMillis() + "";// 认证时间
						int number = operatorService.setwhitelistuser(attestationStatus, userId, authentime);
						if (number == 1) {
							map3.put("Ncode", "2000");
							map3.put("msg", "数据插入成功");
							map3.put("Code", "201");
						} else {
							map3.put("Ncode", "405");
							map3.put("msg", "数据插入失败");
							map3.put("Code", "405");
						}
					}
					map3.put("Operator","1");
				} else {
					map3.put("Ncode", "2000");
					map3.put("msg", "用户不是白名单");
					map3.put("Code", "202");
				}
		  }
		   

//for (AuthenticationInformation authenticationInformation : CertificationCenter) {
//	   int id = authenticationInformation.getId();
//	   if(id==1) {
//		   authenticationInformation.setCertificationstatus(attestationStatus);
////		   map1.put("userAttestation", authenticationInformation);
//		   list.add(authenticationInformation);
//	   }
//	   
//	   if(id==3) {
//		   authenticationInformation.setCertificationstatus(attestationStatus1);
//		   list.add(authenticationInformation);
//	   }
//}

             

		   
			return map3;
		   
	   }
	   
	   
	   //返回用户是否可以借钱的状态
//	   @RequestMapping("/isBorrow")
//	    @ResponseBody
//	    @Transactional
//	    public Map<String, Object> isBorrow(int userId,int companyId) throws ParseException{
//		   String userAttestation =null;
//		   String Operator =null;
//		   String bankcard =null;
//		   Map<String, Object> map = userAttestationService.getuserAttestation(userId);
//		   Map<String, Object> map1 = new HashMap<String, Object>();
//
//		   if(map==null) {
//			   userAttestation ="0";
//		   }else {
//		       userAttestation = (String) map.get("attestationStatus");
//		   }
//		   
//		   Map<String, Object> map2 = operatorService.getOperator(userId);
//		   if(map2==null) {
//			   Operator ="0";
//		   }else {
//		       Operator = (String) map2.get("attestationStatus");
//		   }
//		   
//		   
//		   Map<String, Object> map3 = bankcardMapper.getattestationStatus(userId);
//		   if(map3==null) {
//			   bankcard ="0";
//		   }else {
//			   bankcard = (String) map3.get("attestationStatus");
//		   }
//		   
////		   int riskControlPoints = intUserService.getRiskControlPoints(userId);
//		   String shareOfState = intUserService.getshareOfState(userId);
//		   		   
////		   if(userAttestation.equals("1")&&Operator.equals("1")&&bankcard.equals("1")&&(shareOfState.equals("2")||(shareOfState.equals("4")))) {
//			   if(userAttestation.equals("1")&&Operator.equals("1")&&bankcard.equals("1")&&("2".equals(shareOfState)||"4".equals(shareOfState))) {
//			   map1.put("code","200");
//			   map1.put("msg", "满足条件");
//			   map1.put("Ncode","2000");
//		   }else {
//			   map1.put("Ncode","400");
//			   map1.put("code","400");
//			   map1.put("msg", "不满足条件");
//		}
//			   
//			   
//				if("1".equals(userAttestation)) {	
//				String phone2 = intUserService.getphone(userId);// 用户登录进来的手机号
//
//				PhoneDeal pDeal = new PhoneDeal();
//			   String newphone2 = pDeal.decryption(phone2);
//			   Map<String, Object> map7 = userAttestationService.getuserAttestation(userId);
//				String idcard_number = (String) map7.get("idcard_number");
//			   int number = intWhitelistuserService.getWhitelistuser(newphone2, idcard_number);
//				if (number == 0) {
//			   
//	
//			String orderStatus = intOrderService.getorderStatus1(userId, companyId);  
//			if("3".equals(orderStatus)) {
//				
//				String realtime = intOrderService.getrealtime(userId);//用户最后一个订单的还款时间
//				String authentime = operatorService.getauthentime(userId);//运营商认证时间
//				long rtime = Long.parseLong(realtime);
//				long atime = Long.parseLong(authentime);
//				Map<String, Object> map6 =  retrialWindControlMapper.getretrialWindControl(companyId);
//				int howmanyDaysApart = (int) map6.get("howmanyDaysApart");
//				String ifrestore = (String) map6.get("ifrestore");
//				
//				
//		           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		           String sd = sdf.format(new Date(Long.parseLong(realtime))); // 时间戳转换日期
//		           Date date1 = sdf.parse(sd);
//		           Calendar calendar  =   Calendar.getInstance();
//				    calendar.setTime(date1); //需要将date数据转移到Calender对象中操作
//				    calendar.add(calendar.DATE, howmanyDaysApart);//把日期往后增加n天.正数往后推,负数往前移动 
//				    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");  
//				    date1=calendar.getTime();  //这个时间就是日期往后推一天的结果 
//				   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//				   df.format(new Date());// new Date()为获取当前系统时间
//				   Date date = new SimpleDateFormat("yyyy-MM-dd").parse(df.format(new Date()));//取时间  
//				    int num = compare_date(df.format(date),sdf1.format(date1));
//				    if(num==1&&rtime>atime) {//num=1时用户认证过期要重新认证
//				    	shareOfState = "5";
//				    	intUserService.updateshareOfState(userId, shareOfState);
//				    	String attestationStatus = "3";
//                     operatorService.updateAttestationStatus(attestationStatus, userId);
//                     String applyState = "2";
//                     intUserService.updateapplyState(applyState,userId);
//                     if("1".equals(ifrestore)) {
//                    	 BigDecimal canBorrowlines = borrowMoneyMessageMapper.getCanBorrowlines(companyId);
//                    	 intUserService.updateCanBorrowLines(canBorrowlines, userId);
//                     }
//					   map1.put("Ncode","400");
//					   map1.put("code","400");
//					   map1.put("msg", "不满足条件");
//				    }
//				    
//			}
//			}
//				}
//			
//		   
//			return map1;
//		   
//	   }
	   
	   
	   //返回用户是否可以借钱的状态（运营商免认证）
	   @RequestMapping("/isBorrow")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> isBorrow(int userId,int companyId) throws ParseException{
		   String userAttestation =null;
		   String Operator =null;
		   String bankcard =null;
		   Map<String, Object> map = userAttestationService.getuserAttestation(userId);
		   Map<String, Object> map1 = new HashMap<String, Object>();

		   if(map==null) {
			   userAttestation ="0";
		   }else {
		       userAttestation = (String) map.get("attestationStatus");
		   }
		   
		   Map<String, Object> map2 = operatorService.getOperator(userId);
		   if(map2==null) {
			   Operator ="0";
		   }else {
		       Operator = (String) map2.get("attestationStatus");
		   }
		   
		   
		   Map<String, Object> map3 = bankcardMapper.getattestationStatus(userId);
		   if(map3==null) {
			   bankcard ="0";
		   }else {
			   bankcard = (String) map3.get("attestationStatus");
		   }
		   
//		   int riskControlPoints = intUserService.getRiskControlPoints(userId);
		   String shareOfState = intUserService.getshareOfState(userId);
		   		   
//		   if(userAttestation.equals("1")&&Operator.equals("1")&&bankcard.equals("1")&&(shareOfState.equals("2")||(shareOfState.equals("4")))) {
			   if(userAttestation.equals("1")&&bankcard.equals("1")&&("2".equals(shareOfState)||"4".equals(shareOfState))) {
			   map1.put("code","200");
			   map1.put("msg", "满足条件");
			   map1.put("Ncode","2000");
		   }else {
			   map1.put("Ncode","400");
			   map1.put("code","400");
			   map1.put("msg", "不满足条件");
		}
			   
			   
				if("1".equals(userAttestation)) {	
				String phone2 = intUserService.getphone(userId);// 用户登录进来的手机号

				PhoneDeal pDeal = new PhoneDeal();
			   String newphone2 = pDeal.decryption(phone2);
			   Map<String, Object> map7 = userAttestationService.getuserAttestation(userId);
				String idcard_number = (String) map7.get("idcard_number");
			   int number = intWhitelistuserService.getWhitelistuser(newphone2, idcard_number);
				if (number == 0) {
			   
	
			String orderStatus = intOrderService.getorderStatus1(userId, companyId);  
			if("3".equals(orderStatus)) {
				
				String realtime = intOrderService.getrealtime(userId);//用户最后一个订单的还款时间
				String authentime = operatorService.getauthentime(userId);//运营商认证时间
				long rtime = Long.parseLong(realtime);
				long atime = Long.parseLong(authentime);
				Map<String, Object> map6 =  retrialWindControlMapper.getretrialWindControl(companyId);
				int howmanyDaysApart = (int) map6.get("howmanyDaysApart");
				String ifrestore = (String) map6.get("ifrestore");
				
				
		           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		           String sd = sdf.format(new Date(Long.parseLong(realtime))); // 时间戳转换日期
		           Date date1 = sdf.parse(sd);
		           Calendar calendar  =   Calendar.getInstance();
				    calendar.setTime(date1); //需要将date数据转移到Calender对象中操作
				    calendar.add(calendar.DATE, howmanyDaysApart);//把日期往后增加n天.正数往后推,负数往前移动 
				    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");  
				    date1=calendar.getTime();  //这个时间就是日期往后推一天的结果 
				   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				   df.format(new Date());// new Date()为获取当前系统时间
				   Date date = new SimpleDateFormat("yyyy-MM-dd").parse(df.format(new Date()));//取时间  
				    int num = compare_date(df.format(date),sdf1.format(date1));
				    if(num==1&&rtime>atime) {//num=1时用户认证过期要重新认证
				    	shareOfState = "5";
				    	intUserService.updateshareOfState(userId, shareOfState);
				    	String attestationStatus = "3";
                    operatorService.updateAttestationStatus(attestationStatus, userId);
                    String applyState = "2";
                    intUserService.updateapplyState(applyState,userId);
                    if("1".equals(ifrestore)) {
                   	 BigDecimal canBorrowlines = borrowMoneyMessageMapper.getCanBorrowlines(companyId);
                   	 intUserService.updateCanBorrowLines(canBorrowlines, userId);
                    }
					   map1.put("Ncode","400");
					   map1.put("code","400");
					   map1.put("msg", "不满足条件");
				    }
				    
			}
			}
				}
			
		   
			return map1;
		   
	   }
	   
	   public static int compare_date(String DATE1, String DATE2) {//比较时间大小
		   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		   try {
		       Date dt1 = df.parse(DATE1);
		       Date dt2 = df.parse(DATE2);
		       if (dt1.getTime() > dt2.getTime()) {
		           return 1;
		       } else if (dt1.getTime() < dt2.getTime()) {
		           return -1;
		       } else {
		           return 0;
		       }
		   } catch (Exception exception) {
		       exception.printStackTrace();
		   }
		   return 0;
		        }
}
