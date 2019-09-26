package com.zhita.service.manage.finance;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.CollectionMapper;
import com.zhita.dao.manage.HomepageTongjiMapper;
import com.zhita.dao.manage.NewMapper;
import com.zhita.dao.manage.PaymentRecordMapper;
import com.zhita.dao.manage.PostloanorderMapper;
import com.zhita.dao.manage.ThirdpricefindMapper;
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
import com.zhita.model.manage.PriceTongji;
import com.zhita.model.manage.Repayment_setting;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.model.manage.Thirdpricefind;
import com.zhita.util.DateListUtil;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;



@Service
public class FinanceServiceimp implements FinanceService{
	
	
	@Autowired
	private PostloanorderMapper postloanorder;
	
	
	@Autowired
	private PaymentRecordMapper padao;
	
	
	
	@Autowired
	private CollectionMapper coldao;
	
	
	
	@Autowired
	private PostloanorderMapper pdap;
	
	
	
	@Autowired
	private NewMapper newdao;
	
	
	
	@Autowired
	private ThirdpricefindMapper thirdpricefindMapper;
	
	@Autowired
	private HomepageTongjiMapper homepageTongjiMapper;
	
	

	@Override
	public Map<String, Object> AllPaymentrecord(Payment_record payrecord) {
		PhoneDeal p = new PhoneDeal();
		if(payrecord.getPhone() != null){
			if(payrecord.getPhone().length()!=0){
				payrecord.setPhone(p.encryption(payrecord.getPhone()));
			}
			
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
			if(payments.get(i).getPhone()!=null){
				if(payments.get(i).getPhone().length()!=0){
					payments.get(i).setPhone(p.decryption(payments.get(i).getPhone()));
				}
				
			}
			payments.get(i).setPhone(tm.mobileEncrypt(payments.get(i).getPhone()));
		
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PaymentRecord", payments);
		map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> Huankuan(Payment_record payrecord) {
		PhoneDeal p = new PhoneDeal();
		if(payrecord.getPhone() != null){
			if(payrecord.getPhone().length()!=0){
				payrecord.setPhone(p.encryption(payrecord.getPhone()));
			}
			
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
			if(rapay.get(i).getPhone()!=null){
				if(rapay.get(i).getPhone().length()!=0){
					rapay.get(i).setPhone(p.decryption(rapay.get(i).getPhone()));
				}
			}
			
			rapay.get(i).setPhone(tm.mobileEncrypt(rapay.get(i).getPhone()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Repayment", rapay);
		map.put("pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> OrderPayment(Orderdetails orderNumber) {
		orderNumber.setCompanyId(orderNumber.getCompanyId());
		PhoneDeal p = new PhoneDeal();
		Orderdetails ordea = padao.SelectPaymentOrder(orderNumber);
		
		int a = ordea.getRealityBorrowMoney().compareTo(ordea.getMakeLoans());
		System.out.println("状态:"+a+"金额111CC:"+ordea.getRealityBorrowMoney()+"金额2222BBB:"+ordea.getMakeLoans());
		if(a==0){
			System.out.println("后置");
			BigDecimal aa =ordea.getInterestPenaltySum().add(ordea.getTechnicalServiceMoney());
			ordea.setOrder_money(ordea.getRealityBorrowMoney().add(ordea.getInterestSum()).add(aa));
			System.out.println(ordea.getRealityBorrowMoney()+"CCC"+ordea.getInterestSum()+"CCCC11"+ordea.getInterestPenaltySum()+"金额:"+ordea.getTechnicalServiceMoney());
			System.out.println(ordea.getOrder_money());
		}else{
			System.out.println("前置");
			ordea.setOrder_money(ordea.getInterestPenaltySum().add(ordea.getRealityBorrowMoney()));//应还总金额
		}
		
		
		
		
		if(ordea.getInterestSum() == null){
			ordea.setRealityBorrowMoney(ordea.getRealityBorrowMoney());
		}else{
			ordea.setRealityBorrowMoney(ordea.getInterestPenaltySum().add(ordea.getRealityBorrowMoney()));
		}
		
		
		System.out.println(ordea.getDeferAfterReturntime()+"风控:"+ordea.getRiskcontrolname()+"分数:"+ordea.getRiskmanagementFraction());
		/*Orderdetails qianzhi = postloanorder.SelectQianshouldReapyMoney(ordea.getOrderId());//前置应还金额
		
		if(qianzhi.getRealityBorrowMoney().compareTo(qianzhi.getMakeLoans()) == 0){
			ordea.setOrder_money(ordea.getShouldReapyMoney());//应还总金额
		}else{
			ordea.setOrder_money(ordea.getInterestInAll().add(ordea.getRealityBorrowMoney()));
		}*/
		
		
		
		ordea.setOrderCreateTime(Timestamps.stampToDate(ordea.getOrderCreateTime()));//时间戳转换
		Deferred defe =  coldao.DefNuma(ordea.getOrderId());
		ordea.setDefeNum(defe.getDefeNum());
		System.out.println("次数:"+ordea.getDefeNum());
//		ordea.setOrder_money(padao.OrderMoneySum(ordea.getOrderId()).add(ordea.getInterestSum()));
		System.out.println("次数:"+ordea.getDefeNum()+"金额:"+ordea.getOrder_money());
		//interestSum  order_money  realityBorrowMoney
		System.out.println(defe.getInterestOnArrears());
		if(defe.getInterestOnArrears()==null){
			defe.setInterestOnArrears(new BigDecimal(0));
		}
		ordea.setDefeMoney(defe.getInterestOnArrears());
		System.out.println("未解密:"+ordea.getPhone());
		String paone = p.decryption(ordea.getPhone());
		System.out.println("111:"+paone);
		ordea.setPhone(paone);
		ordea.setShouldReturnTime(Timestamps.stampToDate(ordea.getShouldReturnTime()));
		String defetime = padao.DefeTime(ordea.getOrderId());
		String offDefetime = padao.offDefetime(ordea.getOrderId());
		if(defetime==null){
			defetime = "0";
		}
		if(offDefetime==null){
			offDefetime = "0";
		}
		if(offDefetime!=null && defetime != null){
			int result = defetime.compareTo(offDefetime);
			if(result>0){//defetime 大于 offDefetime
				ordea.setDeferAfterReturntime(Timestamps.stampToDate(defetime));
			}else if(result<0){
				ordea.setDeferAfterReturntime(Timestamps.stampToDate(offDefetime));
			}else if(result==0){
				ordea.setDeferAfterReturntime(ordea.getShouldReturnTime());
			}
		}
		
		if(ordea.getDeferBeforeReturntime()==null){
			ordea.setDeferBeforeReturntime(ordea.getShouldReturnTime());
		}else if(ordea.getDeferBeforeReturntime()!=null){
			ordea.setDeferBeforeReturntime(Timestamps.stampToDate(ordea.getDeferAfterReturntime()));
		}
		ordea.setRegisteTime(Timestamps.stampToDate(ordea.getRegisteTime()));
		System.out.println("时间:"+ordea.getDeferAfterReturntime()+":AAA:");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", ordea);
		return map;
	}




	@Override
	public Map<String, Object> Accountadjus(Accountadjustment acc) {
		PhoneDeal p = new PhoneDeal();
		if(acc.getPhone() != null){
			if(acc.getPhone().length()!=0){
				acc.setPhone(p.encryption(acc.getPhone()));
			}
			
		}
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			acc.setAmou_time(Timestamps.dateToStamp1(sim.format(new Date())));
			acc.setAccounttime(Timestamps.dateToStamp1(acc.getAccounttime()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		acc.setRename_id(padao.selectPatyId(acc.getTypename()));
		Integer addId = padao.AddCAccount(acc);
		if(addId != null){
			Integer updateId = padao.UpdateOrdermoney(acc);
			if(updateId != null){
				map.put("code", 200);
				map.put("desc", "成功");
			}else{
				map.put("code", 0);
				map.put("desc", "失败");
			}
			
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
		
		if(orderNumber.getIdcard_number()==null && orderNumber.getPhone()==null){
			
				map.put("Orderdetails", "条件不能为null");
				map.put("Deferred", 0);
			
			
		}else{
				PhoneDeal p = new PhoneDeal();
				if(orderNumber.getPhone() != null){
					if(orderNumber.getPhone().length()==11){
						System.out.println("进加密");
						orderNumber.setPhone(p.encryption(orderNumber.getPhone()));
						System.out.println("已加密手机号:"+orderNumber.getPhone());
					}
					
				}
				
				
				Orderdetails ordetails = padao.OrdeRepayment(orderNumber);
				
				if(ordetails==null){
					map.put("Orderdetails", "没有该用户未还订单");
					map.put("Deferred", 0);
					return map;
				}
				System.out.println("实际到账金额"+ordetails.getRealityAccount());
				Deferred defe = coldao.DefNuma(ordetails.getOrderId());
				if(ordetails!=null){
					ordetails.setOrderCreateTime(Timestamps.stampToDate(ordetails.getOrderCreateTime()));//实借时间
					ordetails.setDeferAfterReturntime(padao.DeferrAdefe(ordetails.getOrderId()));
					ordetails.setDeferAfterReturntime(Timestamps.stampToDate(ordetails.getDeferAfterReturntime()));//延期后应还时间
					ordetails.setPhone(p.decryption(ordetails.getPhone()));
					if(defe.getInterestOnArrears()== null){
						defe.setInterestOnArrears(new BigDecimal(0));
					}
					ordetails.setDefeMoney(defe.getInterestOnArrears());
					ordetails.setDefeNum(defe.getDefeNum());
					if(ordetails.getInterestPenaltySum() == null){
						ordetails.setInterestPenaltySum(new BigDecimal(0));
					}
					ordetails.setRealityBorrowMoney(ordetails.getShouldReapyMoney().add(ordetails.getInterestPenaltySum()));//放款金额 + 利息
					ordetails.setInterestPenaltySum(ordetails.getInterestSum().add(ordetails.getInterestPenaltySum()));//含逾期总利息
					ordetails.setShouldReturnTime(Timestamps.stampToDate(ordetails.getShouldReturnTime()));
					map.put("aaa", ordetails.getInterestPenaltySum());
					map.put("Orderdetails", ordetails);
					
				}
			}
			
		return map;
	}




	@Override
	public Map<String, Object> SelectOrderAccount(Orderdetails ordetail) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PhoneDeal p = new PhoneDeal();
		if(ordetail.getPhone() != null){
			if(ordetail.getPhone().length()!=0){
				ordetail.setPhone(p.encryption(ordetail.getPhone()));
			}
			
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
			
			accounts.get(i).setAmou_time(Timestamps.stampToDate(accounts.get(i).getAmou_time()));
			String ps = p.decryption(accounts.get(i).getPhone());
			accounts.get(i).setPhone(tm.mobileEncrypt(ps));
			Accountadjustment ac = padao.Maxtotalamount(accounts.get(i).getOrderId());
			
			accounts.get(i).setAccounttime(Timestamps.stampToDate(ac.getAccounttime()));
			accounts.get(i).setAmountmoney(ac.getAmountmoney());
			accounts.get(i).setTotalamount(ac.getTotalamount());
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
			if(ordetail.getPhone().length()!=0){
				ordetail.setPhone(p.encryption(ordetail.getPhone()));
			}
			
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
			if(ordetail.getPhone().length()!=0){
				ordetail.setPhone(p.encryption(ordetail.getPhone()));
			}
			
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
			if(ordetail.getPhone().length()!=0){
				ordetail.setPhone(p.encryption(ordetail.getPhone()));
			}
			
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
			}else if(under.get(i).getState().equals("收入")){
				under.get(i).setThname(padao.RepaymentName(under.get(i).getChannel()));
			}
		}
		map.put("Undertheline", under);
		return map;
	}




	@Override
	public Map<String, Object> ThirdpatyAll(Integer compayId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Thirdparty_interface paymentname = newdao.NewloanRepayment(compayId);//获取系统设置的 放款名称   和  还款名称
		map.put("Loan_setting", paymentname.getLoanSource());
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
		System.out.println(unde.getOffinetransfertime());
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
		
		if(banl.getStartu_time() == null){
			SimpleDateFormat sima = new SimpleDateFormat("yyyy-MM-dd");
			String stimea = sima.format(new Date());
			Calendar calendar = Calendar.getInstance();
			Date date = null;
			Integer day = pdap.SelectHuan(banl.getCompanyId());//获取天数
			calendar.add(Calendar.DATE, -day);//把日期往后增加n天.正数往后推,负数往前移动 
			date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
			String c = sima.format(date);//结束时间
			String b = sima.format(new Date());
			banl.setStartu_time(c+" 00:00:00");
			banl.setEnd_time(b+" 23:59:59");
		}
		
		
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
			Collections.reverse(times); // 倒序排列 
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
		if(banl.getStart_time()!=null){
			try {
				banl.setStart_time(Timestamps.dateToStamp1(banl.getStart_time()));
				banl.setEnd_time(Timestamps.dateToStamp1(banl.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String stime = sim.format(new Date());
			try {
				banl.setStart_time(Timestamps.dateToStamp1(stime+" 00:00:00"));
				banl.setEnd_time(Timestamps.dateToStamp1(stime+" 23:59:59"));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println(banl.getStart_time()+"ccc"+banl.getEnd_time()
				);
		Bankdeductions bank = padao.OnDefe(banl);//查询实借金额笔数
//		Bankdeductions bank = padao.OneBank(banl);//realborrowing     实借笔数        realexpenditure   世界金额 
		//Bankdeductions a = padao.OneCollection(banl);//查询逾期金额
		Bankdeductions b = padao.Onrepayment(banl);//查询还款金额笔数
		Bankdeductions g = padao.DefeMoeny(banl);//延期记录   defeNum 次数    deferredamount  金额
		Bankdeductions c = padao.OneMoney(banl);//查询延期费
		Bankdeductions e = padao.XianJianmian(banl);//查询线下记录	条数 和 金额	defeNum 次数  deferredamount 金额
		Bankdeductions f = padao.BankMoneys(banl);//查询银行扣款记录   defeNum 次数    deferredamount  金额
		
		if(c.getRealexpenditure()==null){
			c.setRealexpenditure(new BigDecimal(0));
		}
		
		if(b.getRealexpenditure()==null){
			b.setRealexpenditure(new BigDecimal(0));
		}
		
		if(g.getDeferredamount()==null){
			g.setDeferredamount(new BigDecimal(0));
		}
		
		
		if(e.getDeferredamount()==null){
			e.setDeferredamount(new BigDecimal(0));
		}
		
		if(f.getDeferredamount()==null){
			f.setDeferredamount(new BigDecimal(0));
		}

		
		if(bank!=null){
			if(bank.getRealborrowing() != null){
				if(bank.getRealborrowing() !=0){
					bank.setBankcardName(""+bank.getRealborrowing()+","+bank.getRealexpenditure()+","+0+"");//实借笔数    实借金额
				}else{
					bank.setBankcardName(""+0+","+0+","+0+"");//实借笔数    实借金额
				}
			}else{
				bank.setBankcardName(""+0+","+0+","+0+"");//实借笔数    实借金额
			}
		
		}else{
			bank.setBankcardName(""+0+","+0+","+0+"");//实借笔数    实借金额
		}
		
		
		if(bank!=null){
			if(bank.getBankMoneys() != null){
				if(f.getDefeNum()!=0){
					bank.setBankMoneys(""+f.getDefeNum()+","+f.getDeferredamount()+","+0+"");
				}else{
					bank.setBankMoneys(""+0+","+0+","+0+"");
				}
			}else{
				bank.setBankMoneys(""+0+","+0+","+0+"");//实借笔数    实借金额
			}
		}else{
			bank.setBankMoneys(""+0+","+0+","+0+"");//实借笔数    实借金额
		}
	
		
		if(e!=null){
			if(e.getDeferredamount()!=null){
				if(e.getDefeNum()!=0){
					bank.setXianJianmianCount(""+e.getDefeNum()+","+e.getDeferredamount()+","+0+"");
				}else{
					bank.setXianJianmianCount(""+0+","+0+","+0+"");
				}
			}else{
				bank.setXianJianmianCount(""+0+","+0+","+0+"");
			}
		}else{
			bank.setXianJianmianCount(""+0+","+0+","+0+"");
		}
		
		
		
		
		
		
		
		if(b!=null){
			if(b.getRealborrowing()!=null){
				if(b.getRealborrowing() != 0){
					bank.setDeductionstatus(""+b.getRealborrowing()+","+b.getRealexpenditure()+","+0+"");//实还笔数    实还金额
				}else{
					bank.setDeductionstatus(""+0+","+0+","+0+"");//实还笔数    实还金额
				}
			}else{
				bank.setDeductionstatus(""+0+","+0+","+0+"");//实还笔数    实还金额
			}
			
		}else{
			bank.setDeductionstatus(""+0+","+0+","+0+"");//实还笔数    实还金额
		}
		
		
		
		if(c!=null){
			if(c.getDefeNum() != null){
				if(c.getDefeNum() != 0 ){
					c.setDefeNum(c.getDefeNum()+g.getDefeNum());
					c.setDeferredamount(c.getDeferredamount().add(g.getDeferredamount()));
					bank.setName(""+c.getDefeNum()+","+c.getDeferredamount()+","+0+"");//延期数    延期费
				}else{
					bank.setName(""+0+","+0+","+0+"");//延期数    延期费
				}
			}else{
				bank.setName(""+0+","+0+","+0+"");//延期数    延期费
			}
		}else{
			bank.setName(""+0+","+0+","+0+"");//延期数    延期费
		}
			
			
			
			if(b.getRealexpenditure()==null){
				b.setRealexpenditure(new BigDecimal(0));
			}
			
			if(c.getDeferredamount()==null){
				c.setDeferredamount(new BigDecimal(0));
			}
			
			if(f.getDeferredamount()==null){
				f.setDeferredamount(new BigDecimal(0));
			}
			
			bank.setXianxiaMoney(e.getDeferredamount());//线下总计	
			bank.setXiansMoney(b.getRealexpenditure().add(c.getDeferredamount()).add(f.getDeferredamount()));//线上总计
			bank.setShouruMoney(bank.getXianxiaMoney().add(bank.getXiansMoney()));
		
		
		
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
			off.setSedn_time(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		off.setOffmoney(off.getOrderMoney().subtract(off.getOffusermoney()));
		Integer addId = padao.AddOffJianMian(off);
		if(addId != null){
			Integer updateId = padao.OrdersStatus(off);
			if(updateId != null){
				padao.UserDefeNum(off.getOrderId());
				map.put("code", 200);
				map.put("desc", "操作成功");
			}else{
				map.put("code", 200);
				map.put("desc", "操作失败");
			}
			
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
			if(ord.getPhone().length()!=0){
				ord.setPhone(p.encryption(ord.getPhone()));
			}
			
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
			unders.get(i).setSedn_time(Timestamps.stampToDate(unders.get(i).getSedn_time()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Undertheline", unders);
		return map;
	}




	@Override
	public Map<String, Object> RepaymentAll(Integer compayId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Thirdparty_interface paymentname = newdao.NewloanRepayment(compayId);//获取系统设置的 放款名称   和  还款名称
		map.put("Repayment_setting", paymentname.getRepaymentSource());
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
			if(off.getPhone().length()!=0){
			off.setPhone(p.encryption(off.getPhone()));
			}
		}
		off.setPreextensiontime(padao.OrderShouldTime(off.getOrderId()));
		String status = padao.OrderStatuOrder(off.getOrderId());
		
			String shoureturntime = padao.SelectShouReturnTime(off.getOrderId());
			String stime = Timestamps.stampToDate1(shoureturntime);
			String a = stime.substring(8, 10);//就去时间格式天数
			String b = stime.substring(0, 8);//获取年月   2019-03-
			Integer ac = off.getOnceDeferredDay()+Integer.valueOf(a);//天数加上
			if(ac < 10){
				String aa = "0"+ac;
				off.setDelay_time(b+aa+" 23:59:59");
			}else{
				off.setDelay_time(b+ac+" 23:59:59");
			}
		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ca.setTime(new Date()); //设置时间为当前时间
        Date date = ca.getTime();
        
        try {
        	off.setOperating_time(Timestamps.dateToStamp1(dateFormat.format(new Date())));//操作时间
			off.setDelay_time(Timestamps.dateToStamp1(off.getDelay_time()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Integer addId = padao.AddDelay(off);
		if(addId != null){
				padao.UpdateOrderTime(off);
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
		TuoMinUtil tm = new TuoMinUtil();
		if(of.getPhone() != null){
			if(of.getPhone().length()!=0){
				of.setPhone(p.encryption(of.getPhone()));
			}
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
			String phon = p.decryption(ofa.get(i).getPhone());
			ofa.get(i).setPhone(tm.mobileEncrypt(phon));
			ofa.get(i).setDefeNum(de.getDefeNum());
			if(de.getDefeMoney()==null){
				de.setDefeMoney(new BigDecimal(0));
			}
			ofa.get(i).setDefeMoney(de.getDefeMoney());
			
			ofa.get(i).setDeferAfterReturntime(Timestamps.stampToDate(ofa.get(i).getPreextensiontime()));
			ofa.get(i).setDelay_time(Timestamps.stampToDate(ofa.get(i).getShouldReturnTime()));
			ofa.get(i).setOperating_time(Timestamps.stampToDate(ofa.get(i).getOperating_time()));
		}
		map.put("Offlinedelay", ofa);
		map.put("pageutil", pages);
		return map;
	}

	//后台管理---查询所有
    public List<Thirdpricefind> queryall(Integer companyid){
    	List<Thirdpricefind> list=thirdpricefindMapper.queryall(companyid);
    	return list;
    }

    //后台管理----修改价格
    public int updateprice(BigDecimal price,Integer id){
    	int num=thirdpricefindMapper.updateprice(price, id);
    	return num;
    }
    
    //后台管理---费用统计
    public Map<String, Object> pricetongji(Integer companyId,Integer page,String starttime,String endtime) throws ParseException{
    	int lifeofloan = homepageTongjiMapper.querylifeOfLoan(companyId);//查询借款期限
		
    	List<Thirdpricefind> listprice=thirdpricefindMapper.queryall(companyId);//第三方费用价格表
		List<PriceTongji> listtongji=new ArrayList<>();
		List<PriceTongji> listtongjito=new ArrayList<>();
		PageUtil2 pageUtil=null;
		Date d=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		//String date=sf.format(d);//date为当天时间(格式为年月日)
		
		Date startDate = DateUtils.addDays(d, -lifeofloan);
		Date endDate = d;
		
		
		String startTime = null;//开始时间（年月日格式）
		String endTime = null;//结束时间（年月日格式）
		if((starttime!=null&&!"".equals(starttime))&&(endtime!=null&&!"".equals(endtime))){
			startTime = starttime;
			endTime = endtime;
		}else{
			startTime = sf.format(startDate);
			endTime = sf.format(endDate);
		}
		
		List<String> list=DateListUtil.getDays(startTime, endTime);
		for (int i = 0; i < list.size(); i++) {
			PriceTongji priceTongji=new PriceTongji();
			
			String startTimestamps = Timestamps.dateToStamp(list.get(i));
			String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(list.get(i)))+86400000)+"";
			
			priceTongji.setDate(list.get(i));
			
			int verificationcode = thirdpricefindMapper.querycount(companyId, 1, startTimestamps, endTimestamps);//验证码数
			BigDecimal vercodeprice = listprice.get(0).getPrice();//验证码单价
			priceTongji.setVerificationcode(verificationcode);
			priceTongji.setVerificationprice(BigDecimal.valueOf((int)verificationcode).multiply(vercodeprice));
			
			int idcard = thirdpricefindMapper.querycount(companyId, 2, startTimestamps, endTimestamps);//身份证数
			BigDecimal idcardprice = listprice.get(1).getPrice();//身份证单价
			priceTongji.setIdcard(idcard);
			priceTongji.setIdcardprice(BigDecimal.valueOf((int)idcard).multiply(idcardprice));
			
			int faceid = thirdpricefindMapper.querycount(companyId, 3, startTimestamps, endTimestamps);//人脸数
			BigDecimal faceidprice = listprice.get(2).getPrice();//人脸单价
			priceTongji.setFaceid(faceid);
			priceTongji.setFaceidprice(BigDecimal.valueOf((int)faceid).multiply(faceidprice));
			
			int threeelements = thirdpricefindMapper.querycount(companyId, 4, startTimestamps, endTimestamps);//三要素
			BigDecimal threeelementsprice = listprice.get(3).getPrice();//三要素单价
			priceTongji.setThreeelements(threeelements);
			priceTongji.setThreeelementsprice(BigDecimal.valueOf((int)threeelements).multiply(threeelementsprice));
			
			int operator = thirdpricefindMapper.querycount(companyId, 5, startTimestamps, endTimestamps);//运营商数
			BigDecimal operatorprice = listprice.get(4).getPrice();//运营商单价
			priceTongji.setOperator(operator);
			priceTongji.setOperatorprice(BigDecimal.valueOf((int)operator).multiply(operatorprice));
			
			int riskmanagementjia = thirdpricefindMapper.querycount(companyId, 6, startTimestamps, endTimestamps);//风控数（甲）
			int riskmanagementyi = thirdpricefindMapper.querycount(companyId, 7, startTimestamps, endTimestamps);//风控数（乙）
			int riskmanagementbing = thirdpricefindMapper.querycount(companyId, 8, startTimestamps, endTimestamps);//风控数（丙）
			BigDecimal riskmanagementpricejia = listprice.get(5).getPrice();//风控单价（甲）
			BigDecimal riskmanagementpriceyi = listprice.get(6).getPrice();//风控单价（乙）
			BigDecimal riskmanagementpricebing = listprice.get(7).getPrice();//风控单价（丙）
			priceTongji.setRiskmanagement(riskmanagementjia+riskmanagementyi+riskmanagementbing);
			
			BigDecimal pricejia=BigDecimal.valueOf((int)riskmanagementjia).multiply(riskmanagementpricejia);//风控甲费用
			BigDecimal priceyi=BigDecimal.valueOf((int)riskmanagementyi).multiply(riskmanagementpriceyi);//风控甲费用
			BigDecimal pricebing=BigDecimal.valueOf((int)riskmanagementbing).multiply(riskmanagementpricebing);//风控甲费用
			priceTongji.setRiskmanagementprice(pricejia.add(priceyi).add(pricebing));
			
			int note = thirdpricefindMapper.querycount(companyId, 9, startTimestamps, endTimestamps);//群发短信数
			BigDecimal noteprice = listprice.get(8).getPrice();//群发短信单价
			priceTongji.setNote(note);
			priceTongji.setNoteprice(BigDecimal.valueOf((int)note).multiply(noteprice));
			
			BigDecimal sum=priceTongji.getVerificationprice().add(priceTongji.getIdcardprice()).add(priceTongji.getFaceidprice()).add(priceTongji.getThreeelementsprice()).add(priceTongji.getOperatorprice()).add(priceTongji.getRiskmanagementprice()).add(priceTongji.getNoteprice());
			priceTongji.setSum(sum);//总计
			
			listtongji.add(priceTongji);
		}
		
		DateListUtil.ListSort4(listtongji);//按照应还时间进行倒排序
		
		if(listtongji!=null && !listtongji.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(listtongji,page,10);
    		listtongjito.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1,10,0);

    	}
		
	 	
		Map<String, Object> map=new HashMap<>();
		map.put("listtongjito", listtongjito);
		map.put("pageutil", pageUtil);
		return map;
    }




	@Override
	public Map<String, Object> SelectAccOrders(String orderNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Accountadjustment> accs = padao.SelectAccOrders(orderNumber);
		for(int i=0;i<accs.size();i++){
			accs.get(i).setAccounttime(Timestamps.stampToDate(accs.get(i).getAccounttime()));
		}
		map.put("Accountadjustment", accs);
		return map;
	}




	@Override
	public Map<String, Object> DeleteAccorders(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Accountadjustment orderId = padao.SelectOrderId(id);//查询订单号      调账金额       调账后应还金额
		Orderdetails orderdetails = new Orderdetails();
		orderdetails.setShouldReapyMoney(orderId.getTotalamount().add(orderId.getAmountmoney()));//获取应还金额
		Integer updateId = padao.UpdateOrdertails(orderdetails);
		if(updateId!=null){
			padao.DeleteOrderAcc(id);
		}
		map.put("code", 200);
		map.put("Ncode", 2000);
		map.put("msg", "已删除");
		return map;
	}

}
