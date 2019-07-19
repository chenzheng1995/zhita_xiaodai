package com.zhita.service.manage.chanpayQuickPay;


import com.zhita.model.manage.Bankcard;

public interface Chanpayservice {
	
	
	
	Integer AddBankcard(Bankcard bank);
	
	
	Integer SelectTrxId(Bankcard bank);
	
	
	Integer UpdateChanpay(Integer id);
}
