package com.zhita.service.manage.operational;

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
import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.OperationalMapper;
import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.model.manage.Drainage_of_platform;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.OverdueClass;
import com.zhita.model.manage.Repayment;
import com.zhita.util.DateListUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;


@Service
public class OperationalServiceimp implements OperationalService{
	
	
	@Autowired
	private OperationalMapper operdao;
	
	
	
	@Autowired
	private CollectionMapper coldao;
	
	
	
	@Autowired
	private PostloanorderMapper pdap;
	
	
	
	

	@Override
	public Map<String, Object> PlatformsNu(Orderdetails ordera) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Orders> ordes = new ArrayList<Orders>();
//		try {
//			ordera.setStart_time(Timestamps.dateToStamp1(ordera.getStart_time()));
//			ordera.setEnd_time(Timestamps.dateToStamp1(ordera.getEnd_time()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		List<Integer> ids = coldao.SelectCollectionId(ordera.getCompanyId());
//		ordera.setIds(ids);//查询催收员  参数 公司ID

//		List<Orders> ordes = operdao.Ordersdata(ordera);
//		for(int i=0;i<ordes.size();i++){
//			Orders ord = new Orders();
//			ordes.get(i).setCompanyId(ordera.getCompanyId());
//			ord = operdao.GesamtbetragderDarlehen(ordes.get(i));
//			ordes.get(i).setGesamtbetragderDarlehen(ord.getGesamtbetragderDarlehen());
//			ordes.get(i).setZahlderGesamtdarlehen(ord.getZahlderGesamtdarlehen());
//			
//			ord = operdao.GesamtbetragderRvckzahlung(ordes.get(i));
//			ordes.get(i).setGesamtbetragderRvckzahlung(ord.getGesamtbetragderRvckzahlung());
//			ordes.get(i).setGesamtbetragderNum(ord.getGesamtbetragderNum());
//			
//			ord = operdao.GesamtbetraguberfalligerBetrag(ordes.get(i));
//			ordes.get(i).setGesamtbetraguberfalligerBetrag(ord.getGesamtbetraguberfalligerBetrag());
//			ordes.get(i).setGesamtbetraguberfallNum(ord.getGesamtbetraguberfallNum());
//			
//			ord = operdao.GesamtbetragderVerbindlichkeiten(ordes.get(i));
//			ordes.get(i).setGesamtbetragderVerbindlichkeiten(ord.getGesamtbetragderVerbindlichkeiten());
//			ordes.get(i).setGesamtbetragdererNum(ord.getGesamtbetragdererNum());
//			
//			ordes.get(i).setOrderCreateTime(Timestamps.stampToDate1(ordes.get(i).getOrderCreateTime()));
//		}
//		map.put("Orders", ordes);
//		map.put("PageUtil", pages);
		if(ordera.getStart_time()==null){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String times = sim.format(new Date());
			ordera.setStart_time(times+" 00:00:00");
			ordera.setEnd_time(times+" 23:59:59");
			try {
				ordera.setStart_time(Timestamps.dateToStamp1(ordera.getStart_time()));
				ordera.setEnd_time(Timestamps.dateToStamp1(ordera.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			Integer totalCount = operdao.OrderNum(ordera);
			PageUtil pages = new PageUtil(ordera.getPage(), totalCount);
			ordera.setPage(pages.getPage());
			Orders ord = operdao.ReayMoney(ordera);//获取日期总放款金额   放款数
			Orders o = operdao.Gesamtb(ordera);//还款金额    还款数
			Orders or = operdao.CollMoney(ordera);//逾期金额   逾期数
			Orders os = operdao.HuaiMoney(ordera);//坏账金额  坏账笔数
			
			
			if(ord.getGesamtbetragderDarlehen() == null){//总还款金额
				
				ord.setGesamtbetragderDarlehen(new BigDecimal(0));
				System.out.println("数据输出:"+o.getGesamtbetragderRvckzahlung()+or.getGesamtbetraguberfalligerBetrag()+os.getAmountofbaddebts());
			}
			if(o.getGesamtbetragderRvckzahlung() == null){
				
				o.setGesamtbetragderRvckzahlung(new BigDecimal(0));
				System.out.println(o.getGesamtbetragderRvckzahlung());
				
			}
			if(or.getGesamtbetraguberfalligerBetrag() == null){
				
				or.setGesamtbetraguberfalligerBetrag(new BigDecimal(0));
				System.out.println(or.getGesamtbetraguberfalligerBetrag());
				
			}
			if(os.getAmountofbaddebts()==null){
				
				os.setAmountofbaddebts(new BigDecimal(0));
				System.out.println(os.getAmountofbaddebts());
			}
			ord.setGesamtbetragderRvckzahlung(o.getGesamtbetragderRvckzahlung());
			ord.setGesamtbetragderNum(o.getGesamtbetragderNum());
			ord.setGesamtbetraguberfalligerBetrag(or.getGesamtbetraguberfalligerBetrag());
			ord.setGesamtbetraguberfallNum(or.getGesamtbetraguberfallNum());
			ord.setAmountofbaddebts(os.getAmountofbaddebts());
			ord.setBaddebt(os.getBaddebt());
			ord.setRemittanceTime(times);
			System.out.println(ord.getGesamtbetragderDarlehen()+"总金额:"+ord.getGesamtbetragderDarlehen());
			System.out.println(ord.getGesamtbetragderRvckzahlung()+"总还款金额"+o.getGesamtbetragderRvckzahlung());
			System.out.println(ord.getGesamtbetraguberfalligerBetrag()+"总预期金额:"+or.getGesamtbetraguberfalligerBetrag());
			System.out.println(ord.getAmountofbaddebts()+"坏账金额:"+os.getAmountofbaddebts());
			ordes.add(ord);
			map.put("PageUtil", pages);
		}else{
			List<String> stime = DateListUtil.getDays(ordera.getStart_time(), ordera.getEnd_time());
			for(int i=0;i<stime.size();i++){
				ordera.setStart_time(stime.get(i)+" 00:00:00");
				ordera.setEnd_time(stime.get(i)+" 23:59:59");
				try {
					ordera.setStart_time(Timestamps.dateToStamp1(ordera.getStart_time()));
					ordera.setEnd_time(Timestamps.dateToStamp1(ordera.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				Integer totalCount = operdao.OrderNum(ordera);
				PageUtil pages = new PageUtil(ordera.getPage(), totalCount);
				ordera.setPage(pages.getPage());
				Orders ord = operdao.ReayMoney(ordera);//获取日期总放款金额   放款数
				Orders o = operdao.Gesamtb(ordera);//还款金额    还款数
				Orders or = operdao.CollMoney(ordera);//逾期金额   逾期数
				Orders os = operdao.HuaiMoney(ordera);//坏账金额  坏账笔数
				
				
				if(ord.getGesamtbetragderDarlehen() == null){//总还款金额
					
					ord.setGesamtbetragderDarlehen(new BigDecimal(0));
					System.out.println("数据输出:"+o.getGesamtbetragderRvckzahlung()+or.getGesamtbetraguberfalligerBetrag()+os.getAmountofbaddebts());
				}
				if(o.getGesamtbetragderRvckzahlung() == null){
					
					o.setGesamtbetragderRvckzahlung(new BigDecimal(0));
					System.out.println(o.getGesamtbetragderRvckzahlung());
					
				}
				if(or.getGesamtbetraguberfalligerBetrag() == null){
					
					or.setGesamtbetraguberfalligerBetrag(new BigDecimal(0));
					System.out.println(or.getGesamtbetraguberfalligerBetrag());
					
				}
				if(os.getAmountofbaddebts()==null){
					
					os.setAmountofbaddebts(new BigDecimal(0));
					System.out.println(os.getAmountofbaddebts());
				}
				ord.setGesamtbetragderRvckzahlung(o.getGesamtbetragderRvckzahlung());
				ord.setGesamtbetragderNum(o.getGesamtbetragderNum());
				ord.setGesamtbetraguberfalligerBetrag(or.getGesamtbetraguberfalligerBetrag());
				ord.setGesamtbetraguberfallNum(or.getGesamtbetraguberfallNum());
				ord.setAmountofbaddebts(os.getAmountofbaddebts());
				ord.setBaddebt(os.getBaddebt());
				ord.setRemittanceTime(stime.get(i));
				ordes.add(ord);
				map.put("PageUtil", pages);
			}
		}
		map.put("Orders", ordes);
		
	
		
		return map;
	}



	@Override
	public Map<String, Object> HuanKuan(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orders> orde = new ArrayList<Orders>();
//		try {
//			order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
//			order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		List<Integer> ids = coldao.SelectCollectionId(order.getCompanyId());

//		List<Repayment> ords = operdao.SelectRepayment(order);//获取还款时间   还款笔数  还款总金额 
//		for(int i=0;i<ords.size();i++){
//			ords.get(i).setRepaymentDate(Timestamps.stampToDate(ords.get(i).getRepaymentDate()));
//			Repayment re = operdao.SelectNodeRepayment(order);//获取已逾期笔数
//			if(re == null){
//				ords.get(i).setCollection_count(0);//逾期还款笔数
//				BigDecimal a = new BigDecimal("0");
//				ords.get(i).setCollection_money(a);//逾期还款金额
//			}else{
//				ords.get(i).setCollection_count(re.getCollection_count());//逾期还款笔数
//				ords.get(i).setCollection_money(re.getCollection_money());//逾期还款金额
//			}
//			
//			Orders o = new Orders();
//			o.setOrderCreateTime(ords.get(i).getOrderCreateTime());
//			Orders ord = operdao.GesamtbetragderDarlehen(o);
//			
//			ords.get(i).setCouNum(ord.getZahlderGesamtdarlehen());
//			if(ords.get(i).getCollection_count() != 0 && ords.get(i).getCollection_count() != null){
//				ords.get(i).setRepaymeny_collectiondata(ords.get(i).getRepayment_Count()/ords.get(i).getCollection_count());//还款率	
//			}else{
//				ords.get(i).setRepaymeny_collectiondata(0);//还款率	
//			}
//			ords.get(i).setOrderCreateTime(Timestamps.stampToDate1(ords.get(i).getOrderCreateTime()));
//			
//		}
//		map.put("PageUtil", pages);
//		map.put("Repayment", ords);
		if(order.getStart_time()==null){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stime = sim.format(new Date());
			order.setStart_time(stime+" 00:00:00");
			order.setEnd_time(stime+" 23:59:59");
			
			try {
				order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
				order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			List<Integer> ids = coldao.SelectCollectionId(order.getCompanyId());
			order.setIds(ids);//查询催收员  参数 公司ID
			List<Repayment> orNum = operdao.SelectRepaymentNum(order);
			Integer totalCount = null;
			if(orNum.size() != 0 && orNum != null){
				totalCount = orNum.size();
			}else{
				totalCount = 0;
			}
			PageUtil pages = new PageUtil(order.getPage(), totalCount);	//参数page pagesize
			order.setPage(pages.getPage());
			Orders orders = operdao.OrderHuan(order);//还款数   
			Orders or = operdao.CollMoney(order);//逾期金额   逾期数
			Orders ord = operdao.ReayMoney(order);//获取日期 总放款金额   放款数
			
			
			if(ord.getGesamtbetragderDarlehen() == null){//总还款金额
				
				ord.setGesamtbetragderDarlehen(new BigDecimal(0));
			}
			if(orders.getGesamtbetragderRvckzahlung() == null){
				
				orders.setGesamtbetragderRvckzahlung(new BigDecimal(0));
				
			}
			if(or.getGesamtbetraguberfalligerBetrag() == null){
				
				or.setGesamtbetraguberfalligerBetrag(new BigDecimal(0));
				System.out.println(or.getGesamtbetraguberfalligerBetrag());
				
			}
			ord.setRemittanceTime(stime);
			ord.setGesamtbetraguberfalligerBetrag(or.getGesamtbetraguberfalligerBetrag());
			ord.setGesamtbetraguberfallNum(or.getGesamtbetraguberfallNum());
			ord.setGesamtbetragderNum(orders.getGesamtbetragderNum());
			if(orders.getGesamtbetraguberfallNum()==null){
				orders.setGesamtbetraguberfallNum(0);
			}
			
			if(orders.getGesamtbetragderNum()==null){
				orders.setGesamtbetragderNum(0);
			}
			System.out.println(or.getGesamtbetraguberfalligerBetrag()+"A"+or.getGesamtbetraguberfallNum()+"A"+orders.getZahlderGesamtdarlehen());
			NumberFormat numberFormat = NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(2);
			System.out.println(orders.getGesamtbetraguberfallNum()+"AAA"+orders.getGesamtbetragderNum());
			orders.setCollectionData(numberFormat.format(((float) orders.getGesamtbetraguberfallNum()  / (float)   orders.getGesamtbetragderNum()) * 100));
			orde.add(orders);
			map.put("PageUtil", pages);
		}else{
			List<String> stime = DateListUtil.getDays(order.getStart_time(), order.getEnd_time());
			for(int i=0;i<stime.size();i++){
				order.setStart_time(stime.get(i)+" 00:00:00");
				order.setEnd_time(stime.get(i)+" 23:59:59");
				try {
					order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
					order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				List<Integer> ids = coldao.SelectCollectionId(order.getCompanyId());
				order.setIds(ids);//查询催收员  参数 公司ID
				List<Repayment> orNum = operdao.SelectRepaymentNum(order);
				Integer totalCount = null;
				if(orNum.size() != 0 && orNum != null){
					totalCount = orNum.size();
				}else{
					totalCount = 0;
				}
				System.out.println("数量"+totalCount);
				PageUtil pages = new PageUtil(order.getPage(), totalCount);	//参数page pagesize
				order.setPage(pages.getPage());
				Orders orders = operdao.OrderHuan(order);//还款数   
				Orders or = operdao.CollMoney(order);//逾期金额   逾期数
				Orders ord = operdao.ReayMoney(order);//获取日期 总放款金额   放款数
				if(ord.getGesamtbetragderDarlehen() == null){//总还款金额
					
					ord.setGesamtbetragderDarlehen(new BigDecimal(0));
				}
				if(orders.getGesamtbetragderRvckzahlung() == null){
					
					orders.setGesamtbetragderRvckzahlung(new BigDecimal(0));
					
				}
				if(or.getGesamtbetraguberfalligerBetrag() == null){
					
					or.setGesamtbetraguberfalligerBetrag(new BigDecimal(0));
					System.out.println(or.getGesamtbetraguberfalligerBetrag());
					
				}
				
				
				ord.setGesamtbetraguberfalligerBetrag(or.getGesamtbetraguberfalligerBetrag());
				ord.setGesamtbetraguberfallNum(or.getGesamtbetraguberfallNum());
				ord.setGesamtbetragderNum(orders.getGesamtbetragderNum());
				
				System.out.println(or.getGesamtbetraguberfalligerBetrag()+"A"+or.getGesamtbetraguberfallNum()+"A"+orders.getZahlderGesamtdarlehen());
				ord.setRemittanceTime(stime.get(i));
				if(orders.getGesamtbetraguberfallNum()==null){
					orders.setGesamtbetraguberfallNum(0);
				}
				
				if(orders.getGesamtbetragderNum()==null){
					orders.setGesamtbetragderNum(0);
				}
				NumberFormat numberFormat = NumberFormat.getInstance();
				numberFormat.setMaximumFractionDigits(2);
				orders.setCollectionData(numberFormat.format(((float) orders.getGesamtbetraguberfallNum()  / (float)   orders.getGesamtbetragderNum()) * 100));
				orde.add(orders);
				map.put("PageUtil", pages);
			}
		}
		
		map.put("Repayment", orde);
		return map;
	}



	@Override
	public Map<String, Object> CollectionData(Orderdetails orde) {
		try {
			orde.setStart_time(Timestamps.dateToStamp1(orde.getStart_time()));
			orde.setEnd_time(Timestamps.dateToStamp1(orde.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = operdao.CollectionDataNum(orde);
		PageUtil pages = new PageUtil(orde.getPage(), totalCount);
		orde.setPage(pages.getPage());
		List<Orders> ordesa = operdao.CollectionDatas(orde);
		for(int i=0;i<ordesa.size();i++){
			if(ordesa.get(i).getMakeLoans() != null && ordesa.get(i).getInterestPenaltySum() != null){
				ordesa.get(i).setGesamtbetraguberfalligerBetrag(ordesa.get(i).getMakeLoans().add(ordesa.get(i).getInterestPenaltySum()));
			}else if(ordesa.get(i).getMakeLoans() != null && ordesa.get(i).getInterestPenaltySum() == null){
				ordesa.get(i).setGesamtbetraguberfalligerBetrag(ordesa.get(i).getMakeLoans());
			}else if(ordesa.get(i).getMakeLoans() == null && ordesa.get(i).getInterestPenaltySum() != null){
				ordesa.get(i).setGesamtbetraguberfalligerBetrag(ordesa.get(i).getInterestPenaltySum());
			}
			
			Orders or = new Orders();
			orde.setOrderCreateTime(ordesa.get(i).getOrderCreateTime());
			List<Integer> ordIds = operdao.Beoverdue(orde);
			
			or = operdao.Pressformoney(orde);
			ordesa.get(i).setPassrate(or.getPressformoney());//催收次数
			ordesa.get(i).setBeoverdue(ordIds.size());//催收笔数
			
			
			orde.setCollectionStatus("承诺还款");
			or = operdao.Pressformoney(orde);
			ordesa.get(i).setChenggNum(or.getPressformoney());//成功数
			
			
			
			if(ordesa.get(i).getChenggNum() != null && ordesa.get(i).getChenggNum() != 0){
				ordesa.get(i).setChenggData(ordesa.get(i).getBeoverdue()/ordesa.get(i).getChenggNum());//催收成功率
			}else{
				ordesa.get(i).setChenggData(0);;//催收成功率
			}
			
			orde.setCollectionStatus("态度恶劣");
			ordesa.get(i).setBaddebt(operdao.SelecNumberCollection(orde));//查询坏账数
			ordesa.get(i).setOrderCreateTime(Timestamps.stampToDate(ordesa.get(i).getOrderCreateTime()));
		}
		map.put("PageUtil", pages);
		map.put("Orderdetails", ordesa);
		return map;
	}



	@Override
	public Map<String, Object> OrderBudget(Orderdetails orde) {
		try {
			orde.setStart_time(Timestamps.dateToStamp1(orde.getStart_time()));
			orde.setEnd_time(Timestamps.dateToStamp1(orde.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orders> ord = operdao.SelectOrderBudeNum(orde);
		Integer totalCount = null;
		if(ord.size() != 0){
			totalCount = ord.size();
		}else{
			totalCount = 0;
		}
		PageUtil pages = new PageUtil(orde.getPage(), totalCount);
		orde.setPage(pages.getPage());
		List<Orders> ords = operdao.SelectOrderBude(orde);
		for(int i=0;i<ords.size();i++){
			ords.get(i).setOrderCreateTime(Timestamps.stampToDate(ords.get(i).getOrderCreateTime()));
			ords.get(i).setActualrevenue(ords.get(i).getMakeLoans().subtract(ords.get(i).getRealityAccount()));
		}
		map.put("Pageutil", pages);
		map.put("orders", ords);
		return map;
		
	}



	@Override
	public Map<String, Object> AllDrainageOf(Integer companyId) {
		List<Drainage_of_platform> draina = operdao.AllDrainage(companyId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Drainage_of_platform", draina);
		return map;
	}



	@Override
	public Map<String, Object> AllOverdueclass(Integer companyId) {
		List<OverdueClass> overd = operdao.Overdue_class(companyId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("OverdueClass", overd);
		return map;
	}
	
	public BigDecimal getlastLine(int ordersId) {
		BigDecimal lastLine = operdao.getlastLine(ordersId);
		return lastLine;
	}

}
