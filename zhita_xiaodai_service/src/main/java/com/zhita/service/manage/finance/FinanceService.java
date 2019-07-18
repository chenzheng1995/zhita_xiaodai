package com.zhita.service.manage.finance;

import java.util.Map;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Bankdeduction;
import com.zhita.model.manage.Offlinjianmian;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Undertheline;

public interface FinanceService {
	
	
	Map<String, Object> AllPaymentrecord(Payment_record payrecord);
	
	
	Map<String, Object> Huankuan(Payment_record payrecord);

	
	Map<String, Object> OrderPayment(Orderdetails orderId);
	
	
	Map<String, Object> Accountadjus(Accountadjustment acc);
	
	
	Map<String, Object> OrderAccount(Orderdetails orderNumber);
	
	
	Map<String, Object> SelectOrderAccount(Orderdetails ordetail);
	
	
	Map<String, Object> SelectNoMoney(Orderdetails ordetail);
	
	
	Map<String, Object> SelectOkMoney(Orderdetails ordetail);
	
	
	Map<String, Object> Selectoffine(Orderdetails ordetail);
	
	
	Map<String, Object> ThirdpatyAll(Integer compayId);
	
	
	Map<String, Object> AddUnderthe(Undertheline unde);
	
	
	Map<String, Object> SelectBankDeductOrders(Bankdeduction bank);
	
	
	Map<String, Object> AllBank(Integer orderId);
	
	
	Map<String, Object> AddBank(Bankdeduction banl);
	
	
	Map<String, Object> AllDelayStatis(Bankdeduction banl);
	
	
	Map<String, Object> Financialover(Bankdeduction banl);
	
	
	Map<String, Object> AddOffJianmian(Offlinjianmian off);
	
	
	Map<String, Object> SelectXiaOrder(Orderdetails ord);
	
	
	Map<String, Object> RepaymentAll(Integer compayId);
}
