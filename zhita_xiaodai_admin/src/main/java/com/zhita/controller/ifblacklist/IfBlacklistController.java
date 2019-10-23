package com.zhita.controller.ifblacklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Ifblacklist;
import com.zhita.service.manage.ifblacklist.IntIfblacklistService;

@Controller
@RequestMapping("/ifblacklist")
public class IfBlacklistController {
	
	@Autowired
	private IntIfblacklistService intIfblacklistService;
	
	
	//后台管理——列表查询
	@ResponseBody
	@RequestMapping("/queryAll")
	public Ifblacklist queryAll(Integer companyId){
		return intIfblacklistService.queryAll(companyId);
	}
	
	//后台管理——编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(Ifblacklist record){
    	return intIfblacklistService.updateByPrimaryKey(record);
    }
}
