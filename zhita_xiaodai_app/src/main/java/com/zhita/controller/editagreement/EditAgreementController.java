package com.zhita.controller.editagreement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.dao.manage.AgreementTypeMapper;
import com.zhita.service.manage.editagreement.IntEditagreementService;
import com.zhita.service.manage.login.IntLoginService;

@Controller
@RequestMapping("/editagreement")
public class EditAgreementController {
	
    @Autowired
    AgreementTypeMapper agreementTypeMapper;
    
    @Autowired
    IntEditagreementService intEditagreementService;
	
    
    //获取协议
    @RequestMapping(value="/geteditagreement",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    @Transactional
    public Map<String, Object> geteditagreement(int companyId,int num){
    	Map<String, Object> map = new HashMap<>();
    	String agreementName = null;
    	if(num==1) {
    		agreementName = "用户协议";
    	}
    	if(num==2) {
    		agreementName = "借款协议";
    	}
    	if(num==3) {
    		agreementName = "延期协议";
    	}   	
        int agreementId =  agreementTypeMapper.getagreementId(companyId,agreementName);
        String agreementContent = intEditagreementService.getagreementContent(agreementId);   
        map.put("agreementContent",agreementContent);
        map.put("Ncode","2000");
        
		return map;

    }
}
