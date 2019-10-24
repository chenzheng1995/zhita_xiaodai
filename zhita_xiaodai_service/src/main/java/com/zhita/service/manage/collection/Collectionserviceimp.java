package com.zhita.service.manage.collection;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Sys_user;
import com.zhita.util.DateListUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;


@Service
public class Collectionserviceimp implements Collectionservice{
	
	@Autowired
	private CollectionMapper collmapp;

	
	@Autowired
	private PostloanorderMapper pdap;
	
	
	@Autowired
	private PostloanorderMapper podao;
	
	
	@Override
	public Map<String, Object> allBeoverdueConnection(Collection coll) {
		System.out.println("订单编号:"+coll.getOrderNumber());
		Map<String, Object> map = new HashMap<String, Object>();
		PhoneDeal p = new PhoneDeal();
		Integer totalCount = collmapp.SelectTotalCount(coll);
		PageUtil pages = new PageUtil(coll.getPage(), totalCount);
		TuoMinUtil tm = new TuoMinUtil();
		coll.setPage(pages.getPage());
		pages.setTotalCount(totalCount);
		List<Orderdetails> orders = collmapp.Allorderdetails(coll);
		for(int i=0;i<orders.size();i++){
			String phon = tm.mobileEncrypt(p.decryption(orders.get(i).getPhone()));//脱敏
			orders.get(i).setPhone(phon);//手机号解密 
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setCompanyId(coll.getCompanyId());
			
			if(orders.get(i).getRealityBorrowMoney()==null){
				orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
			}
			
			if(orders.get(i).getRealityAccount()==null){
				orders.get(i).setRealityAccount(new BigDecimal(0));
			}
			orders.get(i).setDeferAfterReturntime(orders.get(i).getOrderCreateTime());
			System.out.println("订单编号:"+orders.get(i).getOrderId());
			if(orders.get(i).getInterestPenaltySum() == null){
				orders.get(i).setInterestPenaltySum(new BigDecimal(0));
			}
			orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum()));
			
			orders.get(i).getRealityAccount().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney()));
			coll.setUserId(orders.get(i).getUserId());
			orders.get(i).setUsernum(collmapp.UserNum(coll));
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			orders.get(i).setDefeNum(collmapp.SelectDefeNum(coll));
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			
			System.out.println("时间:"+orders.get(i).getOrderCreateTime()+"AAA"+orders.get(i).getDeferAfterReturntime()+"金额:"+orders.get(i).getOrder_money()
					+"利息:"+orders.get(i).getRealityBorrowMoney()+"CC:"+orders.get(i).getInterestPenaltySum()+"BB:"+orders.get(i).getInterestSum());
			//Deferred defe = collmapp.DefeSet(orders.get(i));
//			if(defe!=null){
//				orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(defe.getDeferBeforeReturntime()));
//				
//				
//				System.out.println("时间:"+orders.get(i).getOrderCreateTime()+"AAA"+orders.get(i).getDeferAfterReturntime());
//				orders.get(i).setOrder_money(orders.get(i).getInterestPenaltySum().add(orders.get(i).getRealityBorrowMoney()));
//			}else{
//				orders.get(i).setDeferAfterReturntime("/");
//			}
//			
			}
		map.put("Orderdetails", orders);
		map.put("pageutil", pages);
		return map;
		}
		

	
	
	@Override
	public Map<String, Object> Collectionmember(Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(companyId != null){
			Sys_user sy = new Sys_user();
			sy.setCompanyId(companyId);
			sy.setRoleName("催收");
			List<Sys_user> col = collmapp.CollectionAll(sy);
			map.put("collection_member", col);
		}else{
			map.put("code", 0);
			map.put("desc", "公司ID为空");
		}
		return map;
	}
	
	

	@Override
	public Map<String, Object> UpdateColl(Collection col) {
		PhoneDeal p = new PhoneDeal();
		if(col.getPhone() != null){
			col.setPhone(p.encryption(col.getPhone()));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		String[] star = col.getOrderIds().split(",");
		for(int i = 0;i<star.length;i++){
			Collection cola = new Collection();
			cola.setCollectionMemberId(col.getCollectionMemberId());
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String stime = sim.format(new Date());
			try {
				cola.setCollectionTime(Timestamps.dateToStamp1(stime));
			} catch (Exception e) {
				// TODO: handle exception
			}
			cola.setOrderId(Integer.valueOf(star[i]));
			cola.setDeleted("0");
			collmapp.addCollection(cola);
		}
		
			map.put("code", 200);
			map.put("desc", "已分配");
		return map;
	}
	
	

	@Override
	public Map<String, Object> BeoverdueYi(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		PhoneDeal p = new PhoneDeal();
		if(order.getPhone() != null){
			if(order.getPhone().length()!=0){
				order.setPhone(p.encryption(order.getPhone()));
			}
			
		}
		
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
			List<Orderdetails> orders = collmapp.SelectOrdersdetails(order);
			for(int i=0;i<orders.size();i++){
				if(orders.get(i).getCollectionStatus() == null){
					orders.get(i).setCollectionStatus("未催收");
				}
				orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
				orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
				orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectionTime()));
				System.out.println("逾期天数:"+orders.get(i).getOverdueNumberOfDays());
				
				
				orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum()));
				
				if(orders.get(i).getSurplus_money()==null){
					
					orders.get(i).setSurplus_money(new BigDecimal(0));
				}
				
				Deferred des = collmapp.DefeSet(orders.get(i));
				if(des != null ){
					orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(des.getDeferBeforeReturntime()));
					orders.get(i).setInterestOnArrears(des.getInterestOnArrears());
					orders.get(i).setOnceDeferredDay(des.getOnceDeferredDay());
					orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
					System.out.println(des.getDeferAfterReturntime());
				}else{
					orders.get(i).setDeferAfterReturntime("/");
				}
				
				if(orders.get(i).getRealityBorrowMoney()==null){
					orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
				}
				
				if(orders.get(i).getRealityAccount()==null){
					orders.get(i).setRealityAccount(new BigDecimal(0));
				}
				
				int a = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
				if(a==0){
					System.out.println("开始:"+orders.get(i).getOrderId());
					System.out.println(orders.get(i).getRealityBorrowMoney()+":1");
					System.out.println(orders.get(i).getInterestPenaltySum()+":1");
					System.out.println(orders.get(i).getTechnicalServiceMoney()+":1");
					orders.get(i).setRealityBorrowMoney(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney())));
				}
				
				
				
				orders.get(i).setUsernum(pdap.UserNum(orders.get(i).getUserId()));
				orders.get(i).setDefeNum(pdap.DefeUserNum(orders.get(i).getUserId()));
				
				
				
				orders.get(i).setRealityAccount(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestInAll()));
				orders.get(i).setInterestPenaltySum(orders.get(i).getInterestPenaltySum());
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			}
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
		PhoneDeal p = new PhoneDeal();
		if(coll.getPhone() != null){
			coll.setPhone(p.encryption(coll.getPhone()));
		}
		
		if(coll.getStart_time() == null){
			SimpleDateFormat sima = new SimpleDateFormat("yyyy-MM-dd");
			String stimea = sima.format(new Date());
			Calendar calendar = Calendar.getInstance();
			Date date = null;
			Integer day = pdap.SelectHuan(coll.getCompanyId());//获取天数
			calendar.add(calendar.DATE, -day);//把日期往后增加n天.正数往后推,负数往前移动 
			date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
			String c = sima.format(date);//结束时间
			String b = sima.format(new Date());
			coll.setStart_time(c+" 00:00:00");
			coll.setEnd_time(b+" 23:59:59");
		}
		
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
			co.setSameday(collmapp.SelectEctuib(coll));//承诺还款
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
			if(co!=null){
				colles.add(co);
			}
			map.put("Collection", colles);
		}else{
			List<String> stimes = DateListUtil.getDays(coll.getStart_time(), coll.getEnd_time());
			Collections.reverse(stimes); // 倒序排列 
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
				if(co != null){
					colles.add(co);
				}
				map.put("Collection", colles);
			}
		}
		return map;
	}



	@Override
	public Map<String, Object> FenpeiWeiCollection(Collection col) {
		Map<String, Object> map = new HashMap<String, Object>();
		PhoneDeal p = new PhoneDeal();
		if(col.getPhone() != null){
			if(col.getPhone().length()!=0){
				col.setPhone(p.encryption(col.getPhone()));
			}
		}
		
			Integer totalCount = collmapp.AllCountNum(col);
			PageUtil pages = new PageUtil(col.getPage(),totalCount);
			col.setPage(pages.getPage());
			List<Orderdetails> orders = collmapp.FenpeiCollection(col);
			for(int i=0;i<orders.size();i++){
				orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
				orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
				orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getDeferBeforeReturntime()));
				orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
				orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectionTime()));
				
				orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum()));
				
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
				Deferred des = collmapp.DefeSet(orders.get(i));
				if(des!=null){
					orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(des.getDeferAfterReturntime()));
				}else{
					orders.get(i).setDeferAfterReturntime("/");
				}
				
				if(orders.get(i).getRealityBorrowMoney()==null){
					orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
				}
				
				if(orders.get(i).getRealityAccount()==null){
					orders.get(i).setRealityAccount(new BigDecimal(0));
				}
				int a = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
				if(a==0){
					orders.get(i).setRealityBorrowMoney(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney())));
				}
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			}
			map.put("Orderdetails", orders);
			map.put("PageUtil", pages);
		return map;
	}



	@Override
	public Map<String, Object> YiCollection(Collection col) {
		Map<String, Object> map = new HashMap<String, Object>();
		PhoneDeal p = new PhoneDeal();
		if(col.getPhone() != null){
			if(col.getPhone().length()!=0){
				col.setPhone(p.encryption(col.getPhone()));
			}
		}
		try {
			col.setStart_time(Timestamps.dateToStamp1(col.getStart_time()));
			col.setEnd_time(Timestamps.dateToStamp1(col.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(col.getCompanyId() != null){
		Integer totalCount = collmapp.CollectionWeiTotalcount(col);
		PageUtil pages = new PageUtil(col.getPage(), totalCount);
		col.setPage(pages.getPage());
		List<Orderdetails> orders = collmapp.WeiControllerOrdetialis(col);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getDeferBeforeReturntime()));
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
			orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum()));
			if(orders.get(i).getSurplus_money()==null){
				orders.get(i).setSurplus_money(new BigDecimal(0));
			}
			
			if(orders.get(i).getRealityBorrowMoney()==null){
				orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
			}
			
			if(orders.get(i).getRealityAccount()==null){
				orders.get(i).setRealityAccount(new BigDecimal(0));
			}
			int a = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
			if(a==0){
				orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney()));
			}
			System.out.println(orders.get(i).getOrder_money());
			//orders.get(i).setSurplus_money(orders.get(i).getRealityBorrowMoney().subtract(orders.get(i).getRealityAccount()));
			orders.get(i).setCollNum(collmapp.CollNum(orders.get(i).getOrderId()));
			orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			BigDecimal des = collmapp.PrmoiseMoney(orders.get(i).getOrderId());
			orders.get(i).getRealityAccount().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney()));
			int ac = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
			if(ac==0){
				orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum()));
			}

			if(des != null){
				orders.get(i).setPromise_money(des);
			}else{
				
				orders.get(i).setPromise_money(new BigDecimal(0));
			}
			
			
		}
		map.put("Orderdetails", orders);
		map.put("pageutil", pages);
		}else{
			map.put("Orderdetails", "0");
			map.put("pageutil", "数据异常");
		}
		return map;
	}



	@Override
	public Map<String, Object> AddColloetails(Collection col) {
		PhoneDeal p = new PhoneDeal();
		if(col.getPhone() != null){
			col.setPhone(p.encryption(col.getPhone()));
		}
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
		PhoneDeal p = new PhoneDeal();
		if(coll.getPhone() != null){
			coll.setPhone(p.encryption(coll.getPhone()));
		}

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
			
			
			List<Collection> co  = collmapp.Collectionmem(coll);//催收员名称   催收数
			for(int i=0;i<co.size();i++){
				if(co.get(i) != null){
					coll.setCollectionStatus("承诺还款");
					Integer sameday = collmapp.SelectEctuib(coll);
					if(sameday==null){
						sameday=0;
					}
					co.get(i).setSameday(sameday);//承诺还款
					coll.setOrderStatus("2");
					Integer paymentmade = collmapp.SelectcollectionStatusAs(coll);
					if(paymentmade==null){
						paymentmade=0;
					}
					co.get(i).setPaymentmade(paymentmade);//未还清
					coll.setOrderStatus("4");
					Integer connected = collmapp.SelectcollectionStatusAs(coll);
					if(connected==null){
						connected=0;
					}
					co.get(i).setConnected(connected);//累计坏账数
					
					if(co.get(i).getSameday()!=0){
						long collNumdata = ((co.get(i).getSameday()/co.get(i).getOrderNum())/100);
						co.get(i).setCollNumdata(String.valueOf(collNumdata));
					}else{
						a = new BigDecimal(0);
						co.get(i).setCollNumdata(String.valueOf(a));
					}
					co.get(i).setRealtime(stime);
					
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
						as.get(j).setSameday(collmapp.SelectEctuib(coll));//承诺还款
						coll.setOrderStatus("2");
						as.get(j).setPaymentmade(collmapp.SelectcollectionStatusAs(coll));//未还清
						coll.setOrderStatus("4");
						as.get(j).setConnected(collmapp.SelectcollectionStatusAs(coll));//累计坏账数
						
						if(as.get(j).getSameday()!=0){
							long collNumdata = ((as.get(i).getSameday()/as.get(i).getOrderNum())/100);
							as.get(i).setCollNumdata(String.valueOf(collNumdata));
						}else{
							a = new BigDecimal(0);
							as.get(j).setCollNumdata(String.valueOf(a));
						}
						as.get(j).setRealtime(stimes.get(i));
						
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




	@Override
	public List<Orderdetails> allBeoverdueConnectionCollection(Collection col) {
		List<Orderdetails> orders = collmapp.Allorderdetails(col);
		PhoneDeal p = new PhoneDeal();
		Integer totalCount = collmapp.SelectTotalCount(col);
		PageUtil pages = new PageUtil(col.getPage(), totalCount);
		TuoMinUtil tm = new TuoMinUtil();
		col.setPage(pages.getPage());
		pages.setTotalCount(totalCount);
		for(int i=0;i<orders.size();i++){
			String phon = tm.mobileEncrypt(p.decryption(orders.get(i).getPhone()));//脱敏
			orders.get(i).setPhone(phon);//手机号解密 
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setCompanyId(col.getCompanyId());
			if(orders.get(i).getRealityBorrowMoney()==null){
				orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
			}
			if(orders.get(i).getRealityAccount()==null){
				orders.get(i).setRealityAccount(new BigDecimal(0));
			}
			int a = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
			if(a==0){
				orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney()));
			}
			orders.get(i).setDeferAfterReturntime(orders.get(i).getOrderCreateTime());
			System.out.println("订单编号:"+orders.get(i).getOrderId());
			if(orders.get(i).getInterestPenaltySum() == null){
				orders.get(i).setInterestPenaltySum(new BigDecimal(0));
			}
			System.out.println("金额:"+orders.get(i).getRealityBorrowMoney()+"金额2:"+orders.get(i).getInterestPenaltySum()+"借款金额:"+orders.get(i).getCca());
			orders.get(i).setOrder_money(orders.get(i).getCca().add(orders.get(i).getInterestPenaltySum()));
			
			orders.get(i).getRealityAccount().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney()));
			
			
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			
			System.out.println("时间:"+orders.get(i).getOrderCreateTime()+"AAA"+orders.get(i).getDeferAfterReturntime()+"金额:"+orders.get(i).getOrder_money()
					+"利息:"+orders.get(i).getRealityBorrowMoney()+"CC:"+orders.get(i).getInterestPenaltySum()+"BB:"+orders.get(i).getInterestSum());
		}
			return orders;
	}




	@Override
	public Integer SelectTotalCount(Collection col) {
		return collmapp.SelectTotalCount(col);
	}




	@Override
	public List<Orderdetails> BeoverdueYiCollection(Orderdetails order) {
		
		PhoneDeal p = new PhoneDeal();
		if(order.getPhone() != null){
			if(order.getPhone().length()!=0){
				order.setPhone(p.encryption(order.getPhone()));
			}
			
		}
		
		try {
			order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
			order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		List<Orderdetails> orders = collmapp.SelectOrdersdetails(order);
		
		
		for(int i=0;i<orders.size();i++){
			if(orders.get(i).getCollectionStatus() == null){
				orders.get(i).setCollectionStatus("未催收");
			}
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectionTime()));
			System.out.println("逾期天数:"+orders.get(i).getOverdueNumberOfDays());
			if(orders.get(i).getMakeLoans() != null && orders.get(i).getInterestPenaltySum() != null){
				orders.get(i).setOrder_money(orders.get(i).getMakeLoans().add(orders.get(i).getInterestPenaltySum()));
			}else if(orders.get(i).getInterestPenaltySum() != null){
				orders.get(i).setOrder_money(orders.get(i).getInterestPenaltySum());
			}else{
				orders.get(i).setOrder_money(orders.get(i).getMakeLoans());
			}
			
			if(orders.get(i).getSurplus_money()==null){
				
				orders.get(i).setSurplus_money(new BigDecimal(0));
			}
			
			Deferred des = collmapp.DefeSet(orders.get(i));
			if(des != null ){
				orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(des.getDeferBeforeReturntime()));
				orders.get(i).setInterestOnArrears(des.getInterestOnArrears());
				orders.get(i).setOnceDeferredDay(des.getOnceDeferredDay());
				orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(des.getDeferAfterReturntime()));
				System.out.println(des.getDeferAfterReturntime());
			}else{
				orders.get(i).setDeferAfterReturntime("/");
			}
			
			if(orders.get(i).getRealityBorrowMoney()==null){
				orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
			}
			
			if(orders.get(i).getRealityAccount()==null){
				orders.get(i).setRealityAccount(new BigDecimal(0));
			}
			
			int a = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
			if(a==0){
				orders.get(i).setRealityBorrowMoney(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney())));
			}
			
			orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
		}
		return orders;
	}




	@Override
	public List<Collection> ColldetailsYiCollection(Collection coll) {
		
		PhoneDeal p = new PhoneDeal();
		if(coll.getPhone() != null){
			coll.setPhone(p.encryption(coll.getPhone()));
		}
		
		if(coll.getStart_time() == null){
			SimpleDateFormat sima = new SimpleDateFormat("yyyy-MM-dd");
			String stimea = sima.format(new Date());
			Calendar calendar = Calendar.getInstance();
			Date date = null;
			Integer day = pdap.SelectHuan(coll.getCompanyId());//获取天数
			calendar.add(calendar.DATE, -day);//把日期往后增加n天.正数往后推,负数往前移动 
			date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
			String c = sima.format(date);//结束时间
			String b = sima.format(new Date());
			coll.setStart_time(c+" 00:00:00");
			coll.setEnd_time(b+" 23:59:59");
		}
		
		List<Collection> colles = new ArrayList<Collection>();
		
		List<String> stimes = DateListUtil.getDays(coll.getStart_time(), coll.getEnd_time());
		Collections.reverse(stimes); // 倒序排列 
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
			if(co != null){
				colles.add(co);
			}
		}
		return colles;
	}




	@Override
	public List<Collection> CollectionmemberUserlv(Collection coll) {
		
		PhoneDeal p = new PhoneDeal();
		if(coll.getPhone() != null){
			coll.setPhone(p.encryption(coll.getPhone()));
		}
		
		if(coll.getStart_time() == null){
			SimpleDateFormat sima = new SimpleDateFormat("yyyy-MM-dd");
			String stimea = sima.format(new Date());
			Calendar calendar = Calendar.getInstance();
			Date date = null;
			Integer day = pdap.SelectHuan(coll.getCompanyId());//获取天数
			calendar.add(calendar.DATE, -day);//把日期往后增加n天.正数往后推,负数往前移动 
			date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
			String c = sima.format(date);//结束时间
			String b = sima.format(new Date());
			coll.setStart_time(c+" 00:00:00");
			coll.setEnd_time(b+" 23:59:59");
		}
		
		List<Collection> colles = new ArrayList<Collection>();
		
		List<String> stimes = DateListUtil.getDays(coll.getStart_time(), coll.getEnd_time());
		Collections.reverse(stimes); // 倒序排列 
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
			if(co != null){
				colles.add(co);
			}
		}
		return colles;
	}




	@Override
	public List<Orderdetails> FenpeiWeiCollectionAc(Collection col) {
		PhoneDeal p = new PhoneDeal();
		if(col.getPhone() != null){
			if(col.getPhone().length()!=0){
				col.setPhone(p.encryption(col.getPhone()));
			}
		}
		
			Integer totalCount = collmapp.AllCountNum(col);
			PageUtil pages = new PageUtil(col.getPage(),totalCount);
			col.setPage(pages.getPage());
			List<Orderdetails> orders = collmapp.FenpeiCollectionAc(col);
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
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
				Deferred des = collmapp.DefeSet(orders.get(i));
				if(des!=null){
					orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(des.getDeferAfterReturntime()));
				}else{
					orders.get(i).setDeferAfterReturntime("/");
				}
				
				if(orders.get(i).getRealityBorrowMoney()==null){
					orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
				}
				
				if(orders.get(i).getRealityAccount()==null){
					orders.get(i).setRealityAccount(new BigDecimal(0));
				}
				int a = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
				if(a==0){
					orders.get(i).setRealityBorrowMoney(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney())));
				}else{
					orders.get(i).setRealityBorrowMoney(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum()));
				}
				orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			}
		return orders;
	}




	@Override
	public List<Orderdetails> YiCollectionAc(Collection col) {
		PhoneDeal p = new PhoneDeal();
		if(col.getPhone() != null){
			if(col.getPhone().length()!=0){
				col.setPhone(p.encryption(col.getPhone()));
			}
		}
		try {
			col.setStart_time(Timestamps.dateToStamp1(col.getStart_time()));
			col.setEnd_time(Timestamps.dateToStamp1(col.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = collmapp.CollectionWeiTotalcount(col);
		PageUtil pages = new PageUtil(col.getPage(), totalCount);
		col.setPage(pages.getPage());
		List<Orderdetails> orders = collmapp.WeiControllerOrdetialisAc(col);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getDeferBeforeReturntime()));
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
			orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum()));
			if(orders.get(i).getSurplus_money()==null){
				orders.get(i).setSurplus_money(new BigDecimal(0));
			}
			
			if(orders.get(i).getRealityBorrowMoney()==null){
				orders.get(i).setRealityBorrowMoney(new BigDecimal(0));
			}
			
			if(orders.get(i).getRealityAccount()==null){
				orders.get(i).setRealityAccount(new BigDecimal(0));
			}
			int a = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
			if(a==0){
				orders.get(i).getShouldReapyMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney()));
			}
			System.out.println(orders.get(i).getOrder_money());
			//orders.get(i).setSurplus_money(orders.get(i).getRealityBorrowMoney().subtract(orders.get(i).getRealityAccount()));
			orders.get(i).setCollNum(collmapp.CollNum(orders.get(i).getOrderId()));
			orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			BigDecimal des = collmapp.PrmoiseMoney(orders.get(i).getOrderId());
			orders.get(i).getRealityAccount().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney()));
			int ac = orders.get(i).getRealityBorrowMoney().compareTo(orders.get(i).getRealityAccount());
			if(ac==0){
				orders.get(i).setRealityBorrowMoney(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestPenaltySum().add(orders.get(i).getTechnicalServiceMoney())));
			}

			if(des != null){
				orders.get(i).setPromise_money(des);
			}else{
				
				orders.get(i).setPromise_money(new BigDecimal(0));
			}
			
			
		}
		return orders;
		
	}
	
	
	
	
	
	
	

}
