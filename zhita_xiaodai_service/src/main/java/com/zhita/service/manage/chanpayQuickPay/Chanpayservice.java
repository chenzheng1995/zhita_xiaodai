package com.zhita.service.manage.chanpayQuickPay;


import java.util.Map;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;

public interface Chanpayservice {
	
	
	
	Integer AddBankcard(Bankcard bank);
	
	
	Integer SelectTrxId(Bankcard bank);
	
	
	Integer UpdateChanpay(Integer id);
	
	
	Integer AddRepayment(Repayment repay);
	
	
	Integer UpdateOrders(Orders ord);
	
	
}
