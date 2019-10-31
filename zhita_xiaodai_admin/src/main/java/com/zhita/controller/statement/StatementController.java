package com.zhita.controller.statement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Source;
import com.zhita.service.manage.statement.IntstatementService;

@Controller
@RequestMapping("/statement")
public class StatementController {
	
	@Autowired
	private IntstatementService intstatementService;
	
	
	//后台管理——查询所有正常的渠道
	@ResponseBody
	@RequestMapping("/querysource")
	public List<Source> querysource(Integer companyId){
		List<Source> listsource=intstatementService.querysource(companyId);
		return listsource;
	}
	
	//后台管理——还款表
	@ResponseBody
	@RequestMapping("/paymentrecord")
	public Map<String,Object> paymentrecord(Integer companyId,Integer page,String repaystartTime,String repayendTime){
		Map<String,Object> map=intstatementService.paymentrecord(companyId, page, repaystartTime, repayendTime);
		return map;
	}

}
