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
	
	//放款渠道紧急开关——查看当天放款金额
	@ResponseBody
	@RequestMapping("/queryloantoday")
	public Map<String,Object> queryloantoday(Integer companyId){
		Map<String,Object> map=intLoanthresholdvalueService.queryloantoday(companyId);
		return map;
	}
	
	//放款渠道紧急开关——修改放款渠道状态
	@ResponseBody
	@RequestMapping("/upaloanstatus")
    public int upaloanstatus(String status,Integer companyId){
    	int num= intLoanthresholdvalueService.upaloanstatus(status,companyId);
    	return num;
    }

}
