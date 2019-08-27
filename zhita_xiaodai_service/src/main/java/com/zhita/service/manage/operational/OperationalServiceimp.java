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
import com.zhita.model.manage.Source;
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
			ord.setGesamtbetragderRvckzahlung(orders.getGesamtbetragderRvckzahlung());
			if(ord.getGesamtbetraguberfallNum()==null){
				ord.setGesamtbetraguberfallNum(0);
			}
			
			if(ord.getGesamtbetragderNum()==null){
				ord.setGesamtbetragderNum(0);
			}
			
			if(ord.getZahlderGesamtdarlehen() == null){
				ord.setZahlderGesamtdarlehen(0);
			}
			if(ord.getGesamtbetraguberfallNum() == null){
				ord.setGesamtbetraguberfallNum(0);
			}
			
			
			System.out.println(orders.getGesamtbetraguberfallNum()+"AAA"+orders.getGesamtbetragderNum());
			if(ord.getGesamtbetraguberfallNum() !=null && orders.getGesamtbetragderNum()!=null){
				if(ord.getGesamtbetragderNum() != 0){
					ord.setCollectionData((double) ((ord.getGesamtbetraguberfallNum()/ord.getGesamtbetragderNum())*100));
				}else{
					ord.setCollectionData((double) 0);
				}
				
			}else{
				ord.setCollectionData((double) 0);
			}
			System.out.println("催收率:"+ord.getCollectionData());
			orde.add(ord);
			System.out.println("时间:"+ord.getRemittanceTime());
			map.put("Repayment", orde);
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
				if(orders.getZahlderGesamtdarlehen() == null){
					orders.setZahlderGesamtdarlehen(0);
				}
				
				
				ord.setGesamtbetraguberfalligerBetrag(or.getGesamtbetraguberfalligerBetrag());
				ord.setGesamtbetraguberfallNum(or.getGesamtbetraguberfallNum());
				ord.setGesamtbetragderNum(orders.getGesamtbetragderNum());
				ord.setGesamtbetragderRvckzahlung(orders.getGesamtbetragderRvckzahlung());
				ord.setRemittanceTime(stime.get(i));
				System.out.println("时间:"+ord.getRemittanceTime());
				if(ord.getGesamtbetraguberfallNum()==null){
					ord.setGesamtbetraguberfallNum(0);
				}
				
				if(ord.getGesamtbetragderNum()==null){
					ord.setGesamtbetragderNum(0);
				}
				if(ord.getGesamtbetraguberfallNum() !=null && orders.getGesamtbetragderNum()!=null){
					ord.setCollectionData((double) ((ord.getGesamtbetraguberfallNum()*100)   / (ord.getGesamtbetragderNum()*100)));
				}else{
					ord.setCollectionData((double) 0);
				}
				
				orde.add(ord);
				map.put("PageUtil", pages);
				map.put("Repayment", orde);
			}
		}
		return map;
		
		
	}



	@Override
	public Map<String, Object> CollectionData(Orderdetails orde) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orders> ordesa = new ArrayList<Orders>();
		if(orde.getStart_time()==null){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stime = sim.format(new Date());
			orde.setStart_time(stime+" 00:00:00");
			orde.setEnd_time(stime+" 23:59:59");
			try {
				orde.setStart_time(Timestamps.dateToStamp1(orde.getStart_time()));
				orde.setEnd_time(Timestamps.dateToStamp1(orde.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			Orders ord = operdao.OneCollectionData(orde);//获取逾期笔数   逾期金额    逾期罚息
			if(ord.getMakeLoans()==null){
				ord.setMakeLoans(new BigDecimal(0));
			}
			
			if(ord.getInterestPenaltySum()==null){
				ord.setInterestPenaltySum(new BigDecimal(0));
			}
			
			Integer a = operdao.CollectionNumSSA(orde);
			if(a==null){
				a=0;
			}
			ord.setPassrate(a);//催收次数
			Integer b = operdao.CollectionOrders(orde);
			if(b==null){
				b=0;
			}
			ord.setBeoverdue(b);//催收笔数
			orde.setOrderStatus("4");
			Integer c = operdao.OrderOKCollection(orde);
			if(c==null){
				c=0;
			}
			ord.setBaddebt(c);//查询坏账数
			orde.setOrderStatus("3");
			Integer d = operdao.OrderOKCollection(orde);
			if(d==null){
				d=0;
			}
			
			ord.setChenggNum(d);//成功数
			if(ord.getChenggNum()!=0 && ord.getCollection_count() !=0){
				ord.setChenggData((double) ((ord.getChenggNum()/ord.getCollection_count())*100));
			}else{
				ord.setChenggData((double)0);
			}
			
			ord.setOrderCreateTime(stime);
			System.out.println("催收率:"+ord.getChenggData());
			ordesa.add(ord);
			map.put("Orderdetails", ordesa);
		}else{
			List<String> stimes = DateListUtil.getDays(orde.getStart_time(), orde.getEnd_time());
			for(int i = 0;i<stimes.size();i++){
				orde.setStart_time(stimes.get(i)+" 00:00:00");
				orde.setEnd_time(stimes.get(i)+" 23:59:59");
				try {
					orde.setStart_time(Timestamps.dateToStamp1(orde.getStart_time()));
					orde.setEnd_time(Timestamps.dateToStamp1(orde.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				Orders ord = operdao.OneCollectionData(orde);//获取逾期笔数   逾期金额    逾期罚息
				if(ord.getMakeLoans()==null){
					ord.setMakeLoans(new BigDecimal(0));
				}
				
				if(ord.getInterestPenaltySum()==null){
					ord.setInterestPenaltySum(new BigDecimal(0));
				}
				
				Integer a = operdao.CollectionNumSSA(orde);
				if(a==null){
					a=0;
				}
				ord.setPassrate(a);//催收次数
				Integer b = operdao.CollectionOrders(orde);
				if(b==null){
					b=0;
				}
				ord.setBeoverdue(b);//催收笔数
				orde.setOrderStatus("4");
				Integer c = operdao.OrderOKCollection(orde);
				if(c==null){
					c=0;
				}
				ord.setBaddebt(c);//查询坏账数
				orde.setOrderStatus("3");
				Integer d = operdao.OrderOKCollection(orde);
				if(d==null){
					d=0;
				}
				ord.setChenggNum(d);//成功数
				ord.setOrderCreateTime(stimes.get(i));
				ordesa.add(ord);
				map.put("Orderdetails", ordesa);
			}
		}
		
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
		List<Source> draina = operdao.AllDrainage(companyId);
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
