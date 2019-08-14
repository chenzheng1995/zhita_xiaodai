package com.zhita.service.manage.finance;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.zhita.util.DateListUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;



@Service
public class FinanceServiceimp implements FinanceService{
	
	
	
	@Autowired
	private PaymentRecordMapper padao;
	
	
	
	@Autowired
	private CollectionMapper coldao;
	
	
	

	@Override
	public Map<String, Object> AllPaymentrecord(Payment_record payrecord) {
		PhoneDeal p = new PhoneDeal();
		if(payrecord.getPhone() != null){
			payrecord.setPhone(p.encryption(payrecord.getPhone()));
		}
		if(payrecord.getStart_time()!=null && payrecord.getStart_time()!="" && payrecord.getEnd_time()!=null && payrecord.getEnd_time()!=""){
			try {
				payrecord.setStart_time(Timestamps.dateToStamp1(payrecord.getStart_time()));
				payrecord.setEnd_time(Timestamps.dateToStamp1(payrecord.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		Integer totalCount = padao.TotalCountPayment(payrecord);
		PageUtil pages = new PageUtil(payrecord.getPage(), totalCount);
		payrecord.setPage(pages.getPage());
		payrecord.setProfessionalWork("放款");
		TuoMinUtil tm = new TuoMinUtil();
		List<Payment_record> payments = padao.PaymentAll(payrecord);
		for(int i=0;i<payments.size();i++){
			payments.get(i).setRemittanceTime(Timestamps.stampToDate(payments.get(i).getRemittanceTime()));
			payments.get(i).setPhone(p.decryption(payments.get(i).getPhone()));
			payments.get(i).setPhone(tm.mobileEncrypt(payments.get(i).getPhone()));
		
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PaymentRecord", payments);
		return map;
	}




	@Override
	public Map<String, Object> Huankuan(Payment_record payrecord) {
		PhoneDeal p = new PhoneDeal();
		if(payrecord.getPhone() != null){
			payrecord.setPhone(p.encryption(payrecord.getPhone()));
		}
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
		TuoMinUtil tm = new TuoMinUtil();
		List<Payment_record> rapay = padao.RepaymentAll(payrecord);
		for(int i = 0 ;i<rapay.size();i++){
			rapay.get(i).setRepaymentDate(Timestamps.stampToDate(rapay.get(i).getRepaymentDate()));
			rapay.get(i).setPhone(p.decryption(rapay.get(i).getPhone()));
			rapay.get(i).setPhone(tm.mobileEncrypt(rapay.get(i).getPhone()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Repayment", rapay);
		return map;
	}




	@Override
	public Map<String, Object> OrderPayment(Orderdetails orderNumber) {
		orderNumber.setCompanyId(3);
		PhoneDeal p = new PhoneDeal();
		Orderdetails ordea = padao.SelectPaymentOrder(orderNumber);
		if(ordea.getInterestSum() == null){
			ordea.setRealityBorrowMoney(ordea.getRealityBorrowMoney());
		}else{
			ordea.setRealityBorrowMoney(ordea.getInterestPenaltySum().add(ordea.getRealityBorrowMoney()));
		}
		if(ordea.getDeferAfterReturntime()==null || ordea.getDeferAfterReturntime().equals("")){
			ordea.setDeferAfterReturntime("/");
		}else{
			ordea.setDeferAfterReturntime(Timestamps.stampToDate(ordea.getDeferAfterReturntime()));
		}
		String phone = p.decryption(ordea.getPhone());
		ordea.setPhone(phone);
		System.out.println(ordea.getDeferAfterReturntime()+"风控:"+ordea.getRiskcontrolname()+"分数:"+ordea.getRiskmanagementFraction());
		ordea.setPhone(p.encryption(ordea.getPhone()));
		ordea.setOrder_money(ordea.getInterestInAll().add(ordea.getRealityBorrowMoney()));
		ordea.setOrderCreateTime(Timestamps.stampToDate(ordea.getOrderCreateTime()));//时间戳转换
		Deferred defe =  coldao.DefNum(ordea.getOrderId());
		if(defe.getId()==0){
			BigDecimal big = new BigDecimal(0);
			defe.setInterestOnArrears(big);
		}
		System.out.println(defe.getInterestOnArrears());
		orderNumber.setDefeNum(defe.getId());
		orderNumber.setDefeMoney(defe.getInterestOnArrears());
		ordea.setRegisteTime(Timestamps.stampToDate(ordea.getRegisteTime()));
		ordea.setShouldReturnTime(Timestamps.stampToDate(ordea.getShouldReturnTime()));
		ordea.setDeferBeforeReturntime(Timestamps.stampToDate(ordea.getDeferBeforeReturntime()));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", ordea);
		return map;
	}




	@Override
	public Map<String, Object> Accountadjus(Accountadjustment acc) {
		PhoneDeal p = new PhoneDeal();
		if(acc.getPhone() != null){
			acc.setPhone(p.encryption(acc.getPhone()));
		}
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
		System.out.println("身份证号:"+orderNumber.getIdcard_number());
		System.out.println("手机号:"+orderNumber.getPhone());
			PhoneDeal p = new PhoneDeal();
			if(orderNumber.getPhone() != null){
				if(orderNumber.getPhone().length()==11){
					orderNumber.setPhone(p.encryption(orderNumber.getPhone()));
				}
				
			}
			
			Orderdetails ordetails = padao.OrdeRepayment(orderNumber);
			
			if(ordetails!=null){
				ordetails.setOrderCreateTime(Timestamps.stampToDate(ordetails.getOrderCreateTime()));//实借时间
				ordetails.setDeferAfterReturntime(padao.DeferrAdefe(ordetails.getOrderId()));
				ordetails.setDeferAfterReturntime(Timestamps.stampToDate(ordetails.getDeferAfterReturntime()));//延期后应还时间
				map.put("aaa", ordetails.getInterestPenaltySum());
				map.put("Orderdetails", ordetails);
			}else{
				map.put("Orderdetails", "无数据");
				map.put("Deferred", 0);
			}
			
		return map;
	}




	@Override
	public Map<String, Object> SelectOrderAccount(Orderdetails ordetail) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PhoneDeal p = new PhoneDeal();
		if(ordetail.getPhone() != null){
			ordetail.setPhone(p.encryption(ordetail.getPhone()));
		}
		ordetail.setAccounttime(System.currentTimeMillis()+"");
		try {
			ordetail.setAccounttimestart_time(Timestamps.dateToStamp1(ordetail.getAccounttimestart_time()));
			ordetail.setAccounttimeent_time(Timestamps.dateToStamp1(ordetail.getAccounttimeent_time()));
			ordetail.setRealtime(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		TuoMinUtil tm = new TuoMinUtil();
		List<Accountadjustment> accounts = padao.AllAccount(ordetail);
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setAccounttime(Timestamps.stampToDate(accounts.get(i).getAccounttime()));
			accounts.get(i).setAmou_time(Timestamps.stampToDate(accounts.get(i).getAmou_time()));
			String ps = p.decryption(accounts.get(i).getPhone());
			accounts.get(i).setPhone(tm.mobileEncrypt(ps));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> SelectNoMoney(Orderdetails ordetail) {
		ordetail.setAccounttime(System.currentTimeMillis()+"");
		PhoneDeal p = new PhoneDeal();
		if(ordetail.getPhone() != null){
			ordetail.setPhone(p.encryption(ordetail.getPhone()));
		}
		try {
			ordetail.setAccounttimestart_time(Timestamps.dateToStamp1(ordetail.getAccounttimestart_time()));
			ordetail.setAccounttimeent_time(Timestamps.dateToStamp1(ordetail.getAccounttimeent_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		TuoMinUtil tm = new TuoMinUtil();
		List<Accountadjustment> accounts = padao.AllStatu(ordetail);
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setAccounttime(Timestamps.stampToDate(accounts.get(i).getAccounttime()));
			accounts.get(i).setAmou_time(Timestamps.stampToDate(accounts.get(i).getAmou_time()));
			String ps = p.decryption(accounts.get(i).getPhone());
			accounts.get(i).setPhone(tm.mobileEncrypt(ps));
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> SelectOkMoney(Orderdetails ordetail) {
		PhoneDeal p = new PhoneDeal();
		if(ordetail.getPhone() != null){
			ordetail.setPhone(p.encryption(ordetail.getPhone()));
		}
		try {
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ordetail.setRealtime(Timestamps.dateToStamp(sim.format(new Date())));
			ordetail.setAccounttime(System.currentTimeMillis()+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			
			ordetail.setAccounttimestart_time(Timestamps.dateToStamp1(ordetail.getAccounttimestart_time()));
			ordetail.setAccounttimeent_time(Timestamps.dateToStamp1(ordetail.getAccounttimeent_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		TuoMinUtil tm = new TuoMinUtil();
		ordetail.setPage(pages.getPage());
		List<Accountadjustment> accounts = padao.AllNotMoneyStatu(ordetail);
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setAccounttime(Timestamps.stampToDate(accounts.get(i).getAccounttime()));
			accounts.get(i).setAmou_time(Timestamps.stampToDate(accounts.get(i).getAmou_time()));
			String ps = p.decryption(accounts.get(i).getPhone());
			accounts.get(i).setPhone(tm.mobileEncrypt(ps));
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> Selectoffine(Orderdetails ordetail) {
		PhoneDeal p = new PhoneDeal();
		if(ordetail.getPhone() != null){
			ordetail.setPhone(p.encryption(ordetail.getPhone()));
		}
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
		List<Offlinetransfer> under = padao.AllUnderthe(ordetail);
		for (int i = 0; i < under.size(); i++) {
			under.get(i).setOffinetransfertime(Timestamps.stampToDate(under.get(i).getOffinetransfertime()));
			if(under.get(i).getState().equals("支出")){
				under.get(i).setThname(padao.LoanName(under.get(i).getChannel()));
			}
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
		PhoneDeal p = new PhoneDeal();
		if(bank.getPhone() != null){
			bank.setPhone(p.encryption(bank.getPhone()));
		}
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
		TuoMinUtil tm = new TuoMinUtil();
		List<Orderdetails> orders = padao.BankDeduOrder(bank);
		for(int i=0;i<orders.size();i++){
			orders.get(i).setBorrowTimeLimit(Timestamps.stampToDate(orders.get(i).getBorrowTimeLimit()));
			orders.get(i).setShouldReturnTime(Timestamps.stampToDate(orders.get(i).getShouldReturnTime()));
			orders.get(i).setCollectionTime(Timestamps.stampToDate(orders.get(i).getCollectionTime()));
			orders.get(i).setOrderCreateTime(Timestamps.stampToDate(orders.get(i).getOrderCreateTime()));
			orders.get(i).setDeferBeforeReturntime(Timestamps.stampToDate(orders.get(i).getDeferBeforeReturntime()));
			orders.get(i).setDeferAfterReturntime(Timestamps.stampToDate(orders.get(i).getDeferAfterReturntime()));
			orders.get(i).setPhone(p.decryption(orders.get(i).getPhone()));
			orders.get(i).setPhone(tm.mobileEncrypt(orders.get(i).getPhone()));
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



	//
	@Override
	public Map<String, Object> AllDelayStatis(Bankdeductions banl) {
		Map<String, Object> map = new HashMap<String, Object>();
		PhoneDeal p = new PhoneDeal();
		if(banl.getPhone() != null){
			banl.setPhone(p.encryption(banl.getPhone()));
		}
		List<Bankdeductions> banks = new ArrayList<Bankdeductions>();
		
		if(banl.getStartu_time()==null){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stime = sim.format(new Date());
			banl.setStartu_time(stime+" 00:00:00");
			banl.setEnd_time(stime+" 23:59:59");
				try {	
					banl.setStartu_time(Timestamps.dateToStamp1(banl.getStartu_time()));
					banl.setEnd_time(Timestamps.dateToStamp1(banl.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
			
				System.out.println("数据:"+stime);
				Integer totalCount = padao.DelayTatolCount(banl);
				if(totalCount !=null){
					PageUtil pages = new PageUtil(banl.getPage(), totalCount);
					banl.setPage(pages.getPage());
				}else{
					PageUtil pages = new PageUtil(banl.getPage(), 0);
					banl.setPage(pages.getPage());
				}
				
				Bankdeductions bank = padao.BankdeduCtionsData(banl);
				bank.setDeferredTime(stime);
				Bankdeductions b = padao.BankMoney(banl);
				if(bank.getDeferredamount()==null){
					bank.setDeferredamount(new BigDecimal(0));
				}
				
				if(b.getDeduction_money()==null){
					b.setDeduction_money(new BigDecimal(0));
				}
				
				
				bank.setDeduction_money(b.getDeduction_money());
				bank.setUserNum(b.getUserNum());
				System.out.println(bank.getOrderNum()+"A"+bank.getDeferredamount()+"A"+bank.getDeduction_money()+"A"+bank.getUserNum());
				banks.add(bank);
		}else{
			List<String> times =  DateListUtil.getDays(banl.getStartu_time(), banl.getEnd_time());
			for(int i=0;i<times.size();i++){
				banl.setStartu_time(times.get(i)+" 00:00:00");
				banl.setEnd_time(times.get(i)+" 23:59:59");
				try {	
					banl.setStartu_time(Timestamps.dateToStamp1(banl.getStartu_time()));
					banl.setEnd_time(Timestamps.dateToStamp1(banl.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
			
				
				Integer totalCount = padao.DelayTatolCount(banl);
				
				if(totalCount !=null){
					PageUtil pages = new PageUtil(banl.getPage(), totalCount);
					banl.setPage(pages.getPage());
				}else{
					PageUtil pages = new PageUtil(banl.getPage(), 0);
					banl.setPage(pages.getPage());
				}
				
				Bankdeductions bank = padao.BankdeduCtionsData(banl);
				bank.setDeferredTime(times.get(i));
				Bankdeductions b = padao.BankMoney(banl);
				if(bank.getDeferredamount()==null){
					bank.setDeferredamount(new BigDecimal(0));
				}
				
				if(b.getDeduction_money()==null){
					b.setDeduction_money(new BigDecimal(0));
				}
				
				
				bank.setDeduction_money(b.getDeduction_money());
				bank.setUserNum(b.getUserNum());
				banks.add(bank);
			}
		}
		map.put("Bankdeduction", banks);
		return map;
	}




	@Override
	public Map<String, Object> Financialover(Bankdeductions banl) {
		PhoneDeal p = new PhoneDeal();
		if(banl.getPhone() != null){
			banl.setPhone(p.encryption(banl.getPhone()));
		}
		try {
			banl.setStartu_time(Timestamps.dateToStamp1(banl.getStartu_time()));
			banl.setEnd_time(Timestamps.dateToStamp1(banl.getEnd_time()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Bankdeductions bank = padao.OnDefe(banl);//查询实借金额笔数
		Bankdeductions b = padao.Onrepayment(banl);//查询还款金额笔数
		Bankdeductions a = padao.OneCollection(banl);//查询逾期金额
		Bankdeductions c = padao.OneMoney(banl);//查询延期费
//		Bankdeductions bank = padao.OneBank(banl);//realborrowing     实借笔数        realexpenditure   世界金额 
		if(bank.getRealborrowing() != null && bank.getRealexpenditure() != null){
			bank.setBankcardName(""+bank.getRealborrowing()+","+bank.getRealexpenditure()+","+0+"");//实借笔数    实借金额
		}else{
			bank.setBankcardName(""+0+","+0+","+0+"");//实借笔数    实借金额
		}
			
		if(b.getRealreturn() != null && b.getPaymentamount() != null){
			bank.setDeductionstatus(""+b.getRealreturn()+","+0+","+b.getPaymentamount()+"");//实还笔数    实还金额
		}else{
			bank.setDeductionstatus(""+0+","+0+","+0+"");//实还笔数    实还金额
		}
			
		if(a.getOverdueNum() != null && a.getOverdueamount() != null ){
			bank.setOrderNumber(""+a.getOverdueNum()+","+a.getOverdueamount()+","+0+"");//逾期数   逾期费
		}else{
			bank.setOrderNumber(""+0+","+0+","+0+"");//逾期数   逾期费
		}
			
		if(c.getDefeNum() != null && c.getDeferredamount() != null ){
			bank.setName(""+c.getDefeNum()+","+c.getDeferredamount()+","+0+"");//延期数    延期费
		}else{
			bank.setName(""+0+","+0+","+0+"");//延期数    延期费
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Bankdeduction", bank);
		return map;
	}




	@Override
	public Map<String, Object> AddOffJianmian(Offlinjianmian off) {
		PhoneDeal p = new PhoneDeal();
		if(off.getPhone() != null){
			off.setPhone(p.encryption(off.getPhone()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			off.setSedn_time(Timestamps.dateToStamp(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
		PhoneDeal p = new PhoneDeal();
		if(ord.getPhone() != null){
			ord.setPhone(p.encryption(ord.getPhone()));
		}
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
		TuoMinUtil tm = new TuoMinUtil();
		List<Offlinjianmian> unders = padao.XiaOrder(ord);
		for(int i=0;i<unders.size();i++){
			unders.get(i).setPhone(p.decryption(unders.get(i).getPhone()));
			unders.get(i).setPhone(tm.mobileEncrypt(unders.get(i).getPhone()));
		}
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
		PhoneDeal p = new PhoneDeal();
		if(off.getPhone() != null){
			off.setPhone(p.encryption(off.getPhone()));
		}
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
		PhoneDeal p = new PhoneDeal();
		if(of.getPhone() != null){
			of.setPhone(p.encryption(of.getPhone()));
		}
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
