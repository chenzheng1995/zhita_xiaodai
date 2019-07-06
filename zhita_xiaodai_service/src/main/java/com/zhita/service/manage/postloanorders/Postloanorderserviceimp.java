package com.zhita.service.manage.postloanorders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Overdue;
import com.zhita.util.PageUtil;

@Service
public class Postloanorderserviceimp implements Postloanorderservice{
	
	
	@Autowired
	private PostloanorderMapper postloanorder;
	
	

	@Override
	public Map<String, Object> allpostOrders(Orderdetails details) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		details.setReturntime(sim.format(new Date()));
		Integer totalCount = postloanorder.totalCount(details);
		PageUtil pages = new PageUtil(details.getPage(), totalCount);
		details.setPage(pages.getPage());
		details.setTotalCount(totalCount);
		List<Orderdetails> orderdetils = postloanorder.allOrderdetails(details);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", orderdetils);
		map.put("pageutil", pages);
		return map;
	}
	
	
	
	
	@Override
	public Map<String, Object> allpostOrdersBeoverdue(Orderdetails details) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		details.setReturntime(sim.format(new Date()));
		Integer totalCount = postloanorder.BeoverduetotalCount(details);
		PageUtil pages = new PageUtil(details.getPage(), totalCount);
		details.setPage(pages.getPage());
		details.setTotalCount(totalCount);
		List<Orderdetails> orderdetils = postloanorder.allBeoverdueOrderdetails(details);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", orderdetils);
		map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> SelectCollection(Orderdetails order) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sim.format(new Date());
		order.setShouldReturnTime(date);
		Integer totalCount = postloanorder.WeiNum(order.getShouldReturnTime());
		List<Integer> CollMember  = postloanorder.CollMemberId(order.getCompanyId());//获取催收员ID
		List<Integer> nodeid = postloanorder.SelectNodetId(CollMember);//获取已分配订单ID
		order.setIds(nodeid);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> ordeids = postloanorder.SelectOrderDetails(order);//获取未逾期未分配订单
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageutil", pages);
		map.put("Orderdetails", ordeids);
		return map;
	}




	@Override
	public Map<String, Object> CollectionOrderdet(Orderdetails order) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sim.format(new Date());
		order.setShouldReturnTime(date);
		Integer totalCount = postloanorder.WeiNum(order.getShouldReturnTime());
		List<Integer> CollMember  = postloanorder.CollMemberId(order.getCompanyId());//获取催收员ID
		List<Integer> nodeid = postloanorder.SelectNodetId(CollMember);//获取已分配订单ID
		order.setIds(nodeid);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> ordeids = postloanorder.OrderDetails(order);//获取未逾期未分配订单
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageutil", pages);
		map.put("Orderdetails", ordeids);
		return map;
	}




	@Override
	public Map<String, Object> CollectionRecovery(Orderdetails order) {
		List<Collection> totalCount = postloanorder.DateNum(order);
		Integer asa = null;
		if(totalCount.size() != 0){
			asa = totalCount.size();
		}else{
			asa = 0;
		}
		PageUtil pages = new PageUtil(order.getPage(), asa);
		order.setPage(pages.getPage());
		List<Collection> cols = postloanorder.CollDateNum(order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Collection", cols);
		map.put("Pageutil", pages);
 		return map;
	}




	@Override
	public Map<String, Object> OverdueUser(Orderdetails order) {
		List<Collection> totalCount = postloanorder.MemberNum(order);
		Integer asa = null;
		if(totalCount.size() != 0){
			asa = totalCount.size();
		}else{
			asa = 0;
		}
		PageUtil pages = new PageUtil(order.getPage(), asa);
		order.setPage(pages.getPage());
		List<Collection> cols = postloanorder.MemberName(order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Collection", cols);
		map.put("Pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> MyOverdues(Orderdetails order) {
		Integer totalCount = postloanorder.MyOrderNum(order);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> orders = postloanorder.MyOrderdue(order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", orders);
		map.put("Pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> UpdateOverdue(Orderdetails order) {
		Integer updateId = postloanorder.OverdueStatu(order);
		Map<String, Object> map = new HashMap<String, Object>();
		if(updateId != null){
			map.put("code", 200);
			map.put("desc", "修改完成");
		}else{
			map.put("code", 0);
			map.put("desc", "修改失败");
		}
		return map;
	}




	@Override
	public Map<String, Object> UpdateOrder(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(order.getIds().size() != 0){
			for(int i=0;i<order.getIds().size();i++){
				Overdue ovdeu = new Overdue();
				ovdeu.setOverdue_id(order.getIds().get(i));
				ovdeu.setCollectionMemberId(order.getCollectionMemberId());
				ovdeu.setCollectiondate(sim.format(new Date()));
				postloanorder.AddOverdue(ovdeu);
			}
			map.put("code", 200);
			map.put("desc", "已分配");
		}else{
			map.put("code", 0);
			map.put("desc", "数据异常");
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
