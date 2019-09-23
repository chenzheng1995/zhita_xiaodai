package com.zhita.controller.overtextmessagingsetting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.OverTextmessagingSetting;
import com.zhita.model.manage.TextmessagingTemplate;
import com.zhita.service.manage.overtextmessagingsetting.IntovertextsettingService;

@Controller
@RequestMapping("/overtextsetting")
public class OvertextmessagingsettingController {
	
	@Autowired
	private IntovertextsettingService intovertextsettingService;
	
	//后台管理---查询列表
	@ResponseBody
	@RequestMapping("/queryAll")
	public Map<String,Object> queryAll(Integer companyId,Integer page){
	    return intovertextsettingService.queryAll(companyId, page);
	}
	
	//后台管理---添加功能（查询出所有模板）
	@ResponseBody
	@RequestMapping("/querytexttemp")
    public List<TextmessagingTemplate> querytexttemp(){
    	return intovertextsettingService.querytexttemp();
	}
	
	//后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
	public int insert(OverTextmessagingSetting record){
	   return intovertextsettingService.insert(record);
	}
	
	//后台管理---根据id查询
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public OverTextmessagingSetting selectByPrimaryKey(Integer id){
    	return intovertextsettingService.selectByPrimaryKey(id);
    }
	
	//后台管理---编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(OverTextmessagingSetting record){
    	return intovertextsettingService.updateByPrimaryKey(record);
    }
	
	//后台管理----修改假删除状态
	@ResponseBody
	@RequestMapping("/upaFalDel")
    public int upaFalDel(Integer id){
    	return intovertextsettingService.upaFalDel(id);
    }
	
}
