package com.zhita.controller.source;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.Source;
import com.zhita.service.manage.source.IntSourceService;

@Controller
@RequestMapping("/source")
public class SourceController {
	@Autowired
	private IntSourceService intSourceService;
	
	//后台管理---查询渠道表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public Map<String,Object> queryAll(Integer comapnyId,Integer page){
    	Map<String,Object> map=intSourceService.queryAll(comapnyId, page);
    	return map;
    }
	
	//后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intSourceService.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(Source record){
    	int num=intSourceService.insert(record);
    	return num;
    }
	
	//后台管理---根据id查询当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public Source selectByPrimaryKey(Integer id){
    	Source source=intSourceService.selectByPrimaryKey(id);
    	return source;
    }
	
	//后台管理---编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(Source record){
    	int num=intSourceService.updateByPrimaryKey(record);
    	return num;
    }
	
	//后台管理---根据id  对当前对象的假删除状态进行修改
	@ResponseBody
	@RequestMapping("/updateFalDel")
    public int updateFalDel(Integer id){
    	int num=intSourceService.updateFalDel(id);
    	return num;
    }
}
