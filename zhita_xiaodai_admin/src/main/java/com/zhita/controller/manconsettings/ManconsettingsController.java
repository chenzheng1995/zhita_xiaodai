package com.zhita.controller.manconsettings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.service.manage.manconsettings.IntManconsettingsServcie;

@Controller
@RequestMapping("/manconsettings")
public class ManconsettingsController {
	@Autowired
	private IntManconsettingsServcie intManconsettingsServcie;
	
	//后台管理---查询风控设置管理表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<ManageControlSettings> queryAll(Integer companyId){
    	List<ManageControlSettings> list=intManconsettingsServcie.queryAll(companyId);
    	return list;
    }
	
	//后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intManconsettingsServcie.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
	@Transactional
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(ManageControlSettings record){
    	int num=intManconsettingsServcie.insert(record);
    	return num;
    }
	
	//后台管理---根据主键id查询当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public ManageControlSettings selectByPrimaryKey(Integer id){
    	ManageControlSettings manageControlSettings=intManconsettingsServcie.selectByPrimaryKey(id);
    	return manageControlSettings;
    }
    
    //后台管理---编辑保存功能
	@Transactional
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(ManageControlSettings record){
    	int num=intManconsettingsServcie.updateByPrimaryKey(record);
    	return num;
    }
}
