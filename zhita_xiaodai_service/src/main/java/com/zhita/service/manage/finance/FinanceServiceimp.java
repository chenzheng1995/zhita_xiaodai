package com.zhita.service.manage.finance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.PaymentRecordMapper;
import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Deferred_settings;
import com.zhita.model.manage.Loan_setting;
import com.zhita.model.manage.Offlinedelay;
import com.zhita.model.manage.Offlinetransfer;
import com.zhita.model.manage.Offlinjianmian;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment_setting;
import com.zhita.model.manage.Undertheline;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;



@Service
public class FinanceServiceimp implements FinanceService{
	
	
	
	@Autowired
	private PaymentRecordMapper padao;
	
	
	
	@Autowired
	private CollectionMapper coldao;
	
	
	

	@Override
	public Map<String, Object> AllPaymentrecord(Payment_record payrecord) {
//		try {
//			System.out.println(payrecord.getStart_time()+"+++"+payrecord.getEnd_time());
//			payrecord.setStart_time(Timestamps.dateToStamp1(payrecord.getStart_time()));
//			payrecord.setEnd_time(Timestamps.dateToStamp1(payrecord.getEnd_time()));
//			System.out.println(payrecord.getStart_time()+"++++"+payrecord.getEnd_time());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		Integer totalCount = padao.TotalCountPayment(payrecord);
		PageUtil pages = new PageUtil(payrecord.getPage(), totalCount);
		payrecord.setPage(pages.getPage());
		payrecord.setProfessionalWork("放款");
		List<Payment_record> payments = padao.PaymentAll(payrecord);
//		for(int i=0;i<payments.size();i++){
//			payments.get(i).setRemittanceTime(Timestamps.stampToDate(payments.get(i).getRemittanceTime()));
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PaymentRecord", payments);
		return map;
	}




	@Override
	public Map<String, Object> Huankuan(Payment_record payrecord) {
		try {
			payrecord.setStart_time(Timestamps.dateToStamp1(payrecord.getStart_time()));
			payrecord.setEnd_time(Timestamps.dateToStamp1(payrecord.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.RepaymentTotalCount(payrecord);
		PageUtil pages = new PageUtil(payrecord.getPage(), totalCount);
		payrecord.setPage(pages.getPage());
		payrecord.setProfessionalWork("还款");
		List<Payment_record> rapay = padao.RepaymentAll(payrecord);
		for(int i = 0 ;i<rapay.size();i++){
			rapay.get(i).setRemittanceTime(Timestamps.stampToDate(rapay.get(i).getRemittanceTime()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Repayment", rapay);
		return map;
	}




	@Override
	public Map<String, Object> OrderPayment(Orderdetails orderNumber) {
		orderNumber.setCompanyId(3);
		Orderdetails ordea = padao.SelectPaymentOrder(orderNumber);
		if(ordea.getInterestSum() == null){
			ordea.setRealityBorrowMoney(ordea.getRealityBorrowMoney());
		}else{
			ordea.setRealityBorrowMoney(ordea.getInterestPenaltySum().add(ordea.getRealityBorrowMoney()));
		}
		ordea.setOrderCreateTime(Timestamps.stampToDate(ordea.getOrderCreateTime()));//时间戳转换
		Deferred defe =  coldao.DefNum(ordea.getOrderId());
		orderNumber.setDefeNum(defe.getId());
		orderNumber.setDefeMoney(defe.getInterestOnArrears());
		ordea.setOrderCreateTime(Timestamps.stampToDate(ordea.getOrderCreateTime()));
		ordea.setShouldReturnTime(Timestamps.stampToDate(ordea.getShouldReturnTime()));
		ordea.setDeferAfterReturntime(Timestamps.stampToDate(ordea.getDeferAfterReturntime()));
		ordea.setDeferBeforeReturntime(Timestamps.stampToDate(ordea.getDeferBeforeReturntime()));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", ordea);
		return map;
	}




	@Override
	public Map<String, Object> Accountadjus(Accountadjustment acc) {
		try {
			acc.setAmou_time(System.currentTimeMillis()+"");
			acc.setAccounttime(Timestamps.dateToStamp1(acc.getAccounttime()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		Integer addId = padao.AddCAccount(acc);
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
	public Map<String, Object> OrderAccount(Orderdetails orderNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
			Orderdetails ordetails = padao.OrdeRepayment(orderNumber);
			System.out.println(ordetails.getInterestSum()+""+ordetails.getMakeLoans()+""+ordetails.getOrderId());
			ordetails.setOrderCreateTime(Timestamps.stampToDate(ordetails.getOrderCreateTime()));
			ordetails.setOrder_money(ordetails.getInterestSum().add(ordetails.getMakeLoans()));
			if(ordetails.getInterestPenaltySum() != null && ordetails.getRealityBorrowMoney() != null){
				ordetails.setRealityBorrowMoney(ordetails.getInterestPenaltySum().add(ordetails.getRealityBorrowMoney()));
			}else if(ordetails.getInterestPenaltySum() != null && ordetails.getRealityBorrowMoney() == null){
				ordetails.setRealityBorrowMoney(ordetails.getInterestPenaltySum());
			}else{
				ordetails.setRealityBorrowMoney(ordetails.getRealityBorrowMoney());
			}
			
			Deferred defe =  coldao.DefNum(ordetails.getOrderId());
			orderNumber.setDefeNum(defe.getId());
			orderNumber.setDefeMoney(defe.getInterestOnArrears());
			Deferred de = padao.DeleteNumMoney(ordetails.getOrderId());
			//ordetails.setOrderCreateTime(Timestamps.stampToDate(ordetails.getOrderCreateTime()));
			ordetails.setShouldReturnTime(Timestamps.stampToDate(ordetails.getShouldReturnTime()));
			ordetails.setDeferAfterReturntime(Timestamps.stampToDate(ordetails.getDeferAfterReturntime()));
			ordetails.setDeferBeforeReturntime(Timestamps.stampToDate(ordetails.getDeferBeforeReturntime()));
			map.put("aaa", ordetails.getInterestPenaltySum());
			map.put("Orderdetails", ordetails);
			map.put("Deferred", de);
		return map;
	}




	@Override
	public Map<String, Object> SelectOrderAccount(Orderdetails ordetail) {
		try {
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ordetail.setRealtime(Timestamps.dateToStamp1(sim.format(new Date())));
			ordetail.setAccounttime(System.currentTimeMillis()+"");
			ordetail.setStart_time(Timestamps.dateToStamp1(ordetail.getStart_time()));
			ordetail.setEnd_time(Timestamps.dateToStamp1(ordetail.getEnd_time()));
			ordetail.setAccounttimestart_time(Timestamps.dateToStamp1(ordetail.getAccounttimestart_time()));
			ordetail.setAccounttimeent_time(Timestamps.dateToStamp1(ordetail.getAccounttimeent_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		List<Accountadjustment> accounts = padao.AllAccount(ordetail);
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setAccounttime(Timestamps.stampToDate(accounts.get(i).getAccounttime()));
			accounts.get(i).setAmou_time(Timestamps.stampToDate(accounts.get(i).getAmou_time()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> SelectNoMoney(Orderdetails ordetail) {
		try {
			ordetail.setAccounttime(System.currentTimeMillis()+"");
			ordetail.setStart_time(Timestamps.dateToStamp1(ordetail.getStart_time()));
			ordetail.setEnd_time(Timestamps.dateToStamp1(ordetail.getEnd_time()));
			ordetail.setAccounttimestart_time(Timestamps.dateToStamp1(ordetail.getAccounttimestart_time()));
			ordetail.setAccounttimeent_time(Timestamps.dateToStamp1(ordetail.getAccounttimeent_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		List<Accountadjustment> accounts = padao.AllStatu(ordetail);
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setAccounttime(Timestamps.stampToDate(accounts.get(i).getAccounttime()));
			accounts.get(i).setAmou_time(Timestamps.stampToDate(accounts.get(i).getAmou_time()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> SelectOkMoney(Orderdetails ordetail) {
		try {
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ordetail.setRealtime(Timestamps.dateToStamp(sim.format(new Date())));
			ordetail.setAccounttime(System.currentTimeMillis()+"");
			ordetail.setStart_time(Timestamps.dateToStamp1(ordetail.getStart_time()));
			ordetail.setEnd_time(Timestamps.dateToStamp1(ordetail.getEnd_time()));
			ordetail.setAccounttimestart_time(Timestamps.dateToStamp1(ordetail.getAccounttimestart_time()));
			ordetail.setAccounttimeent_time(Timestamps.dateToStamp1(ordetail.getAccounttimeent_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		List<Accountadjustment> accounts = padao.AllNotMoneyStatu(ordetail);
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setAccounttime(Timestamps.stampToDate(accounts.get(i).getAccounttime()));
			accounts.get(i).setAmou_time(Timestamps.stampToDate(accounts.get(i).getAmou_time()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> Selectoffine(Orderdetails ordetail) {
		try {
			ordetail.setStart_time(Timestamps.dateToStamp1(ordetail.getStart_time()));
			ordetail.setEnd_time(Timestamps.dateToStamp1(ordetail.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = padao.SelectUnderthTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		ordetail.setIds(padao.Sys_userIds(ordetail.getCompanyId()));
		List<Undertheline> under = padao.AllUnderthe(ordetail);
		for (int i = 0; i < under.size(); i++) {
			under.get(i).setUnderthe_time(Timestamps.stampToDate(under.get(i).getUnderthe_time()));
		}
		map.put("Undertheline", under);
		return map;
	}




	@Override
	public Map<String, Object> ThirdpatyAll(Integer compayId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Loan_setting> third = padao.SelectThird(compayId);
		map.put("Loan_setting", third);
		return map;
	}


	
	


	@Override
	public Map<String, Object> AddUnderthe(Offlinetransfer unde) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			unde.setOffinetransfertime(System.currentTimeMillis()+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer addId = padao.AddUndertheline(unde);
		if(addId != null){
			map.put("code", 200);
			padao.UpdateOrderType(unde.getOrderId());
			map.put("desc", "添加成功");
		}else{
			map.put("code", 0);
			map.put("desc", "添加失败");
		}
		return map;
	}




	@Override
	public Map<String, Object> SelectBankDeductOrders(Bankdeductions bank) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			bank.setStartu_time(Timestamps.dateToStamp1(bank.getStartu_time()));
			bank.setEnd_time(Timestamps.dateToStamp1(bank.getEnd_time()));
			bank.setStatu_timeOrder(Timestamps.dateToStamp1(bank.getStatu_timeOrder()));
			bank.setEnd_timeOrder(Timestamps.dateToStamp1(bank.getEnd_timeOrder()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.BankDeduOrderNum(bank);
		PageUtil pages = new PageUtil(bank.getPage(), totalCount);
		bank.setPage(pages.getPage());
		List<Orderdetails> orders = padao.BankDeduOrder(bank);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setBorrowTimeLimit(Timestamps.stampToDate(orders.get(i).getBorrowTimeLimit()));
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectionTime()));
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getDeferBeforeReturntime()));
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
		}
		map.put("Orderdetails", orders);
		return map;
	}




	@Override
	public Map<String, Object> AllBank(Integer orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(orderId != null){
			List<Bankdeductions> banls = padao.BanAll(orderId);
			map.put("bankde", banls);
		}else{
			map.put("bankde", "无数据");
		}
		return map;
	}




	@Override
	public Map<String, Object> AddBank(Bankdeductions banl) {
		
		return null;
	}




	@Override
	public Map<String, Object> AllDelayStatis(Bankdeductions banl) {
		try {
			banl.setStartu_time(Timestamps.dateToStamp1(banl.getStartu_time()));
			banl.setEnd_time(Timestamps.dateToStamp1(banl.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = padao.DelayTatolCount(banl);
		if(totalCount !=null){
			PageUtil pages = new PageUtil(banl.getPage(), totalCount);
			banl.setPage(pages.getPage());
		}else{
			PageUtil pages = new PageUtil(banl.getPage(), 0);
			banl.setPage(pages.getPage());
		}
		
		
		List<Bankdeductions> banks = padao.DelayStatisc(banl);
		for (int i = 0; i < banks.size(); i++) {
			banks.get(i).setDeferAfterReturntime(Timestamps.stampToDate(banks.get(i).getDeferAfterReturntime()));
			Bankdeductions ban = padao.SelectBank(banks.get(i));
			banks.get(i).setBranKnum(ban.getBranKnum());
			banks.get(i).setBrankMoney(ban.getBrankMoney());
		}
		map.put("Bankdeduction", banks);
		return map;
	}




	@Override
	public Map<String, Object> Financialover(Bankdeductions banl) {
		try {
			banl.setStartu_time(Timestamps.dateToStamp1(banl.getStartu_time()));
			banl.setEnd_time(Timestamps.dateToStamp1(banl.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Bankdeductions bank = padao.OneBank(banl);//realborrowing     实借笔数        realexpenditure   世界金额 
		bank.setDeferredamount(padao.YanMoney(banl));
		bank.setBankcardName(""+bank.getRealborrowing()+","+bank.getRealexpenditure()+","+0+"");//实借笔数    实借金额
		bank.setDeductionstatus(""+bank.getRealreturn()+","+0+","+bank.getPaymentamount()+"");//实还笔数    实还金额
		bank.setOrderNumber(""+0+","+bank.getOverdueamount()+","+0+"");//实借笔数    实借金额
		bank.setName(""+0+","+bank.getDeferredamount()+","+0+"");//实借笔数    实借金额
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Bankdeduction", bank);
		return map;
	}




	@Override
	public Map<String, Object> AddOffJianmian(Offlinjianmian off) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		off.setSedn_time(sim.format(new Date()));
		Integer addId = padao.AddOffJianMian(off);
		if(addId != null){
			map.put("code", 200);
			map.put("desc", "操作成功");
		}else{
			map.put("code", 0);
			map.put("desc", "数据异常");
		}
		return map;
	}




	@Override
	public Map<String, Object> SelectXiaOrder(Orderdetails ord) {
		try {
			ord.setStart_time(Timestamps.dateToStamp1(ord.getStart_time()));
			ord.setEnd_time(Timestamps.dateToStamp1(ord.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		List<Integer> totalCount = padao.XiaTotalCount(ord);
		Integer a = null;
		if(totalCount == null){
			a=0;
		}else{
			a=totalCount.size();
		}
		PageUtil pages = new PageUtil(ord.getPage(), a);
		ord.setPage(pages.getPage());
		List<Offlinjianmian> unders = padao.XiaOrder(ord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Undertheline", unders);
		return map;
	}




	@Override
	public Map<String, Object> RepaymentAll(Integer compayId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Repayment_setting> repay = padao.SelectRepay(compayId);
		map.put("Repayment_setting", repay);
		return map;
	}




	@Override
	public Map<String, Object> CompanyDelay(Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Deferred_settings deset = padao.OneCompanyDeferr(companyId);
		map.put("Deferred_settings", deset);
		return map;
	}




	@Override
	public Map<String, Object> AddDelay(Offlinedelay off) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, +off.getOnceDeferredDay());
        Date date = ca.getTime();
        off.setOperating_time(sdf.format(new Date()));//操作时间
        off.setDelay_time(sdf.format(date));
        Integer addId = padao.AddDelay(off);
		if(addId != null){
			map.put("code", 200);
			map.put("desc", "已添加");
		}else{
			map.put("code", 0);
			map.put("desc", "数据异常");
		}
		return map;
	}




	@Override
	public Map<String, Object> Delaylabor(Offlinedelay of) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = padao.OffTotalCount(of);
		if(totalCount == null){
			totalCount = 0;
		}
		PageUtil pages = new PageUtil(of.getPage(), totalCount);
		of.setPage(pages.getPage());
		List<Offlinedelay> ofa = padao.Allofflinedelay(of);
		for(int i = 0;i<ofa.size();i++){
			Deferred de = padao.DeleteNumMoney(ofa.get(i).getOrderId());
			ofa.get(i).setDefeNum(de.getDefeNum());
			ofa.get(i).setDefeMoney(de.getDefeMoney());
		}
		map.put("Offlinedelay", ofa);
		map.put("pageutil", pages);
		return map;
	}




}
