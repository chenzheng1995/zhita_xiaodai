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
import com.zhita.model.manage.AuthenticationInformation;
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
	
	
//	认证中心的数据
	   @RequestMapping("/getCertificationCenter")
	    @ResponseBody
	    @Transactional
		public Map<String, Object> getCertificationCenter(int userId,int companyId){
			Map<String, Object> map1 = new HashMap<String, Object>();
		   ArrayList<AuthenticationInformation> CertificationCenter = IntAutheninforService.getCertificationCenter(companyId);
		   Map<String, Object> map = userAttestationService.getuserAttestation(userId);
		   String attestationStatus = (String) map.get("attestationStatus");
		   
		   
		   Map<String, Object> map2 = operatorService.getOperator(userId);
		   String attestationStatus1 = (String) map2.get("attestationStatus");
		   
		   
for (AuthenticationInformation authenticationInformation : CertificationCenter) {
	   int id = authenticationInformation.getId();
	   if(id==1) {
		   authenticationInformation.setCertificationstatus(attestationStatus);
		   map1.put("userAttestation", authenticationInformation);
	   }
	   
	   if(id==3) {
		   authenticationInformation.setCertificationstatus(attestationStatus1);
		   map1.put("Operator", authenticationInformation);
	   }
}


		   
			return map1;
		   
	   }
	   
	   
	   //返回用户是否可以借钱的状态
	   @RequestMapping("/isBorrow")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> isBorrow(int userId,int companyId){
		   Map<String, Object> map = userAttestationService.getuserAttestation(userId);
		   Map<String, Object> map1 = new HashMap<String, Object>();
		   String attestationStatus = (String) map.get("attestationStatus");
		   
		   
		   Map<String, Object> map2 = operatorService.getOperator(userId);
		   String attestationStatus1 = (String) map2.get("attestationStatus");
		   
		   int riskControlPoints = intUserService.getRiskControlPoints(userId);
		   
		   if(attestationStatus.equals("1")&&attestationStatus1.equals("1")&&(riskControlPoints>300||riskControlPoints==300)) {
			   map1.put("code","200");
			   map1.put("msg", "满足条件");
		   }else {
			   map1.put("code","400");
			   map1.put("msg", "不满足条件");
		}
		   
			return map1;
		   
	   }
}
