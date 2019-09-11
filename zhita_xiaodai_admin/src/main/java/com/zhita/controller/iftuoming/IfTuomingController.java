package com.zhita.controller.iftuoming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Iftuoming;
import com.zhita.service.manage.iftuoming.IntIfTuomingService;

@Controller
@RequestMapping("/iftuoming")
public class IfTuomingController {
	@Autowired
	private IntIfTuomingService intIfTuomingService;
	
	//后台管理----查询
	@ResponseBody
	@RequestMapping("/queryAll")
    public Iftuoming queryAll(Integer companyId){
    	Iftuoming iftuoming=intIfTuomingService.queryAll(companyId);
    	return iftuoming;
    }
	
	//后台管理---修改功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(Iftuoming record){
    	int num=intIfTuomingService.updateByPrimaryKey(record);
    	return num;
    }
}
