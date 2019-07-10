package com.zhita.service.manage.collection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Collection_member;
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.util.PageUtil;


@Service
public class Collectionserviceimp implements Collectionservice{
	
	@Autowired
	private CollectionMapper collmapp;

	
	
	@Autowired
	private PostloanorderMapper podao;
	
	
	@Override
	public Map<String, Object> allBeoverdueConnection(Collection coll) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		coll.setRealtime(sim.format(new Date()));
		List<Integer> collIds = collmapp.SelectCollectionId(coll.getCompanyId());//根据公司ID 查询催收员ID
		if(collIds.size() != 0){
			coll.setIds(collmapp.OrderIdMa(collIds)); 
			Integer totalCount = collmapp.SelectTotalCount(coll);
			PageUtil pages = new PageUtil(coll.getPage(), totalCount);
			coll.setPage(pages.getPage());
			pages.setTotalCount(totalCount);
			List<Orderdetails> orders = collmapp.Allorderdetails(coll);
			for(int i=0;i<orders.size();i++){
				orders.get(i).setOrder_money(orders.get(i).getMakeLoans().add(orders.get(i).getInterestPenaltySum()));
				Deferred des = collmapp.DefeSet(orders.get(i));
				Collection colla = collmapp.CollMen(orders.get(i));
				if(des != null && colla != null){
					orders.get(i).setDeferBeforeReturntime(des.getDeferBeforeReturntime());
					orders.get(i).setInterestOnArrears(des.getInterestOnArrears());
					orders.get(i).setOnceDeferredDay(des.getOnceDeferredDay());
					orders.get(i).setDeferAfterReturntime(des.getDeferAfterReturntime());
					orders.get(i).setReallyName(colla.getReallyName());
					orders.get(i).setCollectionTime(colla.getCollectionTime());
					orders.get(i).setCollectionStatus(colla.getCollectionStatus());
				}
				
			}
			map.put("Orderdetails", orders);
			return map;
		}
		map.put("Orderdetails", 0);
		return map;
	}

	
	
	@Override
	public Map<String, Object> Collectionmember(Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(companyId != null){
			List<Collection_member> col = collmapp.CollectionAll(companyId);
			map.put("collection_member", col);
		}else{
			map.put("code", 0);
			map.put("desc", "公司ID为空");
		}
		return map;
	}
	
	

	@Override
	public Map<String, Object> UpdateColl(Collection col) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collection> cols = new ArrayList<Collection>();
		for(int i = 0;i<col.getIds().size();i++){
			Collection cola = new Collection();
			cola.setCollectionMemberId(col.getCollectionMemberId());
			cola.setCollectionTime(sim.format(new Date()));
			cola.setOrderId(col.getIds().get(i));
			cola.setDeleted("0");
			cols.add(cola);
		}
		Integer addId = collmapp.addCollection(cols);
		if(addId != null){
			map.put("code", 200);
			map.put("desc", "已分配");
		}else{
			map.put("code", 0);
			map.put("desc", "网络错误");
		}
		return map;
	}
	
	

	@Override
	public Map<String, Object> BeoverdueYi(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(order.getCompanyId() != null){
			List<Integer> collIds = collmapp.SelectCollectionId(order.getCompanyId());//根据公司ID 查询催收员ID
			List<Integer> ids = collmapp.OrderIdMa(collIds);
			PageUtil pages = new PageUtil(order.getPage(), ids.size());
			order.setPage(pages.getPage());
			pages.setTotalCount(ids.size());
			List<Orderdetails> orders = collmapp.SelectOrdersdetails(order);
			for(int i=0;i<orders.size();i++){
				orders.get(i).setOrder_money(orders.get(i).getMakeLoans().add(orders.get(i).getInterestPenaltySum()));
				Deferred des = collmapp.DefeSet(orders.get(i));
				if(des != null ){
					orders.get(i).setDeferBeforeReturntime(des.getDeferBeforeReturntime());
					orders.get(i).setInterestOnArrears(des.getInterestOnArrears());
					orders.get(i).setOnceDeferredDay(des.getOnceDeferredDay());
					orders.get(i).setDeferAfterReturntime(des.getDeferAfterReturntime());
				}
			}
			map.put("Orderdetails", orders);
		}else{
			map.put("Orderdetails", "0");
			map.put("pageutil", "数据异常");
		}
		return map;
	}



	@Override
	public Map<String, Object> Colldetails(Orderdetails order) {
		Orderdetails orders = collmapp.OneOrdersdetails(order.getOrderNumber());
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = collmapp.CollectionTotalCount();
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		map.put("Orderdetails", orders);
		return map;
	}



	@Override
	public Map<String, Object> Collectionmemberdetails(Collection coll) {
		List<Collection> totalCount = collmapp.SelectSumOrderNum(coll);
		Integer asa = null;
		if(totalCount.size() != 0){
			asa = totalCount.size();
		}else{
			asa = 0;
		}
		PageUtil pages = new PageUtil(coll.getPage(), asa);
		coll.setPage(pages.getPage());
		List<Collection> colles = collmapp.SelectSumOrder(coll);
		for(int i=0;i<colles.size();i++){
			colles.get(i).setCompanyId(coll.getCompanyId());
			colles.get(i).setOrderNum(collmapp.SelectOrderNum(colles.get(i)));//累计订单总数  参数  时间   公司ID
			colles.get(i).setIds(collmapp.SelectCollectionId(coll.getCompanyId()));//根据公司ID 查询催收员ID
			colles.get(i).setCollSum(collmapp.SelectCollectionNum(colles.get(i)));
			colles.get(i).setCollectionStatus("承诺还款");
			colles.get(i).setSameday(collmapp.SelectcollectionStatus(colles.get(i)));//承诺还款
			colles.get(i).setCollectionStatus("电话无人接听");
			colles.get(i).setPaymentmade(collmapp.SelectcollectionStatus(colles.get(i)));//未还清
			colles.get(i).setCollectionStatus("态度恶劣");
			colles.get(i).setConnected(collmapp.SelectcollectionStatus(colles.get(i)));//累计坏账数
			if(colles.get(i).getConnected() != 0 && colles.get(i).getConnected() != null){
				colles.get(i).setCollNumdata((colles.get(i).getOrderNum()/colles.get(i).getConnected()));
			}else{
				colles.get(i).setCollNumdata(100);
			}
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Collection", colles);
		return map;
	}



	@Override
	public Map<String, Object> FenpeiWeiCollection(Collection col) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(col.getCompanyId() != null){
			List<Integer> collIds = collmapp.SelectCollectionId(col.getCompanyId());//根据公司ID 查询催收员ID
			List<Integer> CollectionMemberIds = collmapp.SelectCollectionMemberIds(collIds);//查询已分配催收员订单ID
			List<Integer> coldetids = new ArrayList<Integer>();
			for(int i=0;i<CollectionMemberIds.size();i++){
				List<Integer> id = collmapp.SelectId(CollectionMemberIds.get(i));
				if(id.size() == 0){
					coldetids.add(CollectionMemberIds.get(i));//根据订单ID查询催收详情  返回Null 就是未催收
				}
			}
			col.setIds(coldetids);
			Integer totalCount = collmapp.CollectionTotalcount(col);
			PageUtil pages = new PageUtil(col.getPage(), totalCount);
			col.setPage(pages.getPage());
			List<Orderdetails> orders = collmapp.FenpeiCollection(col);
			for(int i=0;i<orders.size();i++){
				orders.get(i).setOrder_money(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestPenaltySum()));
			}
			map.put("Orderdetails", orders);
		}else{
			map.put("Orderdetails", "0");
			map.put("Pageutil", "数据异常");
		}
		return map;
	}



	@Override
	public Map<String, Object> YiCollection(Collection col) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(col.getCompanyId() != null){
		List<Integer> collIds = collmapp.SelectCollectionId(col.getCompanyId());//根据公司ID 查询催收员ID
		List<Integer> CollectionMemberIds = collmapp.SelectCollectionMemberIds(collIds);//查询已分配催收员订单ID
		List<Integer> coldetids = new ArrayList<Integer>();
		for(int i=0;i<CollectionMemberIds.size();i++){
			List<Integer> id = collmapp.SelectId(CollectionMemberIds.get(i));
			if(id.size() != 0 && id != null){
				coldetids.add(CollectionMemberIds.get(i));//查询已催收ID
			}
		}
		if(coldetids.size() != 0){
			col.setIds(coldetids);
		}else{
			coldetids.add(0);
			col.setIds(coldetids);
		}
		
		Integer totalCount = collmapp.CollectionWeiTotalcount(col);
		PageUtil pages = new PageUtil(col.getPage(), totalCount);
		col.setPage(pages.getPage());
		List<Orderdetails> orders = collmapp.WeiControllerOrdetialis(col);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setSurplus_money(orders.get(i).getRealityBorrowMoney().subtract(orders.get(i).getRealityAccount()));
			orders.get(i).setCollNum(collmapp.CollNum(orders.get(i).getOrderId()));
		}
		map.put("Orderdetails", orders);
		}else{
			map.put("Orderdetails", "0");
			map.put("Pageutil", "数据异常");
		}
		return map;
	}



	@Override
	public Map<String, Object> AddColloetails(Collectiondetails col) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(col.getUser_type() != null){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			col.setCollection_time(sim.format(new Date()));
			Integer addId = collmapp.AddCollectiondetails(col);
			if(addId != null){
				map.put("code", 200);
				map.put("desc", "设置成功");
			}else{
				map.put("code", 0);
				map.put("desc", "设置失败");
			}
		}else{
			map.put("code", 0);
			map.put("desc", "请选择用户状态");
		}
		
		return map;
	}



	@Override
	public List<Collectiondetails> AllCollectiondetail(Integer orderId) {
		Collectiondetails col = new Collectiondetails();
		col.setOrderId(orderId);
		List<Collectiondetails> coldetails = collmapp.Coldetails(col);
		return coldetails;
	}



	@Override
	public Map<String, Object> CollectionmemberUser(Collection coll) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collection> colleas = collmapp.SelectUserNum(coll);
		Integer totalCount = null ;		
		if(colleas.size() != 0){
			totalCount = colleas.size();
		}else{
			totalCount = 0;
		}
		PageUtil pages = new PageUtil(coll.getPage(), totalCount);
		coll.setPage(pages.getPage());
		List<Collection> colles = collmapp.Collectionmemberdetilas(coll);
		for(int i=0;i<colles.size();i++){
			colles.get(i).setCompanyId(coll.getCompanyId());
			colles.get(i).setOrderNum(collmapp.SelectUserCollectionNum(colles.get(i)));
			colles.get(i).setCollectionStatus("承诺还款");
			colles.get(i).setConnected(collmapp.SelectUsercollectionStatus(colles.get(i)));//累计成功
			colles.get(i).setCollectionStatus("承诺还清一部分");
			colles.get(i).setSameday(collmapp.SelectUsercollectionStatus(colles.get(i)));//承诺还款
			colles.get(i).setCollectionStatus("电话无人接听");
			colles.get(i).setPaymentmade(collmapp.SelectUsercollectionStatus(colles.get(i)));//未还清
			colles.get(i).setCollectionStatus("态度恶劣");
			colles.get(i).setConnected(collmapp.SelectUsercollectionStatus(colles.get(i)));//累计坏账数
			if(colles.get(i).getConnected() != 0 && colles.get(i).getConnected() != null){
				colles.get(i).setCollNumdata((colles.get(i).getOrderNum()/colles.get(i).getConnected()));
			}else{
				colles.get(i).setCollNumdata(100);
			}
			
		}
		map.put("Collections", colles);
		return map;
	}
	
	
	
	
	
	
	
	

}
