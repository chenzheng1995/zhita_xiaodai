package com.zhita.service.manage.chanpayQuickPay;


import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Loan_setting;
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
	
	
	String loanSetStatu(Loan_setting loan);
	
	
	Integer loanMaxMoney(Integer companyId);
	
	
	BigDecimal SumpayMoney(Orderdetails ord);
	
	
	Integer SelectOrdersId(Integer userId);

	
	String SelectBankName(Integer userId);
	
	
	String SelectBorrowing(Integer companyId);
	
	
	Integer UpdateRepayStatus(String pipelinenu,String orderNumber);
	
	
	User OneUser(Integer userId);
	
	
	Integer DeleteOrderNumber(String orderNumber,String orderStatus);
	
	
	Orders SelectOrdersUser(Integer orderId);
	
	
	Integer OrderStatus(Integer orderId);
	
	
	Integer Addbankdeduction(Bankdeductions bank);
	
	
	String RepaymentStatus(String orderNumber);
	
	
	Integer UpdateRepayStatusAA(Repayment repay);
	
	
	String DefeStatus(String orderNumber);
	
	
	String paymentStatus(String orderNumber);
	
	
	Integer UpdatePayStatus(String orderNumber);
	
	
	String getPhone(String orderNumber);
	
	
	Deferred getDefeDele(String orderNumber);
	
	
	String getOrderNumberDefe(Integer orderId);
	
	
	Integer getUserId(Integer orderId);
}
