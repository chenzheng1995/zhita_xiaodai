package com.zhita.service.manage.postloanorders;

import java.math.BigDecimal;
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
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Overdue;
import com.zhita.util.DateListUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;

@Service
public class Postloanorderserviceimp implements Postloanorderservice{
	
	
	@Autowired
	private PostloanorderMapper postloanorder;
	
	
	@Autowired
	private CollectionMapper coldao;
	

	@Override
	public Map<String, Object> allpostOrders(Orderdetails details) {
		PhoneDeal p = new PhoneDeal();
		if(details.getPhone().length() != 0){
			details.setPhone(p.encryption(details.getPhone()));
		}
		try {
			details.setReturntime(System.currentTimeMillis()+"");
			details.setStart_time(Timestamps.dateToStamp(details.getStart_time()));
			details.setEnd_time(Timestamps.dateToStamp(details.getEnd_time()));
			details.setDeferBeforeReturntimeStatu_time(Timestamps.dateToStamp(details.getDeferBeforeReturntimeStatu_time()));
			details.setDeferBeforeReturntimeEnd_time(Timestamps.dateToStamp(details.getDeferBeforeReturntimeEnd_time()));
			details.setDeferAfterReturntimeStatu_time(Timestamps.dateToStamp(details.getDeferAfterReturntimeStatu_time()));
			details.setDeferAfterReturntimeEnd_time(Timestamps.dateToStamp(details.getDeferAfterReturntimeEnd_time()));
			details.setRealtimeStatu_time(Timestamps.dateToStamp(details.getRealtimeStatu_time()));
			details.setRealtimeEnd_time(Timestamps.dateToStamp(details.getRealtimeEnd_time()));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Integer totalCount = postloanorder.totalCount(details);
		PageUtil pages = new PageUtil(details.getPage(), totalCount);
		details.setPage(pages.getPage());
		details.setTotalCount(totalCount);
		List<Orderdetails> orderdetils = postloanorder.allOrderdetails(details);
		TuoMinUtil tm = new TuoMinUtil();
		for(int i=0;i<orderdetils.size();i++){
			int a = orderdetils.get(i).getRealityBorrowMoney().compareTo(orderdetils.get(i).getRealityAccount());
			if(a==0){
				orderdetils.get(i).getShouldReapyMoney().add(orderdetils.get(i).getInterestPenaltySum().add(orderdetils.get(i).getTechnicalServiceMoney()));
			}
			orderdetils.get(i).setOrderCreateTime(Timestamps.stampToDate(orderdetils.get(i).getOrderCreateTime()));
			orderdetils.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orderdetils.get(i).getDeferBeforeReturntime()));
			orderdetils.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orderdetils.get(i).getDeferAfterReturntime()));
			orderdetils.get(i).setRealtime(Timestamps.stampToDate(orderdetils.get(i).getRealtime()));
			Deferred defe =  coldao.DefNuma(orderdetils.get(i).getOrderId());
			orderdetils.get(i).setDefeMoney(defe.getInterestOnArrears());
			orderdetils.get(i).setMakeLoans(coldao.SelectMakeLoan(orderdetils.get(i).getOrderId()));
			String op = p.decryption(orderdetils.get(i).getPhone());
			orderdetils.get(i).setPhone(tm.mobileEncrypt(op));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", orderdetils);
		map.put("pageutil", pages);
		return map;
	}
	
	
	
	
	@Override
	public Map<String, Object> allpostOrdersBeoverdue(Orderdetails details) {
		PhoneDeal p = new PhoneDeal();
		if(details.getPhone().length() != 0){
			details.setPhone(p.encryption(details.getPhone()));
		}
		try {
			details.setReturntime(System.currentTimeMillis()+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
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
		PhoneDeal p = new PhoneDeal();
		if(order.getPhone() != null){
			order.setPhone(p.encryption(order.getPhone()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String stime = sim.format(new Date());
		try {
			order.setStart_time(Timestamps.dateToStamp1(stime+" 00:00:00"));
			order.setEnd_time(Timestamps.dateToStamp1(stime+" 23:59:59"));
			order.setShouldReturnTime(System.currentTimeMillis()+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		TuoMinUtil tm = new TuoMinUtil();
		Integer totalCount = postloanorder.WeiNum(order.getCompanyId());
			List<Integer> nodeid = postloanorder.OvOrderId(order.getCompanyId());//获取已分配订单ID
			if(nodeid.size() != 0){
				order.setIds(nodeid);
			}else{
				nodeid.add(0);
				order.setIds(nodeid);
			}
			PageUtil pages = null;
			if(totalCount != null){
				pages = new PageUtil(order.getPage(), totalCount);
				order.setPage(pages.getPage());
			}else{
				pages = new PageUtil(order.getPage(), 0);
				order.setPage(pages.getPage());
			}
			order.setIds(nodeid);
			List<Orderdetails> ordeids = postloanorder.SelectOrderDetails(order);//获取未逾期未分配订单
			for (int i = 0; i < ordeids.size(); i++) {
				ordeids.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(ordeids.get(i).getDeferBeforeReturntime()));
				ordeids.get(i).setDeferAfterReturntime(Timestamps.stampToDate(ordeids.get(i).getDeferAfterReturntime()));
				ordeids.get(i).setRealtime(Timestamps.stampToDate(ordeids.get(i).getRealtime()));
				ordeids.get(i).setOrderCreateTime(Timestamps.stampToDate(ordeids.get(i).getOrderCreateTime()));
				String op = p.decryption(ordeids.get(i).getPhone());
				ordeids.get(i).setInterestInAll(ordeids.get(i).getInterestSum());
				
				ordeids.get(i).setRealityBorrowMoney(ordeids.get(i).getRealityBorrowMoney().add(ordeids.get(i).getInterestSum()));
				
				int a = ordeids.get(i).getRealityBorrowMoney().compareTo(ordeids.get(i).getRealityAccount());
				
				if(a==0){
					ordeids.get(i).setRealityBorrowMoney(ordeids.get(i).getRealityBorrowMoney().add(ordeids.get(i).getInterestPenaltySum().add(ordeids.get(i).getTechnicalServiceMoney())));
				}
				
				System.out.println("金额:"+ordeids.get(i).getRealityAccount());
				ordeids.get(i).setPhone(tm.mobileEncrypt(op));
				System.out.println("天数："+ordeids.get(i).getOnceDeferredDay());
			}
			map.put("Orderdetails", ordeids);
			map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> CollectionOrderdet(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		PhoneDeal p = new PhoneDeal();
		if(order.getPhone() != null){
			if(order.getPhone().length()!=0){
				order.setPhone(p.encryption(order.getPhone()));
			}
		}
		TuoMinUtil tm = new TuoMinUtil();
		System.out.println(order.getCompanyId());
		System.out.println(order.getPage()+"CCCC");
		Integer totalCount = postloanorder.WeiNum(order.getCompanyId());
		System.out.println(totalCount);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> ordeids = postloanorder.AOrderDetails(order);//获取未逾期已分配订单
		for(int i=0;i<ordeids.size();i++){
			Orderdetails ord = new Orderdetails();
			ord.setOrderId(ordeids.get(i).getId());
			ord.setCompanyId(order.getCompanyId());
			Deferred defe = postloanorder.OneDeferred(ord);//获取延期后应还时间 
			if(defe.getDeferAfterReturntime()!=null){
				ordeids.get(i).setDeferAfterReturntime(Timestamps.stampToDate(ordeids.get(i).getDeferAfterReturntime()));//延期后应还时间
			}else{
				ordeids.get(i).setDeferAfterReturntime("/");//延期后应还时间
			}
			ordeids.get(i).setInterestInAll(ordeids.get(i).getInterestSum());
			ordeids.get(i).setInterestSum(ordeids.get(i).getRealityAccount().add(ordeids.get(i).getInterestSum()));
			
			
			ordeids.get(i).setDeferAfterReturntime(Timestamps.stampToDate(ordeids.get(i).getShouldReturnTime()));
			defe = coldao.DefNuma(ord.getOrderId());//获取延期次数   id    延期金额    interestOnArrears
			ordeids.get(i).setDefeNum(defe.getDefeNum());//延期次数
			if(defe.getInterestOnArrears() == null){
				defe.setInterestOnArrears(new BigDecimal(0));
			}
			ordeids.get(i).setDefeMoney(defe.getInterestOnArrears());
			String jiephone = p.decryption(ordeids.get(i).getPhone());//解密手机号
			ordeids.get(i).setPhone(tm.mobileEncrypt(jiephone));//脱敏
			
			ordeids.get(i).setOrderCreateTime(Timestamps.stampToDate(ordeids.get(i).getOrderCreateTime()));//时间转译  订单时间
			ordeids.get(i).setShouldReturnTime(Timestamps.stampToDate(ordeids.get(i).getShouldReturnTime()));//延期前应还时间
		
			ordeids.get(i).setCollectiondate(Timestamps.stampToDate(ordeids.get(i).getCollectiondate()));//分配时间
		}
		map.put("Orderdetails", ordeids);
		map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> CollectionRecovery(Orderdetails order) {
//		order.setShouldReturnTime(System.currentTimeMillis()+"");
//		PhoneDeal p = new PhoneDeal();
//		if(order.getPhone() != null){
//			order.setPhone(p.encryption(order.getPhone()));
//		}
//		try {
//			order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
//			order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		List<Collection> totalCount = postloanorder.DateNum(order);
//		Integer asa = null;
//		if(totalCount.size() != 0){
//			asa = totalCount.size();
//		}else{
//			asa = 0;
//		}
//		PageUtil pages = new PageUtil(order.getPage(), asa);
//		order.setPage(pages.getPage());
//		List<Collection> cols = postloanorder.CollDateNum(order);
//		for(int i=0;i<cols.size();i++){
//			order.setCollectiondate(cols.get(i).getCollectiondate());
//			Integer OrderIdNum = postloanorder.OrderIdNum(order);//订单总数
//			order.setIds(coldao.SelectCollectionId(order.getCompanyId()));
//			List<Integer> OverIdNum = postloanorder.OverIdNum(order);//逾前催收数
//			if(OrderIdNum != null && OverIdNum.size() != 0){
//				cols.get(i).setDialNum(OrderIdNum-OverIdNum.size());
//			}else{
//				cols.get(i).setDialNum(OverIdNum.size());
//			}
//			
//			try {
//				order.setCollectionTime(Timestamps.dateToStamp(cols.get(i).getCollectiondate()));
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			order.setOverdue_phonestaus("未接通");
//			cols.get(i).setNotconnected(postloanorder.connectedNum(order));
//			order.setOverdue_phonestaus("已接通");
//			cols.get(i).setConnected(postloanorder.connectedNum(order));
//			order.setOrderStatus("3");
//			cols.get(i).setSameday(postloanorder.StatusOrders(order));
//			order.setOrderStatus("0");
//			cols.get(i).setPaymentmade(postloanorder.StatusOrders(order));
//			if(cols.get(i).getPaymentmade() != 0 && cols.get(i).getCollection_count() != 0){
//				NumberFormat numberFormat = NumberFormat.getInstance();
//				numberFormat.setMaximumFractionDigits(2);
//				cols.get(i).setPaymentmadeData(numberFormat.format((float) cols.get(i).getPaymentmade() / (float) (cols.get(i).getPaymentmade()+cols.get(i).getSameday()) * 100));
//			}else{
//				cols.get(i).setPaymentmadeData("0");
//			}
//			cols.get(i).setCollectiondate(Timestamps.stampToDate1(cols.get(i).getCollectiondate()));
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Collection> cols = new ArrayList<Collection>();
		if(order.getStart_time()!=null){
			List<String> stimes = DateListUtil.getDays(order.getStart_time(), order.getEnd_time());
			for(int i=0;i<stimes.size();i++){
				order.setStart_time(stimes.get(i)+" 00:00:00");
				order.setEnd_time(stimes.get(i)+" 23:59:59");
				try {
					order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
					order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				Collection co = postloanorder.CollTimeData(order);//已分配总数    collection_count
				co.setCollectiondate(stimes.get(i));//日期
				order.setOverdue_phonestaus("未接通");
				co.setNotconnected(postloanorder.connectedNum(order));
				order.setOverdue_phonestaus("已接通");
				co.setConnected(postloanorder.connectedNum(order));
				order.setOrderStatus("3");
				co.setSameday(postloanorder.StatusOrders(order));//当天还款数
				order.setOrderStatus("0");
				co.setPaymentmade(postloanorder.StatusOrders(order));//当天未还款数
				if(co.getPaymentmade() != 0 && co.getCollection_count() != 0){
					NumberFormat numberFormat = NumberFormat.getInstance();
					numberFormat.setMaximumFractionDigits(2);
					co.setPaymentmadeData(numberFormat.format((float) co.getPaymentmade() / (float) (co.getPaymentmade()+co.getSameday()) * 100));
				}else{
					co.setPaymentmadeData("0");
				}
				System.out.println("数据:"+co.getCollection_count()+"日期:"+co.getCollectiondate()+"已接通:"+co.getConnected());
				cols.add(co);
			}
		}else{
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stimes = sim.format(new Date());
			order.setStart_time(stimes+" 00:00:00");
			order.setEnd_time(stimes+" 23:59:59");
			try {
				order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
				order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			Collection co = postloanorder.CollTimeData(order);//未分配总数    collection_count
			co.setCollectiondate(stimes);//日期
			order.setOverdue_phonestaus("未接通");
			co.setNotconnected(postloanorder.connectedNum(order));//未接通
			order.setOverdue_phonestaus("已接通");
			co.setConnected(postloanorder.connectedNum(order));//已接通
			order.setOrderStatus("3");
			co.setSameday(postloanorder.StatusOrders(order));//当天还款数
			order.setOrderStatus("0");
			co.setPaymentmade(postloanorder.StatusOrders(order));//当天未还款数
			if(co.getPaymentmade() != 0 && co.getCollection_count() != 0){
				NumberFormat numberFormat = NumberFormat.getInstance();
				numberFormat.setMaximumFractionDigits(2);
				co.setPaymentmadeData(numberFormat.format((float) co.getPaymentmade() / (float) (co.getPaymentmade()+co.getSameday()) * 100));
			}else{
				co.setPaymentmadeData("0");
			}
			System.out.println("数据:"+co.getCollection_count()+"日期:"+co.getCollectiondate()+"已接通:"+co.getConnected());
			cols.add(co);
			
		}
		map.put("Collection", cols);
 		return map;
	}




	@Override
	public Map<String, Object> OverdueUser(Orderdetails order) {
//		PhoneDeal p = new PhoneDeal();
//		try {
//			order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
//			order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		List<Collection> totalCount = postloanorder.MemberNum(order);
//		Integer asa = null;
//		if(totalCount.size() != 0){
//			asa = totalCount.size();
//		}else{
//			asa = 0;
//		}
//		PageUtil pages = new PageUtil(order.getPage(), asa);
//		order.setPage(pages.getPage());
//		List<Collection> cols = postloanorder.MemberName(order);
//		System.out.println(cols.size());
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		for(int i=0;i<cols.size();i++){
//			System.out.println("时间:"+cols.get(i).getCollectiondate());
//			//order.setCollectiondate(Timestamps.dateToStamp(cols.get(i).getCollectiondate()));
//			try {
//				calendar.add(Integer.valueOf(cols.get(i).getCollectiondate()), +1);
//				Date date = calendar.getTime();
//				order.setEnd_time(sim.format(date));
//				order.setStart_time(order.getCollectiondate());
//				order.setCollectiondate(cols.get(i).getCollectiondate());
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			
//			order.setCollectionMemberId(cols.get(i).getCollectionMemberId());
//			order.setOverdue_phonestaus("未接通");
//			cols.get(i).setNotconnected(postloanorder.Userphonestaus(order));
//			order.setOverdue_phonestaus("已接通");
//			cols.get(i).setConnected(postloanorder.Userphonestaus(order));
//			order.setOrderStatus("0");
//			cols.get(i).setSameday(postloanorder.UserOrderStatu(order));
//			order.setOrderStatus("3");
//			cols.get(i).setPaymentmade(postloanorder.UserOrderStatu(order));
//			if(cols.get(i).getPaymentmade() != 0 && cols.get(i).getCollection_count() != 0){
//				NumberFormat numberFormat = NumberFormat.getInstance();
//				numberFormat.setMaximumFractionDigits(2);
//				cols.get(i).setPaymentmadeData(numberFormat.format((float) cols.get(i).getPaymentmade() / (float) (cols.get(i).getPaymentmade()+cols.get(i).getSameday()) * 100));
//			}else{
//				cols.get(i).setPaymentmadeData("0");
//			}
//			cols.get(i).setCollectiondate(Timestamps.stampToDate1(cols.get(i).getCollectiondate()));
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<List<Collection>> cols = new ArrayList<List<Collection>>();
		if(order.getStart_time()!=null){
			List<String> stimes = DateListUtil.getDays(order.getStart_time(), order.getEnd_time());
			for(int i=0;i<stimes.size();i++){
				order.setStart_time(stimes.get(i)+" 00:00:00");
				order.setEnd_time(stimes.get(i)+" 23:59:59");
				try {
					order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
					order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				List<Collection> co = postloanorder.CollectionYIData(order);//获取逾前催收员名称  和  ID
				for(int j=0;i<co.size();i++){
				order.setCollectionMemberId(co.get(i).getCollectionMemberId());//把催收员ID set 进order的催收员ID
				
				co.get(j).setCollection_count(postloanorder.CoMentLLection(order));//获取催收员已分配订单数
				
				order.setOverdue_phonestaus("未接通");
				co.get(j).setNotconnected(postloanorder.connectedNum(order));
				order.setOverdue_phonestaus("已接通");
				co.get(j).setConnected(postloanorder.connectedNum(order));
				order.setOrderStatus("3");
				co.get(j).setSameday(postloanorder.StatusOrders(order));//当天还款数
				order.setOrderStatus("0");
				co.get(j).setPaymentmade(postloanorder.StatusOrders(order));//当天未还款数
				if(co.get(j).getPaymentmade() != 0 && co.get(j).getCollection_count() != 0){
					NumberFormat numberFormat = NumberFormat.getInstance();
					numberFormat.setMaximumFractionDigits(2);
					co.get(j).setPaymentmadeData(numberFormat.format((float) co.get(j).getPaymentmade() / (float) (co.get(j).getPaymentmade()+co.get(j).getSameday()) * 100));
				}else{
					co.get(j).setPaymentmadeData("0");
				}
				
				}
				cols.add(co);
				
			}
			
			
		}else{
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stimes = sim.format(new Date());
			order.setStart_time(stimes+" 00:00:00");
			order.setEnd_time(stimes+" 23:59:59");
			try {
				order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
				order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			List<Collection> co = postloanorder.CollectionYIData(order);//获取逾前催收员名称  和  ID
			for(int j=0;j<co.size();j++){
			order.setCollectionMemberId(co.get(j).getCollectionMemberId());//把催收员ID set 进order的催收员ID
			
			co.get(j).setCollection_count(postloanorder.CoMentLLection(order));//获取催收员已分配订单数
			
			order.setOverdue_phonestaus("未接通");
			co.get(j).setNotconnected(postloanorder.connectedNum(order));
			order.setOverdue_phonestaus("已接通");
			co.get(j).setConnected(postloanorder.connectedNum(order));
			order.setOrderStatus("3");
			co.get(j).setSameday(postloanorder.StatusOrders(order));//当天还款数
			order.setOrderStatus("0");
			co.get(j).setPaymentmade(postloanorder.StatusOrders(order));//当天未还款数
			
			if(co.get(j).getPaymentmade() != 0 && co.get(j).getSameday() != 0){
				NumberFormat numberFormat = NumberFormat.getInstance();
				numberFormat.setMaximumFractionDigits(2);
				co.get(j).setPaymentmadeData(numberFormat.format((float) co.get(j).getPaymentmade() / (float) (co.get(j).getPaymentmade()+co.get(j).getSameday()) * 100));
			}else{
				co.get(j).setPaymentmadeData("0");
			}
			
			}
			cols.add(co);
		}
		map.put("Collection", cols);
		return map;
	}




	@Override
	public Map<String, Object> MyOverdues(Orderdetails order) {
		PhoneDeal p = new PhoneDeal();
		Integer totalCount = postloanorder.MyOrderNum(order);
		PageUtil pages = null;
		if(totalCount != null){
			pages = new PageUtil(order.getPage(), totalCount);
			order.setPage(pages.getPage());
		}else{
			pages = new PageUtil(order.getPage(), 0);
			order.setPage(pages.getPage());
		}
		List<Orderdetails> orders = postloanorder.MyOrderdue(order);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));//实借订单
			orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));//逾前还款时间
			Deferred de = postloanorder.OneDeferred(orders.get(i));
			orders.get(i).setDeferAfterReturntime(de.getDeferAfterReturntime());
			if(orders.get(i).getDeferAfterReturntime() != null){
				orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));//逾后还款时间
			}else{
				orders.get(i).setDeferAfterReturntime("/");//逾后还款时间
			}
			orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectiondate()));//分配时间
			Deferred defe =  coldao.DefNuma(orders.get(i).getOrderId());
			orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			if(defe.getInterestOnArrears() != null){
				orders.get(i).setDefeMoney(defe.getInterestOnArrears());//延期金额
			}else{
				BigDecimal a = new BigDecimal(0);
				orders.get(i).setDefeMoney(a);//延期金额
			}
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", orders);
		map.put("pageutil", pages);
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
		PhoneDeal p = new PhoneDeal();
		if(order.getPhone() != null){
			if(order.getPhone().length() != 0){
				order.setPhone(p.encryption(order.getPhone()));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("数据:"+order.getOrderIds());
		String []strs=order.getOrderIds().split(",");
		if(strs.length != 0){
			for(int i=0;i<strs.length;i++){
				Overdue ovdeu = new Overdue();
				ovdeu.setCollectionMemberId(order.getCollectionMemberId());
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(sim.format(new Date()));
				
				
				
				try {
					System.out.println(Timestamps.dateToStamp1(sim.format(new Date())));
					ovdeu.setCollectiondate(Timestamps.dateToStamp1(sim.format(new Date())));//获取当前时间戳
				} catch (Exception e) {
					// TODO: handle exception
				}
				ovdeu.setOrderId(Integer.valueOf(strs[i]));
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
		PhoneDeal p = new PhoneDeal();
		if(order.getPhone() != null){
			if(order.getPhone().length()!=0){
				order.setPhone(p.encryption(order.getPhone()));
			}
			
		}
		
		if(order.getStart_time() != null && order.getEnd_time()!=null && !"".equals(order.getStart_time()) && !"".equals(order.getEnd_time())){
			try {
				order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
				order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if(!"".equals(order.getDeferBeforeReturntimeStatu_time()) && order.getDeferBeforeReturntimeStatu_time()!=null && order.getDeferBeforeReturntimeEnd_time()!=null && !"".equals(order.getDeferBeforeReturntimeEnd_time())){
			try {
				order.setDeferBeforeReturntimeStatu_time(Timestamps.dateToStamp1(order.getDeferBeforeReturntimeStatu_time()));
				order.setDeferBeforeReturntimeEnd_time(Timestamps.dateToStamp1(order.getDeferBeforeReturntimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if(order.getDeferAfterReturntimeStatu_time()!=null && !"".equals(order.getDeferAfterReturntimeStatu_time()) && order.getDeferAfterReturntimeEnd_time()!=null && !"".equals(order.getDeferAfterReturntimeEnd_time())){
			try {
				order.setDeferAfterReturntimeStatu_time(Timestamps.dateToStamp1(order.getDeferAfterReturntimeStatu_time()));
				order.setDeferAfterReturntimeEnd_time(Timestamps.dateToStamp1(order.getDeferAfterReturntimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if(order.getRealtimeStatu_time()!=null && !"".equals(order.getRealtimeStatu_time())  && order.getRealtimeEnd_time()!=null && !"".equals(order.getRealtimeEnd_time())){
			try {
				order.setRealtimeStatu_time(Timestamps.dateToStamp1(order.getRealtimeStatu_time()));
				order.setRealtimeEnd_time(Timestamps.dateToStamp1(order.getRealtimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if(order.getStatus1()==null){
				order.setStatus1("2");
				order.setStatus2("3");
			
		}else if(order.getStatus1().equals("2")){
			order.setStatus2("2");
		}else if(order.getStatus1().equals("3")){
			order.setStatus2("3");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = postloanorder.YiHuanOrdersTotalCountOO(order);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> orders = postloanorder.YiHuanOrders(order);
		System.out.println("查询条数:"+orders.size());
		for(int i=0;i<orders.size();i++){
		orders.get(i).setCompanyId(order.getCompanyId());
		orders.get(i).setRealtime(Timestamps.stampToDate(orders.get(i).getRealtime()));
		Deferred defe = postloanorder.OneDeferred(orders.get(i));
		TuoMinUtil tm = new TuoMinUtil();
		Orderdetails qianzhi = postloanorder.SelectQianshouldReapyMoney(orders.get(i).getOrderId());//前置应还金额
		
		if(qianzhi.getRealityBorrowMoney().compareTo(qianzhi.getMakeLoans()) == 0){
			orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney());//应还总金额
		}else{
			orders.get(i).setOrder_money(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestSum()));//应还总金额
		}
		
		if(orders.get(i).getSurplus_money()==null){
			orders.get(i).setSurplus_money(new BigDecimal(0));
		}
		BigDecimal ca = orders.get(i).getInterestPenaltySum().add(orders.get(i).getRealityBorrowMoney());
		
		orders.get(i).setRealityBorrowMoney(orders.get(i).getShouldReapyMoney());
		
		System.out.println("111:"+orders.get(i).getRealityBorrowMoney()+"AA"+orders.get(i).getInterestSum());
		BigDecimal jianmian = postloanorder.JianMianmoney(orders.get(i).getOrderId());
		if(jianmian==null){
			jianmian=new BigDecimal(0);
		}
		
		BigDecimal sumMoney = postloanorder.BankMoney(orders.get(i).getOrderId());
		if(sumMoney == null){
			sumMoney = new BigDecimal(0);
		}
		
		sumMoney.add(jianmian);
		orders.get(i).setRealityBorrowMoney(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestPenaltySum()).add(orders.get(i).getInterestSum()));//应还金额
		
		
		System.out.println("应还总金额:"+orders.get(i).getOrder_money()+"实借金额:"+orders.get(i).getRealityAccount()+"放贷金额:"+orders.get(i).getMakeLoans()+"应还金额:"+orders.get(i).getRealityBorrowMoney());
		
		
		orders.get(i).setShijiMoney(orders.get(i).getRealityBorrowMoney().subtract(orders.get(i).getSurplus_money()));

		if(orders.get(i).getDeferAfterReturntime()==null){
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
		}else{
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
		}
		
		orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
		orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
		System.out.println("实还时间:"+orders.get(i).getRealtime());
		if(orders.get(i).getRealtime() != null && !("").equals(orders.get(i).getRealtime())){
			if(orders.get(i).getRealtime().length()!=0){
				orders.get(i).setRealtime(orders.get(i).getRealtime());
			}
		}else{
			orders.get(i).setRealtime("/");
		}
		orders.get(i).setPhone(tm.mobileEncrypt(orders.get(i).getPhone()));
		System.out.println("渠道名:"+orders.get(i).getSourceName()+"手机号:"+orders.get(i).getPhone()+"订单时间:"+orders.get(i).getOrderCreateTime()+"实还时间:"+orders.get(i).getRealtime());
		}
		map.put("Orderdetails", orders);
		map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> CollecOrders(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		PhoneDeal p = new PhoneDeal();
		TuoMinUtil tm = new TuoMinUtil();
		if(order.getPhone() != null ){
			if(order.getPhone().length()!=0){
				order.setPhone(p.encryption(order.getPhone()));
			}
			
		}
		
		if(order.getStart_time()!="" && order.getStart_time()!=null && order.getEnd_time()!=null && order.getEnd_time()!=""){
			try {
				order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
				order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(order.getDeferBeforeReturntimeStatu_time()!="" && order.getDeferBeforeReturntimeStatu_time()!=null && order.getDeferBeforeReturntimeEnd_time()!=null && order.getDeferBeforeReturntimeEnd_time()!=""){
			try {
				order.setDeferBeforeReturntimeStatu_time(Timestamps.dateToStamp1(order.getDeferBeforeReturntimeStatu_time()));
				order.setDeferBeforeReturntimeEnd_time(Timestamps.dateToStamp1(order.getDeferBeforeReturntimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(order.getDeferAfterReturntimeStatu_time()!=null && order.getDeferAfterReturntimeStatu_time()!="" && order.getDeferAfterReturntimeEnd_time()!=null && order.getDeferAfterReturntimeEnd_time()!=""){
			try {
				order.setDeferAfterReturntimeStatu_time(Timestamps.dateToStamp1(order.getDeferAfterReturntimeStatu_time()));
				order.setDeferAfterReturntimeEnd_time(Timestamps.dateToStamp1(order.getDeferAfterReturntimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(order.getRealtimeStatu_time()!=null && order.getRealtimeStatu_time()!="" && order.getRealtimeEnd_time()!=null && order.getRealtimeEnd_time()!=""){
			try {
				order.setRealtimeStatu_time(Timestamps.dateToStamp1(order.getRealtimeStatu_time()));
				order.setRealtimeEnd_time(Timestamps.dateToStamp1(order.getRealtimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
		
		
		order.setOrderStatus("1");
		Integer totalCount = postloanorder.YiHuanOrdersTotalCount(order);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> orders = postloanorder.HuaiZhangOrdersAA(order);
		for(int i=0;i<orders.size();i++){
		orders.get(i).setCompanyId(order.getCompanyId());
		Deferred defe = postloanorder.OneDeferred(orders.get(i));
		orders.get(i).setDeferBeforeReturntime(orders.get(i).getShouldReturnTime());
		String jiephone = p.decryption(orders.get(i).getPhone());
		orders.get(i).setPhone(tm.mobileEncrypt(jiephone));
		System.out.println(orders.get(i).getInterestSum());
		orders.get(i).setRealityBorrowMoney(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestSum()));//本期应还金额
		orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
		orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
		System.out.println("逾期罚金:"+orders.get(i).getInterestPenaltySum());
		if(orders.get(i).getRealtime() != null){
			if(orders.get(i).getRealtime().length()!=0){
				orders.get(i).setRealtime(Timestamps.stampToDate(orders.get(i).getRealtime()));
			}
		}else{
			orders.get(i).setRealtime("/");
		}
		orders.get(i).setOrder_money(orders.get(i).getInterestPenaltySum().add(orders.get(i).getRealityBorrowMoney()));//应还总金额
		
		Orderdetails qianzhi = postloanorder.SelectQianshouldReapyMoney(orders.get(i).getOrderId());//前置应还金额
		
		if(qianzhi.getRealityBorrowMoney().compareTo(qianzhi.getMakeLoans()) == 0){
			orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney());//应还总金额
		}else{
			orders.get(i).setOrder_money(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestSum()));//应还总金额
		}
		
		if(orders.get(i).getSurplus_money()==null){
			orders.get(i).setShijiMoney(orders.get(i).getRealityBorrowMoney());
		}
		
		}
		map.put("Orderdetails", orders);
		map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> HuaiZhangOrders(Orderdetails order) {
		PhoneDeal p = new PhoneDeal();
		TuoMinUtil tm = new TuoMinUtil();
		if(order.getPhone() != null){
			if(order.getPhone().length()!=0){
				order.setPhone(p.encryption(order.getPhone()));
			}
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(order.getStart_time()!="" && order.getStart_time()!=null && order.getEnd_time()!=null && order.getEnd_time()!=""){
			try {
				order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
				order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(order.getDeferBeforeReturntimeStatu_time()!="" && order.getDeferBeforeReturntimeStatu_time()!=null && order.getDeferBeforeReturntimeEnd_time()!=null && order.getDeferBeforeReturntimeEnd_time()!=""){
			try {
				order.setDeferBeforeReturntimeStatu_time(Timestamps.dateToStamp1(order.getDeferBeforeReturntimeStatu_time()));
				order.setDeferBeforeReturntimeEnd_time(Timestamps.dateToStamp1(order.getDeferBeforeReturntimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(order.getDeferAfterReturntimeStatu_time()!=null && order.getDeferAfterReturntimeStatu_time()!="" && order.getDeferAfterReturntimeEnd_time()!=null && order.getDeferAfterReturntimeEnd_time()!=""){
			try {
				order.setDeferAfterReturntimeStatu_time(Timestamps.dateToStamp1(order.getDeferAfterReturntimeStatu_time()));
				order.setDeferAfterReturntimeEnd_time(Timestamps.dateToStamp1(order.getDeferAfterReturntimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(order.getRealtimeStatu_time()!=null && order.getRealtimeStatu_time()!="" && order.getRealtimeEnd_time()!=null && order.getRealtimeEnd_time()!=""){
			try {
				order.setRealtimeStatu_time(Timestamps.dateToStamp1(order.getRealtimeStatu_time()));
				order.setRealtimeEnd_time(Timestamps.dateToStamp1(order.getRealtimeEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		order.setOrderStatus("4");
		Integer totalCount = postloanorder.YiHuanOrdersTotalCount(order);
		PageUtil pages = new PageUtil(order.getPage(), totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> orders = postloanorder.HuaiZhangOrdersAA(order);
		for(int i=0;i<orders.size();i++){
		orders.get(i).setCompanyId(order.getCompanyId());
		orders.get(i).setPhone(tm.mobileEncrypt(p.decryption(orders.get(i).getPhone())));
		Deferred defe = postloanorder.OneDeferred(orders.get(i));
		
		if(defe.getDeferAfterReturntime()!=null){
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(defe.getDeferAfterReturntime()));
		}else{
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
		}
		
		System.out.println(orders.get(i).getInterestSum());
		orders.get(i).setRealityBorrowMoney(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestSum()));//本期应还金额
		orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
		orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
		System.out.println("逾期罚金:"+orders.get(i).getInterestPenaltySum());
		if(orders.get(i).getRealtime() != null){
			if(orders.get(i).getRealtime().length()!=0){
				orders.get(i).setRealtime(Timestamps.stampToDate(orders.get(i).getRealtime()));
			}
		}else{
			orders.get(i).setRealtime("/");
		}
		
		//orders.get(i).setOrder_money(orders.get(i).getInterestPenaltySum().add(orders.get(i).getRealityBorrowMoney()));//应还总金额
		Orderdetails qianzhi = postloanorder.SelectQianshouldReapyMoney(orders.get(i).getOrderId());//前置应还金额
		
		if(qianzhi.getRealityBorrowMoney().compareTo(qianzhi.getMakeLoans()) == 0){
			orders.get(i).setOrder_money(orders.get(i).getShouldReapyMoney());//应还总金额
		}else{
			orders.get(i).setOrder_money(orders.get(i).getRealityBorrowMoney().add(orders.get(i).getInterestSum()));//应还总金额
		}
		
		if(orders.get(i).getSurplus_money()!=null){
			orders.get(i).setShijiMoney(orders.get(i).getRealityBorrowMoney().subtract(orders.get(i).getSurplus_money()));
		}else{
			orders.get(i).setShijiMoney(orders.get(i).getRealityBorrowMoney());
		}
		}
		map.put("Orderdetails", orders);
		map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> WanchengUser(Overdue ov) {
		Integer updateId = postloanorder.UserOverdue(ov);
		Map<String, Object> map = new HashMap<String, Object>();
		if(updateId != null){
			map.put("code", 200);
			map.put("desc", "已完成联系");
		}else{
			map.put("code", 0);
			map.put("desc", "数据异常");
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
