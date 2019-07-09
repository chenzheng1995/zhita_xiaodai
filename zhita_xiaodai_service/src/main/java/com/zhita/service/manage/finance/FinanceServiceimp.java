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
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.PaymentRecord;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.model.manage.Undertheline;
import com.zhita.util.PageUtil;



@Service
public class FinanceServiceimp implements FinanceService{
	
	
	
	@Autowired
	private PaymentRecordMapper padao;
	
	
	

	@Override
	public Map<String, Object> AllPaymentrecord(PaymentRecord payrecord) {
		Integer totalCount = padao.TotalCountPayment(payrecord);
		PageUtil pages = new PageUtil(payrecord.getPage(), totalCount);
		payrecord.setPage(pages.getPage());
		List<PaymentRecord> payments = padao.PaymentAll(payrecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PaymentRecord", payments);
		map.put("Pageutil", pages);
		return map;
	}




	@Override
	public Map<String, Object> Huankuan(PaymentRecord payrecord) {
		Integer totalCount = padao.RepaymentTotalCount(payrecord);
		PageUtil pages = new PageUtil(payrecord.getPage(), totalCount);
		payrecord.setPage(pages.getPage());
		List<Repayment> rapay = padao.RepaymentAll(payrecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Repayment", rapay);
		map.put("Pageutil", pages);
		return null;
	}




	@Override
	public Map<String, Object> OrderPayment(Integer orderId) {
		Orderdetails ordea = padao.SelectPaymentOrder(orderId);
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
	public Map<String, Object> OrderAccount(String orderNumber) {
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
		map.put("PageUtil", pages);
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
		map.put("PageUtil", pages);
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
		map.put("PageUtil", pages);
		return null;
	}




	@Override
	public Map<String, Object> Selectoffine(Orderdetails ordetail) {
		
		return null;
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

}
