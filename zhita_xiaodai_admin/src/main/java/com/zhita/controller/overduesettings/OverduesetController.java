package com.zhita.controller.overduesettings;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.OverdueSettings;
import com.zhita.service.manage.deferredsettings.IntDeferredsetService;
import com.zhita.service.manage.overduesettings.IntOverduesetService;

@Controller
@RequestMapping("/overdueset")
public class OverduesetController {
	@Autowired
	private IntOverduesetService intOverduesetService;
	@Autowired
	private IntDeferredsetService intDeferredsetService;
	
	 //后台管理---查询逾期信息表
	 @ResponseBody
	 @RequestMapping("/queryAll")
	 public List<OverdueSettings> queryAll(Integer companyId){
		 List<OverdueSettings> list=intOverduesetService.queryAll(companyId);
		 return list;
	 }
	 
	//后台管理---(添加功能)先查询借款信息表的产品id和产品名称    公司表信息     供添加时选择
	@ResponseBody
	@RequestMapping("/queryAllBorrow")
	public Map<String,Object> queryAllBorrow(Integer companyId){
		Map<String,Object> map=intDeferredsetService.queryAllBorrow(companyId);
		return map;
	}
	
	//后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
	public int insert(OverdueSettings record){
		int num=intOverduesetService.insert(record);
		return num;
	}
	
	 //后台管理---根据当前id查询出当前用户信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
	 public OverdueSettings selectByPrimaryKey(Integer id){
		 OverdueSettings overdueSettings=intOverduesetService.selectByPrimaryKey(id);
		 return overdueSettings;
	 }
	
	//后台管理---修改保存
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
	public int updateByPrimaryKey(OverdueSettings record){
		int num=intOverduesetService.updateByPrimaryKey(record);
		return num;
	}
	
	//后台管理---修改当前对象的假删除状态
	@ResponseBody
	@RequestMapping("/upaFalseDel")
	 public int upaFalseDel(String operator,Integer id){
		 int num=intOverduesetService.upaFalseDel(operator, id);
		 return num;
	  }
}
