package com.zhita.controller.deferredsettings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.DeferredSettings;
import com.zhita.service.manage.deferredsettings.IntDeferredsetService;

@Controller
@RequestMapping("/deferredset")
public class DeferredsetController {
	@Autowired
	private IntDeferredsetService intDeferredsetService;
	
	//后台管理---查询延期设置表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<DeferredSettings> queryAll(Integer companyId){
    	List<DeferredSettings> list=intDeferredsetService.queryAll(companyId);
    	return list;
    }
	
	//后台管理---(添加功能)先查询借款信息表的产品id和产品名称    公司表信息     供添加时选择
/*	@ResponseBody
	@RequestMapping("/queryAllBorrow")
    public Map<String,Object> queryAllBorrow(Integer companyId){
		Map<String,Object> map=intDeferredsetService.queryAllBorrow(companyId);
		return map;
    }*/
	
	//后台管理---添加功能
/*	@ResponseBody
	@RequestMapping("/insert")
    public int insert(DeferredSettings record){
    	int num=intDeferredsetService.insert(record);
    	return num;
    }*/
	
	//后台管理---根据当前id查询出当前对象信息
/*	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public DeferredSettings selectByPrimaryKey(Integer id){
    	DeferredSettings deferredSettings=intDeferredsetService.selectByPrimaryKey(id);
    	return deferredSettings;
    }*/
	
	//后台管理---修改保存功能
	@Transactional
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(DeferredSettings record){
    	int num=intDeferredsetService.updateByPrimaryKey(record);
    	return num;
    }
}
