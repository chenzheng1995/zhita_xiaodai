package com.zhita.controller.source;

import java.text.ParseException;
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
    public Map<String,Object> queryAll(Integer companyId,Integer page){
    	Map<String,Object> map=intSourceService.queryAll(companyId, page);
    	return map;
    }
	
	//后台管理---添加功能（查询出所有公司   模板和风控）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public Map<String,Object> queryAllCompany(Integer companyId){
		Map<String,Object> map=intSourceService.queryAllCompany(companyId);
    	return map;
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
    public int updateByPrimaryKey(Source record) throws ParseException{
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
	
	//后台管理----根据前端传过来的链接判断该链接是否存在source表
	@ResponseBody
	@RequestMapping("/queryIfLink")
	public int queryIfLink(String link){
		int returnvalue=0;
		int value=intSourceService.queryIfLink(link);
		if(value==0){
			returnvalue=0;
		}else{
			returnvalue=1;
		}
		return returnvalue;
	}
}
