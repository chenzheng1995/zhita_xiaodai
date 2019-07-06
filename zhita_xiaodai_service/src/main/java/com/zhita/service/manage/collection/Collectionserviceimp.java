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
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Collection_member;
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Orderdetails;
import com.zhita.util.PageUtil;


@Service
public class Collectionserviceimp implements Collectionservice{
	
	@Autowired
	private CollectionMapper collmapp;

	
	
	@Override
	public Map<String, Object> allBeoverdueConnection(Collection coll) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> collIds = collmapp.SelectCollectionId(coll.getCompanyId());//根据公司ID 查询催收员ID
		if(collIds.size() != 0){
			coll.setIds(collmapp.OrderId(collIds)); 
			Integer totalCount = collmapp.SelectTotalCount(coll);
			PageUtil pages = new PageUtil(coll.getPage(), totalCount);
			coll.setPage(pages.getPage());
			pages.setTotalCount(totalCount);
			List<Orderdetails> orders = collmapp.Allorderdetails(coll);
			map.put("Orderdetails", orders);
			map.put("pageutil", pages);
			return map;
		}
		map.put("Orderdetails", 0);
		return map;
	}

	
	
	@Override
	public Map<String, Object> Collectionmember(Integer companyId) {
		List<Collection_member> col = collmapp.CollectionAll(companyId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collection_member", col);
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
			List<Integer> ids = collmapp.OrderId(collIds);
			PageUtil pages = new PageUtil(order.getPage(), ids.size());
			order.setPage(pages.getPage());
			pages.setTotalCount(ids.size());
			List<Orderdetails> orders = collmapp.SelectOrdersdetails(order);
			map.put("Orderdetails", orders);
			map.put("pageutil", pages);
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Collection", colles);
		map.put("Pageutil", pages);
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
			map.put("Orderdetails", orders);
			map.put("Pageutil", pages);
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
		col.setIds(coldetids);
		Integer totalCount = collmapp.CollectionWeiTotalcount(col);
		PageUtil pages = new PageUtil(col.getPage(), totalCount);
		col.setPage(pages.getPage());
		List<Orderdetails> orders = collmapp.WeiControllerOrdetialis(col);
		
		map.put("Orderdetails", orders);
		map.put("PageUtil", pages);
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
	public Map<String, Object> AllCollectiondetail(Integer orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collectiondetails> coldetails = collmapp.SelectCollectiondetails(orderId);
		map.put("collectiondetails", coldetails);
		return map;
	}



	@Override
	public Map<String, Object> CollectionmemberUser(Collection collectio) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collection> colleas = collmapp.SelectUserNum(collectio);
		Integer totalCount = null ;		
		if(colleas.size() != 0){
			totalCount = colleas.size();
		}else{
			totalCount = 0;
		}
		PageUtil pages = new PageUtil(collectio.getPage(), totalCount);
		collectio.setPage(pages.getPage());
		List<Collection> colles = collmapp.Collectionmemberdetilas(collectio);
		map.put("Collections", colles);
		map.put("PageUtil", pages);
		return map;
	}
	
	
	
	
	
	
	
	

}
