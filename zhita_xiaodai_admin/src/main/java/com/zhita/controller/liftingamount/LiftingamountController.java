package com.zhita.controller.liftingamount;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.LiftingAmount;
import com.zhita.service.manage.deferredsettings.IntDeferredsetService;
import com.zhita.service.manage.liftingamount.IntLiftingamountServcie;

@Controller
@RequestMapping("/liftingamount")
public class LiftingamountController {
	@Autowired
	private IntLiftingamountServcie intLiftingamountServcie;
	@Autowired
	private IntDeferredsetService intDeferredsetService;
	
	//后台管理---查询续借提额表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<LiftingAmount> queryAll(Integer comapnyId){
    	List<LiftingAmount> list=intLiftingamountServcie.queryAll(comapnyId);
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
    public int insert(LiftingAmount record){
    	int num=intLiftingamountServcie.insert(record);
    	return num;
    }
	
	//后台管理---根据当前id查询当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public  LiftingAmount selectByPrimaryKey(Integer id){
    	LiftingAmount liftingAmount=intLiftingamountServcie.selectByPrimaryKey(id);
    	return liftingAmount;
    }
	
	//后台管理---更新保存
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(LiftingAmount record){
    	int num=intLiftingamountServcie.updateByPrimaryKey(record);
    	return num;
    }
	
	//后台管理---修改当前对象的假删除状态
	@ResponseBody
	@RequestMapping("/upaFalseDel")
	public int upaFalseDel(String operator,Integer id){
		int num=intLiftingamountServcie.upaFalseDel(operator, id);
		return num;
	}
}
