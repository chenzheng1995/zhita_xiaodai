package com.zhita.service.manage.Statistic;


import java.util.Map;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.MouthBankName;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;

public interface Statisticsservice {
	
	
	
	MouthBankName SendBankcomm(String BankCommonName,String AcctNo,String AcctName,String TransAmt,String LiceneceNo,
			String Phone,Integer sys_userId,Integer deductionproportion,String orderNumber,Integer orderId,Integer userId);
	
	
	Map<String, Object> AllCollection(Orderdetails order);
	
	
	
	String IDnumber(Integer userId);
	
	
	Integer UpdateBank(Bankdeductions ban);
	
	
	Map<String, Object> AllBankdeduData(Bankcard ban);
	
	
	Map<String, Object> AllBankdetail(Bankdeductions bank);
	
	
	Map<String, Object> AllDetails(Bankdeductions bank);
	
	
	Integer UpdateOrderSurp(Orders o);
	
	
	Integer SelectUserId(Integer MerUserId);
	
	
	Integer SelectTrxId(Bankcard bank);
	
	
	Integer AddBankcard(Bankcard bank);
	
	
	Integer UpdateChanpay(Integer userId);
	
	
	Integer deleteBank(Integer userId);
	
	
	Integer AddRepayment(Repayment reapy);
	
	
	Integer UpdateOrders(Orders ord);
	
	
	Integer AddDeferred(Deferred defe);
	
	
	Integer UpdateDefeOrders(Orders ord);
	
	
	void SelectId(String orderNumber);
	
}
