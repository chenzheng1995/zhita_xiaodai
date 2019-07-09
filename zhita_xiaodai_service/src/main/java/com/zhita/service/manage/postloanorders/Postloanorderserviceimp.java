package com.zhita.service.manage.postloanorders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Overdue;
import com.zhita.util.PageUtil;

@Service
public class Postloanorderserviceimp implements Postloanorderservice{
	
	
	@Autowired
	private PostloanorderMapper postloanorder;
	
	
	@Autowired
	private CollectionMapper coldao;
	

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
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sim.format(new Date());
		order.setShouldReturnTime(date);
		Integer totalCount = postloanorder.WeiNum(order.getShouldReturnTime());
		List<Integer> CollMember  = postloanorder.CollMemberId(order.getCompanyId());//获取催收员ID
		if(CollMember.size() != 0 && null != CollMember){
			List<Integer> nodeid = postloanorder.SelectNodetId(CollMember);//获取已分配订单ID
			order.setIds(nodeid);
			PageUtil pages = new PageUtil(order.getPage(), totalCount);
			order.setPage(pages.getPage());
			List<Orderdetails> ordeids = postloanorder.SelectOrderDetails(order);//获取未逾期未分配订单
			map.put("pageutil", pages);
			map.put("Orderdetails", ordeids);
		}else{
			map.put("pageutil", "无数据");
		}
		return map;
	}




	@Override
	public Map<String, Object> CollectionOrderdet(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sim.format(new Date());
		order.setShouldReturnTime(date);
		Integer totalCount = postloanorder.WeiNum(order.getShouldReturnTime());
		List<Integer> CollMember  = postloanorder.CollMemberId(order.getCompanyId());//获取催收员ID
		if(CollMember.size() != 0 && null != CollMember){
		List<Integer> nodeid = postloanorder.SelectNodetId(CollMember);//获取已分配订单ID
		order.setIds(nodeid);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> ordeids = postloanorder.AOrderDetails(order);//获取未逾期未分配订单
		map.put("pageutil", pages);
		map.put("Orderdetails", ordeids);
		}else{
			map.put("Orderdetails", "无数据");
		}
		
		return map;
	}




	@Override
	public Map<String, Object> CollectionRecovery(Orderdetails order) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Collection> totalCount = postloanorder.DateNum(order);
		Integer asa = null;
		if(totalCount.size() != 0){
			asa = totalCount.size();
		}else{
			asa = 0;
		}
		order.setShouldReturnTime(sim.format(new Date()));
		PageUtil pages = new PageUtil(order.getPage(), asa);
		order.setPage(pages.getPage());
		List<Collection> cols = postloanorder.CollDateNum(order);
		for(int i=0;i<cols.size();i++){
			Integer OrderIdNum = postloanorder.OrderIdNum(order);//订单总数
			order.setIds(coldao.SelectCollectionId(order.getCompanyId()));
			Integer OverIdNum = postloanorder.OverIdNum(order);//逾前催收数
			cols.get(i).setDialNum(OrderIdNum-OverIdNum);
			order.setOverdue_phonestaus("未接通");
			cols.get(i).setNotconnected(postloanorder.connectedNum(order));
			order.setOverdue_phonestaus("已接通");
			cols.get(i).setConnected(postloanorder.connectedNum(order));
			order.setStatu("2");
			cols.get(i).setSameday(postloanorder.StatusOrders(order));
			order.setStatu("1");
			cols.get(i).setPaymentmade(postloanorder.StatusOrders(order));
			cols.get(i).setPaymentmadeData(cols.get(i).getCollection_count()/cols.get(i).getPaymentmade());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Collection", cols);
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
		for(int i=0;i<cols.size();i++){
			Integer sum = postloanorder.UserNumOrder(order);
			Integer notNull = postloanorder.UserDianlNum(order);
			cols.get(i).setDialNum(sum-notNull);
			order.setOverdue_phonestaus("未接通");
			cols.get(i).setNotconnected(postloanorder.Userphonestaus(order));
			order.setOverdue_phonestaus("已接通");
			cols.get(i).setConnected(postloanorder.Userphonestaus(order));
			order.setBorrowMoneyState("已还款");
			cols.get(i).setPaymentmade(postloanorder.UserOrderStatu(order));
			order.setBorrowMoneyState("未还款");
			cols.get(i).setPaymentmade(postloanorder.UserOrderStatu(order));
			cols.get(i).setPaymentmadeData(cols.get(i).getCollection_count()/cols.get(i).getPaymentmade());
		}
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




	@Override
	public Map<String, Object> YiHuanOrders(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = postloanorder.YiHuanOrdersTotalCount(order);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> orders = postloanorder.YiHuanOrders(order);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setInterMoney(orders.get(i).getRealityBorrowMoney().multiply(orders.get(i).getInterestOnArrears()));
			orders.get(i).setOrderSum_money(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterMoney()));
		}
		map.put("Orderdetails", orders);
		map.put("PageUtil", pages);
		return map;
	}




	@Override
	public Map<String, Object> CollecOrders(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		order.setShouldReturnTime(sim.format(new Date()));
		Integer totalCount = postloanorder.CollecOrdersTotalCount(order);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> orders = postloanorder.CollecOrders(order);
		for(int i=0;i<orders.size();i++){
			order.setOrderId(orders.get(i).getOrderId());
			orders.get(i).setPhone_num(postloanorder.Phone_num(order));
		}
		map.put("Orderdetails", orders);
		map.put("PageUtil", pages);
		return map;
	}




	@Override
	public Map<String, Object> HuaiZhangOrders(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = postloanorder.HuaiZhangOrdersTotalCount(order);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> orders = postloanorder.HuaiZhangOrders(order);
		for(int i=0;i<orders.size();i++){
			order.setOrderId(orders.get(i).getOrderId());
			orders.get(i).setPhone_num(postloanorder.Phone_num(order));
		}
		map.put("Orderdetails", orders);
		map.put("PageUtil", pages);
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
