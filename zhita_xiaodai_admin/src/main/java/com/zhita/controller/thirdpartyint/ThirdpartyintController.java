package com.zhita.controller.thirdpartyint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.ThirdpartyInterface;
import com.zhita.service.manage.thirdpartyint.IntThirdpartyintService;

@Controller
@RequestMapping("thirdpartyint")
public class ThirdpartyintController {
	@Autowired
	private IntThirdpartyintService intThirdpartyintService;
	
	//后台管理---查询出第三方接口配置表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<ThirdpartyInterface> queryAll(Integer companyId){
    	List<ThirdpartyInterface> list=intThirdpartyintService.queryAll(companyId);
    	return list;
    }
	
	//后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intThirdpartyintService.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(ThirdpartyInterface record){
    	int num=intThirdpartyintService.insert(record);
    	return num;
    }
	
    //后台管理---根据id查询当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public ThirdpartyInterface selectByPrimaryKey(Integer id){
    	ThirdpartyInterface thirdpartyInterface=intThirdpartyintService.selectByPrimaryKey(id);
    	return thirdpartyInterface;
    }
	
	 //后台管理---编辑保存功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(ThirdpartyInterface record){
    	int num=intThirdpartyintService.updateByPrimaryKey(record);
    	return num;
    }
}
