package com.zhita.controller.operationaldata;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhita.model.manage.Orderdetails;
import com.zhita.service.manage.operational.OperationalService;


@Controller
@RequestMapping("operation")
public class OperationalController {
	
	
	
	@Autowired
	private OperationalService oser;
	
	

	
	/**
	 * 平台总数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("platformsNum")
	public Map<String, Object> Platformsoppicues(Orderdetails or){
		return oser.PlatformsNu(or);
	}
	
	
	
	
	
	
	
	/**
	 * 还款数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("HuanKuandata")
	public Map<String, Object> HuanKuandata(Orderdetails or){
		return oser.HuanKuan(or);
	}
	
	
	
	
	
	
	
	/**
	 * 逾期数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionData")
	public Map<String, Object> CollectionData(Orderdetails or){
		return oser.CollectionData(or);
	}
	
	
	
	
	
	
	/**
	 * 订单收支数据
	 * @param or
	 * @return
	 */
	@ResponseBody
	@RequestMapping("OrderBudget")
	public Map<String, Object> OrderBudget(Orderdetails or){
		return oser.OrderBudget(or);
	}
	
	
	
	
	/**
	 * 查询引流平台
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllDrainage")
	public Map<String, Object> AllDrainge(Integer companyId){
		return oser.AllDrainageOf(companyId);
	}
	
	
	
	
	
	/**
	 * 逾期等级
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("YuqiM")
	public Map<String, Object> YuqiM(Integer companyId){
		return oser.AllOverdueclass(companyId);
	}
}
