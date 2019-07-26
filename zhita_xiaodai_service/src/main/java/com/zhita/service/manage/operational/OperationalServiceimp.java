package com.zhita.service.manage.operational;

import java.math.BigDecimal;
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
		try {
			ordera.setStart_time(Timestamps.dateToStamp1(ordera.getStart_time()));
			ordera.setEnd_time(Timestamps.dateToStamp1(ordera.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> ids = coldao.SelectCollectionId(ordera.getCompanyId());
		ordera.setIds(ids);//查询催收员  参数 公司ID
		Integer totalCount = operdao.OrderNum(ordera);
		PageUtil pages = new PageUtil(ordera.getPage(), totalCount);
		ordera.setPage(pages.getPage());
		List<Orders> ordes = operdao.Ordersdata(ordera);
		for(int i=0;i<ordes.size();i++){
			Orders ord = new Orders();
			ordes.get(i).setCompanyId(ordera.getCompanyId());
			ord = operdao.GesamtbetragderDarlehen(ordes.get(i));
			ordes.get(i).setGesamtbetragderDarlehen(ord.getGesamtbetragderDarlehen());
			ordes.get(i).setZahlderGesamtdarlehen(ord.getZahlderGesamtdarlehen());
			
			ord = operdao.GesamtbetragderRvckzahlung(ordes.get(i));
			ordes.get(i).setGesamtbetragderRvckzahlung(ord.getGesamtbetragderRvckzahlung());
			ordes.get(i).setGesamtbetragderNum(ord.getGesamtbetragderNum());
			
			ord = operdao.GesamtbetraguberfalligerBetrag(ordes.get(i));
			ordes.get(i).setGesamtbetraguberfalligerBetrag(ord.getGesamtbetraguberfalligerBetrag());
			ordes.get(i).setGesamtbetraguberfallNum(ord.getGesamtbetraguberfallNum());
			
			ord = operdao.GesamtbetragderVerbindlichkeiten(ordes.get(i));
			ordes.get(i).setGesamtbetragderVerbindlichkeiten(ord.getGesamtbetragderVerbindlichkeiten());
			ordes.get(i).setGesamtbetragdererNum(ord.getGesamtbetragdererNum());
			
			ordes.get(i).setOrderCreateTime(Timestamps.stampToDate(ordes.get(i).getOrderCreateTime()));
		}
		map.put("Orders", ordes);
		map.put("PageUtil", pages);
		return map;
	}



	@Override
	public Map<String, Object> HuanKuan(Orderdetails order) {
		try {
			order.setStart_time(Timestamps.dateToStamp1(order.getStart_time()));
			order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, Object> map = new HashMap<String, Object>();
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
		List<Repayment> ords = operdao.SelectRepayment(order);//获取还款时间   还款笔数  还款总金额 
		for(int i=0;i<ords.size();i++){
			ords.get(i).setRepaymentDate(Timestamps.stampToDate(ords.get(i).getRepaymentDate()));
			Repayment re = operdao.SelectNodeRepayment(order);//获取已逾期笔数
			if(re == null){
				ords.get(i).setCollection_count(0);//逾期还款笔数
				BigDecimal a = new BigDecimal("0");
				ords.get(i).setCollection_money(a);//逾期还款金额
			}else{
				ords.get(i).setCollection_count(re.getCollection_count());//逾期还款笔数
				ords.get(i).setCollection_money(re.getCollection_money());//逾期还款金额
			}
			
			Orders o = new Orders();
			o.setOrderCreateTime(ords.get(i).getOrderCreateTime());
			Orders ord = operdao.GesamtbetragderDarlehen(o);
			
			ords.get(i).setCouNum(ord.getZahlderGesamtdarlehen());
			if(ords.get(i).getCollection_count() != 0 && ords.get(i).getCollection_count() != null){
				ords.get(i).setRepaymeny_collectiondata(ords.get(i).getRepayment_Count()/ords.get(i).getCollection_count());//还款率	
			}else{
				ords.get(i).setRepaymeny_collectiondata(0);//还款率	
			}
			ords.get(i).setOrderCreateTime(Timestamps.stampToDate1(ords.get(i).getOrderCreateTime()));
			
		}
		map.put("PageUtil", pages);
		map.put("Repayment", ords);
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
