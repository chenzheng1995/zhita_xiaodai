package com.zhita.controller.certificationcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.ast.SQLPartitionValue.Operator;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.zhita.dao.manage.BankcardMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.Bankcard;
import com.zhita.service.manage.autheninfor.IntAutheninforService;
import com.zhita.service.manage.operator.OperatorService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;

import sun.text.normalizer.ICUBinary.Authenticate;

@Controller
@RequestMapping(value="/CertificationCenter")
public class CertificationCenterController {
	
	@Autowired
	IntAutheninforService IntAutheninforService;
	
	@Autowired
	UserAttestationService userAttestationService;
	
	@Autowired
	OperatorService operatorService;
	
	@Autowired
	IntUserService intUserService;
	
	@Autowired
	BankcardMapper bankcardMapper;
	
	
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
		   
		   ArrayList<String> list = IntAutheninforService.getifAuthentication(companyId);
		   String userAttestationAutheninfor = list.get(0);
		   String operatorAutheninfor = list.get(1);
		   String bankcardAutheninfor = list.get(2);
		   String zhimaAutheninfor = list.get(3);
		   
		   
		   
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
             map3.put("userAttestation", userAttestation);
             map3.put("Operator", Operator);
             map3.put("bankcard", bankcard);
             map3.put("zhima", zhima);
             map3.put("userAttestationAutheninfor", userAttestationAutheninfor);
             map3.put("operatorAutheninfor", operatorAutheninfor);
             map3.put("bankcardAutheninfor", bankcardAutheninfor);
             map3.put("zhimaAutheninfor", zhimaAutheninfor);
             

		   
			return map3;
		   
	   }
	   
	   
	   //返回用户是否可以借钱的状态
	   @RequestMapping("/isBorrow")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> isBorrow(int userId,int companyId){
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
			   if(userAttestation.equals("1")&&Operator.equals("1")&&bankcard.equals("1")&&("2".equals(shareOfState)||"4".equals(shareOfState))) {
			   map1.put("code","200");
			   map1.put("msg", "满足条件");
		   }else {
			   map1.put("code","400");
			   map1.put("msg", "不满足条件");
		}
		   
			return map1;
		   
	   }
}
