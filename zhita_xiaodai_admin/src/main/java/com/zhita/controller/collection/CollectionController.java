package com.zhita.controller.collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Orderdetails;
import com.zhita.service.manage.collection.Collectionservice;


@Controller
@RequestMapping("collection")
public class CollectionController {
	
	
	@Autowired
	private Collectionservice collservice;

	
	
	
	
	/**
	 * 查询逾期未分配订单
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BeoverdueCollection")
	public Map<String, Object> BeoverdueCollection(Collection coll){
		return collservice.allBeoverdueConnection(coll);
	}
	
	
	
	
	
	
	/**
	 * 查询催收员
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("collectionmember")
	public Map<String, Object> Collectionmemeber(Integer companyId){
		return collservice.Collectionmember(companyId);
	}
	
	
	
	
	
	
	/**
	 * 分配催单员
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddCollection")
	public Map<String, Object> UpdateCollection(Collection col){
		return collservice.UpdateColl(col);
	}
	
	
	
	
	
	
	
	/**
	 * 查询已逾期已分配
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BeoverdueYifenp")
	public Map<String, Object> BeoverdueYifen(Orderdetails order){
		return collservice.BeoverdueYi(order);
	}
	
	
	
	
	
	
	
	/**
	 * 催收率详情
	 * @param orders
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Collectiondetails")
	public Map<String, Object> AllocatedOrders(Orderdetails orders){
		return collservice.Colldetails(orders);
	}
	
	
	
	
	
	
	/**
	 * 查询催收率报表
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionLv")
	public Map<String, Object> CollectionLv(Collection coll){
		return collservice.Collectionmemberdetails(coll);
	}
	
	
	
	
	
	
	
	/**
	 * 查询催收员催收率报表
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CollectionUserLv")
	public Map<String, Object> CollectionUserLv(Collection coll){
		return collservice.CollectionmemberUser(coll);
	}
	
	
	
	
	
	
	/**
	 * 查询催收员工报表
	 * @param coll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Collectionmemberdetails")
	public Map<String, Object> Collectionmemberdetails(Collection coll){
		return collservice.Collectionmemberdetails(coll);
	}
	
	
	
	
	
	
	
	/**
	 * 已分配未催收
	 * @param col
	 * @return
	 */
	@ResponseBody
	@RequestMapping("FenpeiWeiCollection")
	public Map<String, Object> FenpeiWeiCollection(Collection col){
		return collservice.FenpeiWeiCollection(col);
	}
	
	
	
	
	
	
	
	
	/**
	 * 已分配已催收
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("YiCollection")
	public Map<String, Object> YiCollection(Collection col){
		return collservice.YiCollection(col);
	}
	
	
	
	
	
	
	
	/**
	 * 已分配未催收  完成联系  功能
	 * @param coldets
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddCollectiondertilas")
	public Map<String, Object> AddCollectiondertails(Collectiondetails coldets){
		return collservice.AddColloetails(coldets);
	}
	
	
	
	
	
	
	/**
	 * 查看催收记录
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Alldetails")
	public Map<String, Object> AllDetails(Integer orderId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collectiondetails> cols = collservice.AllCollectiondetail(orderId);
		map.put("Collectiondetils", cols);
		return map;
	}
	
	
	
	
	
}