package com.zhita.controller.loanthresholdvalue;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.loanthresholdvalue.IntLoanthresholdvalueService;

@Controller
@RequestMapping("/loanthresholdvalue")
public class LoanthresholdvalueController {
	
	@Autowired
	private IntLoanthresholdvalueService intLoanthresholdvalueService;
	
	//放款渠道最大阀值
	@ResponseBody
	@RequestMapping("/loanmax")
	public Map<String,Object> loanmax(Integer companyId){
		Map<String,Object> map=intLoanthresholdvalueService.loanmax(companyId);
		return map;
	}
	
	//修改最大阀值
	@ResponseBody
	@RequestMapping("/upamaxthresholdvalue")
	public int upamaxthresholdvalue(Integer maxthresholdvalue){
		int num=intLoanthresholdvalueService.upamaxthresholdvalue(maxthresholdvalue);
		return num;
	}

}
