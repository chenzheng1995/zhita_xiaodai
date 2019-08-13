package com.zhita.controller.editagreement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.AgreementType;
import com.zhita.model.manage.EditAgreement;
import com.zhita.service.manage.editagreement.IntEditagreementService;

@Controller
@RequestMapping("/editagreement")
public class EditagreementController {
	@Autowired
	private IntEditagreementService intEditagreementService;
	
	//后台管理----查询协议分类表所有的协议
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<AgreementType> queryAll(Integer companyId){
    	List<AgreementType> list=intEditagreementService.queryAll(companyId);
    	return list;
    }
    
    //后台管理----根据agreementType（协议分类）查询
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public EditAgreement selectByPrimaryKey(Integer typeid){
    	EditAgreement editAgreement=intEditagreementService.selectByPrimaryKey(typeid);
    	return editAgreement;
    }
    
    //后台管理----更新保存
	@ResponseBody
	@RequestMapping("/updateByPrimaryKeyWithBLOBs")
    public  int updateByPrimaryKeyWithBLOBs(EditAgreement record){
    	int num=intEditagreementService.updateByPrimaryKeyWithBLOBs(record);
    	return num;
    }
}
