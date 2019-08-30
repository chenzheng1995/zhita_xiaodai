package com.zhita.service.manage.chanpayQuickPay;


import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.User;


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
	
	
	Integer SelectUserId(Integer userId);
	
	
	Integer deleteBank(Integer userId);
	
	
	void SelectId(String orderNumber);
	
	
	String loanSetStatu(Integer companyId);
	
	
	Integer loanMaxMoney(Integer companyId);
	
	
	BigDecimal SumpayMoney(Orderdetails ord);
	
	
	Integer SelectOrdersId(Integer userId);

	
	String SelectBankName(Integer userId);
	
	
	String SelectBorrowing(Integer companyId);
	
	
	Integer UpdateRepayStatus(String pipelinenu,String orderNumber);
	
	
	User OneUser(Integer userId);
}
