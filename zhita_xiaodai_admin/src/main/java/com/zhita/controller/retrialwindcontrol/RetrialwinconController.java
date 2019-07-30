package com.zhita.controller.retrialwindcontrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.RetrialWindControl;
import com.zhita.service.manage.deferredsettings.IntDeferredsetService;
import com.zhita.service.manage.retrialwindcontrol.IntRetrialwinconService;

@Controller
@RequestMapping("/retrialwincon")
public class RetrialwinconController {
	@Autowired
	private IntRetrialwinconService intRetrialwinconService;
	@Autowired
	private IntDeferredsetService intDeferredsetService;
	
	//后台管理---查询再审风控表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<RetrialWindControl> queryAll(Integer companyId){
    	List<RetrialWindControl> list=intRetrialwinconService.queryAll(companyId);
    	return list;
    }
	
	//后台管理---(添加功能)先查询借款信息表的产品id和产品名称    公司表信息     供添加时选择
/*	@ResponseBody
	@RequestMapping("/queryAllBorrow")
	public Map<String,Object> queryAllBorrow(Integer companyId){
		Map<String,Object> map=intDeferredsetService.queryAllBorrow(companyId);
		return map;
	}*/
	
	//后台管理----添加功能
/*	@ResponseBody
	@RequestMapping("/insert")
    public int insert(RetrialWindControl record){
    	int num=intRetrialwinconService.insert(record);
    	return num;
    }*/
	
	//后台管理---根据当前id查询当前对象现在
/*	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public RetrialWindControl selectByPrimaryKey(Integer id){
    	RetrialWindControl retrialWindControl=intRetrialwinconService.selectByPrimaryKey(id);
    	return retrialWindControl;
    }*/
	
	//后台管理---更新功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(RetrialWindControl record){
    	int num=intRetrialwinconService.updateByPrimaryKey(record);
    	return num;
    }
}
