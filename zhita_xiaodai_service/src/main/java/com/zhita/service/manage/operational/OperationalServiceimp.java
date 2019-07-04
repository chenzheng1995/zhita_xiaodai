package com.zhita.service.manage.operational;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.OperationalMapper;
import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;
import com.zhita.util.PageUtil;


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
		List<Orders> ors = operdao.OrderNum(ordera);
		Integer totalCount = null;
		if(ors.size()!=0){
			totalCount = ors.size();
		}else{
			totalCount = 0;
		}
		PageUtil pages = new PageUtil(ordera.getPage(), totalCount);
		ordera.setPage(pages.getPage());
		List<Orders> ordes = operdao.Ordersdata(ordera);
		for(int i=0;i<ordes.size();i++){
			ordera.setRiskManagemenType("成功");
			ordes.get(i).setAdoptcount(operdao.datastatistics(ordera));//放款通过数
			ordera.setRiskManagemenType("通过已借款");
			ordes.get(i).setLoancount(operdao.datastatistics(ordera));//实际借款数
			ordes.get(i).setPassrate(ordes.get(i).getAdoptcount()/ordes.get(i).getLoancount());//放款通过率
			ordes.get(i).setBeoverdue(operdao.Datacollection(ordera));//逾期笔数
			ordes.get(i).setMemberid(pdap.CollMemberId(ordera.getCompanyId()));//获取催收员ID
			ordes.get(i).setPressformoney(operdao.Cuishoudata(ordera));//催款笔数
			ordera.setCollectionStatus("态度恶劣");
			ordes.get(i).setBaddebt(operdao.PhoneHuai(ordera));
			ordes.get(i).setRealborrowing(operdao.SelectRealborrowing(ordera));//实借金额
			ordes.get(i).setRealreturn(operdao.SelectRealityAccount(ordera));//实还金额
			ordera.setCollectionStatus("态度恶劣");
			ordes.get(i).setAmountofbaddebts(operdao.SelectAmountofbaddebts(ordera));
		}
		map.put("Orders", ordes);
		map.put("PageUtil", pages);
		return map;
	}



	@Override
	public Map<String, Object> HuanKuan(Orderdetails order) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Repayment> orNum = operdao.SelectRepaymentNum(order);
		Integer totalCount = null;
		if(orNum.size() != 0){
			totalCount = orNum.size();
		}else{
			totalCount = 0;
		}
		order.setIds(coldao.SelectCollectionId(order.getCompanyId()));//查询催收员  参数 公司ID
		PageUtil pages = new PageUtil(order.getPage(), totalCount);	//参数page pagesize
		order.setPage(pages.getPage());
		List<Repayment> ords = operdao.SelectRepayment(order);//获取还款时间   还款数     还款金额
		for(int i=0;i<ords.size();i++){
			ords.get(i).setIds(operdao.ConnectionidAll(order));//
			Repayment res = operdao.RepaymentCollection(order);
			ords.get(i).setCollection_count(res.getCollection_count());//逾期还款数
			ords.get(i).setCollection_money(res.getCollection_money());//逾期还款金额
			ords.get(i).setRepaymeny_collectiondata(ords.get(i).getRepayment_Count()/ords.get(i).getCollection_count());//还款率
		}
		map.put("Repayment", ords);
		map.put("pageutil", pages);
		return map;
	}



	@Override
	public Map<String, Object> CollectionData(Orderdetails orde) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orderdetails> col = operdao.CollectionDataNum(orde);
		Integer totalCount = null;
		if(col.size() != 0){
			totalCount = col.size();
		}else{
			totalCount = 0;
		}
		PageUtil pages = new PageUtil(orde.getPage(), totalCount);
		orde.setPage(pages.getPage());
		List<Orderdetails> ordesa = operdao.CollectionDatas(orde);
		for(int i=0;i<ordesa.size();i++){
			orde.setIds(pdap.CollMemberId(orde.getCompanyId()));//获取催收员
			List<Integer> orids = operdao.CollectionNumberofreminders(orde);//查询订单笔数
			ordesa.get(i).setNumberofreminders(orids.size());
			ordesa.get(i).setNumberCollection(operdao.SelecNumberCollection(orde));//查询催收次数
			orde.setCollectionStatus("承诺还款");
			ordesa.get(i).setCollectionSuccess(operdao.SelecNumberCollection(orde));//查询成功数
			ordesa.get(i).setCollectionSuccessdata(ordesa.get(i).getCollection_count()/ordesa.get(i).getCollectionSuccess());//催收成功率
			orde.setCollectionStatus("态度恶劣");
			ordesa.get(i).setBaddebt(operdao.SelecNumberCollection(orde));//查询坏账数
		}
		map.put("PageUtil", pages);
		map.put("Orderdetails", ordesa);
		return map;
	}



	@Override
	public Map<String, Object> OrderBudget(Orderdetails orde) {
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
			ords.get(i).setActualrevenue(ords.get(i).getMakeLoans().subtract(ords.get(i).getRealityAccount()));
		}
		map.put("Pageutil", pages);
		map.put("orders", ords);
		return map;
		
	}

}
