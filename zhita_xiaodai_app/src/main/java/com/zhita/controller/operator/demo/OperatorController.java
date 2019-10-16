package com.zhita.controller.operator.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.zhita.controller.mozhang.Magicwand3Demo;
import com.zhita.controller.sanwang.Cellphone;
import com.zhita.controller.xinyan.action.OperatorAction;
import com.zhita.dao.manage.ApplynumberMapper;
import com.zhita.dao.manage.AuthenticationInformationMapper;
import com.zhita.dao.manage.BankcardMapper;
import com.zhita.dao.manage.ThirdcalltongjiMapper;
import com.zhita.dao.manage.ThirdpartyInterfaceMapper;
import com.zhita.dao.manage.ThreeElementsMapper;
import com.zhita.dao.manage.ZhimiRiskMapper;
import com.zhita.model.manage.UserJson;
import com.zhita.service.manage.applycondition.IntApplyconditionService;
import com.zhita.service.manage.autheninfor.IntAutheninforService;
import com.zhita.service.manage.blacklistuser.IntBlacklistuserService;
import com.zhita.service.manage.manconsettings.IntManconsettingsServcie;
import com.zhita.service.manage.operator.OperatorService;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;
import com.zhita.service.manage.whitelistuser.IntWhitelistuserService;
import com.zhita.util.MD5Utils;
import com.zhita.util.PhoneDeal;
import com.zhita.util.PostAndGet;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.TuoMinUtil;

@Controller
@RequestMapping(value = "/Operator")
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

	@Autowired
	ThirdcalltongjiMapper thirdcalltongjiMapper;

	@Autowired
	BankcardMapper bankcardMapper;

	@Autowired
	ApplynumberMapper applynumberMapper;

	@Autowired
	IntAutheninforService intAutheninforService;
	
	@Autowired
	ThirdpartyInterfaceMapper thirdpartyInterfaceMapper;
	
	@Autowired
	AuthenticationInformationMapper  authenticationInformationMapper;
	
	
	
	//360运营商接口
	@RequestMapping("/getOperator")
	@ResponseBody
	@Transactional
	public Map<String, Object> getOperator(int userId, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		  String secondattributes = "运营商";
		   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
		   if("1".equals(status)) {
		AuthTokenDemo authTokenDemo = new AuthTokenDemo();
		authTokenDemo.toNotify();
		Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
		String name = (String) userAttestation.get("trueName");
		String idNumber = (String) userAttestation.get("idcard_number");
		String reqId = phone + System.currentTimeMillis();


		String authentime = System.currentTimeMillis() + "";// 认证时间
		int num = operatorService.getuserId(userId);
		if (num == 0) {
			int number = operatorService.setredIdAndPhone(reqId, userId, phone, authentime);
			if (number == 1) {
				map.put("Ncode", "2000");
				map.put("msg", "数据插入成功");
				map.put("Code", "201");
			} else {
				map.put("Ncode", "405");
				map.put("msg", "数据插入失败");
				map.put("Code", "405");
			}
		} else {
			operatorService.updatereqId(userId, reqId, authentime);
		}

		H5ReportDemo h5ReportDemo = new H5ReportDemo();
		String redirectUrl = h5ReportDemo.getH5Report(userId, phone, name, idNumber, reqId);// 运营商链接
		map.put("redirectUrl", redirectUrl);
		map.put("Ncode", "2000");
		   }else {
			   String authentime = System.currentTimeMillis() + "";// 认证时间
			   int num = operatorService.getuserId(userId);
				if (num == 0) {
					int number = operatorService.setoperator(userId,authentime,phone);
					if (number == 1) {
						map.put("Ncode", "2000");
						map.put("msg", "数据插入成功");
						map.put("code", "200");
					} else {
						map.put("Ncode", "405");
						map.put("msg", "数据插入失败");
						map.put("code", "405");
					}
				} else {
					operatorService.updateoperator(userId,authentime,phone);
					map.put("Ncode", "2000");
					map.put("msg", "数据更新成功");
					map.put("code", "200");
				}
				String attestationStatus = "1";
				operatorService.updateAttestationStatus(attestationStatus, userId);
				intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
				map.put("Ncode", "2000");
				map.put("Code", "407"); 
		   }
		return map;
	}

	
	//360运营商接口
	@RequestMapping("/updateOperatorJson")
	@ResponseBody
	@Transactional
	public Map<String, Object> updateOperatorJson(int userId) {
		Map<String, Object> map = new HashMap<>();
		int companyId =3;
		String operatorsAuthentication = thirdpartyInterfaceMapper.getOperatorsAuthentication(companyId);
		if("360".equals(operatorsAuthentication)) {
			 String secondattributes = "运营商";
			   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
			   if("1".equals(status)) {
			Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
			String attestationStatus = null;
			String name = (String) userAttestation.get("trueName");
			String idNumber = (String) userAttestation.get("idcard_number");

			Map<String, Object> operator = operatorService.getOperator(userId);
			String search_id = (String) operator.get("search_id");
			String phone = (String) operator.get("phone");
			String reqId = (String) operator.get("reqId");

			// Map<String, Object> map2 = operatorService.getOperator(userId);
			// String url = (String) map2.get("operatorJson");
			// JSONObject sampleObject = JSON.parseObject(url);
			// String error = sampleObject.getString("error");

			H5ReportQueryDemo h5ReportQueryDemo = new H5ReportQueryDemo();
			String url = h5ReportQueryDemo.getH5ReportQuery(userId, phone, name, idNumber, reqId, search_id);
			JSONObject sampleObject = JSON.parseObject(url);
			String error = sampleObject.getString("error");
			if (error.equals("200")) {
				attestationStatus = "1";
				operatorService.updateAttestationStatus(attestationStatus, userId);
				intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
				int number = operatorService.updateOperatorJson(url, userId);
				if (number == 1) {
					String thirdtypeid = "5";
					String date = System.currentTimeMillis() + "";
					thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date);
					map.put("msg", "数据更新成功");
				} else {
					map.put("msg", "数据更新失败");
				}
				map.put("Ncode", "2000");
				map.put("msg", "认证成功");
				map.put("Code", "200");
			} else {
				if (error.equals("30000")) {
					if (url.indexOf("205") != -1) {
						attestationStatus = "2";
						operatorService.updateAttestationStatus(attestationStatus, userId);
						intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
						map.put("Ncode", "2000");
						map.put("msg", "数据抓取中，请5分钟后再调一下该接口");
						map.put("Code", "300");
					}
				} else {
					map.put("Ncode", "2000");
					map.put("msg", "认证失败");
					map.put("Code", "401");
				}

			}
			// map.put("msg", "数据抓取中，请5分钟后再调一下该接口");
			// map.put("Code", "300");
			   }else {
					String attestationStatus = "1";
					operatorService.updateAttestationStatus(attestationStatus, userId);
					intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
					map.put("Ncode", "2000");
					map.put("Code", "200"); 
			   }
		}
		if("嘉州".equals(operatorsAuthentication)) {
			Map<String, Object> map1 = operatorService.getjiazhouoperator(userId);
			String crawlerId = (String) map1.get("crawlerId");
			String crawlerToken = (String) map1.get("crawlerToken");
			String sms_verify_code = (String) map1.get("sms_verify_code");
			operatorService.updatejiazhouoperator(userId, crawlerId, crawlerToken,sms_verify_code);
				String secondattributes = "运营商";
				   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
				   if("1".equals(status)) {
				String appId = "8625";
				String secret_key = "2260bc42b69e0bd65a73b2086fc4d412";
				String crawlerType = "OperatorReport";
				PostAndGet pGet = new PostAndGet();	    
			    String str = pGet.sendPost("http://bbk.jmy919.cn/api/Gateway/operate?crawlerId="+crawlerId+"&crawlerToken="+crawlerToken+"&sms_verify_code="+sms_verify_code+"&appId="+appId+"&crawlerType="+crawlerType+"&secret_key="+secret_key,"");
				JSONObject sampleObject = JSON.parseObject(str);
				if(sampleObject!=null) {
				String code = sampleObject.getString("code");
				if (code.equals("200")) {			
					UserJson paramObject = new UserJson();
					paramObject.setJsonString(str);
					paramObject.setUserId(userId);
					String json  = JSONObject.toJSONString(paramObject);
					pGet.doJsonPost("http://fk.rong51dai.com/zhita_heitong_Fengkong/jiaZhouOperator/setOperator",json);
					String attestationStatus = "1";
					operatorService.updateAttestationStatus(attestationStatus, userId);
					intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
					int number = operatorService.updateOperatorJson(str, userId);
					if (number == 1) {
						String thirdtypeid = "5";
						String date = System.currentTimeMillis() + "";
						thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date);
						map.put("msg", "数据更新成功");
					} else {
						map.put("msg", "数据更新失败");
					}
					map.put("Ncode", "2000");
					map.put("msg", "认证成功");
					map.put("Code", "200");
				} else {
					if (code.equals("400")) {
							String attestationStatus = "2";
							operatorService.updateAttestationStatus(attestationStatus, userId);
							intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
							map.put("Ncode", "2000");
							map.put("msg", "数据抓取中，请5分钟后再调一下该接口");
							map.put("Code", "300");
					} else {
						map.put("Ncode", "2000");
						map.put("msg", "认证失败");
						map.put("Code", "401");
					}
				}
				}else {
					String attestationStatus = "1";
					operatorService.updateAttestationStatus(attestationStatus, userId);
					intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
					map.put("Ncode", "407");
					map.put("msg", "运营商没调通");
					map.put("Code", "407");
				}
				   }else {
						String attestationStatus = "1";
						operatorService.updateAttestationStatus(attestationStatus, userId);
						intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
						map.put("Ncode", "2000");
						map.put("msg", "认证成功");
						map.put("Code", "200");
				   }
		}
		
		
		 
		return map;

	}
	
	
	//嘉州金控运营商接口
	@RequestMapping("/jzjkOperator")
	@ResponseBody
	@Transactional
	public Map<String, Object> jzjkOperator(int userId,String password,String phone) {  //password是服务密码
		Map<String, Object> map = new HashMap<>();
		  String secondattributes = "运营商";
		   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
		   if("1".equals(status)) {
		String username =phone;//手机号
		Map<String, Object> map1 = userAttestationService.getuserAttestation(userId);		
		String identityName =(String) map1.get("trueName");//姓名
		String identityNo = (String) map1.get("idcard_number");//身份证号
		String crawlerType = "OperatorReport";//类型
		String appId = "8625";//应用ID
		String secret_key = "2260bc42b69e0bd65a73b2086fc4d412";//应用秘钥
		String string = "appId="+appId+"&crawlerType="+crawlerType+"&identityName="+identityName+"&identityNo="+identityNo+"&password="+password+"&username="+username+"&secret_key="+secret_key;
		MD5Utils mUtils = new MD5Utils();		
		String sign = mUtils.getMD5(string);//签名
		PostAndGet pGet = new PostAndGet();
		String str = pGet.sendPost("http://bbk.jmy919.cn/api/Gateway/index?username="+username+"&password="+password+"&identityName="+identityName+"&identityNo="+identityNo+"&crawlerType="+crawlerType+"&appId="+appId+"&secret_key="+secret_key+"&sign="+sign,"");
		JSONObject jsonObject = JSONObject.parseObject(str);
		if(jsonObject!=null) {
			int code = (int) jsonObject.get("code");			
			if(code==400) {
				map.put("Ncode", "2000");
				map.put("msg", "手机号使用太频繁，请两分钟之后再尝试");
				map.put("code", "408");
				return map;
			}
		JSONObject data = (JSONObject) jsonObject.get("data");
			String crawlerId = data.getString("crawlerId");//爬虫ID
			String crawlerToken = data.getString("crawlerToken");//爬虫Token
			String authentime = System.currentTimeMillis() + "";// 认证时间
			int num = operatorService.getuserId(userId);
			if (num == 0) {
				int number = operatorService.setoperator(userId,authentime,phone);
				if (number == 1) {
					map.put("Ncode", "2000");
					map.put("msg", "数据插入成功");
					map.put("code", "200");
				} else {
					map.put("Ncode", "405");
					map.put("msg", "数据插入失败");
					map.put("code", "405");
				}
			} else {
				operatorService.updateoperator(userId,authentime,phone);
				map.put("Ncode", "2000");
				map.put("msg", "数据更新成功");
				map.put("code", "200");
			}
			
			map.put("crawlerId",crawlerId);
			map.put("crawlerToken",crawlerToken);
		}else {
			String attestationStatus = "1";
			operatorService.updateAttestationStatus(attestationStatus, userId);
			intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
			map.put("Ncode", "2000");
			map.put("msg", "运营商没调通");
			map.put("code", "407");
		}
		   }else {
			   String authentime = System.currentTimeMillis() + "";// 认证时间
			   int num = operatorService.getuserId(userId);
				if (num == 0) {
					int number = operatorService.setoperator(userId,authentime,phone);
					if (number == 1) {
						map.put("Ncode", "2000");
						map.put("msg", "数据插入成功");
						map.put("code", "200");
					} else {
						map.put("Ncode", "405");
						map.put("msg", "数据插入失败");
						map.put("code", "405");
					}
				} else {
					operatorService.updateoperator(userId,authentime,phone);
					map.put("Ncode", "2000");
					map.put("msg", "数据更新成功");
					map.put("code", "200");
				}
				String attestationStatus = "1";
				operatorService.updateAttestationStatus(attestationStatus, userId);
				intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
				map.put("Ncode", "2000");
				map.put("code", "407"); 
		   }
		return map;
	}
	
	
	//嘉州金控运营商接口
	@RequestMapping("/jzjkupdateOperatorJson")
	@ResponseBody
	@Transactional
	public Map<String, Object> jzjkupdateOperatorJson(int userId,String crawlerId,String crawlerToken,String sms_verify_code) {
		Map<String, Object> map = new HashMap<>();
		operatorService.updatejiazhouoperator(userId, crawlerId, crawlerToken,sms_verify_code);
		RedisClientUtil redis = new RedisClientUtil();
		String aca = redis.getjiazhou("jzjkupdateOperatorJson"+userId);
		if(aca != null){
			map.put("Ncode", 0);
			map.put("code", "0");
			map.put("msg", "请勿重复点击,请两分钟之后再尝试");
			return map;
		}else {
			redis.setjiazhou("jzjkupdateOperatorJson"+userId,"1");
			String secondattributes = "运营商";
			   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
			   if("1".equals(status)) {
			String appId = "8625";
			String secret_key = "2260bc42b69e0bd65a73b2086fc4d412";
			String crawlerType = "OperatorReport";
			PostAndGet pGet = new PostAndGet();	    
		    String str = pGet.sendPost("http://bbk.jmy919.cn/api/Gateway/operate?crawlerId="+crawlerId+"&crawlerToken="+crawlerToken+"&sms_verify_code="+sms_verify_code+"&appId="+appId+"&crawlerType="+crawlerType+"&secret_key="+secret_key,"");
		    boolean a = str.endsWith("<br /><b>Fatal error</b>:  Uncaught think\\exception\\ErrorException: Unknown: Skipping numeric key 10 in Unknown:0Stack trace:#0 [internal function]: think\\Error::appError(8, 'Unknown: Skippi...', 'Unknown', 0, NULL)#1 {main}  thrown in <b>Unknown</b> on line <b>0</b><br />");
			if(a==true) {
				str = str.replace("<br /><b>Fatal error</b>:  Uncaught think\\exception\\ErrorException: Unknown: Skipping numeric key 10 in Unknown:0Stack trace:#0 [internal function]: think\\Error::appError(8, 'Unknown: Skippi...', 'Unknown', 0, NULL)#1 {main}  thrown in <b>Unknown</b> on line <b>0</b><br />","");
			}
		    System.out.println(str);
			JSONObject sampleObject = JSON.parseObject(str);
			if(sampleObject!=null) {
			String code = sampleObject.getString("code");
			if (code.equals("200")) {			
				UserJson paramObject = new UserJson();
				paramObject.setJsonString(str);
				paramObject.setUserId(userId);
				String json  = JSONObject.toJSONString(paramObject);
				pGet.doJsonPost("http://fk.rong51dai.com/zhita_heitong_Fengkong/jiaZhouOperator/setOperator",json);
				String attestationStatus = "1";
				operatorService.updateAttestationStatus(attestationStatus, userId);
				intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
				int number = operatorService.updateOperatorJson(str, userId);
				if (number == 1) {
					int companyId = 3;
					String thirdtypeid = "5";
					String date = System.currentTimeMillis() + "";
					thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date);
					map.put("msg", "数据更新成功");
				} else {
					map.put("msg", "数据更新失败");
				}
				map.put("Ncode", "2000");
				map.put("msg", "认证成功");
				map.put("code", "200");
			} else {
				if (code.equals("400")) {
						String attestationStatus = "0";
						operatorService.updateAttestationStatus(attestationStatus, userId);
						intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
						map.put("Ncode", "2000");
						map.put("msg", "手机号使用太频繁，请两分钟之后再尝试");
						map.put("code", "408");
				} else {
					map.put("Ncode", "2000");
					map.put("msg", "认证失败");
					map.put("code", "401");
				}
			}
			}else {
				String attestationStatus = "1";
				operatorService.updateAttestationStatus(attestationStatus, userId);
				intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
				map.put("Ncode", "407");
				map.put("msg", "运营商没调通");
				map.put("code", "407");
			}
			   }else {
					String attestationStatus = "1";
					operatorService.updateAttestationStatus(attestationStatus, userId);
					intUserService.updateOperatorAuthenStatus(attestationStatus, userId);
					map.put("Ncode", "2000");
					map.put("msg", "认证成功");
					map.put("code", "200");
			   }
		}
		  
		return map; 
	
	}
	
	
	
	//获取运营商
	@RequestMapping("/acquireOperator")
	@ResponseBody
	@Transactional
	public Map<String, Object> acquireOperator(int companyId) {
		Map<String, Object> map = new HashMap<>();
		String type = "";
		String operatorsAuthentication = thirdpartyInterfaceMapper.getOperatorsAuthentication(companyId);
		if("360".equals(operatorsAuthentication)) {
			type = "1";
		}
		if("嘉州".equals(operatorsAuthentication)) {
			type = "2";
		}
		map.put("operatorsAuthentication", operatorsAuthentication);
		map.put("code",200);
		map.put("Ncode",2000);
		map.put("type", type);
		return map;
		
	}
	
	

	// 判断用户是不是黑名单
	@RequestMapping("/isBlacklist")
	@ResponseBody
	@Transactional
	public Map<String, Object> isBlacklist(String phone, String idCard, int companyId, int userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("Ncode", "2000");
		map.put("msg", "不是黑名单 ");
		map.put("code", "200");
		if (idCard == null || idCard.isEmpty()) {
			int num1 = intBlacklistuserService.getid(phone, companyId);// 判断手机号是否是黑名单
			if (num1 == 1) {
				map.put("Ncode", "407");
				map.put("msg", "手机号黑名单 ");
				map.put("code", "407");
				map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
				intUserService.updateifBlacklist(userId);
				return map;
			}

		} else {
			int num1 = intBlacklistuserService.getid(phone, companyId);// 判断手机号是否是黑名单
			int num2 = intBlacklistuserService.getid1(idCard, companyId);// 判断身份证是否是黑名单
			if (num1 == 1) {
				map.put("Ncode", "407");
				map.put("msg", "手机号黑名单 ");
				map.put("code", "407");
				map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
				intUserService.updateifBlacklist(userId);
				return map;
			}
			if (num2 == 1) {
				map.put("Ncode", "408");
				map.put("msg", "身份证黑名单 ");
				map.put("code", "408");
				map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
				intUserService.updateifBlacklist(userId);
				return map;
			}
		}

		return map;

	}

	// 判断用户是不是重复用户
	@RequestMapping("/isRepeat")
	@ResponseBody
	@Transactional
	public Map<String, Object> isRepeat(int userId, int companyId, String phone) {
		Map<String, Object> map = new HashMap<>();
		map.put("Ncode", "2000");
		PhoneDeal pDeal = new PhoneDeal();
		map.put("msg", "不是重复用户");
		map.put("code", "200");
		boolean a = false;
		int id1 = 0;
		String idCard = userAttestationService.getidCard(userId);
		List<Integer> list = userAttestationService.getuserId(idCard);
		for (int id : list) {
			String attestationStatus = operatorService.getattestationStatus(id);
			if (attestationStatus == null) {
				attestationStatus = "0";
			}
			if (userId != id && (attestationStatus.equals("1") || attestationStatus.equals("2"))) {
				a = true;
				id1 = id;
				String phone1 = intUserService.getphone(id);
				String newphone = pDeal.decryption(phone1);
				TuoMinUtil tUtil = new TuoMinUtil();
				String newphone1 = tUtil.mobileEncrypt(newphone);
				map.put("Ncode", "401");
				map.put("msg", "该用户是重复用户");
				map.put("code", "401");
				map.put("prompt", "您的身份证已被使用,使用者手机号码为" + newphone1 + ",如有疑问请联系客服。");
				intUserService.updateifBlacklist(userId);
				Map<String, Object> map1 = userAttestationService.getuserAttestation(userId);
				String name = (String) map1.get("trueName");
				String date = System.currentTimeMillis() + "";

				int num1 = intBlacklistuserService.getid(phone, companyId);// 判断手机号是否是黑名单
				if (num1 == 0) {
					String blackType = "2";
					intBlacklistuserService.setBlacklistuser(idCard, userId, companyId, phone, name, date, blackType);
				}
				break;
			}
		}

		if (a == true) {
			for (int id : list) {
				String attestationStatus = operatorService.getattestationStatus(id);
				if (attestationStatus == null) {
					attestationStatus = "0";
				}
				if (id != id1) {
					intUserService.updateifBlacklist(id);
					Map<String, Object> map1 = userAttestationService.getuserAttestation(id);
					String name = (String) map1.get("trueName");
					phone = intUserService.getphone(id);
					String newphone = pDeal.decryption(phone);
					String date = System.currentTimeMillis() + "";
					int num1 = intBlacklistuserService.getid(newphone, companyId);// 判断手机号是否是黑名单
					if (num1 == 0) {
						String blackType = "2";
						intBlacklistuserService.setBlacklistuser(idCard, id, companyId, newphone, name, date,
								blackType);
					}
				}
			}
		}

		return map;

	}

	// 判断用户是否年龄或者地域不允许借钱
	@RequestMapping("/conditions")
	@ResponseBody
	@Transactional
	public Map<String, Object> conditions(int userId, int companyId) throws ParseException, Exception {
		Map<String, Object> map = new HashMap<>();
		String shareOfState = "0";
		map.put("Ncode", "2000");
		map.put("code", "200");
		map.put("msg", "符合条件");
		Map<String, Object> map1 = userAttestationService.getuserAttestation(userId);
		String address = (String) map1.get("address");// 身份证上的住址
		// String aS = address.substring(0, 3);// 身份证地址截取前三位
		String birth_year = (String) map1.get("birth_year");// 出生年份
		String birth_month = (String) map1.get("birth_month");// 出生月份
		String birth_day = (String) map1.get("birth_day");// 出生日
		String birthday = birth_year + "-" + birth_month + "-" + birth_day;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int age = getAge(sdf.parse(birthday));
		Map<String, Object> map2 = intApplyconditionService.getApplycondition(companyId);
		int minimumage = (int) map2.get("minimumage");
		int maximumage = (int) map2.get("maximumage");
		String refuseApplyProvince = (String) map2.get("refuseApplyProvince");
		if (age < minimumage || age > maximumage) {
			intUserService.updateshareOfState(userId, shareOfState);
			map.put("Ncode", "2000");
			map.put("code", "405");
			map.put("msg", "年龄不符合条件");
			return map;
		}
		String[] aString = refuseApplyProvince.split("/");
		for (int i = 0; i < aString.length; i++) {
			if (address.indexOf(aString[i]) != -1) {
				intUserService.updateshareOfState(userId, shareOfState);
				map.put("Ncode", "2000");
				map.put("code", "406");
				map.put("msg", "地域不符合条件");
				return map;
			}

		}




		return map;

	}

	// 判断用户是否年龄或者地域不允许借钱
	@RequestMapping("/getModel")
	@ResponseBody
	@Transactional
	public String getModel(int userId) {
		String model = intUserService.getModel(userId);
		return model;
	}

	// 分控状态
	 @RequestMapping("/getshareOfState")
	 @ResponseBody
	 @Transactional
	 public Map<String, Object> getshareOfState(int userId) {
	 String Operator = null;
	 String bankcard = null;
	 Map<String, Object> map = new HashMap<>();
	 String authenticationName = "手机运营商";
	 int companyId = 3;
	   String ifAuthentication =  authenticationInformationMapper.getoperifAuthentication(authenticationName,companyId);
	   if("1".equals(ifAuthentication)) {
		   String secondattributes = "运营商";
		   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
		   if("1".equals(status)) {		   
	 String shareOfState = intUserService.getshareOfState(userId);
	 if ("0".equals(shareOfState) || ("1".equals(shareOfState)) ||
	 ("3".equals(shareOfState))) {
	 int riskControlPoints = intUserService.getRiskControlPoints(userId);
	 int sourceId = intUserService.getsourceId(userId);
	 String sourceName = intSourceService.getsourceName(sourceId);
	 int manageControlId = intSourceService.getmanageControlId(sourceName);//风控id
	 Map<String, Object> map1 =
	 intManconsettingsServcie.getManconsettings(manageControlId);
	 String atrntlFractionalSegment = (String)
	 map1.get("atrntlFractionalSegment");
	 String roatnptFractionalSegment = (String)
	 map1.get("roatnptFractionalSegment");
	 String airappFractionalSegment = (String)
	 map1.get("airappFractionalSegment");
	 int roatnptFractionalSegmentSmall = Integer
	 .parseInt(roatnptFractionalSegment.substring(0,
	 roatnptFractionalSegment.indexOf("-")));
	 int roatnptFractionalSegmentBig =
	 Integer.parseInt(roatnptFractionalSegment
	 .substring(roatnptFractionalSegment.indexOf("-") + 1,
	 roatnptFractionalSegment.length()));
	
//	 if (riskControlPoints < roatnptFractionalSegmentSmall) {
//	 String orderNumber = intOrderService.getorderNumber(userId);
//	 if (orderNumber == null || !"3".equals(orderNumber)) {
//	 shareOfState = "0";
//	 intUserService.updateshareOfState(userId, shareOfState);
//	 }
//	 }
//	 if (riskControlPoints > roatnptFractionalSegmentSmall &&
//	 riskControlPoints < roatnptFractionalSegmentBig) {
//	 shareOfState = "1";
//	 intUserService.updateshareOfState(userId, shareOfState);
//	 }
//	 if (riskControlPoints > roatnptFractionalSegmentBig) {
//	 shareOfState = "2";
//	 intUserService.updateshareOfState(userId, shareOfState);
//	 }
	
	 }
	 
	 map.put("Ncode", "2000");
	
	 String userAttestation = null;
	 Map<String, Object> map3 =
	 userAttestationService.getuserAttestation(userId);
	
	 if (map3 == null) {
	 userAttestation = "0";
	 } else {
	 userAttestation = (String) map3.get("attestationStatus");
	 }
	
	 Map<String, Object> map2 = operatorService.getOperator(userId);
	 if (map2 == null) {
	 Operator = "0";
	 } else {
	 Operator = (String) map2.get("attestationStatus");
	 }
	
	 Map<String, Object> map4 = bankcardMapper.getbankcard(userId);
	 if (map4 == null) {
	 bankcard = "0";
	 } else {
	 bankcard = (String) map4.get("attestationStatus");
	 }
	 if ("1".equals(userAttestation) && "1".equals(Operator) &&
	 "1".equals(bankcard)) {
	 if("2".equals(shareOfState)||"4".equals(shareOfState)||"5".equals(shareOfState)||"0".equals(shareOfState))
	 {
	 String applyState = intUserService.getapplyState(userId);
	 if("2".equals(applyState)) {
	 String applynumber = intUserService.getapplynumber(userId);
	 if(applynumber==null) {
	 String timStamp = System.currentTimeMillis() + "";// 当前时间戳
	 applynumber = "SQ" + userId + timStamp;// 申请编号
	 intUserService.setuser(userId, timStamp, applynumber);
	 String state = "0";
	 applynumberMapper.setapplynumber(userId,timStamp,applynumber,state);
	 }else{
	 String timStamp = System.currentTimeMillis() + "";// 当前时间戳
	 applynumber = "SQ" + userId + timStamp;// 申请编号
	 intUserService.updateuser(userId, timStamp, applynumber);
	 applynumberMapper.updatestate(userId);
	 String state = "0";
	 applynumberMapper.setapplynumber(userId,timStamp,applynumber,state);
	 }
	 applyState = "1";
	 intUserService.updateapplyState(applyState, userId);
	 }
	 }
	 }
	 map.put("shareOfState", shareOfState);
		   }
		   if("2".equals(status)) {
				String shareOfState = intUserService.getshareOfState(userId);
				if ("0".equals(shareOfState) || ("1".equals(shareOfState))) {
					int riskControlPoints = intUserService.getRiskControlPoints(userId);
					int sourceId = intUserService.getsourceId(userId);
					String sourceName = intSourceService.getsourceName(sourceId);
					int manageControlId = intSourceService.getmanageControlId(sourceName);// 风控id
					Map<String, Object> map1 = intManconsettingsServcie.getManconsettings(manageControlId);
					String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
					String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
					String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
					int roatnptFractionalSegmentSmall = Integer
							.parseInt(roatnptFractionalSegment.substring(0, roatnptFractionalSegment.indexOf("-")));
					int roatnptFractionalSegmentBig = Integer.parseInt(roatnptFractionalSegment
							.substring(roatnptFractionalSegment.indexOf("-") + 1, roatnptFractionalSegment.length()));
		
					if (riskControlPoints < roatnptFractionalSegmentSmall) {
						String orderNumber = intOrderService.getorderNumber(userId);
						if (orderNumber == null || !"3".equals(orderNumber)) {
							shareOfState = "0";
							intUserService.updateshareOfState(userId, shareOfState);
						}
					}
					if (riskControlPoints > roatnptFractionalSegmentSmall && riskControlPoints < roatnptFractionalSegmentBig) {
						shareOfState = "1";
						intUserService.updateshareOfState(userId, shareOfState);
					}
					if (riskControlPoints > roatnptFractionalSegmentBig) {
						shareOfState = "2";
						intUserService.updateshareOfState(userId, shareOfState);
					}
		
				}
				map.put("Ncode", "2000");
		
				String userAttestation = null;
				Map<String, Object> map3 = userAttestationService.getuserAttestation(userId);
		
				if (map3 == null) {
					userAttestation = "0";
				} else {
					userAttestation = (String) map3.get("attestationStatus");
				}
		
				Map<String, Object> map2 = operatorService.getOperator(userId);
				if (map2 == null) {
					Operator = "0";
				} else {
					Operator = (String) map2.get("attestationStatus");
				}
		
				Map<String, Object> map4 = bankcardMapper.getbankcard(userId);
				if (map4 == null) {
					bankcard = "0";
				} else {
					bankcard = (String) map4.get("attestationStatus");
				}
				if ("1".equals(userAttestation) && "1".equals(bankcard)) {
//					if ("2".equals(shareOfState) || "4".equals(shareOfState) || "5".equals(shareOfState)||"0".equals(shareOfState)) {
						String applyState = intUserService.getapplyState(userId);
						if ("2".equals(applyState)) {
							String applynumber = intUserService.getapplynumber(userId);
							if (applynumber == null) {
								String timStamp = System.currentTimeMillis() + "";// 当前时间戳
								applynumber = "SQ" + userId + timStamp;// 申请编号
								intUserService.setuser(userId, timStamp, applynumber);
								String state = "0";
								applynumberMapper.setapplynumber(userId, timStamp, applynumber, state);
							} else {
								String timStamp = System.currentTimeMillis() + "";// 当前时间戳
								applynumber = "SQ" + userId + timStamp;// 申请编号
								intUserService.updateuser(userId, timStamp, applynumber);
								applynumberMapper.updatestate(userId);
								String state = "0";
								applynumberMapper.setapplynumber(userId, timStamp, applynumber, state);
							}
							applyState = "1";
							intUserService.updateapplyState(applyState, userId);
						}
//					}
				}
				map.put("shareOfState", shareOfState);
		   }
		   
	   }
	   
	   
	   if("2".equals(ifAuthentication)) {
			String shareOfState = intUserService.getshareOfState(userId);
			if ("0".equals(shareOfState) || ("1".equals(shareOfState))) {
				int riskControlPoints = intUserService.getRiskControlPoints(userId);
				int sourceId = intUserService.getsourceId(userId);
				String sourceName = intSourceService.getsourceName(sourceId);
				int manageControlId = intSourceService.getmanageControlId(sourceName);// 风控id
				Map<String, Object> map1 = intManconsettingsServcie.getManconsettings(manageControlId);
				String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
				String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
				String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
				int roatnptFractionalSegmentSmall = Integer
						.parseInt(roatnptFractionalSegment.substring(0, roatnptFractionalSegment.indexOf("-")));
				int roatnptFractionalSegmentBig = Integer.parseInt(roatnptFractionalSegment
						.substring(roatnptFractionalSegment.indexOf("-") + 1, roatnptFractionalSegment.length()));
	
				if (riskControlPoints < roatnptFractionalSegmentSmall) {
					String orderNumber = intOrderService.getorderNumber(userId);
					if (orderNumber == null || !"3".equals(orderNumber)) {
						shareOfState = "0";
						intUserService.updateshareOfState(userId, shareOfState);
					}
				}
				if (riskControlPoints > roatnptFractionalSegmentSmall && riskControlPoints < roatnptFractionalSegmentBig) {
					shareOfState = "1";
					intUserService.updateshareOfState(userId, shareOfState);
				}
				if (riskControlPoints > roatnptFractionalSegmentBig) {
					shareOfState = "2";
					intUserService.updateshareOfState(userId, shareOfState);
				}
	
			}
			map.put("Ncode", "2000");
	
			String userAttestation = null;
			Map<String, Object> map3 = userAttestationService.getuserAttestation(userId);
	
			if (map3 == null) {
				userAttestation = "0";
			} else {
				userAttestation = (String) map3.get("attestationStatus");
			}
	
			Map<String, Object> map2 = operatorService.getOperator(userId);
			if (map2 == null) {
				Operator = "0";
			} else {
				Operator = (String) map2.get("attestationStatus");
			}
	
			Map<String, Object> map4 = bankcardMapper.getbankcard(userId);
			if (map4 == null) {
				bankcard = "0";
			} else {
				bankcard = (String) map4.get("attestationStatus");
			}
			if ("1".equals(userAttestation) && "1".equals(bankcard)) {
//				if ("2".equals(shareOfState) || "4".equals(shareOfState) || "5".equals(shareOfState)||"0".equals(shareOfState)) {
					String applyState = intUserService.getapplyState(userId);
					if ("2".equals(applyState)) {
						String applynumber = intUserService.getapplynumber(userId);
						if (applynumber == null) {
							String timStamp = System.currentTimeMillis() + "";// 当前时间戳
							applynumber = "SQ" + userId + timStamp;// 申请编号
							intUserService.setuser(userId, timStamp, applynumber);
							String state = "0";
							applynumberMapper.setapplynumber(userId, timStamp, applynumber, state);
						} else {
							String timStamp = System.currentTimeMillis() + "";// 当前时间戳
							applynumber = "SQ" + userId + timStamp;// 申请编号
							intUserService.updateuser(userId, timStamp, applynumber);
							applynumberMapper.updatestate(userId);
							String state = "0";
							applynumberMapper.setapplynumber(userId, timStamp, applynumber, state);
						}
						applyState = "1";
						intUserService.updateapplyState(applyState, userId);
					}
//				}
			}
			map.put("shareOfState", shareOfState);
	   }
	 return map;
	
	 }

//	// 分控状态(运营商免认证)
//	@RequestMapping("/getshareOfState")
//	@ResponseBody
//	@Transactional
//	public Map<String, Object> getshareOfState(int userId) {
//		String Operator = null;
//		String bankcard = null;
//
//		String shareOfState = intUserService.getshareOfState(userId);
//		if ("0".equals(shareOfState) || ("1".equals(shareOfState))) {
//			int riskControlPoints = intUserService.getRiskControlPoints(userId);
//			int sourceId = intUserService.getsourceId(userId);
//			String sourceName = intSourceService.getsourceName(sourceId);
//			int manageControlId = intSourceService.getmanageControlId(sourceName);// 风控id
//			Map<String, Object> map1 = intManconsettingsServcie.getManconsettings(manageControlId);
//			String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
//			String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
//			String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
//			int roatnptFractionalSegmentSmall = Integer
//					.parseInt(roatnptFractionalSegment.substring(0, roatnptFractionalSegment.indexOf("-")));
//			int roatnptFractionalSegmentBig = Integer.parseInt(roatnptFractionalSegment
//					.substring(roatnptFractionalSegment.indexOf("-") + 1, roatnptFractionalSegment.length()));
//
//			if (riskControlPoints < roatnptFractionalSegmentSmall) {
//				String orderNumber = intOrderService.getorderNumber(userId);
//				if (orderNumber == null || !"3".equals(orderNumber)) {
//					shareOfState = "0";
//					intUserService.updateshareOfState(userId, shareOfState);
//				}
//			}
//			if (riskControlPoints > roatnptFractionalSegmentSmall && riskControlPoints < roatnptFractionalSegmentBig) {
//				shareOfState = "1";
//				intUserService.updateshareOfState(userId, shareOfState);
//			}
//			if (riskControlPoints > roatnptFractionalSegmentBig) {
//				shareOfState = "2";
//				intUserService.updateshareOfState(userId, shareOfState);
//			}
//
//		}
//		Map<String, Object> map = new HashMap<>();
//		map.put("Ncode", "2000");
//
//		String userAttestation = null;
//		Map<String, Object> map3 = userAttestationService.getuserAttestation(userId);
//
//		if (map3 == null) {
//			userAttestation = "0";
//		} else {
//			userAttestation = (String) map3.get("attestationStatus");
//		}
//
//		Map<String, Object> map2 = operatorService.getOperator(userId);
//		if (map2 == null) {
//			Operator = "0";
//		} else {
//			Operator = (String) map2.get("attestationStatus");
//		}
//
//		Map<String, Object> map4 = bankcardMapper.getbankcard(userId);
//		if (map4 == null) {
//			bankcard = "0";
//		} else {
//			bankcard = (String) map4.get("attestationStatus");
//		}
//		if ("1".equals(userAttestation) && "1".equals(bankcard)) {
////			if ("2".equals(shareOfState) || "4".equals(shareOfState) || "5".equals(shareOfState)||"0".equals(shareOfState)) {
//				String applyState = intUserService.getapplyState(userId);
//				if ("2".equals(applyState)) {
//					String applynumber = intUserService.getapplynumber(userId);
//					if (applynumber == null) {
//						String timStamp = System.currentTimeMillis() + "";// 当前时间戳
//						applynumber = "SQ" + userId + timStamp;// 申请编号
//						intUserService.setuser(userId, timStamp, applynumber);
//						String state = "0";
//						applynumberMapper.setapplynumber(userId, timStamp, applynumber, state);
//					} else {
//						String timStamp = System.currentTimeMillis() + "";// 当前时间戳
//						applynumber = "SQ" + userId + timStamp;// 申请编号
//						intUserService.updateuser(userId, timStamp, applynumber);
//						applynumberMapper.updatestate(userId);
//						String state = "0";
//						applynumberMapper.setapplynumber(userId, timStamp, applynumber, state);
//					}
//					applyState = "1";
//					intUserService.updateapplyState(applyState, userId);
//				}
////			}
//		}
//		map.put("shareOfState", shareOfState);
//		return map;
//
//	}

	// 做三要素认证
	// @RequestMapping("/threeElements")
	// @ResponseBody
	// @Transactional
	// public Map<String, Object> getthreeElements(int userId, String phone, int
	// companyId)
	// throws UnsupportedEncodingException {
	// Map<String, Object> map1 = new HashMap<>();
	// String code1 = threeElementsMapper.getcode(userId, phone);
	// if ("0".equals(code1)) {
	// map1.put("Ncode", "2000");
	// map1.put("code", "200");
	// map1.put("msg", "认证一致");
	// return map1;
	// }
	//
	// String ifBlacklist = intUserService.getifBlacklist2(userId);
	// if ("1".equals(ifBlacklist)) {
	// map1.put("Ncode", "402");
	// map1.put("code", "402");
	// map1.put("msg", "黑名单用户");
	// map1.put("prompt", "您暂时不符合我们的要求");
	// return map1;
	// }
	//
	// Map<String, Object> map =
	// userAttestationService.getuserAttestation(userId);
	// String trueName = (String) map.get("trueName");
	// String idcard_number = (String) map.get("idcard_number");
	// OperatorAction operatorAction = new OperatorAction();
	// Map<String, Object> map2 = operatorAction.certification(idcard_number,
	// trueName, phone);
	// String result = (String) map2.get("result");
	// String trans_id = (String) map2.get("trans_id");
	// JSONObject jsonObject = null;
	// jsonObject = JSONObject.parseObject(result);
	// jsonObject = jsonObject.getJSONObject("data");
	// String code = jsonObject.getString("code");
	// if ("0".equals(code)) {
	// int certification_number = 0;
	// int num = threeElementsMapper.getnum(userId, phone);
	// if (num == 0) {
	// threeElementsMapper.setThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// }
	// if (num > 0) {
	// threeElementsMapper.updateThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// }
	// String thirdtypeid = "4";
	// String date = System.currentTimeMillis() + "";
	// thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date);
	// map1.put("Ncode", "2000");
	// map1.put("code", "200");
	// map1.put("msg", "认证一致");
	//
	// }
	// if ("1".equals(code)) {
	// int num = threeElementsMapper.getnum(userId, phone);
	// if (num == 0) {
	// int certification_number = 1;
	// threeElementsMapper.setThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// }
	// if (num > 0) {
	// int certification_number =
	// threeElementsMapper.getCertificationnumber(userId, phone);
	// certification_number = certification_number + 1;
	// threeElementsMapper.updateThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// if (certification_number > 2) {
	// intUserService.updateifBlacklist(userId);
	// String date = System.currentTimeMillis() + "";
	// int num1 = intBlacklistuserService.getid(phone, companyId);// 判断手机号是否是黑名单
	// if (num1 == 0) {
	// String blackType = "5";
	// intBlacklistuserService.setBlacklistuser(idcard_number, userId,
	// companyId, phone, trueName,
	// date, blackType);
	// }
	//
	// }
	// }
	// String thirdtypeid = "4";
	// String date = System.currentTimeMillis() + "";
	// thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date);
	// map1.put("code", "405");
	// map1.put("msg", "认证不一致");
	// map1.put("prompt", "请使用本人手机号认证");
	// }
	// if ("2".equals(code)) {
	// int certification_number = 0;
	// int num = threeElementsMapper.getnum(userId, phone);
	// if (num == 0) {
	// threeElementsMapper.setThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// }
	// if (num > 0) {
	// certification_number = threeElementsMapper.getCertificationnumber(userId,
	// phone);
	// threeElementsMapper.updateThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// }
	// map1.put("Ncode", "407");
	// map1.put("code", "407");
	// map1.put("msg", "认证信息不存在");
	// map1.put("prompt", "认证信息不存在，请重新认证");
	// }
	// if ("9".equals(code)) {
	// int certification_number = 0;
	// int num = threeElementsMapper.getnum(userId, phone);
	// if (num == 0) {
	// threeElementsMapper.setThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// }
	// if (num > 0) {
	// certification_number = threeElementsMapper.getCertificationnumber(userId,
	// phone);
	// threeElementsMapper.updateThreeElements(userId, code, trans_id,
	// certification_number, phone);
	// }
	// map1.put("Ncode", "409");
	// map1.put("code", "409");
	// map1.put("msg", "其他异常");
	// map1.put("prompt", "未知错误，请联系客服");
	// }
	//
	// return map1;
	//
	// }

	 
	 
	 //新颜三要素
//	@RequestMapping("/threeElements")
//	@ResponseBody
//	@Transactional
//	public Map<String, Object> getthreeElements(int userId, String phone, int companyId)
//			throws UnsupportedEncodingException {
//		Map<String, Object> map1 = new HashMap<>();
//		
//		 String authenticationName = "手机运营商";
//		   String ifAuthentication =  authenticationInformationMapper.getoperifAuthentication(authenticationName,companyId);
//		   if("1".equals(ifAuthentication)) {
//			   String secondattributes = "三要素";
//			   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
//			   if("1".equals(status)) {
//				   String code1 = threeElementsMapper.getcode(userId, phone);
//					 if ("0".equals(code1)) {
//					map1.put("Ncode", "2000");
//					map1.put("code", "200");
//					map1.put("msg", "认证一致");
//					return map1;
//					 }
//					
//					 String ifBlacklist = intUserService.getifBlacklist2(userId);
//					 if ("1".equals(ifBlacklist)) {
//					 map1.put("Ncode", "402");
//					 map1.put("code", "402");
//					 map1.put("msg", "黑名单用户");
//					 map1.put("prompt", "您暂时不符合我们的要求");
//					 return map1;
//					 }
//					
//					 Map<String, Object> map =
//					 userAttestationService.getuserAttestation(userId);
//					 String trueName = (String) map.get("trueName");
//					 String idcard_number = (String) map.get("idcard_number");
//					 OperatorAction operatorAction = new OperatorAction();
//					 Map<String, Object> map2 =
//					 operatorAction.certification(idcard_number, trueName, phone);
//					 String result = (String) map2.get("result");
//					 String trans_id = (String) map2.get("trans_id");
//					 JSONObject jsonObject = null;
//					 jsonObject = JSONObject.parseObject(result);
//					 jsonObject = jsonObject.getJSONObject("data");
//					 String code = jsonObject.getString("code");
//					 if ("0".equals(code)) {
//					 int certification_number = 0;
//					 int num = threeElementsMapper.getnum(userId, phone);
//					 if (num == 0) {
//					 threeElementsMapper.setThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 }
//					 if (num > 0) {
//					 threeElementsMapper.updateThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 }
//					 String thirdtypeid = "4";
//					 String date = System.currentTimeMillis() + "";
//					 thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid,
//					 date);
//					 map1.put("Ncode", "2000");
//					 map1.put("code", "200");
//					 map1.put("msg", "认证一致");
//					
//					 }
//					 if ("1".equals(code)) {
//					 int num = threeElementsMapper.getnum(userId, phone);
//					 if (num == 0) {
//					 int certification_number = 1;
//					 threeElementsMapper.setThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 }
//					 if (num > 0) {
//					 int certification_number =
//					 threeElementsMapper.getCertificationnumber(userId, phone);
//					 certification_number = certification_number + 1;
//					 threeElementsMapper.updateThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 if (certification_number > 2) {
//					 intUserService.updateifBlacklist(userId);
//					 String date = System.currentTimeMillis() + "";
//					 int num1 = intBlacklistuserService.getid(phone, companyId);// 判断手机号是否是黑名单
//					 if (num1 == 0) {
//					 String blackType = "5";
//					 intBlacklistuserService.setBlacklistuser(idcard_number, userId,
//					 companyId, phone, trueName,
//					 date, blackType);
//					 }
//					
//					 }
//					 }
//					 String thirdtypeid = "4";
//					 String date = System.currentTimeMillis() + "";
//					 thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid,
//					 date);
//					 map1.put("code", "405");
//					 map1.put("msg", "认证不一致");
//					 map1.put("prompt", "请使用本人手机号认证");
//					 }
//					 if ("2".equals(code)) {
//					 int certification_number = 0;
//					 int num = threeElementsMapper.getnum(userId, phone);
//					 if (num == 0) {
//					 threeElementsMapper.setThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 }
//					 if (num > 0) {
//					 certification_number =
//					 threeElementsMapper.getCertificationnumber(userId, phone);
//					 threeElementsMapper.updateThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 }
//					 map1.put("Ncode", "407");
//					 map1.put("code", "407");
//					 map1.put("msg", "认证信息不存在");
//					 map1.put("prompt", "认证信息不存在，请重新认证");
//					 }
//					 if ("9".equals(code)) {
//					 int certification_number = 0;
//					 int num = threeElementsMapper.getnum(userId, phone);
//					 if (num == 0) {
//					 threeElementsMapper.setThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 }
//					 if (num > 0) {
//					 certification_number =
//					 threeElementsMapper.getCertificationnumber(userId, phone);
//					 threeElementsMapper.updateThreeElements(userId, code, trans_id,
//					 certification_number, phone);
//					 }
//					 map1.put("Ncode", "409");
//					 map1.put("code", "409");
//					 map1.put("msg", "其他异常");
//					 map1.put("prompt", "未知错误，请联系客服");
//					 }
//			   }
//			   if("2".equals(status)) {
//					map1.put("Ncode", "2000");
//					map1.put("code", "200");
//					map1.put("msg", "认证一致");
//					return map1;   
//			   }
//			   
//		   }else {
//				map1.put("Ncode", "2000");
//				map1.put("code", "200");
//				map1.put("msg", "认证一致");
//				return map1; 
//		   }
//		
//		
//		
//		 return map1;
//
//	}
	 
	 
	 //阿里三要素
		@RequestMapping("/threeElements")
		@ResponseBody
		@Transactional
		public Map<String, Object> getthreeElements(int userId, String phone, int companyId)
				throws UnsupportedEncodingException {
			Map<String, Object> map1 = new HashMap<>();
			
			 String authenticationName = "手机运营商";
			   String ifAuthentication =  authenticationInformationMapper.getoperifAuthentication(authenticationName,companyId);
			   if("1".equals(ifAuthentication)) {
				   String secondattributes = "三要素";
				   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
				   if("1".equals(status)) {
					   String code1 = threeElementsMapper.getcode(userId, phone);
						 if ("0".equals(code1)) {
						map1.put("Ncode", "2000");
						map1.put("code", "200");
						map1.put("msg", "认证一致");
						return map1;
						 }
						
						 String ifBlacklist = intUserService.getifBlacklist2(userId);
						 if ("1".equals(ifBlacklist)) {
						 map1.put("Ncode", "402");
						 map1.put("code", "402");
						 map1.put("msg", "黑名单用户");
						 map1.put("prompt", "您暂时不符合我们的要求");
						 return map1;
						 }
						
						 Map<String, Object> map =
						 userAttestationService.getuserAttestation(userId);
						 String trueName = (String) map.get("trueName");
						 String idcard_number = (String) map.get("idcard_number");
						 Cellphone cellphone = new Cellphone();
						 String result =
						 cellphone.getCellphone(idcard_number, trueName, phone);
						 JSONObject jsonObject = null;
						 jsonObject = JSONObject.parseObject(result);						 
						 String error_code = jsonObject.getString("error_code");
						 JSONObject jsonObject1 = jsonObject.getJSONObject("result");
						 String VerificationResult = jsonObject1.getString("VerificationResult");								
						 if ("1".equals(VerificationResult)) {
						 int certification_number = 0;
						 int num = threeElementsMapper.getnum(userId, phone);
						 if (num == 0) {
						 threeElementsMapper.setThreeElements1(userId, VerificationResult,
						 certification_number, phone);
						 }
						 if (num > 0) {
						 threeElementsMapper.updateThreeElements1(userId, VerificationResult,
						 certification_number, phone);
						 }
						 String thirdtypeid = "4";
						 String date = System.currentTimeMillis() + "";
						 thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid,
						 date);
						 map1.put("Ncode", "2000");
						 map1.put("code", "200");
						 map1.put("msg", "认证一致");
						
						 }
						 if ("-1".equals(VerificationResult)) {
						 int num = threeElementsMapper.getnum(userId, phone);
						 if (num == 0) {
						 int certification_number = 1;
						 threeElementsMapper.setThreeElements1(userId, VerificationResult,
						 certification_number, phone);
						 }
						 if (num > 0) {
						 int certification_number =
						 threeElementsMapper.getCertificationnumber(userId, phone);
						 certification_number = certification_number + 1;
						 threeElementsMapper.updateThreeElements1(userId, VerificationResult,
						 certification_number, phone);
						 if (certification_number > 2) {
						 intUserService.updateifBlacklist(userId);
						 String date = System.currentTimeMillis() + "";
						 int num1 = intBlacklistuserService.getid(phone, companyId);// 判断手机号是否是黑名单
						 if (num1 == 0) {
						 String blackType = "5";
						 intBlacklistuserService.setBlacklistuser(idcard_number, userId,
						 companyId, phone, trueName,
						 date, blackType);
						 }
						
						 }
						 }
						 String thirdtypeid = "4";
						 String date = System.currentTimeMillis() + "";
						 thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid,
						 date);
						 map1.put("code", "405");
						 map1.put("msg", "认证不一致");
						 map1.put("prompt", "请使用本人手机号认证");
						 }
						 if ("2".equals(VerificationResult)) {
						 int certification_number = 0;
						 int num = threeElementsMapper.getnum(userId, phone);
						 if (num == 0) {
						 threeElementsMapper.setThreeElements1(userId, VerificationResult,
						 certification_number, phone);
						 }
						 if (num > 0) {
						 certification_number =
						 threeElementsMapper.getCertificationnumber(userId, phone);
						 threeElementsMapper.updateThreeElements1(userId, VerificationResult,
						 certification_number, phone);
						 }
						 map1.put("Ncode", "407");
						 map1.put("code", "407");
						 map1.put("msg", "认证信息不存在");
						 map1.put("prompt", "认证信息不存在，请重新认证");
						 }						 
				   }
				   if("2".equals(status)) {
						map1.put("Ncode", "2000");
						map1.put("code", "200");
						map1.put("msg", "认证一致");
						return map1;   
				   }
				   
			   }else {
					map1.put("Ncode", "2000");
					map1.put("code", "200");
					map1.put("msg", "认证一致");
					return map1; 
			   }
			
			
			
			 return map1;
	
		}	 
	 

	// 魔杖接口
	@RequestMapping("/setmagicwand3")
	@ResponseBody
	@Transactional
	public Map<String, Object> setmagicwand3(String mobile, int user_id, String name, String idcard) throws Exception {
		Map<String, Object> map = new HashMap<>();
		Magicwand3Demo mDemo = new Magicwand3Demo();
		String resContent = mDemo.enhance(mobile, user_id + "", name, idcard);
		JSONObject jsonObject = null;
		jsonObject = JSONObject.parseObject(resContent);

		String code = jsonObject.getString("code");
		if ("0000".equals(code)) {

		}

		return map;
	}

	// 判断是否是白名单用户，如果是，风控直接通过
	// @RequestMapping("/getwhitelistuser")
	// @ResponseBody
	// @Transactional
	// public Map<String, Object> getwhitelistuser(String phone, int userId,
	// String name, String idcard_number) {
	// Map<String, Object> map = new HashMap<>();
	// String attestationStatus = "1";
	// int num = intWhitelistuserService.getWhitelistuser1(phone, idcard_number,
	// name);
	// if (num > 0) {
	// int num1 = operatorService.getuserId(userId);
	// if (num1 == 0) {
	// String authentime = System.currentTimeMillis() + "";// 认证时间
	// int number = operatorService.setwhitelistuser(attestationStatus, userId,
	// authentime);
	// if (number == 1) {
	// String shareOfState = "2";
	// intUserService.updateshareOfState(userId, shareOfState);
	// map.put("Ncode", "2000");
	// map.put("msg", "数据插入成功");
	// map.put("Code", "201");
	// } else {
	// map.put("Ncode", "405");
	// map.put("msg", "数据插入失败");
	// map.put("Code", "405");
	// }
	// }
	//
	// } else {
	// map.put("Ncode", "2000");
	// map.put("msg", "用户不是白名单");
	// map.put("Code", "202");
	// }
	//
	// return map;
	// }

	// 分控分数
	@RequestMapping("/getScore")
	@ResponseBody
	@Transactional
	public Map<String, Object> getScore(int userId) throws UnsupportedEncodingException {

		String shareOfState = null;
		int score = 0;
		Map<String, Object> map = new HashMap<>();
		map.put("Ncode", "2000");
		// shareOfState ="6";
		// intUserService.updateshareOfState(userId, shareOfState);

		int companyId = 3;
		ArrayList<String> list = intAutheninforService.getifAuthentication(companyId);
		String operatorAutheninfor = list.get(2);
		if ("2".equals(operatorAutheninfor)) {
			int sourceId = intUserService.getsourceId(userId);
			String sourceName = intSourceService.getsourceName(sourceId);
			int manageControlId = intSourceService.getmanageControlId(sourceName);// 风控id
			Map<String, Object> map1 = intManconsettingsServcie.getManconsettings(manageControlId);
			String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
			String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
			String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
			int roatnptFractionalSegmentSmall = Integer
					.parseInt(roatnptFractionalSegment.substring(0, roatnptFractionalSegment.indexOf("-")));
			int riskControlPoints = roatnptFractionalSegmentSmall + 1;
			intUserService.setRiskControlPoints(userId, riskControlPoints);
			shareOfState = "1";
			intUserService.updateScore1(userId, shareOfState);
			map.put("code", 200);
			return map;
		}
		
		String authenticationName = "手机运营商";
		   String ifAuthentication =  authenticationInformationMapper.getoperifAuthentication(authenticationName,companyId);
		   if("1".equals(ifAuthentication)) {
			   String secondattributes = "运营商";
			   String status =  authenticationInformationMapper.secondattributes(secondattributes);  
			   if("2".equals(status)) {
					int sourceId = intUserService.getsourceId(userId);
					String sourceName = intSourceService.getsourceName(sourceId);
					int manageControlId = intSourceService.getmanageControlId(sourceName);// 风控id
					Map<String, Object> map1 = intManconsettingsServcie.getManconsettings(manageControlId);
					String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
					String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
					String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
					int roatnptFractionalSegmentSmall = Integer
							.parseInt(roatnptFractionalSegment.substring(0, roatnptFractionalSegment.indexOf("-")));
					int riskControlPoints = roatnptFractionalSegmentSmall + 1;
					intUserService.setRiskControlPoints(userId, riskControlPoints);
					shareOfState = "1";
					intUserService.updateScore1(userId, shareOfState);
					map.put("code", 200);
					return map;
			   }
			   }

		Map<String, Object> userAttestation = userAttestationService.getuserAttestation(userId);
		String name = (String) userAttestation.get("trueName");
		name = URLEncoder.encode(name,"utf-8");
		String idNumber = (String) userAttestation.get("idcard_number");
		int sourceId = intUserService.getsourceId(userId);
		String sourceName = intSourceService.getsourceName(sourceId);
		String phone1 = operatorService.getphone(userId);// 运营商认证是输入的手机号
		String phone2 = intUserService.getphone(userId);// 用户登录进来的手机号

		PhoneDeal pDeal = new PhoneDeal();
		String newphone = pDeal.decryption(phone1);
		String newphone2 = pDeal.decryption(phone2);
		Map<String, Object> map3 = userAttestationService.getuserAttestation(userId);
		String idcard_number = (String) map3.get("idcard_number");
		String address = (String) map3.get("address");
		int number = intWhitelistuserService.getWhitelistuser(newphone2, idcard_number);
		if (number != 0) {
			shareOfState = "2";
			intUserService.updateScore1(userId, shareOfState);
			map.put("code", 200);

			return map;
		}

		

		int manageControlId = intSourceService.getmanageControlId(sourceName);// 风控id
		Map<String, Object> map1 = intManconsettingsServcie.getManconsettings(manageControlId);
		String rmModleName = (String) map1.get("rmModleName");
		
		//自己的运营商
//		if ("风控甲".equals(rmModleName)) {
		PostAndGet pGet = new PostAndGet();
		PhoneDeal phoneDeal = new PhoneDeal();
		String phone = intUserService.getphone(userId);
		String newphone1 = phoneDeal.decryption(phone);
		Map<String,Object> map2  = pGet.sendGet3("http://fk.rong51dai.com/zhita_heitong_Fengkong/fraction/Exhibitionfraction?userId="+userId+"&phone="+newphone1+"&name="+name+"&idNumber="+idNumber);
		score =(int) map2.get("count");
		String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
		String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
		String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
		int roatnptFractionalSegmentSmall = Integer
				.parseInt(roatnptFractionalSegment.substring(0, roatnptFractionalSegment.indexOf("-")));
		int roatnptFractionalSegmentBig = Integer.parseInt(roatnptFractionalSegment
				.substring(roatnptFractionalSegment.indexOf("-") + 1, roatnptFractionalSegment.length()));

		if (score < roatnptFractionalSegmentSmall) {
			shareOfState = "0";
			map.put("code", 200);
			map.put("msg", "分数不够");
		}
		if (score > roatnptFractionalSegmentSmall && score < roatnptFractionalSegmentBig) {
			shareOfState = "1";
			map.put("code", 200);
			map.put("msg", "需要人工审核");
		}
		if (score > roatnptFractionalSegmentBig) {
			shareOfState = "2";
			map.put("code", 200);
			map.put("msg", "分数够了");
		}

		String thirdtypeid = "6";
		String date = System.currentTimeMillis() + "";
		thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date);
		intUserService.updateScore(score, userId, shareOfState);
		map.put("score", score);
		return map;
		


//		String rString = pGet.sendGet("http://fk.rong51dai.com/zhita_heitong_Fengkong/Riskmanage/Risk_ReturnCode?phone=" + newphone1);
//		intUserService.setModel(userId, rString);
//		JSONObject object = JSONObject.parseObject(rString);
//		String phonetype = object.getString("phonetype");
//		String uuidtype = object.getString("uuidtype");
//		String wifitype = object.getString("wifitype");
//		String daytype = object.getString("daytype");
//		String wifimactype = object.getString("wifimactype");
//		String maillistype = object.getString("maillistype");
//		String apptype = object.getString("apptype");
//		System.out.println(phonetype);
//		System.out.println(uuidtype);
//		System.out.println(wifitype);
//		System.out.println(daytype);
//		System.out.println(wifimactype);
//		System.out.println(maillistype);
//		System.out.println(apptype);
//		String thirdtypeid = "6";
//		String date = System.currentTimeMillis() + "";
//		thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date); //风控统计
//		
//		if ("1".equals(phonetype) || "1".equals(uuidtype) || "1".equals(wifitype) || "1".equals(daytype)
//				|| "1".equals(wifimactype) || "1".equals(maillistype) || "1".equals(apptype)) {
//			intUserService.updateshareOfState(userId, shareOfState);
//			map.put("Ncode", "2000");
//			map.put("code", "407");
//			map.put("msg", "其他条件不符合");
//			shareOfState = "0";
//			intUserService.updateScore1(userId, shareOfState);
//			return map;
//		}else {
//			shareOfState = "1";
//			intUserService.updateScore1(userId, shareOfState);
//			map.put("code", 200);
//			return map;
//		}

		
//		if ("风控甲".equals(rmModleName)) {
//
//			Map<String, Object> operator = operatorService.getOperator(userId);
//			String phone = (String) operator.get("phone");
//			String reqId = (String) operator.get("reqId");
//			String search_id = (String) operator.get("search_id");
//
//			ScoreDemo scoreDemo = new ScoreDemo();
//			String result = scoreDemo.getScore(search_id, phone, name, idNumber, reqId);
//			System.out.println("result-----------------------------" + result);
//			JSONObject jsonObject = null;
//			jsonObject = JSONObject.parseObject(result);
//			String tianji_api_tianjiscore_pdscorev5_response = jsonObject
//					.get("tianji_api_tianjiscore_pdscorev5_response").toString();
//			jsonObject = JSONObject.parseObject(tianji_api_tianjiscore_pdscorev5_response);
//
//			System.out.println(
//					"search_id" + search_id + "phone" + phone + "name" + name + "idNumber" + idNumber + "reqId" + reqId
//							+ "tianji_api_tianjiscore_pdscorev5_response" + tianji_api_tianjiscore_pdscorev5_response);
//			score = Integer.parseInt(jsonObject.get("score").toString());
//			String thirdtypeid = "6";
//			String date = System.currentTimeMillis() + "";
//			thirdcalltongjiMapper.setthirdcalltongji(companyId, thirdtypeid, date);
//		}
		/*
		 * if ("风控乙".equals(rmModleName)) { System.out.println(
		 * "rmModleName------------------------------------------" + 1111111);
		 * String fileContent = operatorService.getoperatorJson(userId);
		 * Map<String, Object> map2 =
		 * userAttestationService.getuserAttestation(userId); String
		 * linkmanOneName = (String) map2.get("linkmanOneName"); String
		 * linkmanOnePhone = (String) map2.get("linkmanOnePhone"); String
		 * linkmanTwoName = (String) map2.get("linkmanTwoName"); String
		 * linkmanTwoPhone = (String) map2.get("linkmanTwoPhone"); ZhimiRiskDemo
		 * zDemo = new ZhimiRiskDemo(); String responseStr =
		 * zDemo.getzhimi(fileContent, linkmanOneName, linkmanOnePhone,
		 * linkmanTwoName, linkmanTwoPhone, address, newphone, idcard_number,
		 * name); JSONObject jsonObject = null; jsonObject =
		 * JSONObject.parseObject(responseStr); score = new
		 * Double(jsonObject.getDouble("score")).intValue(); String request_id =
		 * jsonObject.getString("request_id"); String history_apply =
		 * jsonObject.getString("history_apply"); jsonObject =
		 * JSONObject.parseObject(history_apply); int mobile_1h_cnt =
		 * jsonObject.getInteger("mobile_1h_cnt"); int mobile_3h_cnt =
		 * jsonObject.getInteger("mobile_3h_cnt"); int mobile_12h_cnt =
		 * jsonObject.getInteger("mobile_12h_cnt"); int mobile_1d_cnt =
		 * jsonObject.getInteger("mobile_1d_cnt"); int mobile_3d_cnt =
		 * jsonObject.getInteger("mobile_3d_cnt"); int mobile_7d_cnt =
		 * jsonObject.getInteger("mobile_7d_cnt"); int mobile_14d_cnt =
		 * jsonObject.getInteger("mobile_14d_cnt"); int mobile_30d_cnt =
		 * jsonObject.getInteger("mobile_30d_cnt"); int mobile_60d_cnt =
		 * jsonObject.getInteger("mobile_60d_cnt"); int idcard_1h_cnt =
		 * jsonObject.getInteger("idcard_1h_cnt"); int idcard_3h_cnt =
		 * jsonObject.getInteger("idcard_3h_cnt"); int idcard_12h_cnt =
		 * jsonObject.getInteger("idcard_12h_cnt"); int idcard_1d_cnt =
		 * jsonObject.getInteger("idcard_1d_cnt"); int idcard_3d_cnt =
		 * jsonObject.getInteger("idcard_3d_cnt"); int idcard_7d_cnt =
		 * jsonObject.getInteger("idcard_7d_cnt"); int idcard_14d_cnt =
		 * jsonObject.getInteger("idcard_14d_cnt"); int idcard_30d_cnt =
		 * jsonObject.getInteger("idcard_30d_cnt"); int idcard_60d_cnt =
		 * jsonObject.getInteger("idcard_60d_cnt");
		 * zhimiRiskMapper.setzhimiRisk(userId, request_id, mobile_1h_cnt,
		 * mobile_3h_cnt, mobile_12h_cnt, mobile_1d_cnt, mobile_3d_cnt,
		 * mobile_7d_cnt, mobile_14d_cnt, mobile_30d_cnt, mobile_60d_cnt,
		 * idcard_1h_cnt, idcard_3h_cnt, idcard_12h_cnt, idcard_1d_cnt,
		 * idcard_3d_cnt, idcard_7d_cnt, idcard_14d_cnt, idcard_30d_cnt,
		 * idcard_60d_cnt); }
		 */

//		String atrntlFractionalSegment = (String) map1.get("atrntlFractionalSegment");
//		String roatnptFractionalSegment = (String) map1.get("roatnptFractionalSegment");
//		String airappFractionalSegment = (String) map1.get("airappFractionalSegment");
//		int roatnptFractionalSegmentSmall = Integer
//				.parseInt(roatnptFractionalSegment.substring(0, roatnptFractionalSegment.indexOf("-")));
//		int roatnptFractionalSegmentBig = Integer.parseInt(roatnptFractionalSegment
//				.substring(roatnptFractionalSegment.indexOf("-") + 1, roatnptFractionalSegment.length()));
//		System.out.println("roatnptFractionalSegmentSmall--------------------------------------------------"
//				+ roatnptFractionalSegmentSmall);
//		System.out.println(
//				"roatnptFractionalSegmentBig------------------------------------------" + roatnptFractionalSegmentBig);
//		System.out.println("score------------------------------------------" + score);
//		if (score < roatnptFractionalSegmentSmall) {
//			shareOfState = "0";
//			map.put("code", 200);
//			map.put("msg", "分数不够");
//		}
//		if (score > roatnptFractionalSegmentSmall && score < roatnptFractionalSegmentBig) {
//			shareOfState = "1";
//			map.put("code", 200);
//			map.put("msg", "需要人工审核");
//		}
//		if (score > roatnptFractionalSegmentBig) {
//			shareOfState = "2";
//			map.put("code", 200);
//			map.put("msg", "分数够了");
//		}
//
//		String registeTime = intUserService.getregisteTime(userId);
//
//
//		intUserService.updateScore(score, userId, shareOfState);
//		map.put("score", score);
//
//		return map;

	}

	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) { // 出生日期晚于当前时间，无法计算
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR); // 当前年份
		int monthNow = cal.get(Calendar.MONTH); // 当前月份
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); // 当前日期
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth; // 计算整岁数
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;// 当前日期在生日之前，年龄减一
			} else {
				age--;// 当前月份在生日之前，年龄减一

			}
		}
		return age;
	}

}
