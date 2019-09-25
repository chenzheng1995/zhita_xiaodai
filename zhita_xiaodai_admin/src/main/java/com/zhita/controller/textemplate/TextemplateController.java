package com.zhita.controller.textemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.TextmessagingTemplate;
import com.zhita.service.manage.textemplate.IntTextemplateService;

@Controller
@RequestMapping("/texttemplate")
public class TextemplateController {
	
	@Autowired
	private IntTextemplateService intTextemplateService;
	
	//后台管理---短信模板列表查询
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<TextmessagingTemplate> queryAll(){
    	return intTextemplateService.queryAll();
    }
	
	//后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(TextmessagingTemplate record){
    	return intTextemplateService.insert(record);
    }
	
	//后台管理---根据id查询
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public TextmessagingTemplate selectByPrimaryKey(Integer id){
    	return intTextemplateService.selectByPrimaryKey(id);
    }

    //后台管理 ----保存功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(TextmessagingTemplate record){
    	return intTextemplateService.updateByPrimaryKey(record);
    }
	
	//后台管理---更新假删除功能
	@ResponseBody
	@RequestMapping("/upaFalDel")
    public int upaFalDel(Integer id){
    	return intTextemplateService.upaFalDel(id);
    }
}
