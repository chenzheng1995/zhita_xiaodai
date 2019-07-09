package com.zhita.service.manage.finance;

import java.util.Map;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.PaymentRecord;
import com.zhita.model.manage.Undertheline;

public interface FinanceService {
	
	
	Map<String, Object> AllPaymentrecord(PaymentRecord payrecord);
	
	
	Map<String, Object> Huankuan(PaymentRecord payrecord);

	
	Map<String, Object> OrderPayment(Integer orderId);
	
	
	Map<String, Object> Accountadjus(Accountadjustment acc);
	
	
	Map<String, Object> OrderAccount(String orderNumber);
	
	
	Map<String, Object> SelectOrderAccount(Orderdetails ordetail);
	
	
	Map<String, Object> SelectNoMoney(Orderdetails ordetail);
	
	
	Map<String, Object> SelectOkMoney(Orderdetails ordetail);
	
	
	Map<String, Object> Selectoffine(Orderdetails ordetail);
	
	
	Map<String, Object> ThirdpatyAll(Integer compayId);
	
	
	Map<String, Object> AddUnderthe(Undertheline unde);
}
