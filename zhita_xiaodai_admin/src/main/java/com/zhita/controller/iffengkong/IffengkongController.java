package com.zhita.controller.iffengkong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Iffengkong;
import com.zhita.service.manage.iffengkong.IntIffengkongService;

@Controller
@RequestMapping("/iffengkong")
public class IffengkongController {
	
	@Autowired
	private IntIffengkongService intIffengkongService;
	
	//后台管理——列表查询
	@ResponseBody
	@RequestMapping("/queryAll")
	public Iffengkong queryAll(Integer companyId){
		return intIffengkongService.queryAll(companyId);
	}
	
	//后台管理——编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(Iffengkong record){
    	return intIffengkongService.updateByPrimaryKey(record);
    }

}
