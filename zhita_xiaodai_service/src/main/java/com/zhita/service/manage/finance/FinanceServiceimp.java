package com.zhita.service.manage.finance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.PaymentRecordMapper;
import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Bankdeduction;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.model.manage.Undertheline;
import com.zhita.util.PageUtil;



@Service
public class FinanceServiceimp implements FinanceService{
	
	
	
	@Autowired
	private PaymentRecordMapper padao;
	
	
	

	@Override
	public Map<String, Object> AllPaymentrecord(Payment_record payrecord) {
		Integer totalCount = padao.TotalCountPayment(payrecord);
		PageUtil pages = new PageUtil(payrecord.getPage(), totalCount);
		payrecord.setPage(pages.getPage());
		payrecord.setProfessionalWork("放款");
		List<Payment_record> payments = padao.PaymentAll(payrecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PaymentRecord", payments);
		return map;
	}




	@Override
	public Map<String, Object> Huankuan(Payment_record payrecord) {
		Integer totalCount = padao.RepaymentTotalCount(payrecord);
		PageUtil pages = new PageUtil(payrecord.getPage(), totalCount);
		payrecord.setPage(pages.getPage());
		payrecord.setProfessionalWork("还款");
		List<Payment_record> rapay = padao.PaymentAll(payrecord);
		//List<Payment_record> rapay = padao.RepaymentAll(payrecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Repayment", rapay);
		return map;
	}




	@Override
	public Map<String, Object> OrderPayment(Orderdetails orderNumber) {
		Orderdetails ordea = padao.SelectPaymentOrder(orderNumber);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Orderdetails", ordea);
		return map;
	}




	@Override
	public Map<String, Object> Accountadjus(Accountadjustment acc) {
		acc.setStatu("2");
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		acc.setAccounttime(sim.format(new Date()));
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
		map.put("Orderdetails", ordetails);
		return map;
	}




	@Override
	public Map<String, Object> SelectOrderAccount(Orderdetails ordetail) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ordetail.setAccounttime(sim.format(new Date()));
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		List<Accountadjustment> accounts = padao.AllAccount(ordetail);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> SelectNoMoney(Orderdetails ordetail) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ordetail.setAccounttime(sim.format(new Date()));
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		List<Accountadjustment> accounts = padao.AllStatu(ordetail);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> SelectOkMoney(Orderdetails ordetail) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ordetail.setAccounttime(sim.format(new Date()));
		Integer totalCount = padao.AccountTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		List<Accountadjustment> accounts = padao.AllNotMoneyStatu(ordetail);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Accountadjustment", accounts);
		return map;
	}




	@Override
	public Map<String, Object> Selectoffine(Orderdetails ordetail) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = padao.SelectUnderthTotalCount(ordetail);
		PageUtil pages = new PageUtil(ordetail.getPage(), totalCount);
		ordetail.setPage(pages.getPage());
		List<Undertheline> under = padao.AllUnderthe(ordetail);
		map.put("Undertheline", under);
		map.put("PageUtil", pages);
		return map;
	}




	@Override
	public Map<String, Object> ThirdpatyAll(Integer compayId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Thirdparty_interface> third = padao.SelectThird(compayId);
		map.put("Thirdparty_interface", third);
		return map;
	}




	@Override
	public Map<String, Object> AddUnderthe(Undertheline unde) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		unde.setUnderthe_time(sim.format(new Date()));
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
	public Map<String, Object> SelectBankDeductOrders(Bankdeduction bank) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = padao.BankDeduOrderNum(bank);
		PageUtil pages = new PageUtil(bank.getPage(), totalCount);
		bank.setPage(pages.getPage());
		List<Orderdetails> orders = padao.BankDeduOrder(bank);
		map.put("Orderdetails", orders);
		return map;
	}




	@Override
	public Map<String, Object> AllBank(Integer orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(orderId != null){
			List<Bankdeduction> banls = padao.BanAll(orderId);
			
			map.put("bankde", banls);
		}else{
			map.put("bankde", "无数据");
		}
		
		return map;
	}




	@Override
	public Map<String, Object> AddBank(Bankdeduction banl) {
		
		return null;
	}

}
