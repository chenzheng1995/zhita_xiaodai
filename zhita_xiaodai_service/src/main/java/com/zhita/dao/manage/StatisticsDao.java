package com.zhita.dao.manage;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;

public interface StatisticsDao {
	
	Integer AddBankcard(Bankcard bank);
	
	
	Integer SelectTrxId(Bankcard bank);
	
	
	Integer UpdateStatu(Integer id);
	
	
	Integer AddRepay(Repayment repay);
	
	
	Integer UpdateOrders(Orders ord);
	
	
	Integer AddDeferred(Deferred defe);
	
	
	Integer SelectUserdelayTimes(Orders ord);
	
	
	Integer UpdateUser(Orders ord);
	
	
	Integer SelectOrderId(String orderNumber);
	
	
	Integer AddPaymentRecord(Payment_record pay);
	
	
	Integer UpdatePaymentrecord(Payment_record pay);
	
	
	Bankcard SelectBanl(Bankcard userId);
}
