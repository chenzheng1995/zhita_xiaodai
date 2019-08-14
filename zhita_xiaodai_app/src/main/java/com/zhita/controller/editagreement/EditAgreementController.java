package com.zhita.controller.editagreement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/geteditagreement")
    @ResponseBody
    @Transactional
    public String geteditagreement(int companyId,int num){
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
		return agreementContent;

    }
}
