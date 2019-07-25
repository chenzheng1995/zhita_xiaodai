package com.zhita.controller.usthresholdvalue;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.usthresholdvalue.IntUsthresholdvalueService;

@Controller
@RequestMapping("/usthresholdvalue")
public class UsthresholdvalueController {
	@Autowired
	private IntUsthresholdvalueService intUsthresholdvalueService;
	
	//流量渠道最大阀值
	@ResponseBody
	@RequestMapping("/usersourcemax")
	public Map<String,Object> usersourcemax(Integer companyId){
		Map<String,Object> map=intUsthresholdvalueService.usersourcemax(companyId);
		return map;
	}
	
	//修改最大阀值
	@ResponseBody
	@RequestMapping("/upamaxthresholdvalue")
	public int upamaxthresholdvalue(Integer maxthresholdvalue){
		int num=intUsthresholdvalueService.upamaxthresholdvalue(maxthresholdvalue);
		return num;
	}
}
