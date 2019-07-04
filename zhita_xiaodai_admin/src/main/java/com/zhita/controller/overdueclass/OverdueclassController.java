package com.zhita.controller.overdueclass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.OverdueClass;
import com.zhita.service.manage.overdueclass.IntOverdueclassService;

@Controller
@RequestMapping("/overclass")
public class OverdueclassController {
	@Autowired
	private IntOverdueclassService intOverdueclassService;
	
	//后台管理 --- 查询逾期等级表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public int queryAll(Integer companyId){
    	int num=intOverdueclassService.queryAll(companyId);
    	return num;
    }
	
	//后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intOverdueclassService.queryAllCompany();
    	return list;
    }
	
    //后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(OverdueClass record){
    	int num=intOverdueclassService.insert(record);
    	return num;
    }
	
	//后台管理---根据id查询出当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public OverdueClass selectByPrimaryKey(Integer id){
    	OverdueClass overdueClass=intOverdueclassService.selectByPrimaryKey(id);
    	return overdueClass;
    }
	
	//后台管理---修改保存功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(OverdueClass record){
    	int num=intOverdueclassService.updateByPrimaryKey(record);
    	return num;
    }
}
