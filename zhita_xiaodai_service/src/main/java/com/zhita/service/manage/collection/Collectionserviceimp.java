package com.zhita.service.manage.collection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Collection_member;
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.util.DateListUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;


@Service
public class Collectionserviceimp implements Collectionservice{
	
	@Autowired
	private CollectionMapper collmapp;

	
	
	@Autowired
	private PostloanorderMapper podao;
	
	
	@Override
	public Map<String, Object> allBeoverdueConnection(Collection coll) {
		Map<String, Object> map = new HashMap<String, Object>();
		coll.setRealtime(System.currentTimeMillis()+"");
		List<Integer> collIds = collmapp.SelectCollectionId(coll.getCompanyId());//根据公司ID 查询催收员ID
		if(collIds.size() != 0){
			 List<Integer> ids = collmapp.OrderIdMa(coll.getCompanyId());
			if(ids != null && ids.size() != 0){
				coll.setIds(ids); 
			}else{
				ids.add(0);
				System.out.println(ids.size());
				coll.setIds(ids); 
			}
					 
			Integer totalCount = collmapp.SelectTotalCount(coll);
			PageUtil pages = new PageUtil(coll.getPage(), totalCount);
			
			coll.setPage(pages.getPage());
			pages.setTotalCount(totalCount);
			PhoneDeal p = new PhoneDeal();
			List<Orderdetails> orders = collmapp.Allorderdetails(coll);
			for(int i=0;i<orders.size();i++){
				if(orders.get(i).getMakeLoans()!= null && orders.get(i).getInterestPenaltySum() != null){
					orders.get(i).setOrder_money(orders.get(i).getMakeLoans().add(orders.get(i).getInterestPenaltySum()));
				}else if(orders.get(i).getMakeLoans() == null){
					orders.get(i).setOrder_money(orders.get(i).getInterestPenaltySum());
				}else if(orders.get(i).getInterestPenaltySum() == null){
					orders.get(i).setOrder_money(orders.get(i).getMakeLoans());
				}
				Deferred des = collmapp.DefeSet(orders.get(i));
				Collection colla = collmapp.CollMen(orders.get(i));
				orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
				orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
				if(des != null && colla != null){
					orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(des.getDeferBeforeReturntime()));
					orders.get(i).setInterestOnArrears(des.getInterestOnArrears());
					orders.get(i).setOnceDeferredDay(des.getOnceDeferredDay());
					orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(des.getDeferAfterReturntime()));
					orders.get(i).setReallyName(colla.getReallyName());
					orders.get(i).setCollectionTime(colla.getCollectionTime());
					orders.get(i).setCollectionStatus(colla.getCollectionStatus());
				}
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
				
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
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collection> cols = new ArrayList<Collection>();
		String[] star = col.getOrderIds().split(",");
		for(int i = 0;i<star.length;i++){
			Collection cola = new Collection();
			cola.setCollectionMemberId(col.getCollectionMemberId());
			try {
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				cola.setCollectionTime(Timestamps.dateToStamp1(sim.format(new Date())));
			} catch (Exception e) {
				// TODO: handle exception
			}
			cola.setOrderId(Integer.valueOf(star[i]));
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
		
		try {
			order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
			order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		PageUtil pages=null;
		if(order.getCompanyId() != null){
			List<Integer> ids = collmapp.OrderIdMa(order.getCompanyId());
			pages = new PageUtil(order.getPage(), ids.size());
			order.setPage(pages.getPage());
			PhoneDeal p = new PhoneDeal();
			List<Orderdetails> orders = collmapp.SelectOrdersdetails(order);
			for(int i=0;i<orders.size();i++){
				orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
				orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
				orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectionTime()));
				if(orders.get(i).getMakeLoans() != null && orders.get(i).getInterestPenaltySum() != null){
					orders.get(i).setOrder_money(orders.get(i).getMakeLoans().add(orders.get(i).getInterestPenaltySum()));
				}else if(orders.get(i).getInterestPenaltySum() != null){
					orders.get(i).setOrder_money(orders.get(i).getInterestPenaltySum());
				}else{
					orders.get(i).setOrder_money(orders.get(i).getMakeLoans());
				}
				
				Deferred des = collmapp.DefeSet(orders.get(i));
				if(des != null ){
					orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(des.getDeferBeforeReturntime()));
					orders.get(i).setInterestOnArrears(des.getInterestOnArrears());
					orders.get(i).setOnceDeferredDay(des.getOnceDeferredDay());
					orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(des.getDeferAfterReturntime()));
				}
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
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
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collectiondetails> cols = collmapp.OneCollection(order);
		for(int i=0;i<cols.size();i++){
			cols.get(i).setCollection_time(Timestamps.stampToDate(cols.get(i).getCollection_time()));
		}
		map.put("Orderdetails", cols);
		return map;
	}



	@Override
	public Map<String, Object> Collectionmemberdetails(Collection coll) {
//		try {
//			coll.setStart_time(Timestamps.dateToStamp1(coll.getStart_time()));
//			coll.setEnd_time(Timestamps.dateToStamp1(coll.getEnd_time()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		List<Collection> totalCount = collmapp.SelectSumOrderNum(coll);
//		try {
//			coll.setStart_time(Timestamps.dateToStamp(coll.getStart_time()));
//			coll.setEnd_time(Timestamps.dateToStamp(coll.getEnd_time()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		Integer asa = null;
//		if(totalCount.size() != 0){
//			asa = totalCount.size();
//		}else{
//			asa = 0;
//		}
//		PageUtil pages = new PageUtil(coll.getPage(), asa);
//		coll.setPage(pages.getPage());
//		PhoneDeal p = new PhoneDeal();
//		List<Collection> colles = collmapp.SelectSumOrder(coll);
//		for(int i=0;i<colles.size();i++){
//			colles.get(i).setCompanyId(coll.getCompanyId());
//			colles.get(i).setOrderNum(collmapp.SelectOrderNum(colles.get(i)));//累计订单总数  参数  时间   公司ID
//			colles.get(i).setIds(collmapp.SelectCollectionId(coll.getCompanyId()));//根据公司ID 查询催收员ID
//			colles.get(i).setCollSum(collmapp.SelectCollectionNum(colles.get(i)));
//			colles.get(i).setCollectionStatus("承诺还款");
//			colles.get(i).setSameday(collmapp.SelectcollectionStatus(colles.get(i)));//承诺还款
//			colles.get(i).setCollectionStatus("电话无人接听");
//			colles.get(i).setPaymentmade(collmapp.SelectcollectionStatus(colles.get(i)));//未还清
//			colles.get(i).setCollectionStatus("态度恶劣");
//			colles.get(i).setConnected(collmapp.SelectcollectionStatus(colles.get(i)));//累计坏账数
//			if(colles.get(i).getConnected() != 0 && colles.get(i).getConnected() != null){
//				NumberFormat numberFormat = NumberFormat.getInstance();
//				numberFormat.setMaximumFractionDigits(2);
//				colles.get(i).setCollNumdata(numberFormat.format(((float) colles.get(i).getConnected() / (float) colles.get(i).getOrderNum()) * 100));
//			}else{
//				colles.get(i).setCollNumdata("0");
//			}
//			colles.get(i).setOrderCreateTime(Timestamps.stampToDate(colles.get(i).getOrderCreateTime()));
//			
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collection> colles = new ArrayList<Collection>();
		if(coll.getStart_time() == null){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stime = sim.format(new Date());
			coll.setStart_time(stime+" 00:00:00");
			coll.setEnd_time(stime+" 23:59:59");
			
			try {
				coll.setStart_time(Timestamps.dateToStamp1(coll.getStart_time()));
				coll.setEnd_time(Timestamps.dateToStamp1(coll.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			Collection co = collmapp.OneCollecti(coll);
			co.setCollection_count(collmapp.FenCol(coll));//分配订单数
			coll.setCollectionStatus("承诺还款");
			co.setSameday(collmapp.SelectcollectionStatuCC(coll));//承诺还款
			coll.setOrderStatus("2");
			co.setPaymentmade(collmapp.SelectcollectionStatusAs(coll));//未还清
			coll.setOrderStatus("4");
			co.setConnected(collmapp.SelectcollectionStatusAs(coll));//累计坏账数
			BigDecimal a=null;
			if(co.getSameday()!=0){
				a = new BigDecimal(((co.getSameday()*100)/(co.getOrderNum()*100)));
				co.setDataCol(a);
			}else{
				a = new BigDecimal(0);
				co.setDataCol(a);
			}
			
			System.out.println(co.getDataCol());
			co.setRealtime(stime);
			colles.add(co);
			map.put("Collection", colles);
		}else{
			List<String> stimes = DateListUtil.getDays(coll.getStart_time(), coll.getEnd_time());
			for(int i=0;i<stimes.size();i++){
				coll.setStart_time(stimes.get(i)+" 00:00:00");
				coll.setEnd_time(stimes.get(i)+" 23:59:59");
				
				try {
					coll.setStart_time(Timestamps.dateToStamp1(coll.getStart_time()));
					coll.setEnd_time(Timestamps.dateToStamp1(coll.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				Collection co = collmapp.OneCollecti(coll);
				co.setCollection_count(collmapp.FenCol(coll));//分配订单数
				coll.setCollectionStatus("承诺还款");
				co.setSameday(collmapp.SelectcollectionStatuCC(coll));//承诺还款
				coll.setOrderStatus("2");
				co.setPaymentmade(collmapp.SelectcollectionStatusAs(coll));//未还清
				coll.setOrderStatus("4");
				co.setConnected(collmapp.SelectcollectionStatusAs(coll));//累计坏账数
				BigDecimal a=null;
				if(co.getSameday()!=0){
					a = new BigDecimal(((co.getSameday()*100)/(co.getOrderNum()*100)));
					co.setDataCol(a);
				}else{
					a = new BigDecimal(0);
					co.setDataCol(a);
				}
				co.setRealtime(stimes.get(i));
				colles.add(co);
				map.put("Collection", colles);
			}
		}
		return map;
	}



	@Override
	public Map<String, Object> FenpeiWeiCollection(Collection col) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer as = 0;
		if(col.getCompanyId() != null){
				List<Integer> id = collmapp.SelectId(col.getCompanyId());
				if(id.size() != 0){
					col.setIds(id);
					as=id.size();
				}else{
					id.add(0);
					col.setIds(id);
				}
			}
			Integer totalCount = collmapp.AllCountNum(col);
			PageUtil pages = new PageUtil(col.getPage(),totalCount-as);
			col.setPage(pages.getPage());
			PhoneDeal p = new PhoneDeal();
			List<Orderdetails> orders = collmapp.FenpeiCollection(col);
			for(int i=0;i<orders.size();i++){
				orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
				orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
				orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getDeferBeforeReturntime()));
				orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
				orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectionTime()));
				if(orders.get(i).getRealityBorrowMoney() != null && orders.get(i).getInterestPenaltySum() != null){
					orders.get(i).setOrder_money(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestPenaltySum()));
				}else if(orders.get(i).getRealityBorrowMoney() != null && orders.get(i).getInterestPenaltySum() == null){
					orders.get(i).setOrder_money(orders.get(i).getRealityBorrowMoney());
				}else{
					orders.get(i).setOrder_money(orders.get(i).getInterestPenaltySum());
				}
				Deferred des = collmapp.DefeSet(orders.get(i));
				orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(des.getDeferAfterReturntime()));
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			}
			map.put("Orderdetails", orders);
			map.put("PageUtil", pages);
		return map;
	}



	@Override
	public Map<String, Object> YiCollection(Collection col) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			col.setStart_time(Timestamps.dateToStamp1(col.getStart_time()));
			col.setEnd_time(Timestamps.dateToStamp1(col.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(col.getCompanyId() != null){
		List<Integer> collIds = collmapp.SelectCollectionId(col.getCompanyId());//根据公司ID 查询催收员ID
		List<Integer> CollectionMemberIds = collmapp.SelectCollectionMemberIds(collIds);//查询已分配催收员订单ID
		List<Integer> coldetids = new ArrayList<Integer>();
		for(int i=0;i<CollectionMemberIds.size();i++){
			List<Integer> id = collmapp.SelectId(CollectionMemberIds.get(i));
			if(id.size() != 0 && id != null){
				coldetids.add(CollectionMemberIds.get(i));//查询已催收ID
			}else{
				coldetids.add(0);
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
		PhoneDeal p = new PhoneDeal();
		col.setPage(pages.getPage());
		List<Orderdetails> orders = collmapp.WeiControllerOrdetialis(col);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setBorrowTimeLimit(Timestamps.stampToDate(orders.get(i).getBorrowTimeLimit()));
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getDeferBeforeReturntime()));
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
			orders.get(i).setSurplus_money(orders.get(i).getRealityBorrowMoney().subtract(orders.get(i).getRealityAccount()));
			orders.get(i).setCollNum(collmapp.CollNum(orders.get(i).getOrderId()));
			orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
		}
		map.put("Orderdetails", orders);
		}else{
			map.put("Orderdetails", "0");
			map.put("Pageutil", "数据异常");
		}
		return map;
	}



	@Override
	public Map<String, Object> AddColloetails(Collection col) {
		Map<String, Object> map = new HashMap<String, Object>();
			try {
				col.setCollectionTime(System.currentTimeMillis()+"");
			} catch (Exception e) {
				// TODO: handle exception
			}
			col.setDeleted("0");
			Integer addId = collmapp.UpdateCollection(col);
			if(addId != null){
				map.put("code", 200);
				map.put("desc", "设置成功");
			}else{
				map.put("code", 0);
				map.put("desc", "设置失败");
			}
		
		
		return map;
	}





	@Override
	public Map<String, Object> CollectionmemberUser(Collection coll) {
//		try {
//			coll.setStart_time(Timestamps.dateToStamp1(coll.getStart_time()));
//			coll.setEnd_time(Timestamps.dateToStamp1(coll.getEnd_time()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		List<Collection> colleas = collmapp.SelectUserNum(coll);
//		Integer totalCount = null ;		
//		if(colleas.size() != 0){
//			totalCount = colleas.size();
//		}else{
//			totalCount = 0;
//		}
//		PageUtil pages = new PageUtil(coll.getPage(), totalCount);
//		coll.setPage(pages.getPage());
//		PhoneDeal p = new PhoneDeal();
//		List<Collection> colles = collmapp.Collectionmemberdetilas(coll);
//		for(int i=0;i<colles.size();i++){
//			colles.get(i).setCollectionTime(Timestamps.stampToDate(colles.get(i).getCollectionTime()));
//			colles.get(i).setCompanyId(coll.getCompanyId());
//			colles.get(i).setOrderNum(collmapp.SelectUserCollectionNum(colles.get(i)));
//			colles.get(i).setCollectionStatus("承诺还款");
//			colles.get(i).setConnected(collmapp.SelectUsercollectionStatus(colles.get(i)));//累计成功
//			colles.get(i).setCollectionStatus("承诺还清一部分");
//			colles.get(i).setSameday(collmapp.SelectUsercollectionStatus(colles.get(i)));//承诺还款
//			colles.get(i).setCollectionStatus("电话无人接听");
//			colles.get(i).setPaymentmade(collmapp.SelectUsercollectionStatus(colles.get(i)));//未还清
//			colles.get(i).setCollectionStatus("态度恶劣");
//			colles.get(i).setConnected(collmapp.SelectUsercollectionStatus(colles.get(i)));//累计坏账数
//			if(colles.get(i).getConnected() != 0 && colles.get(i).getConnected() != null){
//				NumberFormat numberFormat = NumberFormat.getInstance();
//				numberFormat.setMaximumFractionDigits(2);
//				colles.get(i).setCollNumdata(numberFormat.format(((float) colles.get(i).getConnected() / (float) colles.get(i).getOrderNum()) * 100));
//			}else{
//				colles.get(i).setCollNumdata("0");
//			}
//			
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal a=null;
		if(coll.getStart_time()==null){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stime = sim.format(new Date());
			coll.setStart_time(stime+" 00:00:00");
			coll.setEnd_time(stime+" 23:59:59");
			
			try {
				coll.setStart_time(Timestamps.dateToStamp1(coll.getStart_time()));
				coll.setEnd_time(Timestamps.dateToStamp1(coll.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			List<Collection> co = collmapp.Collectionmem(coll);//催收员名称   催收数
			for(int i=0;i<co.size();i++){
				if(co.get(i) != null){
					coll.setCollectionStatus("承诺还款");
					System.out.println("CCC:"+collmapp.SelectcollectionStatuCC(coll));
					co.get(i).setSameday(collmapp.SelectcollectionStatuCC(coll));//承诺还款
					coll.setOrderStatus("2");
					co.get(i).setPaymentmade(collmapp.SelectcollectionStatusAs(coll));//未还清
					coll.setOrderStatus("4");
					co.get(i).setConnected(collmapp.SelectcollectionStatusAs(coll));//累计坏账数
					
					if(co.get(i).getSameday()!=0){
						a = new BigDecimal(((co.get(i).getSameday()*100)/(co.get(i).getOrderNum()*100)));
						co.get(i).setCollNumdata(String.valueOf(a));
					}else{
						a = new BigDecimal(0);
						co.get(i).setCollNumdata(String.valueOf(a));
					}
					co.get(i).setRealtime(stime);
					
				}else{
					co.get(i).setRealtime(stime);
					co.get(i).setSameday(0);
					co.get(i).setPaymentmade(0);
					co.get(i).setConnected(0);
					a = new BigDecimal(0);
					co.get(i).setCollNumdata("0");
					co.get(i).setCollection_count(0);
				}
				
				map.put("Collections", co);
			}
			
		}else{
			List<String> stimes = DateListUtil.getDays(coll.getStart_time(), coll.getEnd_time());
			for (int i = 0; i < stimes.size(); i++) {
				coll.setStart_time(stimes.get(i)+" 00:00:00");
				coll.setEnd_time(stimes.get(i)+" 23:59:59");
				
				try {
					coll.setStart_time(Timestamps.dateToStamp1(coll.getStart_time()));
					coll.setEnd_time(Timestamps.dateToStamp1(coll.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				List<Collection> as = collmapp.Collectionmem(coll);//催收员名称   催收数
				for(int j = 0; j < as.size(); j++){
					if(as.get(j) != null){
						coll.setCollectionStatus("承诺还款");
						as.get(j).setSameday(collmapp.SelectcollectionStatuCC(coll));//承诺还款
						coll.setOrderStatus("2");
						as.get(j).setPaymentmade(collmapp.SelectcollectionStatusAs(coll));//未还清
						coll.setOrderStatus("4");
						as.get(j).setConnected(collmapp.SelectcollectionStatusAs(coll));//累计坏账数
						
						if(as.get(j).getSameday()!=0){
							a = new BigDecimal(((as.get(j).getSameday()*100)/(as.get(j).getOrderNum()*100)));
							as.get(j).setCollNumdata(String.valueOf(a));
						}else{
							a = new BigDecimal(0);
							as.get(j).setCollNumdata(String.valueOf(a));
						}
						as.get(j).setRealtime(stimes.get(i));
						
					}else{
						as.get(j).setRealtime(stimes.get(i));
						as.get(j).setReallyName("0");
						as.get(j).setSameday(0);
						as.get(j).setPaymentmade(0);
						as.get(j).setConnected(0);
						a = new BigDecimal(0);
						as.get(j).setCollNumdata("0");
						as.get(j).setCollection_count(0);
					}
				}
				
				
				map.put("Collections", as);
		}
		}
		
		return map;
	}



	@Override
	public Map<String, Object> JieShuCollection(Integer orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer updateId = collmapp.UpdateCollectionDelete(orderId);
		if(updateId != null){
			Integer OrderUpdate = collmapp.UpdateOrderStatus(orderId);
			if(OrderUpdate != null){
				map.put("code", "200");
				map.put("desc", "完成催收");
			}else{
				map.put("code", "0");
				map.put("desc", "数据异常");
			}
		}else{
			map.put("code", "0");
			map.put("desc", "数据异常");
		}
		return map;
	}



	@Override
	public Map<String, Object> AddCollOrders(Collectiondetails col) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			col.setCollection_time(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer addId = collmapp.AddCollOrders(col);
		if(addId != null){
			map.put("code", 200);
			map.put("desc", "添加成功");
		}else{
			map.put("code", 0);
			map.put("desc", "添加失败");
		}
		return map;
	}
	
	
	
	
	
	
	
	

}
