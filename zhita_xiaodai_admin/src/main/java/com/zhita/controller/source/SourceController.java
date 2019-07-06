package com.zhita.controller.source;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Source;
import com.zhita.service.manage.source.IntSourceService;
/**
 * 渠道模块管理
 * @author lhq
 * @{date} 2019年7月5日
 */
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
	
	//后台管理---添加功能（查询出所有公司和风控）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public Map<String,Object> queryAllCompany(Integer companyId){
		Map<String,Object> map=intSourceService.queryAllCompany(companyId);
    	return map;
    }
    
    //后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(Source record,String templateName){
    	int num=intSourceService.insert(record,templateName);
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
    public int updateByPrimaryKey(Source record,String templateName){
    	int num=intSourceService.updateByPrimaryKey(record,templateName);
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
