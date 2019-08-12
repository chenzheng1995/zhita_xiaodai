package com.zhita.controller.applycondition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.ApplyCondition;
import com.zhita.service.manage.applycondition.IntApplyconditionService;

@Controller
@RequestMapping("/applycondition")
public class ApplyconditonController {
	@Autowired
	private IntApplyconditionService intApplyconditionService;
	
	//后台管理---查询申请条件配置表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<ApplyCondition> queryAll(Integer companyId){
    	List<ApplyCondition> list=intApplyconditionService.queryAll(companyId);
    	return list;
    }
	
	  //后台管理---添加功能（查询出所有公司）
/*	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intApplyconditionService.queryAllCompany();
    	return list;
    }*/
	
	  //后台管理---添加功能
	/*@ResponseBody
	@RequestMapping("/insert")
    public int insert(ApplyCondition record){
    	int num=intApplyconditionService.insert(record);
    	return num;
    }*/
	
	//后台管理---根据id查询出当前对象
	/*@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public ApplyCondition selectByPrimaryKey(Integer id){
    	ApplyCondition applyCondition=intApplyconditionService.selectByPrimaryKey(id);
    	return applyCondition;
    }*/
	
	 //后台管理---编辑功能
	@Transactional
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(ApplyCondition record){
    	int num=intApplyconditionService.updateByPrimaryKey(record);
    	return num;
    }

}
