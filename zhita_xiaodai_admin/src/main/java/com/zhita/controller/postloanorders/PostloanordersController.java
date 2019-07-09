package com.zhita.controller.postloanorders;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Orderdetails;
import com.zhita.service.manage.postloanorders.Postloanorderservice;

/**
 * 贷后订单管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("postloanor")
public class PostloanordersController {
	
	@Autowired
	private Postloanorderservice postloanor;
	
	
	/**
	 * 期限内订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("postOrders")
	public Map<String, Object> SelectOrderpostloanor(Orderdetails detils){
		return postloanor.allpostOrders(detils);
	}
	
	
	
	/**
	 * 已还订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("YihuanOrders")
	public Map<String, Object> SelectYihuanOrders(Orderdetails detils){
		return postloanor.YiHuanOrders(detils);
	}
	
	
	
	
	
	
	/**
	 * 坏账订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("HuaiZhangOrders")
	public Map<String, Object> SelectHuaiZhangOrders(Orderdetails detils){
		return postloanor.HuaiZhangOrders(detils);
	}
	
	
	
	
	

	/**
	 * 已逾期订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionOrders")
	public Map<String, Object> CollectionOrders(Orderdetails detils){
		return postloanor.CollecOrders(detils);
	}
	
	
	
	
	
	
	
	/**
	 * 逾期订单
	 * @param detils
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BeoverduepostOrders")
	public Map<String, Object> SelectBeoverdueOrderpostloanor(Orderdetails detils){
		return postloanor.allpostOrdersBeoverdue(detils);
	}
	
	
	
	
	
	/**
	 * 未逾期未分配
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionNoOrder")
	public Map<String, Object> SelectCollection(Orderdetails order){
		return postloanor.SelectCollection(order);
	}
	
	
	
	
	/**
	 * 订单分配催收员
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("UpdateOver")
	public Map<String, Object> UpdateOver(Orderdetails order){
		return postloanor.UpdateOrder(order);
	}
	
	
	
	
	
	/**
	 * 未逾期已分配
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("NoCollection")
	public Map<String, Object> ConnectionOrder(Orderdetails order){
		return postloanor.CollectionOrderdet(order);
	}
	
	
	
	
	/**
	 * 逾前催收率报表
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionRecoveryrate")
	public Map<String, Object> Recoveryrate(Orderdetails order){
		return postloanor.CollectionRecovery(order);
	}
	
	
	 
	
	/**
	 * 逾前催收员
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("OverdueUser")
	public Map<String, Object> OverdueUser(Orderdetails order){
		return postloanor.OverdueUser(order);
	}
	
	
	
	
	/**
	 * 查看我的催收订单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("MyOverdue")
	public Map<String, Object> MyOverdue(Orderdetails order){
		return postloanor.MyOverdues(order);
	}
	
	
	
	
	/**
	 * 修改催收订单ID
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("UpdateOverdue")
	public Map<String, Object> UpdateOverdue(Orderdetails order){
		return postloanor.UpdateOverdue(order);
	}
}
