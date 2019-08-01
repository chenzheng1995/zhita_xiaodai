package com.zhita.service.manage.chanpayQuickPay;


import java.util.Map;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;

public interface Chanpayservice {
	
	
	
	Integer AddBankcard(Bankcard bank);
	
	
	Integer SelectTrxId(Bankcard bank);
	
	
	Integer UpdateChanpay(Integer id);
	
	
	Integer AddRepayment(Repayment repay);
	
	
	Integer UpdateOrders(Orders ord);
	
	
	Integer AddPayment_record(Payment_record pay);
	
	
	Integer AddDeferred(Deferred defe);
	
	
	Integer UpdateDefeOrders(Orders ord);
	
	
	Integer UpdatePayment(Payment_record pay);
	
	
	Bankcard SelectBank(Bankcard bankcard);
	
	
	Orders OneOrders(Orders or);
	
	
	Integer DeleteChan(Integer userId);

}
